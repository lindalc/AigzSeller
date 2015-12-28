package com.zykj.aiguanzhu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:13
 * @version 1.0 
 * @Description 预约详情
 */
public class ReserationDetailActivity extends BaseActivity {

	private Context mContext = ReserationDetailActivity.this;
	private int reserationid;
	
	private TextView txt_username,txt_otherinfo,txt_action,txt_datetime,txt_personnumber,txt_mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration_detail);
		
		reserationid = getIntent().getIntExtra("reserationid", -1); 
		
		initData();
	}
	
	public void initData(){
		setTitleContent(R.drawable.title_back, 0,R.string.reserationdetail );
		mLeftBtn.setOnClickListener(this);
		
		txt_username = (TextView) findViewById(R.id.activity_reserationdetail_username);
		txt_otherinfo = (TextView) findViewById(R.id.activity_reserationdetail_otherinfo);
		txt_action = (TextView) findViewById(R.id.activity_reserationdetail_action);
		txt_datetime = (TextView) findViewById(R.id.activity_reserationdetail_datetime);
		txt_personnumber = (TextView) findViewById(R.id.activity_reserationdetail_personnumber);
		txt_mobile = (TextView) findViewById(R.id.activity_reserationdetail_mobile);
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
