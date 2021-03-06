package com.sjhy.platform.client.dto.vo.newGame;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Data
@Component
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResultVo {

    @Autowired
    private ResultVo resultVo;

    // roleId,iosId
    private Long iosId;

    // 登录返回参数
    private Long serverDate;
    private Long monthlyTime;
    private Long endMonthlyTime;
    private Long adTime;
    private Boolean putArchive;

    // 购买商品返回参数
    private String goodName;
    private Map<String,String> propMap;

    // 登录
    public ResultVo getLogin(Long iosId, Date monthlyTime, Date adTime, Boolean putArchive){
        resultVo = new ResultVo();
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
        resultVo.setPutArchive(putArchive);
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
