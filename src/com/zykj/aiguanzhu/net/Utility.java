package com.zykj.aiguanzhu.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.channels.UnresolvedAddressException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.eneity.MorePicture;
import com.zykj.aiguanzhu.utils.ToolsUtil;



/**
 * @author  lc 
 * @date 创建时间：2015-12-24 下午4:36:02 
 * @version 1.0 
 * @Description
 */
public class Utility {
	private static AigzParameters mRequestHeader = new AigzParameters();
	private static HttpHeaderFactory mAuth;
	
	private static final int SET_CONNECTION_TIMEOUT = 50000;
	private static final int SET_SOCKET_TIMEOUT = 30000;
	
	public static final String HTTPMETHOD_POST = "POST";
	public static final String HTTPMETHOD_GET = "GET";
	public static final String HTTPMETHOD_DELETE = "DELETE";
	
	public static final String BOUNDARY = "7cd4a6d158c";
	public static final String MP_BOUNDARY = "--" + BOUNDARY;
	public static final String END_MP_BOUNDARY = "--" + BOUNDARY + "--";
	public static final String MULTIPART_FORM_DATA = "multipart/form-data";
	
	
	public static void setHeader(String httpMethod, HttpUriRequest request,AigzParameters authParam, String url) {
		if (!isBundleEmpty(mRequestHeader)) {
			for (int loc = 0; loc < mRequestHeader.size(); loc++) {
				String key = mRequestHeader.getKey(loc);
				request.setHeader(key, mRequestHeader.getValue(key));
			}
		}
		if (!isBundleEmpty(authParam) && mAuth != null) {
			String authHeader = mAuth.getWeiboAuthHeader(httpMethod, url, authParam);
			if (authHeader != null) {
				request.setHeader("Authorization", authHeader);
			}
		}
		request.setHeader("User-Agent", System.getProperties().getProperty("http.agent"));
	}
	
	public static boolean isBundleEmpty(AigzParameters bundle) {
		/*
		 * if (bundle == null || bundle.size() == 0) { return true; }
		 */
		if (bundle == null) {
			return true;
		}
		return false;
	}

	
	
	public static String encodeUrl(AigzParameters parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0; loc < parameters.size(); loc++) {
			if (first){
				first = false;
			}
			else {
				sb.append(",");
			}
			try {
				if(!parameters.getKey(loc).equals("pic")){
					sb.append(URLEncoder.encode("\""+parameters.getKey(loc)+"\"", "UTF-8") + ":" + URLEncoder.encode("\""+parameters.getValue(loc)+"\"", "UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static HttpClient getNewHttpClient(long timeout) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();

			// HttpConnectionParams.setConnectionTimeout(params, 10000);
			// HttpConnectionParams.setSoTimeout(params, 10000);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			// HttpProtocolParams.setContentCharset(params, HTTP.);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			// Set the default socket timeout (SO_TIMEOUT) // in
			// milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setConnectionTimeout(params, Utility.SET_CONNECTION_TIMEOUT);
			long soc_time = Utility.SET_SOCKET_TIMEOUT + timeout;
			HttpConnectionParams.setSoTimeout(params, (int) soc_time);
			HttpClient client = new DefaultHttpClient(ccm, params);
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
	
	public static String openUrl(String url,String method,AigzParameters params){
		if(method.endsWith("GET")){
			url = url + "?json={" + encodeUrl(params)+"}"; // Log.e("url",url);
			Log.e("----","url"+url);
			try {
				url = URLDecoder.decode(url,"UTF-8");
				ToolsUtil.print("123123", "123");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;
	}
	
	public static String openUrl(String url, String method, AigzParameters params, List<MorePicture> filePath) throws AigzException {
		String result = "";

		long timeout = 0;
		/*
		 * File files = null; if(!TextUtils.isEmpty(file)){ files = new File(file); timeout = files.length() * 1000/(PER_SPEED * 1024); }
		 */

		HttpClient client = getNewHttpClient(timeout);
		// mClient = client;
		try {
			HttpUriRequest request = null;
			ByteArrayOutputStream bos = null;

			if (method.equals("GET")) {
				url = url + "?json={" + encodeUrl(params)+"}"; // Log.e("url",url);
				Log.e("url", url);
				HttpGet get = new HttpGet(url);
				request = get;
				ToolsUtil.print("----", "---request="+request);
			} else if (method.equals("POST")) {
				HttpPost post = new HttpPost(url);
				byte[] data = null;
				bos = new ByteArrayOutputStream(1024 * 50);
				if (filePath != null && filePath.size() > 0) {
					/*
					 * UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List<? extends NameValuePair>) params); entity.setContentEncoding("UTF-8"); //entity.setContentType("application/json"); post.setEntity(entity);
					 */
					Utility.paramToUpload(bos, params);
					post.setHeader("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);
					// post.setHeader("Charset", "UTF-8");
					for (int i = 0; i < filePath.size(); i++) {
						Log.e("filePath", filePath.get(i).filePath);
						/* Bitmap bf = BitmapFactory.decodeFile(filePath.get(i).filePath); */
						// Log.e("length", String.valueOf(file.length()));
						Utility.fileContentToUpload(bos, new File(filePath.get(i).filePath), filePath.get(i).key);
					}
					bos.write(("\r\n" + END_MP_BOUNDARY).getBytes());
				} else {
					post.setHeader("Content-Type", "application/x-www-form-urlencoded");
					String postParam = encodeParameters(params);
					data = postParam.getBytes("UTF-8");
					bos.write(data);
				}
				data = bos.toByteArray();
				bos.close();
				// UrlEncodedFormEntity entity = getPostParamters(params);
				ByteArrayEntity formEntity = new ByteArrayEntity(data);
				post.setEntity(formEntity);
				request = post;
			} else if (method.equals("DELETE")) {
				request = new HttpDelete(url);
			}
			setHeader(method, request, params, url);
			HttpResponse response = client.execute(request);
			ToolsUtil.print("----", "---response="+response);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();

			if (statusCode != 200) {
				result = read(response);
				String err = null;
				int errCode = 0;
				try {
					JSONObject json = new JSONObject(result);
					err = json.getString("error");
					errCode = json.getInt("error_code");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// throw new WeiboException(String.format(err), errCode);e
			}
			// parse content stream from response
			result = read(response);
			return result;
		} catch (IOException e) {
			Log.e("e.getClass()", e.getClass().toString());
			if (e.getClass().toString().equalsIgnoreCase("class java.nio.channels.UnresolvedAddressException")) {
				throw new AigzException("UnresolvedAddress", e, R.string.unknown_addr);
			} else if (e.getClass().toString().equalsIgnoreCase("class java.net.UnknownHostException")) {
				throw new AigzException("UnknownHost", e, R.string.error_host);
			} else if (e.getClass().toString().equalsIgnoreCase("class org.apache.http.conn.ConnectTimeoutException")) {
				throw new AigzException("ConnectionTimeout", e, R.string.timeout);
			} else if (e.getClass().toString().equalsIgnoreCase("class java.net.SocketTimeoutException")) {
				throw new AigzException("SocketTimeout", e, R.string.timeout);
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
				client = null;
			}
		}
		return null;
	}
	
	/**
	 * Implement a weibo http request and return results .
	 * 
	 * @param context
	 *            : context of activity
	 * @param url
	 *            : request url of open api
	 * @param method
	 *            : HTTP METHOD.GET, POST, DELETE
	 * @param params
	 *            : Http params , query or postparameters
	 * @param Token
	 *            : oauth token or accesstoken
	 * @param loginType
	 *            1-需要检测用户是否登录 0-不需要检测
	 * @return UrlEncodedFormEntity: encoed entity
	 * @throws ConnectTimeoutException
	 * @throws UnresolvedAddressException
	 * @throws SocketTimeoutException
	 * @throws UnknownHostException
	 * 
	 */

	public static String openUrl(String url, String method, AigzParameters params, int loginType) throws AigzException {
		/* params.add("userType", "2"); */

		
		/*
		 * if(loginType == 1){ params.add("uid", Common.getUid(StringsApp.getInstance())); }else if(loginType == 2){ if(!TextUtils.isEmpty(Common.getUid(StringsApp.getInstance()))){ params.add("uid", Common.getUid(StringsApp.getInstance())); } }
		 */

		String rlt = "";
		if (params != null && !params.equals("")) {
			for (int loc = 0; loc < params.size(); loc++) {
				String key = params.getKey(loc);
				if (key.equals("pic")) {
					// HashMap<String,List<MorePicture>> picMap =params.getPicList("pic");
					params.remove(key);
				}
			}
		}

		rlt = openUrl(url, method, params);

		ToolsUtil.print("----", "---rlt="+rlt);
		return rlt;
	}
	
	private static byte[] getFileByte(File file) {

		byte[] buffer = null;
		FileInputStream fin;
		try {
			fin = new FileInputStream(file.getPath());
			int length;
			try {
				length = fin.available();
				buffer = new byte[length];
				fin.read(buffer);
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * Read http requests result from response .
	 * 
	 * @param response
	 *            : http response by executing httpclient
	 * 
	 * @return String : http response content
	 */
	public static String read(HttpResponse response) {
		String result = "";
		HttpEntity entity = response.getEntity();
		InputStream inputStream;
		try {
			inputStream = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			// Read response into a buffered stream
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			// Return result from buffered stream
			result = new String(content.toByteArray(), "UTF-8");
			return result;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Upload weibo contents into output stream .
	 * 
	 * @param baos
	 *            : output stream for uploading weibo
	 * @param params
	 *            : post parameters for uploading
	 * @return void
	 */
	private static void paramToUpload(OutputStream baos, AigzParameters params) {
		String key = "";
		for (int loc = 0; loc < params.size(); loc++) {
			try {
				// key = URLEncoder.encode(params.getKey(loc), "UTF-8");
				key = params.getKey(loc);
				StringBuilder temp = new StringBuilder(10);
				temp.setLength(0);
				temp.append(MP_BOUNDARY).append("\r\n");
				temp.append("content-disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
				// temp.append(URLEncoder.encode(params.getValue(key), "UTF-8")).append("\r\n");
				temp.append(params.getValue(key)).append("\r\n");
				byte[] res;
				res = temp.toString().getBytes();
				baos.write(res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Upload file into output stream .
	 * 
	 * @param out
	 *            : output stream for uploading
	 * @param file
	 *            : file for uploading
	 * @param filekey
	 *            :uploaded files' key;
	 * @return void
	 */
	private static void fileContentToUpload(OutputStream out, /* Bitmap imgpath */File file, String key) throws AigzException {
		StringBuilder temp = new StringBuilder();

		temp.append(MP_BOUNDARY).append("\r\n");
		/*
		 * temp.append("Content-Disposition: form-data; name=\"f_upload\"; filename=\"" + file.getName() + "") .append("").append("\"\r\n");
		 */
		temp.append("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + file.getName())
		/* .append(key+".png") */.append("").append("\"\r\n");
		byte[] fileData = getFileByte(file);
		String filetype = "multipart/form-data";

		temp.append("Content-Type: ").append(filetype).append("\r\n\r\n");
		byte[] res = temp.toString().getBytes();
		BufferedInputStream bis = null;
		try {
			out.write(res);
			out.write(fileData);
			// imgpath.compress(CompressFormat.PNG, 75, out);
			out.write("\r\n".getBytes());
			// out.write(("\r\n" + END_MP_BOUNDARY).getBytes());
		} catch (IOException e) {
			throw new AigzException(e);
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					throw new AigzException(e);
				}
			}
		}
	}

	public static String encodeParameters(AigzParameters httpParams) {
		if (null == httpParams || Utility.isBundleEmpty(httpParams)) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		int j = 0;
		for (int loc = 0; loc < httpParams.size(); loc++) {
			String key = httpParams.getKey(loc);
			if (j != 0) {
				buf.append(",");
			}
			try {
				buf.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
			j++;
		}
		return buf.toString();

	}
	
	public static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}
	}

}
