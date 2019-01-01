package com.sjhy.platform.test;

import com.sjhy.platform.biz.test.PersonBO;
import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.test.Person;
import com.sjhy.platform.client.service.test.PersonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 万二(Zheng Liu)
 */
@RestController
public class PersonController {

    @Resource
    private PersonBO personBO;

    @Resource
    private PersonService personService;

    @RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    public String hello(String name) {
        return "hi, " + name;
    }

    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    public ResultDTO<String> test() {
        return personService.test();
    }

    @RequestMapping(value = "/countPerson.do", method = RequestMethod.GET)
    public Integer count() {
        return personBO.countPerson();
    }

    @RequestMapping(value = "/getPerson.do", method = RequestMethod.GET)
    public ResultDTO<Person> getPerson(Long personId) {

        return personService.get(null, personId);
    }

}
