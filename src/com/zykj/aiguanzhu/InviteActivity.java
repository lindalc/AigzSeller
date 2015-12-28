package com.zykj.aiguanzhu;

import java.util.ArrayList;

import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.eneity.ReserationUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:05
 * @version 1.0 
 * @Description 我的邀请
 */
public class InviteActivity extends BaseActivity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
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
//需替换的数据Start
		ReserationUser aUser = new ReserationUser(null,"用户1",null,"蒙山生态火锅","2015-11-20",1,3);
		ReserationUser bUser = new ReserationUser(null,"用户2",null,"蒙山生态火锅","2015-11-20",1,0);
		
		listReseration.add(aUser);
		listReseration.add(bUser);
		
//End
		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listview.setAdapter(adapterReseration);
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
				break;
			default:
				break;
			}
		}
	
}
