package com.zykj.aiguanzhu.utils;

import android.os.Handler;
import android.widget.ImageView;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-23 ����11:21:14 
 * @version 1.0 
 * @Description ����ͼƬ�ֲ�ʹ�ã����������ͼƬ�µ�FrameAnimation֡�����������ڴ�ѹ��
 * @Title: ͼƬ�ֲ���ʾ
 */
public class SceneAnimation {
	private ImageView mImageView;
	/** ��Ҫ���ŵ�ͼƬ��Դ���� */
	private int[] mImageRess;
	/** ÿ��ͼƬ���ŵļ�� */
	private int[] mDurations;
	/** ������ż��һ�£���Ϊ����ͼƬ���õĲ���ʱ�� */
	private int mDuration;
	/** �ֲ�ͼƬ��ǰ������*/
	private int mCurIndex;
	/** ������ͼƬ�������������ڼ��������ֲ� */
	private int mImagesCount;
	/** һ�ֲ�����Ϻ�����һ�ֵĲ��ż�� */
	private int mBreakDelay;
	String mImageUri = "drawable://";

//	���ʹ��ImageLoader�ֲ�����֡��������
//	private ImageLoader mImageLoader = ImageLoader.getInstance(); 

	/**
	 * ��ʼ���ֲ���
	 * @param mImageView ��ʾͼƬ��mImageView
	 * @param mImageRess ��ʾ��ͼƬ����
	 * @param mDuration  ����ͼƬͳһ�ļ��ʱ��
	 */
	public SceneAnimation(ImageView mImageView, int[] mImageRess, int mDuration) {
		super();
		this.mImageView = mImageView;
		this.mImageRess = mImageRess;
		this.mDuration = mDuration;
		mImagesCount = mImageRess.length;

		mImageView.setBackgroundResource(mImageRess[0]);
		play(1);
	}

	/**
	 * ��ʼ���ֲ�������أ�ÿ��ͼƬ�Ĳ���ʱ��ɲ�ͬ
	 * @param mImageView ��ʾͼƬ��mImageView
	 * @param mImageRess ��ʾ��ͼƬ����
	 * @param mDurations ����ͼƬ�ļ��ʱ������
	 */
	public SceneAnimation(ImageView mImageView, int[] mImageRess,
			int[] mDurations) {
		super();
		this.mImageView = mImageView;
		this.mImageRess = mImageRess;
		this.mDurations = mDurations;
		mImagesCount = mImageRess.length;

		mImageView.setBackgroundResource(mImageRess[0]);
		play(1);
	}

	/**
	 * ��ʼ���ֲ�������أ�ÿ��ͼƬ�Ĳ���ʱ��ɲ�ͬ,ͬʱ����ÿһ�ֲ��Ž����󣬵ڶ��ֿ�ʼ���ŵļ��ʱ��
	 * @param mImageView ��ʾͼƬ��mImageView
	 * @param mImageRess ��ʾ��ͼƬ����
	 * @param mDurations ����ͼƬ�ļ��ʱ������
	 * @param mBreakDelay ͼƬ���ŵ����һ��2�ֲ���֮��ļ��
	 */
	public SceneAnimation(ImageView mImageView, int[] mImageRess,
			int[] mDurations, int mBreakDelay) {
		super();
		this.mImageView = mImageView;
		this.mImageRess = mImageRess;
		this.mDurations = mDurations;
		this.mBreakDelay = mBreakDelay;
		mImagesCount = mImageRess.length;

		mImageView.setBackgroundResource(mImageRess[0]);
		playInterval(1);
	}

	/**
	 * ��ʼ���ֲ���,ͬʱ����ÿһ�ֲ��Ž����󣬵ڶ��ֿ�ʼ����ǰ�ļ��ʱ��
	 * @param mImageView ��ʾͼƬ��mImageView
	 * @param mImageRess ��ʾ��ͼƬ����
	 * @param mDuration  ����ͼƬͳһ�ļ��ʱ��
	 * @param mBreakDelay ͼƬ���ŵ����һ��2�ֲ���֮��ļ��
	 */
	public SceneAnimation(ImageView mImageView, int[] mImageRess,
			int mDuration, int mBreakDelay) {
		super();
		this.mImageView = mImageView;
		this.mImageRess = mImageRess;
		this.mDuration = mDuration;
		this.mBreakDelay = mBreakDelay;
		mImagesCount = mImageRess.length;

		mImageView.setBackgroundResource(mImageRess[0]);
		playInterval(1);
	}

	/**
	 * ѭ������ͼƬ
	 * @param mCurIndex ��һ����Ҫ����ͼƬͼƬ��id
	 */
	private void play(final int mCurIndex) {
		this.mCurIndex = mCurIndex;
		handler.postDelayed(runnable, mDurations == null ? mDuration : mDurations[mCurIndex]);
	}

	/**
	 * ѭ������ͼƬ��ÿһ�ִ��м��ʱ��
	 * @param mCurIndex ��һ����Ҫ����ͼƬͼƬ��id
	 */
	private void playInterval(final int mCurIndex) {
		this.mCurIndex = mCurIndex;
		handler.postDelayed(runnableDelay, mCurIndex == mImagesCount - 1 && mBreakDelay > 0 ? mBreakDelay
				: mDurations == null ? mDuration : mDurations[mCurIndex]);
		
	}
	
	private Handler handler = new Handler();
	
	/**
	 * ʵ����runnable������handler���գ���ֹ�ڴ�й¶
	 */
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			mImageView.setBackgroundResource( mImageRess[mCurIndex] );
			mCurIndex = (mCurIndex+1) % mImagesCount;
			handler.postDelayed(this, mDurations == null ? mDuration : mDurations[mCurIndex]);
		}
	};
	
	/**
	 * ʵ����runnable������handler���գ���ֹ�ڴ�й¶
	 */
	private Runnable runnableDelay = new Runnable() {
		
		@Override
		public void run() {
			mImageView.setBackgroundResource( mImageRess[mCurIndex] );
			mCurIndex = (mCurIndex+1) % mImagesCount;
			handler.postDelayed(this, mCurIndex == mImagesCount - 1 && mBreakDelay > 0 ? mBreakDelay
					: mDurations == null ? mDuration : mDurations[mCurIndex]);
		}
	};
	
	/**
	 * ��ֹ�ݹ��������ֹ�ڴ�й¶
	 */
	public void removeCallBacks() {
		if(runnable != null) {
			handler.removeCallbacks(runnable);
		}
		if(runnableDelay!=null) {
			handler.removeCallbacks(runnableDelay);
		}
	}

}
