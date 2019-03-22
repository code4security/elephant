package com.sjhy.platform.client.dto.vo.newGame;

import com.sjhy.platform.client.dto.player.PlayerIos;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ResultVo {

    @Autowired
    private ResultVo resultVo;

    // 登录返回参数
    private Long iosId;
    private Long serverDate;
    private Date monthlyTime;
    private Date endMonthlyTime;
    private Date adTime;

    //

    public ResultVo getLogin(Long iosId, Date monthlyTime, Date adTime){
        resultVo.setIosId(iosId);
        resultVo.setServerDate(System.currentTimeMillis());
        resultVo.setMonthlyTime(monthlyTime);
        resultVo.setEndMonthlyTime(null);
        resultVo.setAdTime(adTime);
        return resultVo;
    }
}
