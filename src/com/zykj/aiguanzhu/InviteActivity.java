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
 * @date ����ʱ�䣺2015-12-25 ����4:24:05
 * @version 1.0 
 * @Description �ҵ�����
 */
public class InviteActivity extends BaseActivity {

	private Context mContext = InviteActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	
	/**
	 * �ҵ�����
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
	 * �ҵ�����
	 */
	public void initInviteData(){
		setTitleContent(R.drawable.title_orange_back, R.drawable.title_orange_add,R.string.myinvite,true);
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		
		myinviteLayout = (LinearLayout) findViewById(R.id.activity_myinvite);
		
		listview = (ListView) findViewById(R.id.acitivity_invite_listview);
		listReseration = new ArrayList<ReserationUser>();
//���滻������Start
		ReserationUser aUser = new ReserationUser(null,"�û�1",null,"��ɽ��̬���","2015-11-20",1,3);
		ReserationUser bUser = new ReserationUser(null,"�û�2",null,"��ɽ��̬���","2015-11-20",1,0);
		
		listReseration.add(aUser);
		listReseration.add(bUser);
		
//End
		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listview.setAdapter(adapterReseration);
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
			case R.id.right_btn:
				// TODO ����
				break;
			default:
				break;
			}
		}
	
}
