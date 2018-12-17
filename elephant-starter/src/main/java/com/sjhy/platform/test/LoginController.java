package com.sjhy.platform.test;

import com.sjhy.platform.biz.bo.LoginBO;
import com.sjhy.platform.biz.exception.AccountAlreadyBindingOtherException;
import com.sjhy.platform.biz.exception.DeviceSignNullException;
import com.sjhy.platform.biz.exception.EmptyAccountNameException;
import com.sjhy.platform.biz.exception.NotExistAccountException;
import com.sjhy.platform.biz.vo.RegularLoginVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @HJ
 */
@RestController
public class LoginController {
    @Resource
    private LoginBO loginBO;

    @RequestMapping(value = "/one.do", method = RequestMethod.GET)
    public String loginChallenge(int clientId, String channelUserId, String deviceUniqueId, String channelId, String gameId) throws DeviceSignNullException,
            EmptyAccountNameException, AccountAlreadyBindingOtherException, NotExistAccountException {
        RegularLoginVO loginInfo = loginBO.loginChallenge(clientId,channelUserId,deviceUniqueId,channelId,gameId);
        return "s="+loginInfo.getSrp6Info().getSalt().toString(16)+"\n"
                + "B="+loginInfo.getSrp6Info().getB().toString(16)+"\n";
    }
}
