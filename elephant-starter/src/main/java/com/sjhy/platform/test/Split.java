package com.sjhy.platform.test;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.Map;

@Data
@Scope("prototype")
public class Split {

    @Autowired
    private Split resultVo;

    // roleId,iosId
    private Long iosId;

    // 登录返回参数
    private Long serverDate;
    private Long monthlyTime;
    private Long endMonthlyTime;
    private Long adTime;

    // 购买商品返回参数
    private String goodName;
    private Map<String,String> propMap;

    // 登录
    public Split getLogin(Long iosId, Date monthlyTime, Date adTime){
        resultVo.setIosId(iosId);
        resultVo.setServerDate(System.currentTimeMillis());
        if (monthlyTime == null){
            resultVo.setMonthlyTime(null);
        }else {
            resultVo.setMonthlyTime(monthlyTime.getTime());
        }
        if (adTime == null){
            resultVo.setAdTime(null);
        }else {
            resultVo.setAdTime(adTime.getTime());
        }
        resultVo.setEndMonthlyTime(null);
        return resultVo;
    }

    // 商品
    public Split getPayProp(String goodName, Map propMap){
        resultVo.setGoodName(goodName);
        resultVo.setPropMap(propMap);
        return  resultVo;
    }
}
