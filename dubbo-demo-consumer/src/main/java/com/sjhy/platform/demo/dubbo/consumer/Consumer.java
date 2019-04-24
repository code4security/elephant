package com.sjhy.platform.demo.dubbo.consumer;

import com.sjhy.platform.client.dto.common.ResultDTO;
import com.sjhy.platform.client.service.test.PersonService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Liu Zheng
 */
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"springbeans-dubbo-consumer.xml"});
        context.start();
        PersonService personService = (PersonService) context.getBean("personService"); // get remote service proxy
        while (true) {
            try {
                Thread.sleep(1000);
                ResultDTO<String> testRst = personService.test(); // call remote method
                System.out.println(testRst.getSuccessResult()); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

}
