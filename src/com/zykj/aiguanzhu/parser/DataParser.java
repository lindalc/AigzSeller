package com.zykj.aiguanzhu.parser;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.eneity.AttentionUserDetail;
import com.zykj.aiguanzhu.eneity.CartCheck;
import com.zykj.aiguanzhu.eneity.Dingdan;
import com.zykj.aiguanzhu.eneity.MyIntegral;
import com.zykj.aiguanzhu.eneity.ReserationDetail;
import com.zykj.aiguanzhu.eneity.ReserationUser;
import com.zykj.aiguanzhu.eneity.SellerInfo;
import com.zykj.aiguanzhu.net.AigzException;
import com.zykj.aiguanzhu.net.AigzParameters;
import com.zykj.aiguanzhu.net.Utility;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;
/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午10:30:41 
 * @version 1.0 
 * @Description 解析数据类 
 */
public class DataParser {
	
	private static RequestQueue mRequestQueue; 

    private DataParser() {
    	
    }
    
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        //使用默认的 cacheThreadPool
        client.setTimeout(15);
        client.setConnectTimeout(15);
        client.setMaxConnections(20);
        client.setResponseTimeout(20);
    }
    
    public static String request(String url, AigzParameters params, String httpMethod, int loginType) throws AigzException, UnsupportedEncodingException{
		String rlt = null;
		rlt = Utility.openUrl(url, httpMethod, params,loginType);
//		if(rlt != null && rlt.length() != 0){
//			int c = rlt.indexOf("{");
//			if(c != 0){
//				rlt = rlt.subSequence(c, rlt.length()).toString();
//			}
//		}
		rlt = URLDecoder.decode(rlt, "UTF-8");
		ToolsUtil.print("----", "---rlt="+rlt);
		return rlt;

	}

	public static String requestProtocol(String url, AigzParameters params, String httpMethod) throws AigzException{
		String rlt = null;
		rlt = Utility.openUrl(url, httpMethod, params,0);
		return rlt;

	}

/////////////////////////////  Demo  START   ///////////////////////////////////////////
   /** public static void getTestJson(final Handler handler){
        XHttpTool.getData(new Callback() {
 * @throws AigzException 
 * @throws UnsupportedEncodingException 
            @Override
            public void onFailure(Request request, IOException e) {
            }

            
            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().string();
                if (res != null) {
                    // TODO 实体类解析
                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(res);
                        ///////////   解析  //////////////////////
                        JSONObject banner = jsonObject.getJSONObject("xx");
                        TopDishDetail dish = new Gson().fromJson(banner.toString(), token.getType());
                        topDishDetails.add(dish);     
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },BASE_URL+"test");
    }
    */
/////////////////////////////  Demo  END   ///////////////////////////////////////////
	
	public static void cancel(Context mContext){
		mRequestQueue.cancelAll(mContext);
	}
    
/////////////////////////////  我的积分 Start   ///////////////////////////////////////////
	/**
	 * 我的积分
	 * @param method
	 * @param url
	 * @param json
	 * @param handler
	 */
   public static void getMyPsdJson(final Context mContext,int method,String url,JsonObject json,final Handler handler,final int i){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				if(i == 0){ // 积分
					status = response.getJSONObject("status");
					String succeed = status.getString("succeed");
					ToolsUtil.print("----", "succeed:"+succeed);
					if (succeed.equals("1")) //成功
					{
						JSONObject data = response.getJSONObject("data");
						String chargevalue = data.optString("chargevalue");
						String drawvalue = data.optString("drawvalue");
						String rebatevalue = data.optString("rebatevalue");
						String histotalvalue = data.optString("histotalvalue");
						String remainvalue = data.optString("remainvalue");
						
						MyIntegral mPsd = new MyIntegral();
						mPsd.setChargevalue(chargevalue);
						mPsd.setDrawvalue(drawvalue);
						mPsd.setRebatevalue(rebatevalue);
						mPsd.setHistotalvalue(histotalvalue);
						mPsd.setRemainvalue(remainvalue);
						
						Message msg = new Message();
						msg.what = DataConstants.MAINACTIVITY_PSD;
						msg.obj = mPsd;
						handler.sendMessage(msg);
					}
				}else if(i == 1){ //积分变化
					status = response.getJSONObject("status");
					String succeed = status.getString("succeed");
					ToolsUtil.print("----", "succeed:"+succeed);
					if (succeed.equals("1")) //成功
					{
						JSONArray data = response.getJSONArray("data");
						
						ArrayList<MyIntegral> integral = new ArrayList<MyIntegral>();
						
						integral = new Gson().fromJson(data.toString(), new TypeToken<ArrayList<MyIntegral>>(){}.getType());
			
						Message msg = new Message();
						msg.what = DataConstants.MAINACIVITY_INTEGRALLIST;
						msg.obj = integral;
						handler.sendMessage(msg);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
/////////////////////////////  我的积分 End   ///////////////////////////////////////////
   
   /**
    * 我的关注
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getAttention(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				ArrayList<AttentionUser> list = new ArrayList<AttentionUser>();
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				ToolsUtil.print("----", "succeed:"+succeed);
				if (succeed.equals("1")) //成功
				{
					JSONArray data = response.getJSONArray("data");
					TypeToken<ArrayList<AttentionUser>> typetoken = new TypeToken<ArrayList<AttentionUser>>(){};
					list = new Gson().fromJson(data.toString(), typetoken.getType());
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_ATTENTION;
					msg.obj = list;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 用户资料
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getAttentionDetail(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				if (succeed.equals("1")) //成功
				{
					JSONObject data = response.getJSONObject("data");
					
					AttentionUserDetail aUser = new AttentionUserDetail();
					
					aUser = new Gson().fromJson(data.toString(), new TypeToken<AttentionUserDetail>(){}.getType());
					ToolsUtil.print("----", "aUser:"+aUser);
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_ATTENTIONDEAIL;
					msg.obj = aUser;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 预约详情
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getReserationDetail(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				ToolsUtil.print("----", "succeed:"+succeed);
				if (succeed.equals("1")) //成功
				{
					JSONObject data = response.getJSONObject("data");
					
					ReserationDetail aUser = new ReserationDetail();
					
					aUser = new Gson().fromJson(data.toString(), new TypeToken<ReserationDetail>(){}.getType());
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_RESERATIONDETAIL;
					msg.obj = aUser;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 订单验证码
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getYanZhengMa(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				String errdesc = status.getString("errdesc");
				if (succeed.equals("1")) //成功
				{
					JSONObject data = response.getJSONObject("data");
					
					Dingdan dingdan = new Dingdan();
					
					dingdan = new Gson().fromJson(data.toString(), new TypeToken<Dingdan>(){}.getType());
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_CODE;
					msg.obj = dingdan;
					handler.sendMessage(msg);
				}else if(succeed.equals("0")){
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_CODE_FAULT;
					msg.obj = errdesc;
					handler.sendMessage(msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 预约删除
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getReserationDelete(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				String errdesc = status.getString("errdesc");
				ToolsUtil.print("----", "succeed:"+succeed);
				Message msg = new Message();
				msg.what = DataConstants.RESERATION_DELETE;
				msg.obj = errdesc;
				handler.sendMessage(msg);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 预约用户
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getReserationUser(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				ToolsUtil.print("----", "succeed:"+succeed);
				if (succeed.equals("1")) //成功
				{
					JSONArray data = response.getJSONArray("data");
					
					ArrayList<ReserationUser> list = new ArrayList<ReserationUser>();
					
					list = new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ReserationUser>>(){}.getType());
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_RESERATIONUSER;
					msg.obj = list;
					handler.sendMessage(msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 卡券核销
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getCartCheck(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				ToolsUtil.print("----", "succeed:"+succeed);
				if (succeed.equals("1")) //成功
				{
					JSONArray data = response.getJSONArray("data");
					
					ArrayList<CartCheck> list = new ArrayList<CartCheck>();
					
					list = new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CartCheck>>(){}.getType());
					
					Message msg = new Message();
					msg.what = DataConstants.MAINACTIVITY_CARTCHECK;
					msg.obj = list;
					handler.sendMessage(msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 确认订单
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getConfirm(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				if (succeed.equals("1")) //成功
				{
					JSONObject data = response.getJSONObject("data");
					
					Dingdan aUser = new Dingdan();
					
					aUser = new Gson().fromJson(data.toString(), new TypeToken<Dingdan>(){}.getType());
					ToolsUtil.print("----", "aUser:"+aUser);
					
					Message msg = new Message();
					msg.what = DataConstants.DINGDAN_CONFIRM;
					msg.obj = aUser;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   /**
    * 修改预约状态
    * @param mContext
    * @param method
    * @param url
    * @param json
    * @param handler
    */
   public static void getReserationUpdate(final Context mContext,int method,String url,JsonObject json,final Handler handler){
	   ToolsUtil.print("----", "url:"+url);
	   mRequestQueue =  Volley.newRequestQueue(mContext);  
	   JsonObjectRequest jr = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			
			JSONObject status;
			try {
				status = response.getJSONObject("status");
				String succeed = status.getString("succeed");
				String errdesc = status.getString("errdesc");
				ToolsUtil.print("----", "succeed:"+succeed);
				Message msg = new Message();
				msg.what = DataConstants.RESERATION_DETAIL;
				msg.obj = errdesc;
				handler.sendMessage(msg);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	   }, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			RequestDailog.closeDialog();
            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		}
	});
	   mRequestQueue.add(jr);  
   }
   
   
}
