package com.sjhy.platform.biz.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);
	
	private static final int HTTP_TIMEOUT = 10000;
	
	private static class TrustAnyTrustManager implements X509TrustManager {
        
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }  
      
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }  
      
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;  
        }  
    }
	
	public String postRequest(String requestURL, String[][] postParams) {
		HttpClient client = new HttpClient();
		
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		PostMethod post   = new PostMethod(requestURL);
		
		NameValuePair[] data = new NameValuePair[postParams.length];
		try {
			for (int i = 0; i < postParams.length; i++) {
				data[i] = new NameValuePair(postParams[i][0], postParams[i][1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		post.setRequestBody(data);
		try {
			client.executeMethod(post);
			return post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public String postRequest(String requestURL, List<NameValuePair> postParams) {
		HttpClient client = new HttpClient();
		
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		
		PostMethod post   = new PostMethod(requestURL);
		
		NameValuePair[] data = new NameValuePair[postParams.size()];
		try {
			for (int i = 0; i < postParams.size(); i++) {
				data[i] = postParams.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		post.setRequestBody(data);
		
		try {
			client.executeMethod(post);
			return post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
	public Object[] postRequestEntityReturnStatusCode(String requestURL, String postParams) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		PostMethod post = new PostMethod(requestURL);

		try {

			RequestEntity entity = new ByteArrayRequestEntity(postParams.getBytes("UTF-8"));
			post.setRequestEntity(entity);

			client.executeMethod(post);
			int statusCode = post.getStatusCode();
			String responseData = post.getResponseBodyAsString();
			Object[] result = new Object[2];
			result[0] = Integer.valueOf(statusCode);
			result[1] = responseData;
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public Object[] postRequestEntityReturnStatusCode(String requestURL, byte[] postDatas) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		PostMethod post = new PostMethod(requestURL);

		try {

			RequestEntity entity = new ByteArrayRequestEntity(postDatas);
			post.setRequestEntity(entity);

			client.executeMethod(post);
			int statusCode = post.getStatusCode();
			String responseData = post.getResponseBodyAsString();
			Object[] result = new Object[2];
			result[0] = Integer.valueOf(statusCode);
			result[1] = responseData;
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public String getRequest(String requestURL) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		GetMethod get = new GetMethod(requestURL);
		try {
			client.executeMethod(get);
			return get.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return null;
	}
	
	public String getRequest(String requestURL, String[][] headerParams) {
		HttpClient client = new HttpClient();
		
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		GetMethod get = new GetMethod(requestURL);
		
		try {
			for (int i = 0; i < headerParams.length; i++) {
				get.setRequestHeader(headerParams[i][0], headerParams[i][1]);
			}
			
			client.executeMethod(get);
			
			return get.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		
		return null;
	}

	public Object[] postRequestEntity(String requestURL, String postParams, String[][] headerParams) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		PostMethod post = new PostMethod(requestURL);

		try {
			for (int i = 0; i < headerParams.length; i++) {
				post.setRequestHeader(headerParams[i][0], headerParams[i][1]);
			}
			RequestEntity entity = new ByteArrayRequestEntity(postParams.getBytes("UTF-8"));
			post.setRequestEntity(entity);

			client.executeMethod(post);
			int statusCode = post.getStatusCode();
			String responseData = post.getResponseBodyAsString();
			Object[] result = new Object[2];
			result[0] = Integer.valueOf(statusCode);
			result[1] = responseData;
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public String postRequestEntity(String requestURL, String entity) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.connection.timeout", HTTP_TIMEOUT);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		PostMethod post = new PostMethod(requestURL);
		try {
			post.setRequestHeader("Content-Type", "text/json");
			RequestEntity requestEntity = new ByteArrayRequestEntity(entity.getBytes("UTF-8"));
			post.setRequestEntity(requestEntity);
			client.executeMethod(post);
			return post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public String postRequest(String requestURL, String[][] uploadParams, File uploadFile) {
		URL url = null;
		HttpURLConnection urlconn = null;
		BufferedReader rd = null;

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String beginData = "--";
		// 分隔符
		String BOUNDARY = "-------------------------" + getUniqueID(12).toLowerCase();

		StringBuffer sb = new StringBuffer();
		if (uploadParams != null && uploadParams.length > 0) {
			for (int i = 0; i < uploadParams.length; i++) {
				sb.append(beginData);
				sb.append(BOUNDARY).append("\r\n");
				sb.append("Content-Disposition: form-data; name=\"");
				sb.append(uploadParams[i][0]).append("\"").append("\r\n\r\n");
				sb.append(uploadParams[i][1]);
				sb.append("\r\n");
			}
		}
		sb.append(beginData);
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data; name=\"uploadData\"; filename=\"");
		sb.append(uploadFile.getName());
		sb.append("\"\r\n");
		sb.append("Content-Type: application/octet-stream\r\n\r\n");

		byte[] data = sb.toString().getBytes();
		byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

		try {
			url = new URL(requestURL);
			urlconn = (HttpURLConnection) url.openConnection();
			// 设置表单类型和分隔符
			urlconn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 设置内容长度
			urlconn.setRequestProperty("Content-Length", String.valueOf(data.length + uploadFile.length() + endData.length));
			urlconn.setRequestMethod("POST");
			urlconn.setDoOutput(true);
			urlconn.setDoInput(true);
			urlconn.setUseCaches(false);
			urlconn.setRequestProperty("connection", "Keep-Alive");
			urlconn.setRequestProperty("cache-control", "no-cache");

			urlconn.connect();

			OutputStream out = urlconn.getOutputStream();
			bos = new BufferedOutputStream(out);
			bos.write(data);

			InputStream ist = new FileInputStream(uploadFile);
			bis = new BufferedInputStream(ist);

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.write(endData);
			bos.flush();

			rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			sb = new StringBuffer();
			
			while (rd.ready()) {
				sb.append(rd.readLine());
			}
			
			return sb.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (urlconn != null) {
					urlconn.disconnect();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		return null;
	}

	public Object[] postRequestNotFile(String requestURL, String[][] uploadParams) {
		Object[] result=new Object[2];
		URL url = null;
		HttpURLConnection httpConn = null;

		BufferedReader rd = null;

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String beginData = "--";
		// 分隔符
		String BOUNDARY = "-------------------------" + getUniqueID(12).toLowerCase();

		StringBuffer sb = new StringBuffer();
		if (uploadParams != null && uploadParams.length > 0) {
			for (int i = 0; i < uploadParams.length; i++) {
				sb.append(beginData);
				sb.append(BOUNDARY).append("\r\n");
				sb.append("Content-Disposition: form-data; name=\"");
				sb.append(uploadParams[i][0]).append("\"").append("\r\n");
				sb.append("Content-Type: text/plain; charset=utf-8");
				sb.append("Content-Transfer-Encoding: 8bit");
				sb.append("\r\n\r\n");
				sb.append(uploadParams[i][1]);
				sb.append("\r\n");
			}
		}
		
		byte[] data = sb.toString().getBytes();
		byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

		try {
			url      = new URL(requestURL);
			httpConn = (HttpURLConnection) url.openConnection();
			
			// 设置表单类型和分隔符
			httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 设置内容长度
			httpConn.setRequestProperty("Content-Length", String.valueOf(data.length + endData.length));
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("cache-control", "no-cache");

			httpConn.connect();

			OutputStream out = httpConn.getOutputStream();
			bos = new BufferedOutputStream(out);

			bos.write(data);

			bos.write(endData);
			bos.flush();
			
			rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			sb = new StringBuffer();
			while (rd.ready()) {
				sb.append(rd.readLine());
			}
			int reponseCode=httpConn.getResponseCode();
			String responseContent=sb.toString();
			result[0]=reponseCode;
			result[1]=responseContent;
			return result;
		} catch (IOException ex) {
			ex.printStackTrace();
			log.error("postRequestNotFile:",ex);
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (httpConn != null) {
					httpConn.disconnect();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * 发送https请求
	 * @param requestURL
	 * @param postParams
	 * @return
	 */
	public String sendHttpsPost(String requestUrl, String postParams){
		 StringBuffer buffer = new StringBuffer();
		 
		 try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new TrustAnyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            httpUrlConn.setRequestProperty("content-type", "text/json");
            httpUrlConn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("POST");

            // 当有数据需要提交时
            if (null != postParams) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(postParams.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            log.error("Apple server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

		return buffer.toString();
	}

	/**
	 * 发送https请求
	 * @param requestURL
	 * @param postParams
	 * @return
	 */
	public String sendHttpsPostAuthorization(String requestUrl, String postParams, String auth){
		 //StringBuffer buffer = new StringBuffer();

		 HttpsURLConnection httpURLConnection = null;
			OutputStream out;

			TrustManager[] tm = {new TrustAnyTrustManager()};
			try {
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				URL sendUrl = new URL(requestUrl);
				httpURLConnection = (HttpsURLConnection) sendUrl.openConnection();
				httpURLConnection.setSSLSocketFactory(ssf);
				httpURLConnection.setDoInput(true); // true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
				httpURLConnection.setDoOutput(true); // true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
				httpURLConnection.setUseCaches(false); // 禁止缓存
				
				httpURLConnection.setReadTimeout(HTTP_TIMEOUT); // 30秒读取超时
				httpURLConnection.setConnectTimeout(HTTP_TIMEOUT); // 30秒连接超时
				String method = "POST";
				httpURLConnection.setRequestMethod(method);
				httpURLConnection.setRequestProperty("Content-Type", "application/json");
				httpURLConnection.setRequestProperty("Authorization", auth);
				out = httpURLConnection.getOutputStream();
				out.write(postParams.getBytes());
				out.flush();
				out.close();
				InputStream in = httpURLConnection.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = in.read(buff)) != -1) {
					buffer.write(buff, 0, len);
				}
				//System.out.println(String.format("verify sucess response:%s", buffer.toString()));
				
				return buffer.toString();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		return "";
	}

	private String getUniqueID(int len) {
		if (len < 1)
			return null;
		SecureRandom sr = new SecureRandom();
		final char[] alphabet = ("12345abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ67890").toCharArray();
		byte[] b = new byte[len];
		sr.nextBytes(b);
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			int index = b[i] % alphabet.length;
			if (index < 0)
				index += alphabet.length;
			out[i] = alphabet[index];
		}
		return new String(out);
	}

	public static void main(String[] args) {
		HttpUtil a = new  HttpUtil();
		String aa = a.postRequestEntity(
				"http://msdktest.qq.com/auth/check_token?timestamp=1523366890"
				+ "&appid=wxd14b937027eb5c92&sig=3272edd36f52d0a49ad80e9337150e8a"
				+ "&openid=oqGpl0_e7Q2W3Vo_mE-GRS2wkXBk&openkey=8_GNWk9SSe-xSpJkk3pc9vDmSLDLnZvxNtQRW-Kk-cKlQbLxwczqp3b4TMXrKZ05lD89BjzNH-P8qiwYYwM4kVC_MqO1DLDg4EtwN_n8wmGIU&encode=2", 
				"{\"openid\":\"oqGpl0_e7Q2W3Vo_mE-GRS2wkXBk\",\"accessToken\":\"8_GNWk9SSe-xSpJkk3pc9vDmSLDLnZvxNtQRW-Kk-cKlQbLxwczqp3b4TMXrKZ05lD89BjzNH-P8qiwYYwM4kVC_MqO1DLDg4EtwN_n8wmGIU\"}");
		System.out.println(aa);
	}
}
