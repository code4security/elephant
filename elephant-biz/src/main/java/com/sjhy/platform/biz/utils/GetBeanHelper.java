package com.sjhy.platform.biz.utils;

import com.sjhy.platform.biz.verify.IVerifySession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 从Spring中获取非单例的对象(代替new)
 * 这种方法不是很好,形成了对Spring的依赖
 * 但是 Lookup方法注入 需求修改的类太多了,
 * 而且需要仔细研究涉及到的每个类是 singleton　还是 prototype
 * 对Netty的处理机制也不熟悉,所以所有Service的引用都从这里走,这些都是单例
 * @author SHACS
 *
 */
@Component
public class GetBeanHelper {
	private static ApplicationContext applicationContext; // 保存spring工厂的引用
	
	public static void setApplicationContext(ApplicationContext ac) throws BeansException {
		GetBeanHelper.applicationContext = ac; // 获得该ApplicationContext引用
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	public static Properties getServerConfig(){
    	return (Properties)applicationContext.getBean("serverConfig");
    }

    public static IVerifySession getChannelVerify(String channelId){
		return (IVerifySession)applicationContext.getBean(channelId);
	}

}
