package com.sjhy.platform.biz.bo;

import com.alibaba.fastjson.JSON;
import com.sjhy.platform.biz.pay.*;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.biz.deploy.config.AppConfig;
import com.sjhy.platform.biz.deploy.config.GamePayConfig;
import com.sjhy.platform.client.dto.enumerate.PayChannelEnum;
import com.sjhy.platform.client.dto.enumerate.SubChannelEnum;
import com.sjhy.platform.biz.deploy.exception.NoSuchRoleException;
import com.sjhy.platform.biz.deploy.config.KairoErrorCode;
import com.sjhy.platform.biz.deploy.exception.KairoException;
import com.sjhy.platform.client.dto.game.GameChannelSetting;
import com.sjhy.platform.client.dto.game.PayGoods;
import com.sjhy.platform.client.dto.game.Server;
import com.sjhy.platform.client.dto.history.PlayerPayLog;
import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import com.sjhy.platform.biz.deploy.utils.HashKit;
import com.sjhy.platform.biz.deploy.utils.MD5Util;
import com.sjhy.platform.biz.deploy.utils.StringUtils;
import com.sjhy.platform.client.dto.vo.*;
import com.sjhy.platform.client.dto.vo.cachevo.PlayerRoleVO;
import com.sjhy.platform.client.dto.vo.pay.*;
import com.sjhy.platform.persist.mysql.game.PayGoodsMapper;
import com.sjhy.platform.persist.mysql.game.ServerMapper;
import com.sjhy.platform.persist.mysql.history.PlayerPayLogMapper;
import com.sjhy.platform.persist.mysql.player.PlayerChannelMapper;
import com.sjhy.platform.persist.mysql.player.PlayerRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.RoleNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class PayBO {
    private static final Logger logger = LoggerFactory.getLogger(PayBO.class);
    @Resource
    private PlayerRoleMapper playerRoleMapper;
    @Resource
    private PlayerPayLogMapper playerPayLogMapper;
    @Resource
    private PlayerChannelMapper playerChannelMapper;
    @Resource
    private PayGoodsMapper payGoodsMapper;
    @Resource
    private ServerMapper serverMapper;
    @Autowired
    private AddYYBOrderGPProxy addYYBOrderGPProxy;
    @Autowired
    private AddJinliOrderGPProxy addJinliOrderGPProxy;
    @Autowired
    private AddLenovoOrderGPProxy addLenovoOrderGPProxy;
    @Autowired
    private AddOrderGPProxy addOrderGPProxy;
    @Autowired
    private AddQihooOrderGPProxy addQihooOrderGPProxy;
    @Autowired
    private AddTxOrderGPProxy addTxOrderGPProxy;
    @Autowired
    private AddVivoOrderGPProxy addVivoOrderGPProxy;

    private final String OFFLINE_GOOGS_ID_1 = "open_one_0";
    private final String OFFLINE_GOOGS_ID_2 = "open_two_1";

    /**
     * 应用宝渠道
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddYYBOrderResultVO addYYBOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(), sc.getRoleId());

        String[] datas = new String[16];
        datas[0] = String.valueOf(sc.getRoleId());
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
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddTxOrderResultVO addTxOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String[] datas = new String[16];
        datas[0] = String.valueOf(sc.getRoleId());
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
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddQihooOrderResultVO addQihooOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(),order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String[] datas = new String[11];
        datas[0] = String.valueOf(sc.getRoleId());
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

        String channelUserId = playerChannelMapper.selectByChannelUserId(order.getGameId(), order.getChannelId(), palyerRole.getPlayerId());

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

    /**
     * vivo渠道
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddVivoOrderResultVO addVivoOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String[] datas = new String[12];
        datas[0] = String.valueOf(sc.getRoleId());
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

        String channelUserId = playerChannelMapper.selectByChannelUserId(order.getGameId(), order.getChannelId(), palyerRole.getPlayerId());

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

        datas[11] = "";

        AddVivoOrderResultVO addOrderResult = addVivoOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加Vivo渠道支付订单错误：resultCode="+resultCode);
            }
        }

        if(goods != null){
            addOrderResult.setGoodsDesc(goods.getGoodsDes());
            addOrderResult.setRmbPrice(goods.getRmb().toString());
        }

        return addOrderResult;
    }

    /**
     * 金立渠道
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddJinliOrderResultVO addJinliOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), order.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String[] datas = new String[12];
        datas[0] = String.valueOf(sc.getRoleId());
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

        String channelUserId = playerChannelMapper.selectByChannelUserId(order.getGameId(), order.getChannelId(), palyerRole.getPlayerId());

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
        // 内网IP
        datas[10] = String.valueOf(server.getIpInternal()+"|"+server.getPortNumber());

        datas[11] = "";

        AddJinliOrderResultVO addOrderResult = addJinliOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加金立渠道支付订单错误：resultCode="+resultCode);
            }
        }

        addOrderResult.setRmbPrice(goods.getRmb().toString());

        return addOrderResult;
    }

    /**
     * 联想渠道
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddLenovoOrderResultVO addLenovoOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException{
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(), palyerRole.getGameId());
        if(goods == null) {
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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
            throw new KairoException(KairoErrorCode.ERROR_LENOVO_ADD_ORDER, "lenvo渠道参数[extra]错误");
        }

        if(extraMap.get("lpsust") == null) {
            throw new KairoException(KairoErrorCode.ERROR_LENOVO_ADD_ORDER, "lenvo渠道参数[extra]错误|lpsust null");
        }

        String[] datas = new String[12];
        datas[0] = String.valueOf(sc.getRoleId());
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

        String channelUserId = playerChannelMapper.selectByChannelUserId(order.getGameId(), order.getChannelId(), palyerRole.getPlayerId());

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

        datas[11] = extraMap.get("lpsust");

        AddLenovoOrderResultVO addOrderResult = addLenovoOrderGPProxy.sendPost(datas);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();
            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.error("添加Lenovo渠道支付订单错误：resultCode="+resultCode);
            }
        }

        addOrderResult.setRmbPrice(goods.getRmb().toString());
        // 赠送英雄id
        /*addOrderResult.setChannelGoodsId(goods.getChannelGoodsId());*/

        return addOrderResult;
    }

    /**
     * 添加支付订单
     * @param order
     * @param extra
     * @return
     * @throws KairoException
     */
    public AddOrderResultVO addOrder(ServiceContext sc, PayAddOrderVO order, String extra) throws KairoException {
        String orderId = null;

        if (sc.getRoleId() <= 0 ){
            throw new KairoException(KairoErrorCode.ERROR_PAY_UID_ISNULL);
        }

        String goodId = order.getGoodId();

        if (goodId == null){
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ISNULL);
        }

        // 检查是否存在此商品
        PayGoods goods = payGoodsMapper.selectByGChannelId(goodId, order.getChannelId(),order.getGameId());
        if(goods == null) {
            System.out.println(sc.getRoleId() + "_" + goodId+"_"+order.getChannel());
            throw new KairoException(KairoErrorCode.ERROR_PAY_GOOD_ID_ERROR, sc.getRoleId() + "_" + goodId);
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

        PlayerRoleVO palyerRole = (PlayerRoleVO) playerRoleMapper.selectByRoleId(order.getGameId(),sc.getRoleId());

        String[] datas = new String[12];

        datas[0] = String.valueOf(sc.getRoleId());
        logger.error("datas[0]===================================>>>"+String.valueOf(sc.getRoleId()));

        datas[1] = palyerRole.getRoleName();
        logger.error("datas[1]===================================>>>"+palyerRole.getRoleName());

        datas[2] = String.valueOf(palyerRole.getGameId());
        logger.error("datas[2]===================================>>>"+String.valueOf(palyerRole.getGameId()));

        datas[3] = GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, "");

        logger.error("datas[3]===================================>>>"+GetBeanHelper.getServerConfig().getProperty(AppConfig.SERVER_ID, ""));

        Server server = new Server();
        server.setGameId(order.getGameId());
        server.setServerId(StringUtils.getInt(datas[3]));
        server = serverMapper.selectByServer(server);
        logger.error("serverOk=========================>"+server);

        if(server == null){
            throw new KairoException(KairoErrorCode.ERROR_PARAM);
        }

        datas[4] = String.valueOf(server.getIpInternal()+"|"+server.getPortNumber());
        logger.error("datas[4]===================================>>>"+String.valueOf(server.getIpInternal()+"|"+server.getPortNumber()));

        datas[5] = goodId;
        logger.error("datas[5]===================================>>>"+goodId);

        datas[6] = String.valueOf(goodNumber);
        logger.error("datas[6]===================================>>>"+String.valueOf(goodNumber));

        datas[7] = String.valueOf(channel);
        logger.error("datas[7]===================================>>>"+String.valueOf(channel));

        if (order.getRemark() != null){
            datas[8] = order.getRemark();
            logger.error("datas[8]===================================>>>"+order.getRemark());
        }else{
            datas[8] = "";
            logger.error("datas[8]===================================>>>null");
        }

        datas[9] = "Android";// 默认为android
        logger.error("datas[9]===================================>>>Android");

        datas[10] = order.getChannelId();
        logger.error("datas[10]===================================>>>"+order.getChannelId());

        datas[11] = "";
        logger.error("datas[11]===================================>>>jiushi+null+de");

        logger.error("datas===================================>>>"+datas);

        AddOrderResultVO addOrderResult = addOrderGPProxy.sendPost(datas);
        logger.error("addOrderResult===================================>>>"+addOrderResult);

        if (addOrderResult != null){
            int resultCode = addOrderResult.getResultCode();

            if (resultCode == GamePayConfig.SUCCESS){
                orderId = addOrderResult.getOrderId();
            }else{
                logger.info("添加支付订单错误shijun：resultCode="+resultCode);
            }
        }

        addOrderResult.setGoodName(goods.getGoodsName());
        addOrderResult.setGoodDic(goods.getGoodsDes());

        if(order.getChannelId().equals("2300")) {

            addOrderResult.setGoodName(goods.getGoodsName());
            addOrderResult.setGoodDic(goods.getGoodsDes());

            setHwPaySign(goods, orderId, palyerRole.getGameId()+"", order.getChannelId(), addOrderResult);
        } else if(order.getChannelId().equals("2100")) {
            addOrderResult.setGoodName(goods.getGoodsName());
            addOrderResult.setGoodDic(goods.getGoodsDes());

            long createTime = System.currentTimeMillis();

            addOrderResult.setCreateTime(createTime);

            addOrderResult.setSign(getMzPaySign(goods, orderId, palyerRole.getGameId()+"", order.getChannelId(), createTime));
        } else if(order.getChannelId().equals("2900")) {
            addOrderResult.setGoodName(goods.getGoodsName());
            addOrderResult.setGoodDic(goods.getGoodsDes());
            String[] good = goods.getProp().split("_");
            addOrderResult.setIngot(good[1]);
            long createTime = System.currentTimeMillis();

            addOrderResult.setCreateTime(createTime);

            setBiliPaySign(goods, orderId, palyerRole.getGameId()+"", order.getChannelId(), addOrderResult);
        } else if(order.getChannelId().equals("1900")) {
            addOrderResult.setGoodName(goods.getGoodsName());
            addOrderResult.setGoodDic(goods.getGoodsDes());
            String[] good = goods.getProp().split("_");
            addOrderResult.setIngot(good[1]);
            long createTime = System.currentTimeMillis();

            addOrderResult.setCreateTime(createTime);

            setAnzhiPaySign(goods, orderId, palyerRole.getGameId()+"", order.getChannelId(), addOrderResult);
        }else if(order.getChannelId().equals("2600")) {
            addOrderResult.setGoodName(goods.getGoodsName());
            addOrderResult.setGoodDic(goods.getGoodsDes());
            String[] good = goods.getProp().split("_");
            addOrderResult.setIngot(good[1]);

            long createTime = System.currentTimeMillis();
            addOrderResult.setCreateTime(createTime);

            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            addOrderResult.setRmbPrice(df.format(goods.getRmb()));

            String playM4399 = playerChannelMapper.selectByChannelUserId(palyerRole.getGameId(),palyerRole.getChannelId(),palyerRole.getPlayerId());
            // String secret = "0c910709baf84bbebe94db215fff1097";
            String secret = "a8d0b772a7c933b7949375de5104c09b";

            setM4399PaySign(orderId,playM4399,server.getServerId(),secret,orderId,(int)createTime,null,0,addOrderResult);
        } else {
            GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(palyerRole.getGameId()+"", order.getChannelId());

            if(gameChannelSetting != null) {
                addOrderResult.setNotifyUrl(gameChannelSetting.getNotifyUrl());
            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        addOrderResult.setRmbPrice(df.format(goods.getRmb()));

        return addOrderResult;
    }

    /**
     * 4399 渠道签名
     * @param orderid
     * @param uid
     * @param serverid
     * @param secret
     * @param mark
     * @param time
     * @param couponMark
     * @param couponMonkey
     * @param info
     */
    public void setM4399PaySign(String orderid, String uid, int serverid, String secret,String mark, int time, String couponMark,
                                int couponMonkey, AddOrderResultVO info) {
        String sign = MD5Util.me().md5Hex(orderid+uid+info.getRmbPrice()+info.getIngot()+serverid+secret
                +mark+time+couponMark+couponMonkey);
        System.out.println("=========sign============>" + sign);
        info.setSign(sign);
    }

    /**
     * 华为支付签名
     * @param goods
     * @param orderId
     * @param gameId
     * @param channelId
     * @param addOrderResult
     */
    public void setHwPaySign(PayGoods goods, String orderId, String gameId, String channelId, AddOrderResultVO addOrderResult) {
        GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(gameId, channelId);

        if(gameChannelSetting == null) {
            return ;
        }

        Map<String, Object> signParams = new HashMap<String, Object>();

        signParams.put("productName", goods.getGoodsName());
        signParams.put("productDesc", goods.getGoodsDes());
        signParams.put("applicationID", gameChannelSetting.getAppId());
        signParams.put("requestId", orderId);

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        signParams.put("amount", df.format(goods.getRmb()));
        signParams.put("merchantId", gameChannelSetting.getPayKey());
        signParams.put("sdkChannel", 3);
        signParams.put("url", gameChannelSetting.getNotifyUrl());
        //signParams.put("extReserved", orderId);

        String baseSign = getNoSign(signParams, false);

        logger.error("PayService|baseSign="+baseSign);

        String sign     = HashKit.sign(baseSign, gameChannelSetting.getPayPrivateKey().trim());

        addOrderResult.setSign(sign);
        addOrderResult.setNotifyUrl(gameChannelSetting.getNotifyUrl());
    }

    /**
     * 魅族支付签名
     * @param goods
     * @param orderId
     * @param gameId
     * @param channelId
     * @param createTime
     * @return
     */
    public String getMzPaySign(PayGoods goods, String orderId, String gameId, String channelId, long createTime) {
        GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(gameId, channelId);

        if(gameChannelSetting == null) {
            return "";
        }

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        StringBuilder builder = new StringBuilder();
        final String equalStr = "=";
        final String andStr   = "&";

        builder.append("app_id" + equalStr + gameChannelSetting.getAppId() + andStr);
        builder.append("cp_order_id" + equalStr + orderId + andStr);
        builder.append("create_time" + equalStr + createTime + andStr);
        builder.append("pay_type" + equalStr + "0" + andStr);

        builder.append("product_body" + equalStr + goods.getGoodsDes() + andStr);
        builder.append("product_id" + equalStr + goods.getGoodsName() + andStr);
        builder.append("product_subject" + equalStr + goods.getGoodsName() + andStr);
        builder.append("total_price" + equalStr + df.format(goods.getRmb()) + andStr);
        builder.append("user_info" + equalStr + orderId);

        builder.append(":" + gameChannelSetting.getAppSecret());

        //System.out.println("==========str===========>" + builder.toString());

        String sign = MD5Util.me().md5Hex(builder.toString());
        //System.out.println("=========sign============>" + sign);

        return sign;
    }

    /**
     * B站支付签名
     * @param goods
     * @param orderId
     * @param gameId
     * @param channelId
     * @param info
     */
    public void setBiliPaySign(PayGoods goods, String orderId, String gameId, String channelId, AddOrderResultVO info) {
        GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(gameId, channelId);

        if(gameChannelSetting == null) {
            return ;
        }
        info.setNotifyUrl(gameChannelSetting.getNotifyUrl()==null?"":gameChannelSetting.getNotifyUrl());;

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        StringBuilder builder = new StringBuilder();
        String[] good = goods.getProp().split("_");
        builder.append(good[1]);
        //builder.append(df.format(goods.getRmb()));
        builder.append(goods.getRmb().intValue());
        builder.append(info.getNotifyUrl());
        builder.append(orderId);

        builder.append(gameChannelSetting.getAppSecret());
        System.out.println("==========str===========>" + builder.toString());
        String sign = MD5Util.me().md5Hex(builder.toString());
        System.out.println("=========sign============>" + sign);

        info.setSign(sign);
    }

    /**
     * 安智支付签名
     * @param goods
     * @param orderId
     * @param gameId
     * @param channelId
     * @param info
     */
    public void setAnzhiPaySign(PayGoods goods, String orderId, String gameId, String channelId, AddOrderResultVO info) {
        GameChannelSetting gameChannelSetting = VerifySessionBO.getGameChannelSetting(gameId, channelId);
        if(gameChannelSetting == null) {
            return ;
        }
        String sign = MD5Util.me().md5Hex(gameChannelSetting.getAppSecret().toString());
        System.out.println("=========sign============>" + sign);
        info.setSign(sign);
    }

    /**
     * 获取签名
     * @param params
     * @param includeEmptyParam
     * @return
     */
    public static String getNoSign(Map<String, Object> params, boolean includeEmptyParam) {
        //对参数按照key做升序排序，对map的所有value进行处理，转化成string类型
        //拼接成key=value&key=value&....格式的字符串
        StringBuilder content = new StringBuilder();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String value = null;
        Object object = null;
        boolean isFirstParm = true;
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            object = params.get(key);

            if (object == null) {
                value = "";
            }else if (object instanceof String) {
                value = (String) object;
            } else {
                value = String.valueOf(object);
            }

            if (includeEmptyParam || !StringUtils.isEmpty(value)) {
                content.append((isFirstParm ? "" : "&") + key + "=" + value);
                isFirstParm = false;
            } else {
                continue;
            }
        }
        //待签名的字符串
        return content.toString();
    }

    /**
     * 获取奖牌
     * @return
     * @throws RoleNotFoundException
     */
    public PayLogVO getMedal(ServiceContext sc) throws RoleNotFoundException {
        // 玩家信息取得
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(sc.getGameId(),sc.getRoleId());
        if(role == null) {
            throw new RoleNotFoundException();
        }
        List<PlayerPayLog> payLogs = playerPayLogMapper.selectByPayLog(sc.getGameId(),sc.getRoleId());

        int num = 0;

        PayLogVO payLog = new PayLogVO();
        payLog.setAdTime(role.getAdtime());
        payLog.setVipTime(role.getViptime());

        for(PlayerPayLog data : payLogs) {
            if("subscription_on_case0_00".equals(data.getGoodsName()) || "ad_off_case0".equals(data.getGoodsName())) {// 如果是购买黄金会员
                continue;
            }

            PayLogVO.GoodsInfo goods = new PayLogVO.GoodsInfo();
            goods.setGoodsId(data.getGoodsName());

            num = data.getCurrencyGet()==null?0:data.getCurrencyGet();
            num += data.getCurrencyGetExtra()==null?0:data.getCurrencyGetExtra();

            goods.setNum(num);
            goods.setPayTime(data.getCreateTime());
            payLog.getGoodsInfo().add(goods);
        }

        return payLog;
    }

    /**
     * 是否购买单机商品
     * @param goodsName
     * @return
     * @throws NoSuchRoleException
     */
    public String[] isPlayerBuyOfflineGoods(ServiceContext sc, String goodsName) throws NoSuchRoleException {
        PlayerPayLog payLog = new PlayerPayLog();

        // 用户信息
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(sc.getGameId(),sc.getRoleId());
        if(role == null) {
            throw new NoSuchRoleException();
        }

        if(goodsName == null || goodsName.trim().length() <= 0){
            payLog = playerPayLogMapper.selectByOpen(sc.getGameId(),sc.getRoleId());
        }else{
            payLog = playerPayLogMapper.selectByGood(sc.getGameId(),sc.getRoleId(),goodsName);
        }

        int count = playerPayLogMapper.countByPlayerPayLog(payLog);

        String[] ret = {"0", "0", ""};
        if(count > 0) {
            ret[0] = "1";
        }

        double showPrice = 0.0;

        if(goodsName == null || goodsName.trim().length() <= 0) {
            // 商品是否打折
            PayGoods goods = null;

            goods = payGoodsMapper.selectByGChannelId(OFFLINE_GOOGS_ID_1, sc.getChannelId(), role.getGameId());

            if(goods != null) {
                if(goods.getDiscountBegin() != null
                        && goods.getDiscountBegin().getTime() <= Calendar.getInstance().getTimeInMillis()
                        && goods.getDiscountEnd() != null
                        && goods.getDiscountEnd().getTime() >=  Calendar.getInstance().getTimeInMillis())
                {
                    ret[1] = "1";
                    // 打折的价格
                    ret[2] = goods.getRmb().toString();
                    System.out.println("chann1elId=" +sc.getChannelId() +"|begin:"+goods.getDiscountBegin()+"|end:"+goods.getDiscountEnd());
                }else{
                    PayGoods goods2 = null;
                    goods = payGoodsMapper.selectByGChannelId(OFFLINE_GOOGS_ID_1, sc.getChannelId(), role.getGameId());
                    // 不打折价格
                    if(goods2 != null) {
                        // 打折的价格
                        ret[2] = goods2.getRmb().toString();
                    }
                    System.out.println("chann2elId=" +sc.getChannelId() +"|begin:"+goods.getDiscountBegin()+"|end:"+goods.getDiscountEnd());
                }

            }
        } else {
            // 商品是否打折
            PayGoods goods = null;
            goods = payGoodsMapper.selectByGChannelId(OFFLINE_GOOGS_ID_1, sc.getChannelId(), role.getGameId());
            if(goods != null) {
                ret[1] = "1";
                // 打折的价格
                ret[2] = goods.getRmb().toString();
                System.out.println("goodsName:" + goodsName + "chann1elId=" +sc.getChannelId() +"|begin:"+goods.getDiscountBegin()+"|end:"+goods.getDiscountEnd());
            }
        }
        return ret;
    }

    /**
     * 支付通知
     * @param notify
     * @throws NoSuchRoleException
     */
    public int payNotify(ServiceContext sc, PayNotifyVO notify) throws NoSuchRoleException{
        String orderId = notify.getOrderId();

        logger.error("payNotify->orderId:"+orderId+"");

        if (orderId == null){
            logger.error("======>payNotify->orderId<========:"+orderId+"|err=支付订单异常orderId null");
            return 0;
        }

        // 玩家存在与否
        PlayerRoleVO role = (PlayerRoleVO) playerRoleMapper.selectByRoleId(sc.getGameId(),sc.getRoleId());
        if(role == null) {
            logger.error("======>payNotify->sc.getRoleId()<========:"+orderId+"|err=角色不存在异常");
            throw new NoSuchRoleException();
        }
        int payStatus = notify.getPayStatus();

        return payStatus;
        // （注释） 到前端调用
        /*if (payStatus == PayStatusEnum.Success.getValue()){
            logger.error("this here -->addPayValue");
            addPayValue(sc.getRoleId(), notify, gameId);
        }else{
            logger.error("this here -->addPayValue error");
            SendNotifyVO(sc.getRoleId(), notify, 0, 0, notify.getVirtualAmount());
        }*/
    }

}
