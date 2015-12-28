package com.zykj.aiguanzhu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-25 ����4:24:45
 * @version 1.0 
 * @Description �ҵĻ���
 */
public class MyPsdActivity extends BaseActivity {

	private Context mContext = MyPsdActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	
	/**
	 * ����
	 */
	private LinearLayout mypsdLayout;
	private TextView txt_chargevalue,txt_invitevalue,txt_spendvalue,txt_depositevalue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_psd);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initPsdData();
	}

	/**
	 * ����
	 */
	public void initPsdData(){
		setTitleContent(R.drawable.title_orange_back, R.string.mypsd);
		mLeftBtn.setOnClickListener(this);
		
		mypsdLayout = (LinearLayout) findViewById(R.id.activity_mypsd);
		mypsdLayout.setVisibility(View.VISIBLE);
	}
	
	 /*
		 * ��ť����¼�
		 * (non-Javadoc)
		 * @see com.weiyuan.BaseActivity#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			super.onClick(v);
			switch (v.getId()) {
			case R.id.left_btn:
				this.finish();
				break;
			case R.id.right_btn:
				// TODO ����������
				break;
			default:
				break;
			}
		}

}
