package com.zykj.aiguanzhu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zykj.aiguanzhu.adapters.ConcernuserListViewAdapter;
import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.custome.CustomDialog;
import com.zykj.aiguanzhu.custome.CustomDialog.Builder;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.eneity.ReserationUser;
import com.zykj.aiguanzhu.net.AigzException;
import com.zykj.aiguanzhu.net.EntityHandler;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-23 ����4:55:02
 * @version 1.0 
 * @Description �ҵĹ�ע
 */
public class AttentionActivity extends BaseActivity {

	private Context mContext = AttentionActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	

	private ListView listView;
	/**
	 * ��ע�û���list����
	 */
	private ConcernuserListViewAdapter adapterAttention;
	private ArrayList<AttentionUser> listAttention;


	

	
    private AsyncHttpResponseHandler handler = new EntityHandler<AttentionUser>(AttentionUser.class) {

		@Override
		public void onReadSuccess(List<AttentionUser> list) {
			// TODO Auto-generated method stub
			listAttention.addAll(list);
			adapterAttention.notifyDataSetChanged();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initAttentionData();
		
	}
	
	/**
	 *  ��Ӧ����ConUserDetailActivity
	 */
	public void initAttentionData(){
		
		setTitleContent(R.drawable.title_orange_back, R.string.attention_title);
		mLeftBtn.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listAttention = new ArrayList<AttentionUser>();
//���滻������Start		
		AttentionUser aUser = new AttentionUser("����ע,������,����õ��Լ�!","��ַ",null,"������",1,"2015-3-18");
		listAttention.add(aUser);
//End	
		adapterAttention = new ConcernuserListViewAdapter(mContext,listAttention);
		listView.setAdapter(adapterAttention);
		
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
			// TODO ����������
			break;
		default:
			break;
		}
	}


}
