package com.zykj.aiguanzhu;

import com.pingplusplus.android.PaymentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

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
	
	private Button recharge_btn;
	
	private RadioGroup radiogroup;
	private RadioButton btn_alipay,btn_wechat;
	
	
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
		
		
		recharge_btn = (Button) findViewById(R.id.recharge_btn);
		recharge_btn.setOnClickListener(this);
		
		radiogroup = (RadioGroup) findViewById(R.id.activity_recharge_radiogroup);
		btn_alipay = (RadioButton) findViewById(R.id.recharge_alipay);
		btn_wechat = (RadioButton) findViewById(R.id.recharge_wechat);
		
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
			}
		});
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
		case R.id.recharge_btn:
			if(btn_alipay.isChecked()){
//				Intent intent = new Intent();
//				String packageName = getPackageName();
//				ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
//				intent.setComponent(componentName);
//				intent.putExtra(PaymentActivity.EXTRA_CHARGE, str_count);
//				startActivityForResult(intent, 1);
				Toast.makeText(mContext, "alipay", Toast.LENGTH_LONG).show();
			}else if(btn_wechat.isChecked()){
				Toast.makeText(mContext, "wechat", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

}
