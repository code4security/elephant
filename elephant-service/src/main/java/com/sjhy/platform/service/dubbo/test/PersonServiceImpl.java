package com.sjhy.platform.service.dubbo.test;

import com.sjhy.platform.biz.test.PersonBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.test.Person;
import com.sjhy.platform.client.service.test.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Liu Zheng
 */
@Service(value="PersonService")
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonBO personBO;

    @Override
    public ResultDTO<String> test() {

        return ResultDTO.getSuccessResult("test from PersonServiceImpl");
    }

    @Override
    public ResultDTO<Person> get(ServiceContext sc, Long personId) {

        return ResultDTO.getSuccessResult(personBO.get(personId));
    }
}
