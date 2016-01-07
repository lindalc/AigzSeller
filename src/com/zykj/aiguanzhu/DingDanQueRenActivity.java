package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.zykj.aiguanzhu.eneity.Dingdan;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DingDanQueRenActivity extends BaseActivity {

	private Context mContext = DingDanQueRenActivity.this;
	
	private TextView txt_bianhao,txt_time,txt_money; 
	
	private int i ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ding_dan_que_ren);
		
		i = getIntent().getIntExtra("i",-1);
		
		initData();
	}

	
	public void initData(){
		setTitleContent(R.drawable.title_orange_back, R.string.cartcheck);
		mLeftBtn.setOnClickListener(this);
		
		txt_bianhao = (TextView) findViewById(R.id.activity_dingdan_txt_bianhao);
		txt_time = (TextView) findViewById(R.id.activity_dingdan_txt_time);
		txt_money = (TextView) findViewById(R.id.activity_dingdan_txt_money);
		if(i==0){
			RequestDailog.showDialog(this, "请稍后");
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderid", getIntent().getIntExtra("id", -1)+"");
			String json = JsonUtils.toJson(map);
			DataParser.getConfirm(mContext, Request.Method.GET, HttpUtils.url_confirm(json), null, handler);
		}else if(i==1){
			RequestDailog.showDialog(this, "请稍后");
			ToolsUtil.print("----", "resultString = " + getIntent().getStringExtra("resultString"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderid", getIntent().getStringExtra("resultString"));
			String json = JsonUtils.toJson(map);
			DataParser.getConfirm(mContext, Request.Method.GET, HttpUtils.url_confirm(json), null, handler);
		}
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.DINGDAN_CONFIRM:
				RequestDailog.closeDialog();
				Dingdan dingdan = (Dingdan) msg.obj;
				
				txt_bianhao.setText(dingdan.getOrdernum());
				txt_time.setText(dingdan.getDelivertytime());
				txt_money.setText(dingdan.getPrice()+"");
				
				break;
			default :
				break;
			}
		};
	};
	
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
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		DataParser.cancel(mContext);
	}
}
