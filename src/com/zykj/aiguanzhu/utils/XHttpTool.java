package com.zykj.aiguanzhu.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-23 ����10:26:08 
 * @version 1.0 
 * @Description ���繤����,����okHttp
 */
public final class XHttpTool {

	private XHttpTool(){

    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        //ʹ��Ĭ�ϵ� cacheThreadPool
        client.setTimeout(15);
        client.setConnectTimeout(15);
        client.setMaxConnections(20);
        client.setResponseTimeout(20);
    }

}
