package com.zykj.aiguanzhu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RechargeActivity extends BaseActivity {

	private Context mContext = RechargeActivity.this;
	
	/**
	 * EditText 积分和金额
	 */
	private EditText et_psd,et_count;
	/**
	 * 接收输入的积分和金额
	 */
	private String str_psd,str_count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		
		initRechargeData();
	}
	
	public void initRechargeData(){
		setTitleContent(R.drawable.title_back, 0, R.string.recharge);
		mLeftBtn.setOnClickListener(this);
		
		et_psd = (EditText) findViewById(R.id.activity_recharge_psd);
		et_count = (EditText) findViewById(R.id.activity_recharge_count);
		
		str_psd = et_psd.getText().toString();
		str_count = et_count.getText().toString();;
		
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
