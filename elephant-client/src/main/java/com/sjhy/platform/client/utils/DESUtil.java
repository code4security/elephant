package com.sjhy.platform.client.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.SecureRandom;

/**
 * 工具类
 * 类说明:
 **/
public class DESUtil {
	private static final Logger logger = Logger.getLogger(DESUtil.class);
	private Cipher ecipher;
	private Cipher dcipher;

	public DESUtil(String keyStr) {
		SecretKey key = null;
		key = getKey(keyStr);

		try {
			ecipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			dcipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (javax.crypto.NoSuchPaddingException e) {
			logger.error(e.getMessage(),e);
		} catch (java.security.NoSuchAlgorithmException e) {
			logger.error(e.getMessage(),e);
		} catch (java.security.InvalidKeyException e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 生成密钥
	 * 
	 */
	public static void generateKeyToFile(String keyFile) {
		try {
			SecureRandom sr = new SecureRandom();
			// 为我们选择的DES算法生成KeyGenerator对象
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);
			FileOutputStream fos = new FileOutputStream(keyFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// 生成密钥
			SecretKey key = kg.generateKey();
			oos.writeObject(key);
			oos.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 得到密钥
	 * 
	 * @return
	 */
	public static SecretKey getKeyFromFile(String keyFile) {
		SecretKey kp = null;
		try {
			InputStream is = new FileInputStream(keyFile);
			ObjectInputStream oos = new ObjectInputStream(is);
			kp = (SecretKey) oos.readObject();
			oos.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return kp;
	}

	/**
	 * 根据字符串得到key
	 * 
	 * @param strKey
	 *            密钥明文
	 */
	public SecretKey getKey(String strKey) {
		try {
			byte[] rawKey = strKey.getBytes("UTF-8");
			DESKeySpec dks = new DESKeySpec(rawKey);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			return secretKey;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public String getEncAndBase64String(String strMing) {
		if (strMing == null) {
			strMing = "";
		}
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		try {
			byteMing = strMing.getBytes("UTF-8");
			byteMi = this.getEncCode(byteMing);

			Base64 base64 = new Base64();

			strMi = new String(base64.encode(byteMi), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String getDesAndBase64String(String strMi) {
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			Base64 base64 = new Base64();
			byteMi = base64.decode(strMi.getBytes("UTF-8"));
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public byte[] getEncCode(byte[] byteS) throws IllegalBlockSizeException, BadPaddingException {
		byte[] byteFina = null;

		byteFina = ecipher.doFinal(byteS);
		
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public byte[] getDesCode(byte[] byteD) throws IllegalBlockSizeException, BadPaddingException {
		byte[] byteFina = null;
		
		byteFina = dcipher.doFinal(byteD);
		
		return byteFina;
	}

	public void encrypt(InputStream in, OutputStream out) {
		try {
			// Bytes written to out will be encrypted
			out = new CipherOutputStream(out, ecipher);

			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			byte[] buf = new byte[1024];
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} catch (java.io.IOException e) {
			logger.error(e.getMessage(),e);
		}
	}

	public void decrypt(InputStream in, OutputStream out) {
		try {
			// Bytes read from in will be decrypted
			in = new CipherInputStream(in, dcipher);

			// Read in the decrypted bytes and write the cleartext to out
			int numRead = 0;
			byte[] buf = new byte[1024];
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
		} catch (java.io.IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	public static void main(String[] args) {
		DESUtil des = new DESUtil("JbJ9kk0nO8mDV9BVL5OOX40VH9dKesZW");
		
		String key = des.getEncAndBase64String("com.kairogame.android.onsen");
		
		System.out.println(key);
	}
}
