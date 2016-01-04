package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zykj.aiguanzhu.adapters.ConcernuserListViewAdapter;
import com.zykj.aiguanzhu.eneity.AttentionUser;
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
	
	String merchantid;
	
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	

	private PullToRefreshListView listView;
	/**
	 * 关注用户的list数据
	 */
	private ConcernuserListViewAdapter adapterAttention;
	private ArrayList<AttentionUser> listAttention;
	private int i =1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		merchantid = getSharedPreferenceValue("merchantid");
		initAttentionData();
	}
	String json;
	/**
	 *  对应的是ConUserDetailActivity
	 */
	public void initAttentionData(){
		
		setTitleContent(R.drawable.title_orange_back, R.string.attention_title);
		mLeftBtn.setOnClickListener(this);
		
		listView = (PullToRefreshListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listAttention = new ArrayList<AttentionUser>();
		
//		if(listAttention !=null){
//			listAttention.clear();
//		}

		adapterAttention = new ConcernuserListViewAdapter(mContext,listAttention);
		listView.setAdapter(adapterAttention);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,ConUserDetailActivity.class);
				intent.putExtra("id", list.get(arg2).getUserid());
				intent.putExtra("name", list.get(arg2).getName());
				mContext.startActivity(intent);
			}
		});
		
		initPTR2();//初始化下拉刷新,上拉加载组件
		setPullDownLayout();//设置下拉布局的相关信息
		
		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("pagenumber", i+"");
		map.put("pagesize", "10");
		json = JsonUtils.toJson(map);
		DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
		
	}
	
	private void setPullDownLayout() {
		// TODO Auto-generated method stub
		//获取下拉布局
		ILoadingLayout proxy = listView.getLoadingLayoutProxy(true,false);
		proxy.setPullLabel("下拉刷新");
		proxy.setReleaseLabel("放开以加载...");
		proxy.setRefreshingLabel("玩命加载中....");
		proxy.setLastUpdatedLabel("最后的更新时间:"+DateFormat.getDateFormat(getApplicationContext()).format(new Date()));
		proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.default_ptr_rotate));
	}

	private void initPTR2() {
		// TODO Auto-generated method stub
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO下拉事件
				new Thread(){
					public void run() {
						try {
							Thread.sleep(3000);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						mHandler.post(new RefredshDataRunnable(list,false));
					};
				}.start();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO 上拉事件
				new Thread(){
					public void run() {
						try {
							Thread.sleep(3000);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mHandler.post(new RefredshDataRunnable(list,true));
						
					};
				}.start();
			}
		});
	}
	
	
	private Handler mHandler = new Handler();
	private ArrayList<AttentionUser> list;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case DataConstants.MAINACTIVITY_ATTENTION:
				RequestDailog.closeDialog();
				if(list != null){
					list.clear();
				}
				list = (ArrayList<AttentionUser>) msg.obj;
				
				if(list != null){
					listAttention.addAll(list);
				}else{
					Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_LONG).show();
				}
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
		default:
			break;
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		DataParser.cancel(mContext);
		System.gc();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		listView = null;
	}
	
	//自定义Handler发送的Runnable实现类
	class RefredshDataRunnable implements Runnable{
		
		private boolean isLoadMore;
		public RefredshDataRunnable(ArrayList<AttentionUser> list,boolean isLoadMore){
			this.isLoadMore = isLoadMore;
		}
		
		@Override
		public void run() {
			// TODO 在UI线程中执行的方法：刷新数据
			RequestDailog.showDialog(mContext, "请稍后");
			if(!isLoadMore){
				listAttention.clear();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", "1");
				map.put("pagesize", "10");
				json = JsonUtils.toJson(map);
				DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", (++i)+"");
				map.put("pagesize", "10");
				json = JsonUtils.toJson(map);
				DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
			}
			
			ToolsUtil.print("----", ""+listAttention.size());
			
			//通知下拉刷新控件，数据已加载完成
			listView.onRefreshComplete();
		}
	}
	
}
