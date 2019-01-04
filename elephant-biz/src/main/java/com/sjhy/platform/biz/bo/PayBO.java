package com.sjhy.platform.biz.bo;

import com.alibaba.fastjson.JSON;
import com.sjhy.platform.biz.pay.*;
import com.sjhy.platform.client.dto.config.AppConfig;
import com.sjhy.platform.client.dto.config.GamePayConfig;
import com.sjhy.platform.client.dto.enumerate.PayChannelEnum;
import com.sjhy.platform.client.dto.enumerate.SubChannelEnum;
import com.sjhy.platform.client.dto.fixed.VirtualCurrency;
import com.sjhy.platform.client.dto.config.KairoErrorCode;
import com.sjhy.platform.client.dto.exception.KairoException;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.game.GiftCodeList;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.client.dto.player.PlayerChannel;
import com.sjhy.platform.client.dto.utils.StringUtils;
import com.sjhy.platform.client.dto.vo.PayAddOrderVO;
import com.sjhy.platform.client.dto.vo.PlayerRoleVO;
import com.sjhy.platform.client.dto.vo.pay.AddQihooOrderResultVO;
import com.sjhy.platform.client.dto.vo.pay.AddTxOrderResultVO;
import com.sjhy.platform.client.dto.vo.pay.AddYYBOrderResultVO;
import com.sjhy.platform.persist.mysql.fixed.VirtualCurrencyMapper;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import com.sjhy.platform.persist.mysql.player.PlayerChannelMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PayBO {
    private static Logger logger = Logger.getLogger(PayBO.class);

    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private GiftCodeBO giftCodeBO;
    @Resource
    private PlayerBO playerBO;
    @Resource
    private PlayerChannelMapper playerChannelMapper;
    @Resource
    private VirtualCurrencyMapper virtualCurrencyMapper;
    @Resource
    private PayGoodsMapper payGoodsMapper;
    @Resource
    private ServerMapper serverMapper;
    @Resource
    private AddYYBOrderGPProxy addYYBOrderGPProxy;
    @Resource
    private AbstractGamePayProxy abstractGamePayProxy;
    @Resource
    private AddJinliOrderGPProxy addJinliOrderGPProxy;
    @Resource
    private AddLenovoOrderGPProxy addLenovoOrderGPProxy;
    @Resource
    private AddOrderGPProxy addOrderGPProxy;
    @Resource
    private AddQihooOrderGPProxy addQihooOrderGPProxy;
    @Resource
    private AddTxOrderGPProxy addTxOrderGPProxy;
    @Resource
    private AddVivoOrderGPProxy addVivoOrderGPProxy;

    /**
     * 兑换礼品码兑换逻辑
     * @param roleId
     * @param redeemCode
     * @return
     * @throws RoleNotFoundException
     * @throws KairoException
     */
    public LinkedHashMap<Integer, Integer> redeemCodeExchange(long roleId, String gameId, String redeemCode) throws RoleNotFoundException, KairoException {
        // 玩家信息取得
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(gameId, roleId);
        if(role == null) {
            throw new RoleNotFoundException();
        }

        // 判断兑换码是否已被使用
        GiftCodeList giftCodeList = giftCodeBO.isValidRedeemCode(redeemCode, role.getChannelId(), role.getGameId(), roleId);
        if(giftCodeList == null){
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        if(StringUtils.isBlank(giftCodeList.getGiftRewardId())) {
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        // 检查同一批次是否已使用过
        if(giftCodeBO.isUseForRedeemLot(roleId, giftCodeList.getId(),gameId) != null) {
            throw new KairoException(KairoErrorCode.ERROR_REDEEM_CODE);
        }

        String goodsInfo = giftCodeList.getGiftRewardId();

        String[] goods = goodsInfo.split(",");
        String[] goodInfo = null;

        LinkedHashMap<Integer, Integer> goodsMap = new LinkedHashMap<Integer, Integer>();

        for(String good : goods) {
            goodInfo = good.split(":");

            if(goodInfo.length > 1) {
                // goodId是否有效
                VirtualCurrency virtualCurrency = virtualCurrencyMapper.selectByUnit(StringUtils.getString(goodInfo[0]));
                if(virtualCurrency != null) {
                    goodsMap.put(StringUtils.getInt(goodInfo[0]), StringUtils.getInt(goodInfo[1]));
                }
            }
        }

        // 更新兑换码
        giftCodeBO.redeemCode(redeemCode, role.getGameId(), role.getPlayerId(), roleId, role.getChannelId(), giftCodeList.getId());

        return goodsMap;
    }

    /**
     * 应用宝渠道
     * @param roleId
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddYYBOrderResultVO addYYBOrder(long roleId, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (roleId <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, roleId + "_" + goodId);
        }

        int goodNumber = order.getGoodNumber();
        if (goodNumber <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_NUMBER_ISNULL);
        }

        int channel = order.getChannel();
        if (channel <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_CHANNEL_ERROR);
        }

        if (PayChannelEnum.valueOf(channel) == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        Map<String, String> extraMap = JSON.parseObject(extra, Map.class);
        if(extraMap == null) {
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "应用宝渠道参数[extra]错误");
        }

        // QQ平台Id
        if(StringUtils.isBlank(extraMap.get("pf"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "应用宝渠道参数[pf]错误");
        }

        // QQ平台Key
        if(StringUtils.isBlank(extraMap.get("pfkey"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "应用宝渠道参数[pfkey]错误");
        }

        if(StringUtils.isBlank(extraMap.get("openid"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "应用宝渠道参数[openid]错误");
        }

        if(StringUtils.isBlank(extraMap.get("openkey"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "应用宝渠道参数[openkey]错误");
        }

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(), roleId);

        String[] datas = new String[16];
        datas[0] = String.valueOf(roleId);
        datas[1] = String.valueOf(goodId);
        datas[2] = String.valueOf(goodNumber);
        datas[3] = String.valueOf(channel);

        if (order.getRemark() != null){
            datas[4] = order.getRemark();
        }

        if (order.getChannelId() != null){
            datas[5] = order.getChannelId();
        }

        // 角色名
        datas[6] = palyerRole.getRoleName();

        datas[7] = palyerRole.getPlayerId()+"";

        // QQ平台Id
        datas[8]  = extraMap.get("pf");
        // QQ平台Key
        datas[9]  = extraMap.get("pfkey");
        datas[10] = extraMap.get("openid");

        if(extraMap.get("subchannel").equals(SubChannelEnum.YYB_QQ.getSubChannelId()+"")) {
            datas[11] = extraMap.get("payToken");
        }else{
            datas[11] = extraMap.get("openkey");
        }

        datas[12] = String.valueOf(palyerRole.getGameId());
        datas[13] = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, "");

        Server server = new Server();
        server.setGameId(order.getGameId());
        server.setServerId(StringUtils.getInt(datas[13]));
        server = serverMapper.selectByServer(server);
        if(server == null){
            throw new KairoException(KairoErrorCode.ERROR_PARAM);
        }

        datas[14] = String.valueOf(server.getIpInternal()+"|"+server.getPortNumber());

        datas[15] = extraMap.get("subchannel");

        AddYYBOrderResultVO addOrderResult = addYYBOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加应用宝支付订单错误：resultCode="+resultCode);
            }
        }

        addOrderResult.setRmbPrice(goods.getRmb().toString());

        return addOrderResult;
    }

    /**
     * 腾讯渠道
     * @param roleId
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddTxOrderResultVO addTxOrder(long roleId, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (roleId <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, roleId + "_" + goodId);
        }

        int goodNumber = order.getGoodNumber();
        if (goodNumber <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_NUMBER_ISNULL);
        }

        int channel = order.getChannel();
        if (channel <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_CHANNEL_ERROR);
        }

        if (PayChannelEnum.valueOf(channel) == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        Map<String, String> extraMap = JSON.parseObject(extra, Map.class);
        if(extraMap == null) {
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[extra]错误");
        }

        // QQ平台Id
        if(StringUtils.isBlank(extraMap.get("pf"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[pf]错误");
        }

        // QQ平台Key
        if(StringUtils.isBlank(extraMap.get("pfkey"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[pfkey]错误");
        }

        if(StringUtils.isBlank(extraMap.get("openid"))){
            throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[openid]错误");
        }

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),roleId);

        String[] datas = new String[16];
        datas[0] = String.valueOf(roleId);
        datas[1] = String.valueOf(goodId);
        datas[2] = String.valueOf(goodNumber);
        datas[3] = String.valueOf(channel);

        if (order.getRemark() != null){
            datas[4] = order.getRemark();
        }

        if (order.getChannelId() != null){
            datas[5] = order.getChannelId();
        }

        // 角色名
        datas[6] = palyerRole.getRoleName();

        datas[7] = palyerRole.getPlayerId()+"";

        // QQ平台Id
        datas[8]  = extraMap.get("pf");
        // QQ平台Key
        datas[9]  = extraMap.get("pfkey");
        datas[10] = extraMap.get("openid");

        if(extraMap.get("subchannel").equals(SubChannelEnum.TX_QQ.getSubChannelId()+"")) {
            if(StringUtils.isBlank(extraMap.get("payToken"))){
                throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[openkey]错误");
            }

            datas[11] = extraMap.get("payToken");
        }else{
            if(StringUtils.isBlank(extraMap.get("openkey"))){
                throw new KairoException(KairoErrorCode.ERROR_YYB_ADD_ORDER, "腾讯手Q渠道参数[openkey]错误");
            }

            datas[11] = extraMap.get("openkey");
        }

        datas[12] = String.valueOf(palyerRole.getGameId());
        datas[13] = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, "");

        Server server = new Server();
        server.setGameId(order.getGameId());
        server.setServerId(StringUtils.getInt(datas[13]));
        server = serverMapper.selectByServer(server);
        if(server == null){
            throw new KairoException(KairoErrorCode.ERROR_PARAM);
        }
        // 使用腾讯云服
        datas[14] = String.valueOf(server.getIp()+"|"+server.getPortNumber());

        datas[15] = extraMap.get("subchannel");

        AddTxOrderResultVO addOrderResult = addTxOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加腾讯手Q渠道支付订单错误：resultCode="+resultCode);
            }
        }

        addOrderResult.setRmbPrice(goods.getRmb().toString());

        return addOrderResult;
    }

    /**
     * 360渠道
     * @param roleId
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddQihooOrderResultVO addQihooOrder(long roleId, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (roleId <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(),order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, roleId + "_" + goodId);
        }

        int goodNumber = order.getGoodNumber();
        if (goodNumber <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_NUMBER_ISNULL);
        }

        int channel = order.getChannel();
        if (channel <= 0){
            throw new KairoException(KairoErrorCode.ERROR_PAY_CHANNEL_ERROR);
        }

        if (PayChannelEnum.valueOf(channel) == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),roleId);

        String[] datas = new String[11];
        datas[0] = String.valueOf(roleId);
        datas[1] = String.valueOf(goodId);
        datas[2] = String.valueOf(goodNumber);
        datas[3] = String.valueOf(channel);

        if (order.getRemark() != null){
            datas[4] = order.getRemark();
        }

        if (order.getChannelId() != null){
            datas[5] = order.getChannelId();
        }

        // 角色名
        datas[6] = palyerRole.getRoleName();

        String channelUserId = playerChannelMapper.selectByPlayerId(order.getGameId(), order.getChannelId(), palyerRole.getPlayerId());

        datas[7] = channelUserId+"";

        datas[8] = String.valueOf(palyerRole.getGameId());
        datas[9] = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, "");

        Server server = new Server();
        server.setGameId(order.getGameId());
        server.setServerId(StringUtils.getInt(datas[9]));
        server = serverMapper.selectByServer(server);
        if(server == null){
            throw new KairoException(KairoErrorCode.ERROR_PARAM);
        }

        datas[10] = String.valueOf(server.getIpInternal()+"|"+server.getPortNumber());

        AddQihooOrderResultVO addOrderResult = addQihooOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加360渠道支付订单错误：resultCode="+resultCode);
            }
        }

        addOrderResult.setUserId(channelUserId);

        addOrderResult.setRmbPrice(goods.getRmb().toString());

        GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(palyerRole.getGameId()+"", order.getChannelId());

        if(gameChannelSetting != null) {
            addOrderResult.setNotifyUrl(gameChannelSetting.getNotifyUrl());
        }

        return addOrderResult;
    }
}
