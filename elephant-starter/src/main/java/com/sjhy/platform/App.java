package com.sjhy.platform;

import com.sjhy.platform.biz.deploy.utils.GetBeanHelper;
import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Hello world!
 *
 */
@MapperScan("com.sjhy.platform.persist.mysql")
@SpringBootApplication(scanBasePackages = {"com.sjhy.platform"})
@ImportResource("classpath:spring/application-context.xml")
@EnableCaching
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
        GetBeanHelper.setApplicationContext(ac);
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        return tomcat;
    }

    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //同时启用http（8080）、https（8443）两个端口
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }

}
