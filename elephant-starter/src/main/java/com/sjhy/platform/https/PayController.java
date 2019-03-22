package com.sjhy.platform.https;
/**
 * ios支付
 */

import com.alibaba.fastjson.JSONObject;
import com.sjhy.platform.biz.deploy.config.IosCode;
import com.sjhy.platform.biz.deploy.utils.DbVerifyUtils;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.game.GameContent;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.history.PlayerPayLog;
import com.sjhy.platform.client.dto.player.PlayerIos;
import com.sjhy.platform.persist.mysql.game.GameContentMapper;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import com.sjhy.platform.persist.mysql.history.PlayerPayLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerIosMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@RestController
@RequestMapping("/pay")
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PlayerPayLogMapper playerPayLogMapper;
    @Autowired
    private DbVerifyUtils dbVerify;
    @Autowired
    private PayGoodsMapper payGoodsMapper;
    @Autowired
    private GameContentMapper gameContentMapper;
    @Autowired
    private PlayerIosMapper playerIosMapper;

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
    @RequestMapping(value = "/ios",method = RequestMethod.POST)
    public ResultDTO<String> setIapCertificate(@RequestParam Long iosId, @RequestParam String receipt, @RequestParam String product_id, @RequestParam String transaction_id,
                                              @RequestParam String gameId, @RequestParam String channelId/*, @RequestParam float rmb*/) {
        //验证传参是否为空
        if (dbVerify.isHasIos(iosId,gameId,channelId) && StringUtils.isNotEmpty(receipt) && StringUtils.isNotEmpty(product_id) && StringUtils.isNotEmpty(transaction_id)) {

            // 查询订单
            PlayerPayLog payLog = playerPayLogMapper.selectByIosPayLog(gameId, iosId, transaction_id);
            String res = null;
            // 判断订单是否存在，如果状态值不为4则返回
            if (payLog != null && payLog.getPayStatus() != 4) {
                    res = IosCode.ERROR_FAILURE.getErrorCode();
            }

            // 如果未查询到该订单，则插入数据库
            if (res == null){
                playerPayLogMapper.insert(new PlayerPayLog(null,iosId,gameId,channelId,product_id,new Date(),
                        0.0f,null,null,transaction_id,4,null,receipt));
            }
            // 更新查询支付信息数据
            payLog = playerPayLogMapper.selectByIosPayLog(gameId,iosId,transaction_id);

            // 查询游戏包名
            // String gamePackage = gameMapper.selectByGameId(gameId).getNameEn();

            String url = certificateUrl;    // 苹果服务器地址
            boolean bol = false; // 返回参数判断
            int status = -1;      // 苹果返回支付状态
            final String certificateCode = receipt;     // 购买凭证

            int j = 0;
            try {
                while (j < 2) {
                    j++;
                    // 发送请求
                    String receipt_data = sendHttpsCoon(url, certificateCode);
                    System.out.println("=============[][][1][][]" + receipt_data);
                    // 解析最外层json
                    JSONObject job = JSONObject.parseObject(receipt_data);
                    // 获取状态值并进行判断
                    status = (int) job.get("status");
                    if (status == 0) {
                        // 验证商品
                        bol = verifyGoods(iosId,gameId,channelId,product_id);
                        // 解析receipt层json
                        /*JSONObject jobReceipt = job.getJSONObject("receipt");
                        // 判断是否存在in_app和游戏包名是否一致
                        if (StringUtils.isNotEmpty(String.valueOf(jobReceipt.getJSONObject("in_app"))) && gamePackage.equalsIgnoreCase(String.valueOf(jobReceipt.get("bid")))) {
                            JSONObject jobIn = JSONObject.parseObject(String.valueOf(jobReceipt));
                            // 遍历in_app
                            for (int i = 1; i < jobIn.size(); i++) {
                                // 判断商品id和订单号是否相同
                                if (jobIn.get("transaction_id").equals(transaction_id) && jobIn.get("product_id ").equals(product_id)) {
                                    bol = true;
                                    break;
                                }
                            }
                        } else {
                            status = 30000;// 没有in_app数值
                        }*/
                    }else if (status == 21007){
                        url = certificateUrlTest;
                        continue;
                    }
                    break;
                }
                // 判断是否成功
                if (bol == true){
                    // 修改支付状态，成功
                    updatePlayerPayLogStatus(payLog.getId(),5,null);
                    return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(),product_id);
                }else {
                    // 修改支付状态，失败
                    updatePlayerPayLogStatus(payLog.getId(),6,String.valueOf(status));
                    return ResultDTO.getFailureResult(IosCode.ERROR_FAILURE.getErrorCode(),IosCode.ERROR_FAILURE.getDesc(),"支付失败");
                }
            }catch (Exception e){
                return ResultDTO.getFailureResult(IosCode.ERROR_UNKNOWN.getErrorCode(),IosCode.ERROR_UNKNOWN.getDesc(),"支付失败");
            }
        }
        return ResultDTO.getFailureResult(IosCode.ERROR_CLIENT_VALUE.getErrorCode(),IosCode.ERROR_CLIENT_VALUE.getDesc(),"支付失败");
    }

    /**
     * 写入日志
     * @param iosId
     * @param gameId
     * @param channelId
     * @param msg
     */
    @RequestMapping(value = "/writeLog", method = RequestMethod.POST)
    public ResultDTO<String> writeLog(@RequestParam Long iosId, @RequestParam String gameId, @RequestParam String channelId, @RequestParam String msg){
        logger.info("{time："+new Date()+";gameId："+gameId+";channelId："+channelId+";roleId："+iosId+";msg："+msg+";}");
        return ResultDTO.getSuccessResult(IosCode.OK.getErrorCode(),"ok");
    }

    /**
     * 验证商品
     * @param iosId
     * @param goodsName
     * @param channelId
     * @param gameId
     * @return
     */
    private Boolean verifyGoods(Long iosId, String goodsName, String channelId, String gameId){
        PayGoods payGoods = payGoodsMapper.selectByGChannelId(goodsName,channelId,gameId);
        // 判断商品不为空
        if (payGoods != null){

            // 初始化参数
            String initprop = payGoods.getProp();   // 所有物品
            String good = null;     // 物品名称
            String num = null;      // 物品数量

            // 获取商品类型
            switch (payGoods.getType()){
                case 1: // 奖牌
                    String[] prop = initprop.split("_");
                    updateGold(iosId,gameId,channelId,prop[1]);
                    break;
                case 2: // 去广告
                    playerIosMapper.updateByPrimaryKeySelective
                            (new PlayerIos(iosId,null,null,null,null,null,null,null,new Date()));
                    break;
                case 3: // 月卡
                    playerIosMapper.updateByPrimaryKeySelective
                            (new PlayerIos(iosId,null,null,null,null,null,new Date(),null,null));
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * 添加花钱所得奖牌数量
     * @param iosId
     * @param gameId
     * @param channelId
     * @param rmbMedal
     */
    private void updateGold(Long iosId, String gameId, String channelId, String rmbMedal){
        // 初始化
        GameContent gameContent = new GameContent();
        gameContent.setRoleId(iosId);
        gameContent.setGameId(gameId);
        gameContent.setChannelId(channelId);
        // 查询
        gameContent = gameContentMapper.selectByRole(gameContent);
        // 赋值
        gameContent.setLastMedal(Integer.valueOf(gameContent.getLastMedal()+rmbMedal));
        gameContent.setRmbMedal(Integer.valueOf(gameContent.getRmbMedal()+rmbMedal));
        gameContent.setTotalMedal(Integer.valueOf(gameContent.getTotalMedal()+rmbMedal));
        // 修改
        gameContentMapper.updateByPrimaryKeySelective(gameContent);
    }

    /**
     * 修改支付订单表订单状态
     * @param id
     * @param payStatus
     * @param iosStatus
     */
    private void updatePlayerPayLogStatus(int id, int payStatus, String iosStatus){
        playerPayLogMapper.updateByPrimaryKeySelective(new PlayerPayLog(id, null, null, null, null,
                null, null, null, null, null, payStatus, iosStatus, null));
    }

    /**
     * 发送请求
     * @param url
     * @param code
     * @return
     */
    private String sendHttpsCoon(@RequestParam String url, @RequestParam String code){
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
