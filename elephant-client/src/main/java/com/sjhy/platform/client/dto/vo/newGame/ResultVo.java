package com.sjhy.platform.client.dto.vo.newGame;

import com.sjhy.platform.client.dto.player.PlayerIos;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Data
@Component
public class ResultVo {

    @Autowired
    private ResultVo resultVo;

    // 登录返回参数
    private Long iosId;
    private Long serverDate;
    private Long monthlyTime;
    private Long endMonthlyTime;
    private Long adTime;

    // 购买商品返回参数
    private String goodName;
    private Map<String,String> propMap;

    // 登录
    public ResultVo getLogin(Long iosId, Date monthlyTime, Date adTime){
        resultVo = new ResultVo();
        resultVo.setIosId(iosId);
        resultVo.setServerDate(System.currentTimeMillis());
        resultVo.setMonthlyTime(monthlyTime.getTime());
        resultVo.setEndMonthlyTime(null);
        resultVo.setAdTime(adTime.getTime());
        return resultVo;
    }

    // 商品
    public ResultVo getPayProp(String goodName,Map propMap){
        resultVo = new ResultVo();
        resultVo.setGoodName(goodName);
        resultVo.setPropMap(propMap);
        return  resultVo;
    }
}
