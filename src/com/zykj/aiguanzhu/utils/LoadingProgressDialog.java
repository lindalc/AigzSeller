package com.zykj.aiguanzhu.utils;

import com.zykj.aiguanzhu.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午11:16:17 
 * @version 1.0 
 * @Description 
 */
public class LoadingProgressDialog {

	/**
	 * 从资源中获取加载动画
	 */
	private static int[] mImageRess = new int[] { 
		//R.drawable.img_loading_1,
			};

	private static int mDuration = 100;

	private static Dialog loadingDialog = null;

	private static SceneAnimation sceneAnimation = null;

	private static Activity mActivity = null;

	/**
	 * 显示一个等待框
	 *
	 * @param context 上下文环境
	 * @param isCancel 是否能用返回取消
	 * @param isRight
	 *            true文字在右边 false在下面
	 */
	public static void show(Activity context, boolean isCancel, boolean isRight) {
		mActivity = context;
		if (!mActivity.isFinishing()) { // 判断 activity 是否已经关闭
			creatDialog(context, "", isCancel, isRight);
		}
	}

	/**
	 * 显示一个等待框
	 *
	 * @param context 上下文环境
	 * @param msg 等待框的文字
	 * @param isCancel 是否能用返回取消
	 * @param isRight
	 *            true文字在右边 false在下面
	 */
	public static void show(Activity context, String msg, boolean isCancel,
							boolean isRight) {
		mActivity = context;
		if (mActivity.isFinishing()) {
			return;
		}
		if (loadingDialog == null) {
			creatDialog(context, msg, isCancel, isRight);
		} else if (!isShowing()) {
			loadingDialog.show();
		}
	}

	private static void creatDialog(Context context, String msg,
									boolean isCancel, boolean isRight) {
		LinearLayout.LayoutParams wrap_content0 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout main = new LinearLayout(context);
		main.setGravity(Gravity.CENTER);
		loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(isCancel);// 是否可以用返回键取消
		View loadingView = View.inflate(context, R.layout.loading_dialog, null);
		ImageView iv = (ImageView) loadingView
				.findViewById(R.id.loadingImageView);
		// 因内存压力过大(帧动画一次加载所有图片进入内存)，已将帧动画替换为图片轮播形式
		/*
		 * AnimationDrawable animationDrawable=(AnimationDrawable)
		 * iv.getBackground(); if(!animationDrawable.isRunning()){
		 * animationDrawable.start(); }
		 */
		sceneAnimation = new SceneAnimation(iv, mImageRess, mDuration);
		main.addView(loadingView, wrap_content0);
		LinearLayout.LayoutParams fill_parent = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		main.setBackgroundResource(R.drawable.loading_dialog);
		loadingDialog.setContentView(main, fill_parent);// 设置布局
		loadingDialog.show();

		// 需要为loadingDialog注册取消监听，如果不注册的话，会有可能报错
		if (loadingDialog != null)
			loadingDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					LoadingProgressDialog.dismiss();
				}
			});
	}

	public static void dismiss() {
		try {
			if (loadingDialog != null && mActivity != null
					&& !mActivity.isFinishing()) {

				loadingDialog.dismiss();
				mActivity = null;
				if (sceneAnimation != null) {
					sceneAnimation.removeCallBacks();
				}
				loadingDialog = null;
				sceneAnimation = null;
				System.gc();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isShowing() {
		return loadingDialog.isShowing();
	}

	public static Dialog getDialog() {
		return loadingDialog;
	}
}

