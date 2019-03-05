package com.sjhy.platform.sanguo;
/**
 * ios支付
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.history.PlayerPayLog;
import com.sjhy.platform.persist.mysql.history.PlayerPayLogMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iap")
public class IapController {

    @Autowired
    private PlayerPayLogMapper playerPayLogMapper;

    //购买凭证验证地址
    private static final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";

    //测试的购买凭证验证地址
    private static final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";

    /**
     * 重写X509TrustManager
     */
    private static TrustManager myX509TrustManager = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
    };

    /**
     * 接收iOS端发过来的购买凭证
     * @param iosId 设备唯一id
     * @param receipt 购买凭证
     * @param product_id 商品名称
     * @param transaction_id 订单号
     */
    @RequestMapping("/setIapCertificate")
    public String setIapCertificate(@RequestParam Long iosId, @RequestParam String receipt, @RequestParam String product_id, @RequestParam String transaction_id,
                                               @RequestParam String gameId, @RequestParam String channelId/*, @RequestParam float rmb*/){
        if(StringUtils.isEmpty(String.valueOf(iosId)) || StringUtils.isEmpty(receipt) || StringUtils.isEmpty(product_id) || StringUtils.isEmpty(transaction_id)){
            return null;
        }

        PlayerPayLog payLog = playerPayLogMapper.selectByIosPayLog(gameId,iosId,product_id);
        String res = null;
        if (payLog != null){
            switch (payLog.getStatus()){
                case 3:
                    res = "客户端访问ios支付失败";
                    break;
                case 4:
                    res = "ok";
                    break;
                case 5:
                    res = "成功";
                    break;
                case 6:
                    res = "苹果支付反馈服务器订单失败";
                    break;
                default:
                    res = "未知错误";
            }
            if (!res.equalsIgnoreCase("ok")){
                return res;
            }
        }

        if (res == null){
            playerPayLogMapper.insert(new PlayerPayLog(null,iosId,gameId,channelId,product_id,new Date(),
                    0.0f,null,null,transaction_id,1,receipt));
        }

        // 更新支付信息数据
        payLog = playerPayLogMapper.selectByIosPayLog(gameId,iosId,product_id);

        // 判断是否为沙箱环境
        String url = null;
        url = false == true? certificateUrl:certificateUrlTest;

        final String certificateCode = receipt;
        if(StringUtils.isNotEmpty(certificateCode)){
            playerPayLogMapper.updateByPrimaryKeySelective(new PlayerPayLog(payLog.getId(),null,null,null,null,
                    null,null,null,null,null,4,null));
            String receipt_data = sendHttpsCoon(url, certificateCode);// 获取结果
            // 解析最外层json
            JSONObject job = JSONObject.parseObject(receipt_data);
            // 解析result层json  receipt和status
            JSONObject jobResult = JSONObject.parseObject("result");
            if (jobResult.get("status").equals("0")){
                // 解析receipt层json
                JSONObject jobReceipt = JSONObject.parseObject("receipt");
                // 判断是否存在in_app，不存在表示作弊
                if (StringUtils.isNotEmpty(jobReceipt.getString("in_app"))){
                    JSONObject jobIn = JSONObject.parseObject(String.valueOf(jobReceipt));
                    // 遍历in_app
                    for (int i=1;i<jobIn.size();i++){
                        // 判断商品id和订单号是否相同
                        if (jobIn.get("transaction_id").equals(transaction_id) && jobIn.get("product_id ").equals(product_id)){
                            playerPayLogMapper.updateByPrimaryKeySelective(new PlayerPayLog(payLog.getId(),null,null,null,null,
                                    null,null,null,null,null,5,null));
                            return "10001@"+product_id;
                        }
                    }
                }
            }else {
                if (jobResult.get("status") != null) {
                    int status = (int) jobResult.get("status");
                    playerPayLogMapper.updateByPrimaryKeySelective(new PlayerPayLog(payLog.getId(), null, null, null, null,
                            null, null, null, null, null, status, null));
                }else {
                    playerPayLogMapper.updateByPrimaryKeySelective(new PlayerPayLog(payLog.getId(), null, null, null, null,
                            null, null, null, null, null, 6, null));
                }
                return "10006";
            }

            return "10004";
        }
        return "10002";
    }

    /**
     * 发送请求
     * @param url
     * @param code
     * @return
     */
    private String sendHttpsCoon(@RequestParam String url,
                                 @RequestParam String code){
        if(url.isEmpty()){
            return null;
        }
        try {
            //设置SSLContext
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);

            //打开连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            //设置套接工厂
            conn.setSSLSocketFactory(ssl.getSocketFactory());
            //加入数据
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/json");

            JSONObject obj = new JSONObject();
            obj.put("receipt-data", code);

            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(obj.toString().getBytes());
            buffOutStr.flush();
            buffOutStr.close();

            //获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line = reader.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
