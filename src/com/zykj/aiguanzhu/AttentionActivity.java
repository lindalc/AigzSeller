package com.zykj.aiguanzhu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.android.volley.Request;
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
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lc
 * @date 创建时间：2015-12-23 下午4:55:02
 * @version 1.0 
 * @Description 我的关注
 */
public class AttentionActivity extends BaseActivity {

	private Context mContext = AttentionActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	

	private ListView listView;
	/**
	 * 关注用户的list数据
	 */
	private ConcernuserListViewAdapter adapterAttention;
	private ArrayList<AttentionUser> listAttention;


   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initAttentionData();
	}
	
	/**
	 *  对应的是ConUserDetailActivity
	 */
	public void initAttentionData(){
		
		setTitleContent(R.drawable.title_orange_back, R.string.attention_title);
		mLeftBtn.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listAttention = new ArrayList<AttentionUser>();

		adapterAttention = new ConcernuserListViewAdapter(mContext,listAttention);
		listView.setAdapter(adapterAttention);
		
		
		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
		
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case DataConstants.MAINACTIVITY_ATTENTION:
				RequestDailog.closeDialog();
				listAttention.clear();
				ArrayList<AttentionUser> list = (ArrayList<AttentionUser>) msg.obj;
				
				listAttention.addAll(list);
				adapterAttention.notifyDataSetChanged();
				break;
			}
		}
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
		case R.id.right_btn:
			// TODO 第三方邀请
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
		listView = null;
		System.gc();
	}
}
