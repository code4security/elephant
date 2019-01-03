package com.sjhy.platform.client.service.test;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.dto.common.ServiceContext;
import com.sjhy.platform.client.dto.test.Person;

/**
 * @author 万二(Zheng Liu)
 */
public interface PersonService {
     ResultDTO<String> test();

     ResultDTO<Person> get(ServiceContext sc, Long personId);

     Person get(Integer personId);
}
