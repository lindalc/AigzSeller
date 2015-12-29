package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zykj.aiguanzhu.eneity.MyIntegral;
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
	
	private RequestQueue mRequestQueue; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_psd);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		mRequestQueue =  Volley.newRequestQueue(this);  
		
		initPsdData();
		
		initData();
	}
	
	public void initData(){
		RequestDailog.showDialog(this, "���Ժ�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		String json = JsonUtils.toJson(map);
		DataParser.getMyPsdJson(mContext,Request.Method.GET, HttpUtils.url_integral(json), null, handler);
	}

	/**
	 * ����
	 */
	public void initPsdData(){
		setTitleContent(R.drawable.title_orange_back, R.string.mypsd);
		mLeftBtn.setOnClickListener(this);
		
		mypsdLayout = (LinearLayout) findViewById(R.id.activity_mypsd);
		mypsdLayout.setVisibility(View.VISIBLE);
		
		txt_chargevalue = (TextView) findViewById(R.id.mypsdchargevalue);
		txt_invitevalue = (TextView) findViewById(R.id.mypsdinvitevalue);
		txt_spendvalue = (TextView) findViewById(R.id.mypsdspendvalue);
		txt_depositevalue = (TextView) findViewById(R.id.mypsddepositvalue);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
				case DataConstants.MAINACTIVITY_PSD:
					RequestDailog.closeDialog();
					
					MyIntegral mPsd = (MyIntegral) msg.obj;
					
					ToolsUtil.print("----", "mPsd = " + mPsd);
					ToolsUtil.print("----", "mPsdgetChargevalue = " + mPsd.getChargevalue());
					
					txt_chargevalue.setText((int)Double.parseDouble(mPsd.getChargevalue())+"");
					txt_invitevalue.setText(mPsd.getRebatevalue());
					txt_spendvalue.setText((int)(Double.parseDouble(mPsd.getHistotalvalue())-Double.parseDouble(mPsd.getRemainvalue()))+"");
					txt_depositevalue.setText(mPsd.getDrawvalue());
					
					break;
			}
		};
	};
	
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
		
		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			DataParser.cancel(mContext);
		}

}
