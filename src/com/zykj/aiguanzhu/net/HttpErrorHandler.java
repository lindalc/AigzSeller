package com.zykj.aiguanzhu.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zykj.aiguanzhu.parser.DataConstants;

/**
 * Created by ss on 15-4-14.
 */
public abstract class HttpErrorHandler extends AbstractHttpHandler {
    private static final String TAG="httpResponse";
    
    public void onJsonSuccess(JSONObject json) {
       String status= json.getString("code");
       String msg=  json.getString("message");
        if(TextUtils.isEmpty(status) || !status.equals("200")){
            if(!TextUtils.isEmpty(msg)){
            Log.e(TAG,msg);}
            onRecevieFailed(status,json);
        }else{
         	onRecevieSuccess(json);
        }
    }

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        try {
			String responseString = responseBody == null?"":new String(responseBody, HTTP.UTF_8);
	        onRecevieFailed("400", JSON.parseObject(DataConstants.ERROR.replace("null", "\""+responseString+"\"")));
//			MyRequestDailog.closeDialog();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

    public abstract void onRecevieSuccess(JSONObject json);

    public void onRecevieFailed(String status,JSONObject json){};
}
