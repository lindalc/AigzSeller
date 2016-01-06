package com.zykj.aiguanzhu.utils;


import com.zykj.aiguanzhu.custome.UIDialog;

import android.content.Context;
import android.util.Log;
import android.view.View.OnClickListener;

/**
 * @author lc
 * @date 创建时间：2015-12-23 上午10:08:16
 * @version 1.0 
 * @Description 工具类
 */
public class ToolsUtil {
	
	private static final String TAG = "ToolsUtil";
	
	// 打印方法
	public static void print(String tag, String msg) {
		if(true){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * Exception 信息提示
	 */
	public static void Notic(Context context, String notic,
			OnClickListener listener) {
		UIDialog.ForNotic(context, notic, listener);
	}
	
}
