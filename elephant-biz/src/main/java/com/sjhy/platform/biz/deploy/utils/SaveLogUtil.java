package com.sjhy.platform.biz.deploy.utils;

import java.io.*;

public class SaveLogUtil {
    
	/**
     * 写入渠道统计日志
     * @param fileName
     * @param fileContent
     */
    public static void writeFile(String filePath, String fileName, String fileContent){
    	try{
    		File f = new File(filePath +"/"+ fileName);
    		
    		// 判断文件是否存在
    		if(!f.exists()){
    			f.createNewFile();
    		}
    		
    		// 指定文件写入方式
    		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8");
    		
    		BufferedWriter writer = new BufferedWriter(write);
    		writer.write(fileContent+"\n");
    		
    		writer.close();
    		
    	}catch(Exception e){
    		
    	}
    }
    
    public static byte[] readFile(String fileName) {
    	try{
    		File f = new File(fileName);
    		
    		// 判断文件是否存在
    		if(!f.exists()) {
    			return null;
    		}
    		
    		FileInputStream fileInput = new FileInputStream(f);
    		
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	    
    	    byte[] buffer = new byte[100];
    	    
    	    int n = 0;
    	    
    	    while (-1 != (n = fileInput.read(buffer))) {
    	        output.write(buffer, 0, n);
    	    }
    	    
    	    fileInput.close();
    	    
    	    return output.toByteArray();
    	}catch(Exception e){
    		
    	}
    	
    	return null;
    }
}
