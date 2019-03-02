package com.sjhy.platform.persist.mysql.test;

import com.sjhy.platform.client.dto.test.Person;
import org.springframework.stereotype.Repository;

/**
 * @author 万二(Zheng Liu)
 */
public interface PersonMapper {

    Person get(Long personId);

    Integer countPerson();

}
