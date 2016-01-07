package com.zykj.aiguanzhu.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.zykj.aiguanzhu.net.UrlContants;

/**
 * @author lc
 * @date 创建时间：2015-12-28 上午10:00:03
 * @version 1.0
 * @Description AsyncHttp 异步联网第三方库
 */
public class HttpUtils {
	// public static final String base_url = "http://115.28.208.196:8080/aigz/";
	public static final String base_url = "http://115.28.67.86:8080/aigz/";
	public static final String img_url = "http://115.28.67.86:8080/aigz/upload/";
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(5000); // 设置链接超时，如果不设置，默认为15s
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
	 * 1注册
	 * 
	 * @param res
	 * @param mobile
	 *            (手机号,必填),username(用户名,必填),password(密码,必填),invitecode(邀请码,选填)
	 */
	public static String url_regist(String json) {
		String url = null;
		url = base_url + "data/register?json=" + json;
		return url;
	}

	/**
	 * 2.登陆
	 * 
	 * @param res
	 * @param username
	 *            （用户名），password（密码）
	 */
	public static String url_login(String json) {
		String url = null;
		url = base_url + "mdata/mlogin?json=" + json;
		return url;
	}
	
	/**
	 * 3.忘记密码
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
	 * 11. 积分
	 * @param json
	 * @return
	 */
	public static String url_integral(String json){
		String url = null;
		url = base_url + "mdata/mIntegral?json=" + json;
		return url;
	}
	
	/**
	 * 12.积分变化
	 * @param json
	 * @return
	 */
	public static String url_integralList(String json){
		String url = null;
		url = base_url + "mdata/integralList?json=" + json;
		return url;
	}
	
	/**
	 * 2.确认订单
	 * @param json
	 * @return
	 */
	public static String url_confirm(String json){
		String url = null;
		url = base_url + "mdata/mconfirmorder?json=" + json;
		return url;
	}

	/**
	 * 4.验证码 
	 * @param json
	 * @return
	 */
	public static String url_yanzhengma(String json){
		String url = null;
		url = base_url + "mdata/identifycode?json=" + json;
		return url;
	}
	
	/**
	 * 5.关注
	 * @param json
	 * @return
	 */
	public static String url_attention(String json){
		String url = null;
		url = base_url + "mdata/attentionUsers?json=" + json;
		return url;
	}
	
	/**
	 * 6.用户资料
	 * @param json
	 * @return
	 */
	public static String url_attentionDetail(String json){
		String url = null;
		url = base_url + "mdata/memberdetail?json=" + json;
		return url;
	}

	/**
	 * 7.预约用户
	 * @param json
	 * @return
	 */
	public static String url_reserationUser(String json){
		String url = null;
		url = base_url + "mdata/reserationUsers?json=" + json;
		return url;
	}
	
	/**
	 * 8.预约详情
	 */
	public static String url_reserationDetail(String json){
		String url = null;
		url = base_url + "mdata/reserationdetail?json=" + json;
		return url;
	}
	
	/**
	 * 9.修改预约状态
	 * @param json
	 * @return
	 */
	public static String url_reserationUpdatel(String json){
		String url = null;
		url = base_url + "mdata/reserationUpdate?json=" + json;
		return url;
	}
	
	/**
	 * 10.预约删除
	 * @param json
	 * @return
	 */
	public static String url_reserationDelete(String json){
		String url = null;
		url = base_url + "mdata/reserationDel?json=" + json;
		return url;
	}
	
	/**
	 * 13.积分充值
	 * @param json
	 * @return
	 */
	public static String url_integralrecharge(String json){
		String url = null;
		url = base_url + "mdata/merchantIntegralAdd?json=" + json;
		return url;
	}
	
	/**
     * 卡券数据
     * @param json
     * @return
     */
    public static String url_cartdata(String json){
		String url = null;
		url = base_url + "mdata/coupondata?json=" + json;
		return url;
	}
	
	/**
	 * 14.卡券核销
	 * @param json
	 * @return
	 */
	public static String url_cartcheck(String json){
		String url = null;
		url = base_url + "mdata/verificationindex?json=" + json;
		return url;
	}
	
	 /*上传头像*/
    public static void postUserAvatar(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.POSIUSERAVATAR), params, handler);
    }
    
    
	
}
