package com.zykj.aiguanzhu.parser;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.net.AigzException;
import com.zykj.aiguanzhu.net.AigzParameters;
import com.zykj.aiguanzhu.net.Utility;
import com.zykj.aiguanzhu.utils.ToolsUtil;
import com.zykj.aiguanzhu.utils.XHttpTool;
/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午10:30:41 
 * @version 1.0 
 * @Description 解析数据类 
 */
public class DataParser {
	
	public static final String BASE_URL = "http://115.28.67.86:8080/aigz/";

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
    
/////////////////////////////  关注用户 Start   ///////////////////////////////////////////
    //TODO 把url完善,可以用bundle放参数进去
    public static void getAttentionUserJson(AsyncHttpResponseHandler handler) throws UnsupportedEncodingException, AigzException{
    	AigzParameters bundle = new AigzParameters();
    	bundle.add("merchantid", 1+"");
    	bundle.add("pagenumber", 1+"");
    	bundle.add("pagesize", 10+"");
    	String url = BASE_URL + "mdata/attentionUsers";
    	String reString = request(url, bundle, Utility.HTTPMETHOD_GET,0);
    	
        client.get(reString, handler);
    }
/////////////////////////////  关注用户 End   ///////////////////////////////////////////
}
