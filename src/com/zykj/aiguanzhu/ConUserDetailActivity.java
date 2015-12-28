package com.zykj.aiguanzhu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-24 ����10:50:39
 * @version 1.0 
 * @Description ��������
 */
public class ConUserDetailActivity extends BaseActivity {

	private Context mContext = ConUserDetailActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_con_user_detail);
		
		initData();
	}
	
	public void initData(){
		
		setTitleContent(R.drawable.title_back, 0, R.string.attention_detail_title);
		mLeftBtn.setOnClickListener(this);
		
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
		default:
			break;
		}
	}

}
