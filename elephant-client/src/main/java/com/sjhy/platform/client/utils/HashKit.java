package com.sjhy.platform.client.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.Map.Entry;

public class HashKit {
	private static final String MAC_NAME = "HmacSHA1" ;
	private static final java.security.SecureRandom random = new java.security.SecureRandom();
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	public static String md5(String srcStr){
		return hash("MD5", srcStr);
	}
	
	public static String sha1(String srcStr){
		return hash("SHA-1", srcStr);
	}
	
	public static String sha256(String srcStr){
		return hash("SHA-256", srcStr);
	}
	
	public static String sha384(String srcStr){
		return hash("SHA-384", srcStr);
	}
	
	public static String sha512(String srcStr){
		return hash("SHA-512", srcStr);
	}
	
	public static String hash(String algorithm, String srcStr) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
			return toHex(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String toHex(byte[] bytes) {
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
	
	/**
	 * md5 128bit 16bytes
	 * sha1 160bit 20bytes
	 * sha256 256bit 32bytes
	 * sha384 384bit 48bites
	 * sha512 512bit 64bites
	 */
	public static String generateSalt(int numberOfBytes) {
		byte[] salt = new byte[numberOfBytes];
		random.nextBytes(salt);
		return toHex(salt);
	}
	
	/**
     * 使用 HMAC-SHA1 签名方法对对 encryptText 进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    public static String HmacSHA1Encrypt(String encryptText, String encryptKey ) throws Exception {
        byte[] data = encryptKey.getBytes( Charsets.UTF_8 );
        SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
        Mac mac = Mac.getInstance ( MAC_NAME );
        mac.init( secretKey );
        byte[] text = encryptText.getBytes( Charsets.UTF_8 );
        byte[] digest = mac.doFinal( text );
        StringBuilder sBuilder = bytesToHexString ( digest );
        return sBuilder.toString();
    }
    
    /**
     * 使用 HMAC-SHA1 签名方法对对 encryptText 进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    public static byte[] HmacSHA1EncryptByte(String encryptText, String encryptKey ) throws Exception {
        byte[] data = encryptKey.getBytes( Charsets.UTF_8 );
        
        SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
        Mac mac = Mac.getInstance ( MAC_NAME );
        mac.init( secretKey );
        
        byte[] text = encryptText.getBytes( Charsets.UTF_8 );
        
        byte[] digest = mac.doFinal( text );
        
        return digest;
    }
    
    /**
     * 转换成Hex
     * @param bytesArray
     */
    public static StringBuilder bytesToHexString(byte[] bytesArray ){
        if ( bytesArray == null ){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        for ( byte b : bytesArray ){
            String hv = String.format("%02x", b);
            sBuilder.append( hv );
        }
        
        return sBuilder;
    }
    
    /**
     * 使用 HMAC-SHA1 签名方法对对 encryptText 进行签名
     * @param encryptData 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
     public static String HmacSHA1Encrypt(byte[] encryptData, String encryptKey ) throws Exception {
         byte[] data = encryptKey.getBytes( Charsets.UTF_8 );
         SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
         
         Mac mac = Mac.getInstance ( MAC_NAME );
         mac.init( secretKey );
         byte[] digest = mac.doFinal( encryptData );
         StringBuilder sBuilder = bytesToHexString ( digest );
         return sBuilder.toString();
     }
     
     /**
 	 * 签名
 	 * @param packageParams
 	 * @return
     * @throws Exception
 	 */
 	public static String createSignByKey(Map<String, String> packageParams) {
 		Set<Entry<String, String>> es  = packageParams.entrySet();
 		
 		StringBuilder sb = new StringBuilder();
 		
 		ArrayList<String> list = new ArrayList<String>();
 		
 		Iterator<Entry<String, String>> it = es.iterator();
 		while (it.hasNext()) {
 			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
 			
 			String k = (String) entry.getKey();
 			String v = (String) entry.getValue();
 			if (null != v && !"".equals(v) && !"sign".equals(k)) {
 				list.add(entry.getKey() + "=" + entry.getValue() + "&");
 			}
 		}
 		
 		// 签名算法第一步
	    String[] arrayToSort = list.toArray(new String[list.size()]);
	    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	     
	    for(int i = 0; i < list.size(); i++) {
	        sb.append(arrayToSort[i]);
	    }
	    
	    String baseStr = sb.toString().substring(0, sb.length()-1);
	    
 		return baseStr;
 	}
 	
 	public static String sign(String content, String privateKey) {
 		if ((content == null) || (privateKey == null)) { return null; } 
 		
 		String charset = "utf-8";
 		
 		try { 
 			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decodeBase64(privateKey));
 			
 			KeyFactory keyf = KeyFactory.getInstance("RSA");
 			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
 			Signature signature = Signature.getInstance("SHA256WithRSA");
 			signature.initSign(priKey); 
 			signature.update(content.getBytes(charset));
 			
 			byte[] signed = signature.sign(); 
 			
 			return Base64Utils.encode(signed);
 		} catch (Exception localException) {
 			
 		} 
 		
 		return null; 
 	}
 	
}




