package com.sjhy.platform.biz.test;

import com.sjhy.platform.client.dto.test.Person;
import com.sjhy.platform.persist.mysql.test.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 万二(Zheng Liu)
 */
@Service
public class PersonBO {

    private PersonMapper personMapper;

    public String test() {
        return "test from PersonBO";
    }

    public Integer countPerson() {
        return personMapper.countPerson();
    }

    public Person get(Long personId) {

        Person p = personMapper.get(personId);

        return p;
    }
}
