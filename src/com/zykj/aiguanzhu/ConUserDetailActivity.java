package com.zykj.aiguanzhu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;

/**
 * @author lc
 * @date 创建时间：2015-12-24 上午10:50:39
 * @version 1.0 
 * @Description 个人详情
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
	 * 按钮点击事件
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
