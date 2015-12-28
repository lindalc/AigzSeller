package com.zykj.aiguanzhu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-23 ����10:22:30 
 * @version 1.0 
 * @Description 
 * 							  1. ��Ļpx --->ת��dp<br/>
 *  						  2.��ÿؼ�����Ļ�е�λ��<br/> 
 *  						  3. ��õ�ǰ���ڵĿ�� <br/>
 */
public class ScreenUtil {
   
	private static int[] px = new int[101];
	private static int[] py = new int[101];
	private int w,h;
	private DisplayMetrics dm;
	public ScreenUtil(Activity activity){
		dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		w = dm.widthPixels;
		h = dm.heightPixels;
		int length = px.length;
		for(int index=0;index<length;index++){
			px[index] = (int)(w*0.01*index);
			py[index] = (int)(h*0.01*index);
		}
	}

	/**
	 * �����ֻ���ǰ����Ļ�ܶȣ����� dp �� px ��λ�Ļ���
	 * @param context
	 * @param dp    ��ת����
	 * @return
	 */
	public static int dp2px(Context context,    int dp){
		int ret = 0;
		Resources resources = context.getResources();
		//  ��ȡ��Ļ�Ĳ�����Ϣ
		DisplayMetrics metrics = resources.getDisplayMetrics();
		// px = dp * (dpi /160)
		ret = (int)(dp * metrics.density);
		return ret;
	}
	
	/**
	 * ��ȡ��ǰ��Ļ�Ŀ��
	 * @return
	 */
	public int getScreenWidth(){
		return w;
	}

	/**
	 * ��ȡ��ǰ����Ļ�ĸ�
	 * @return
	 */
	public int getScreenHeight(){
		return h;
	}

	/**
	 * <li>��ȡ�ؼ��ڴ����е�λ��</li>
	 * <li>������Ҫ�ڽ��洴��֮��</li>
	 * <li>״̬��������������</li>
	 * @param view
	 * @return
	 */
	public int[] getLocationInWindow(View view){
		int[] ret = null;
		if (view != null) {
			ret = new int[2];
			view.getLocationInWindow(ret);
		}
		return ret;
	}

	/**
	 *<li>��ȡ�ؼ���������Ļ�ϵ�λ��</li>
	 * <li>������Ӧ�õ�״̬��Ҳ�������ڡ�</li>
	 * <li>������Ҫ�ڽ��洴��֮��</li>
	 * @param view
	 * @return
	 */
	public int[] getLocationOnScreen(View view){

		int[] ret = null;

		if (view != null) {
			ret = new int[2];
			view.getLocationOnScreen(ret);
		}
		return ret;
	}

	/**
	 * ���x��ֵ
	 * @param index
	 * @return
	 */
	public static int getpixels_x(int index){
		return px[index];
	}
	/**
	 * ���y��ֵ
	 * @param index
	 * @return
	 */
	public static int getpixels_y(int index){
		return py[index];
	}

	public void setViewInWindow(float x , float y){

	}

	public static  void setTranslucentStatus(Activity activity){
		// API ���ڵ���19
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			Window window = activity.getWindow();

			WindowManager.LayoutParams winParams = window.getAttributes();

			final  int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

			winParams.flags  |= bits;

			window.setAttributes(winParams);

		}

	}

}
