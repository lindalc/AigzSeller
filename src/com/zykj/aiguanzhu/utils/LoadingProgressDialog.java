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
 * @date ����ʱ�䣺2015-12-23 ����11:16:17 
 * @version 1.0 
 * @Description 
 */
public class LoadingProgressDialog {

	/**
	 * ����Դ�л�ȡ���ض���
	 */
	private static int[] mImageRess = new int[] { 
		//R.drawable.img_loading_1,
			};

	private static int mDuration = 100;

	private static Dialog loadingDialog = null;

	private static SceneAnimation sceneAnimation = null;

	private static Activity mActivity = null;

	/**
	 * ��ʾһ���ȴ���
	 *
	 * @param context �����Ļ���
	 * @param isCancel �Ƿ����÷���ȡ��
	 * @param isRight
	 *            true�������ұ� false������
	 */
	public static void show(Activity context, boolean isCancel, boolean isRight) {
		mActivity = context;
		if (!mActivity.isFinishing()) { // �ж� activity �Ƿ��Ѿ��ر�
			creatDialog(context, "", isCancel, isRight);
		}
	}

	/**
	 * ��ʾһ���ȴ���
	 *
	 * @param context �����Ļ���
	 * @param msg �ȴ��������
	 * @param isCancel �Ƿ����÷���ȡ��
	 * @param isRight
	 *            true�������ұ� false������
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
		loadingDialog = new Dialog(context, R.style.loading_dialog);// �����Զ�����ʽdialog
		loadingDialog.setCancelable(isCancel);// �Ƿ�����÷��ؼ�ȡ��
		View loadingView = View.inflate(context, R.layout.loading_dialog, null);
		ImageView iv = (ImageView) loadingView
				.findViewById(R.id.loadingImageView);
		// ���ڴ�ѹ������(֡����һ�μ�������ͼƬ�����ڴ�)���ѽ�֡�����滻ΪͼƬ�ֲ���ʽ
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
		loadingDialog.setContentView(main, fill_parent);// ���ò���
		loadingDialog.show();

		// ��ҪΪloadingDialogע��ȡ�������������ע��Ļ������п��ܱ���
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

