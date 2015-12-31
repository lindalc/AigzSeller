package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.eneity.ReserationUser;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.Share;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:05
 * @version 1.0 
 * @Description 我的邀请
 */
public class InviteActivity extends BaseActivity implements OnItemClickListener{

	private Context mContext = InviteActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	
	/**
	 * 我的邀请
	 */
	private LinearLayout myinviteLayout;
	
	private ListView listview;
	private ReserationAdapter adapterReseration;
	private ArrayList<ReserationUser> listReseration;
	
	private String ShareContent ;
	private String ShareTitle;
	private String ShareUrl ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initShare();//初始化分享模块
		initInviteData();
	}

	/**
	 * 我的邀请
	 */
	public void initInviteData(){
		setTitleContent(R.drawable.title_orange_back, R.drawable.title_orange_add,R.string.myinvite,true);
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		
		myinviteLayout = (LinearLayout) findViewById(R.id.activity_myinvite);
		
		listview = (ListView) findViewById(R.id.acitivity_invite_listview);
		listReseration = new ArrayList<ReserationUser>();

		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listview.setAdapter(adapterReseration);
		
//		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		String json = JsonUtils.toJson(map);
		//TODO 解析数据，接收handler 
//		DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
		
		listview.setOnItemClickListener(this);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_INVITE:
				
				break;
			}
		};
	};
	
	private void initShare() {
		ShareContent = "我的爱关注邀请码是:"+getSharedPreferenceValue("invitecode")+"，一起来爱关注吧，http://www.pgyer.com/ov1S";
		ShareTitle = "我的邀请码："+getSharedPreferenceValue("invitecode");
		ShareUrl = "http://www.pgyer.com/ov1S";
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
			case R.id.right_btn:
				// TODO 邀请
				Share.invit(this, ShareContent, ShareTitle, ShareUrl);
				break;
			default:
				break;
			}
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(mContext, MyYaoQingRenActivity.class);
			startActivity(intent);
		}
	
}
