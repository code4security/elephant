package com.sjhy.platform.biz.utils;

import com.sjhy.platform.biz.redis.serializer.FSTSerializer;
import com.sjhy.platform.biz.redis.serializer.JavaSerializer;
import com.sjhy.platform.biz.redis.serializer.Serializer;
import net.sf.ehcache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 对象序列化工具包
 *
 * @author 
 */
public class SerializationUtils {

    private final static Logger logger = LoggerFactory.getLogger(SerializationUtils.class);
    private static Serializer g_ser;

    public static void main(String[] args) throws IOException {
       
    }

    static {
        //String ser = CacheManager.getSerializer();
    	String ser = "fst";
        
        if (ser == null || "".equals(ser.trim()))
            g_ser = new JavaSerializer();
        else {
            if (ser.equals("java")) {
                g_ser = new JavaSerializer();
            } else if (ser.equals("fst")) {
                g_ser = new FSTSerializer();
            } else {
                try {
                    g_ser = (Serializer) Class.forName(ser).newInstance();
                } catch (Exception e) {
                    throw new CacheException("Cannot initialize Serializer named [" + ser + ']', e);
                }
            }
        }
        
        logger.info("Using Serializer -> [" + g_ser.name() + ":" + g_ser.getClass().getName() + ']');
    }

    public static byte[] serialize(Object obj) throws IOException {
        return g_ser.serialize(obj);
    }

    public static Object deserialize(byte[] bytes) throws IOException {
        return g_ser.deserialize(bytes);
    }

}
