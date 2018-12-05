package com.sjhy.platform.test;

import com.sjhy.platform.biz.test.PersonBO;
import com.sjhy.platform.client.dto.test.Person;
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

    @RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    public String hello(String name) {
        return "hi, " + name;
    }

    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    public String test() {
        return personBO.test();
    }

    @RequestMapping(value = "/countPerson.do", method = RequestMethod.GET)
    public Integer count() {
        return personBO.countPerson();
    }

    @RequestMapping(value = "/getPerson.do", method = RequestMethod.GET)
    public Person getPerson(Long personId) {

        return personBO.get(personId);
    }

}
