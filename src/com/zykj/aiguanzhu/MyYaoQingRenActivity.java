package com.zykj.aiguanzhu;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MyYaoQingRenActivity extends BaseActivity {
	private ImageView im_b451_back_btn;//·µ»Ø
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_yao_qing_ren);
		
		initView();
	}

	public void initView() {
		im_b451_back_btn = (ImageView) findViewById(R.id.im_b451_back_btn);

		setListener(im_b451_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b451_back_btn:
			this.finish();
			break;
//		case R.id.im_denglu:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
//			startActivity(itdenglu);
//			break;
		default:
			break;

		}
	}
}
