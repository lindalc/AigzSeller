package com.zykj.aiguanzhu.utils;


import com.zykj.aiguanzhu.custome.UIDialog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.View.OnClickListener;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-23 ����10:08:16
 * @version 1.0 
 * @Description ������
 */
public class ToolsUtil {
	
	private static final String TAG = "ToolsUtil";
	
	// ��ӡ����
	public static void print(String tag, String msg) {
		if(true){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * Exception ��Ϣ��ʾ
	 */
	public static void Notic(Context context, String notic,
			OnClickListener listener) {
		UIDialog.ForNotic(context, notic, listener);
	}
	
	/**
	 * ��ȡ��ǰӦ�õİ汾��
	 */

	public static int getAppVersion(Context context) {
		int version = 0;
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			version = packinfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return version;
		}

		return version;
	}
	
}
