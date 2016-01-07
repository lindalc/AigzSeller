package com.zykj.aiguanzhu;

import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.ToolsUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class IntroActivity extends BaseActivity implements OnPageChangeListener{

	private ViewPager viewPager;
	int a = 0;
	/**
	 * ͼƬ��Դid
	 */
	private int[] imgIdArray;

	private ImageView[] mImageViews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		imgIdArray = new int[] { R.drawable.introduce_1,R.drawable.introduce_2, R.drawable.introduce_3 };

//		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		// // �������뵽ViewGroup��
		// tips = new ImageView[imgIdArray.length];
		// for (int i = 0; i < tips.length; i++) {
		// ImageView imageView = new ImageView(this);
		// imageView.setLayoutParams(new LayoutParams(10, 10));
		// tips[i] = imageView;
		// if (i == 0) {
		// tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
		// } else {
		// tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
		// }
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(
		// new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// layoutParams.leftMargin = 5;
		// layoutParams.rightMargin = 5;
		// group.addView(imageView, layoutParams);
		// }

		// ��ͼƬװ�ص�������
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setTag(i);
			imageView.setScaleType(ScaleType.FIT_XY);
//			imageView.setAdjustViewBounds(true);
			imageView.setOnClickListener(this);

			mImageViews[i] = imageView;
			imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgIdArray[i]));
		}

		// ����Adapter
		viewPager.setAdapter(new MyAdapter());
		// ���ü�������Ҫ�����õ��ı���
		viewPager.setOnPageChangeListener(this);
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
		}
		/**
		 * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ�
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(mImageViews[position
						% mImageViews.length], 0);

			} catch (Exception e) {
			}

			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (arg0 == mImageViews.length - 1) {// ������һҳ
			// �洢�汾��
			if(a>0){

				putSharedPreferenceValue(DataConstants.VERSION,
						ToolsUtil.getAppVersion(this) + "");
				// �洢�Ѿ����й������ı�ʶ
				putSharedPreferenceValue(DataConstants.IS_INTRO, "1");
				
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				a=a+1;
			}
		} 
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		ToolsUtil.print("----","����¼�");
		int position = (Integer) v.getTag();
		if (position == mImageViews.length - 1) {// ������һҳ
			// �洢�汾��
			putSharedPreferenceValue(DataConstants.VERSION,
					ToolsUtil.getAppVersion(this) + "");
			// �洢�Ѿ����й������ı�ʶ
			putSharedPreferenceValue(DataConstants.IS_INTRO, "1");
			
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			viewPager.setCurrentItem(position + 1);
		}
	}

}
