package com.sjhy.platform.biz.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	private MessageDigest md5;
	
    public MD5Util() {
    	try{
    		md5 = MessageDigest.getInstance("MD5");
    	} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static MD5Util me(){
    	return SingletonHolder.instance;
    }
    
    public byte[] md5(byte[] data) {
        return md5.digest(data);
    }

    public byte[] md5(String data) {
        return md5(data.getBytes());
    }
    public String md5Base64(byte[] data)
    {
    	byte[] base64Byte=null;
    	String base64Str="";
    	
		base64Byte = md5(data);    		
		base64Str=Base64Utils.encode(base64Byte);
    	
    	return base64Str;    	
    	
    }
    public String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    public String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
    
    public void update(byte[] data,int begin,int offset){
    	md5.update(data,begin,offset);
    }
    
    public String getDigestHex(){
    	byte[] digestData = md5.digest();
    	
    	if (digestData != null && digestData.length > 0){
    		return HexUtil.toHexString(digestData);
    	}
    	
    	return null;
    }
    
    private static class SingletonHolder{
		protected static final MD5Util instance = new MD5Util();
	}
    
    public static void main(String[] args) {
    	
    //	String md5str="nasifhiashfisfiwhiefnviwehfgeiewhfiwuhfiw";
    	
    	//String md5datal=MD5Util.me().md5Base64(md5str.getBytes());
    
    	
    	
		//System.out.println(MD5Util.me().md5Hex("JbJ9kk0nO8mDV9BVL5OOX40VH9dKesZW|"+"com.kairogame.android.onsen"));
	}
}
