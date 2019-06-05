package nettynio.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *
* @Title: YouguuHttpsClient.java
* @Package com.youguu.game.pay.util
* @Description: http请求类
* @author wangd
* @date 2014年5月5日 上午9:19:04
* @version V1.0
 */
public class YouguuHttpsClient {
	private static String CHARCODING = "UTF-8";
	private int TIMEOUT = 3000;
	public static YouguuHttpsClient  getClient(){
		return new YouguuHttpsClient();
	}

	/**
	 * get请求
	 * @param strUrl
	 * @return
	 */
	public String doGet(String strUrl){

		InputStreamReader insr = null;
		BufferedReader br = null;
		try {

			URL url = new URL(strUrl);
			HttpURLConnection httpcon = (HttpURLConnection) url
					.openConnection();

			httpcon.setDoOutput(false);
			httpcon.setDoInput(true);
			httpcon.setUseCaches(false);
			httpcon.setInstanceFollowRedirects(true);

			httpcon.setRequestMethod("GET");

			//设置10分钟超时
			httpcon.setConnectTimeout(10*10 *1000);
			httpcon.setReadTimeout(10*10 *1000);
			httpcon.connect();
			InputStream is = httpcon.getInputStream();


			insr = new InputStreamReader(is,"UTF-8");
			br = new BufferedReader(insr);
			StringBuffer sb = new StringBuffer("");
			String line  = null;
			while( (line = br.readLine()) !=null){
				sb.append(line);
			}
			httpcon.disconnect();
			return sb.toString();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(insr!=null){
				try {
					insr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 */
		public String doPost(String url,String content){
		OutputStream os = null;
		InputStreamReader insr = null;
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		try {
			URL reqURL = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();

			httpsConn.setSSLSocketFactory(this.getSSLSocketFactory());
			//设置连接和超时时间
			httpsConn.setConnectTimeout(TIMEOUT);

			httpsConn.setReadTimeout(TIMEOUT);

			// 以post方式通信
			httpsConn.setRequestMethod("POST");
			// 设置请求默认属性
//			httpsConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

			if(content!=null){
				httpsConn.setDoOutput(true);

				os = httpsConn.getOutputStream();

				osw = new OutputStreamWriter(os, "UTF-8");

				osw.write(content);

				osw.flush();

			}

			if(httpsConn.getResponseCode()==200){
				insr = new InputStreamReader(httpsConn.getInputStream(),CHARCODING);
				br = new BufferedReader(insr);
				StringBuffer sb = new StringBuffer("");
				String line  = null;
				while( (line = br.readLine()) !=null){
					sb.append(line);
				}
				httpsConn.disconnect();
				return sb.toString();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(osw!=null){
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(insr!=null){
				try {
					insr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}


	/**
	 *
	* @Title: YouguuHttpsClient.java
	* @Package com.youguu.game.pay.util
	* @Description: 证书信任管理
	* @author wangd
	* @date 2014年5月5日 上午9:23:10
	* @version V1.0
	 */
	class MyX509TrustManager implements javax.net.ssl.X509TrustManager{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	private SSLSocketFactory getSSLSocketFactory(){
		SSLSocketFactory ssf  = null;
		TrustManager[] tm = {new MyX509TrustManager ()};
		SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			ssf = sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//从上述SSLContext对象中得到SSLSocketFactory对象

		return ssf;
	}

}
