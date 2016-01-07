package com.zykj.aiguanzhu.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.zykj.aiguanzhu.net.UrlContants;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-28 ����10:00:03
 * @version 1.0
 * @Description AsyncHttp �첽������������
 */
public class HttpUtils {
	// public static final String base_url = "http://115.28.208.196:8080/aigz/";
	public static final String base_url = "http://115.28.67.86:8080/aigz/";
	public static final String img_url = "http://115.28.67.86:8080/aigz/upload/";
	private static AsyncHttpClient client = new AsyncHttpClient(); // ʵ��������
	static {
		client.setTimeout(5000); // �������ӳ�ʱ����������ã�Ĭ��Ϊ15s
		client.setMaxRetriesAndTimeout(3, 5000);
		// client.setEnableRedirects(true);
	}

	public static void initClient(Context c) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(c);
		client.setCookieStore(myCookieStore);
	}

	public static AsyncHttpClient getClient() {

		return client;
	}

	/**
	 * 1ע��
	 * 
	 * @param res
	 * @param mobile
	 *            (�ֻ���,����),username(�û���,����),password(����,����),invitecode(������,ѡ��)
	 */
	public static String url_regist(String json) {
		String url = null;
		url = base_url + "data/register?json=" + json;
		return url;
	}

	/**
	 * 2.��½
	 * 
	 * @param res
	 * @param username
	 *            ���û�������password�����룩
	 */
	public static String url_login(String json) {
		String url = null;
		url = base_url + "mdata/mlogin?json=" + json;
		return url;
	}
	
	/**
	 * 3.��������
	 * 
	 * @param res
	 * @param json={"mobile":"15006598533","loginpwd":"333333"}
	 */
	public static String url_resetpsw(String json) {
		String url = null;
		url = base_url + "data/memberforgetpassword?json="+json;
		return url;
	}

	/**
	 * 11. ����
	 * @param json
	 * @return
	 */
	public static String url_integral(String json){
		String url = null;
		url = base_url + "mdata/mIntegral?json=" + json;
		return url;
	}
	
	/**
	 * 12.���ֱ仯
	 * @param json
	 * @return
	 */
	public static String url_integralList(String json){
		String url = null;
		url = base_url + "mdata/integralList?json=" + json;
		return url;
	}
	
	/**
	 * 2.ȷ�϶���
	 * @param json
	 * @return
	 */
	public static String url_confirm(String json){
		String url = null;
		url = base_url + "mdata/mconfirmorder?json=" + json;
		return url;
	}

	/**
	 * 4.��֤�� 
	 * @param json
	 * @return
	 */
	public static String url_yanzhengma(String json){
		String url = null;
		url = base_url + "mdata/identifycode?json=" + json;
		return url;
	}
	
	/**
	 * 5.��ע
	 * @param json
	 * @return
	 */
	public static String url_attention(String json){
		String url = null;
		url = base_url + "mdata/attentionUsers?json=" + json;
		return url;
	}
	
	/**
	 * 6.�û�����
	 * @param json
	 * @return
	 */
	public static String url_attentionDetail(String json){
		String url = null;
		url = base_url + "mdata/memberdetail?json=" + json;
		return url;
	}

	/**
	 * 7.ԤԼ�û�
	 * @param json
	 * @return
	 */
	public static String url_reserationUser(String json){
		String url = null;
		url = base_url + "mdata/reserationUsers?json=" + json;
		return url;
	}
	
	/**
	 * 8.ԤԼ����
	 */
	public static String url_reserationDetail(String json){
		String url = null;
		url = base_url + "mdata/reserationdetail?json=" + json;
		return url;
	}
	
	/**
	 * 9.�޸�ԤԼ״̬
	 * @param json
	 * @return
	 */
	public static String url_reserationUpdatel(String json){
		String url = null;
		url = base_url + "mdata/reserationUpdate?json=" + json;
		return url;
	}
	
	/**
	 * 10.ԤԼɾ��
	 * @param json
	 * @return
	 */
	public static String url_reserationDelete(String json){
		String url = null;
		url = base_url + "mdata/reserationDel?json=" + json;
		return url;
	}
	
	/**
	 * 13.���ֳ�ֵ
	 * @param json
	 * @return
	 */
	public static String url_integralrecharge(String json){
		String url = null;
		url = base_url + "mdata/merchantIntegralAdd?json=" + json;
		return url;
	}
	
	/**
     * ��ȯ����
     * @param json
     * @return
     */
    public static String url_cartdata(String json){
		String url = null;
		url = base_url + "mdata/coupondata?json=" + json;
		return url;
	}
	
	/**
	 * 14.��ȯ����
	 * @param json
	 * @return
	 */
	public static String url_cartcheck(String json){
		String url = null;
		url = base_url + "mdata/verificationindex?json=" + json;
		return url;
	}
	
	 /*�ϴ�ͷ��*/
    public static void postUserAvatar(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.POSIUSERAVATAR), params, handler);
    }
    
    
	
}
