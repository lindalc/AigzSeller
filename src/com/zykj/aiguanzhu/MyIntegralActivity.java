package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.zykj.aiguanzhu.AttentionActivity.RefredshDataRunnable;
import com.zykj.aiguanzhu.adapters.MyIntegralAdapter;
import com.zykj.aiguanzhu.eneity.AttentionUser;
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
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:45
 * @version 1.0 
 * @Description 我的积分
 */
public class MyIntegralActivity extends BaseActivity {

	private Context mContext = MyIntegralActivity.this;
	private int curPosition;
	private int rstate;
	String merchantid;
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	
	/**
	 * 积分
	 */
	private LinearLayout mypsdLayout;
	
	
	private RequestQueue mRequestQueue; 
	private TextView txt_chargevalue,txt_invitevalue,txt_spendvalue,txt_depositevalue;
	
	/**
	 * 积分变化listview和适配器
	 */
	private PullToRefreshListView listview;
	private ArrayList<MyIntegral> list;
	private MyIntegralAdapter adapter;
	
	private int i = 1;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_integral);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		merchantid = getSharedPreferenceValue("merchantid");
		mRequestQueue =  Volley.newRequestQueue(this);  
		
		initPsdData();
		
		initData();
	}
	
	public void initData(){
		listview = (PullToRefreshListView) findViewById(R.id.activity_myintegral_listview);
		list = new ArrayList<MyIntegral>();
		adapter = new MyIntegralAdapter(mContext, list);
		listview.setAdapter(adapter);
		
		initPTR2();//初始化下拉刷新,上拉加载组件
		setPullDownLayout();//设置下拉布局的相关信息
		
		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		String json = JsonUtils.toJson(map);
		DataParser.getMyPsdJson(mContext,Request.Method.GET, HttpUtils.url_integral(json), null, handler,0);
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("merchantid", "1");
		map1.put("pagenumber", i+"");
		map1.put("pagesize", "20");
		String json1 = JsonUtils.toJson(map);
		DataParser.getMyPsdJson(mContext,Request.Method.GET, HttpUtils.url_integralList(json1), null, handler,1);
	}

	/**
	 * 积分
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
	
	private void setPullDownLayout() {
		// TODO Auto-generated method stub
		//获取下拉布局
		ILoadingLayout proxy = listview.getLoadingLayoutProxy(true,false);
		proxy.setPullLabel("下拉刷新");
		proxy.setReleaseLabel("放开以加载...");
		proxy.setRefreshingLabel("玩命加载中....");
		proxy.setLastUpdatedLabel("最后的更新时间:"+DateFormat.getDateFormat(getApplicationContext()).format(new Date()));
		proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.a));
	}

	private void initPTR2() {
		// TODO Auto-generated method stub
		listview.setMode(Mode.BOTH);
		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO下拉事件
				new Thread(){
					public void run() {
						try {
							Thread.sleep(1000);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						mHandler.post(new RefredshDataRunnable(in,false));
					};
				}.start();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO 上拉事件
				new Thread(){
					public void run() {
						try {
							Thread.sleep(1000);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mHandler.post(new RefredshDataRunnable(in,true));
						
					};
				}.start();
			}
		});
	}
	
	
	private Handler mHandler = new Handler();
	private ArrayList<MyIntegral> in;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
				case DataConstants.MAINACTIVITY_PSD:
					
					
					MyIntegral mPsd = (MyIntegral) msg.obj;
					
					ToolsUtil.print("----", "mPsd = " + mPsd);
					ToolsUtil.print("----", "mPsdgetChargevalue = " + mPsd.getChargevalue());
					
					txt_chargevalue.setText((int)Double.parseDouble(mPsd.getChargevalue())+"");
					txt_invitevalue.setText(mPsd.getRebatevalue());
					txt_spendvalue.setText((int)(Double.parseDouble(mPsd.getHistotalvalue())-Double.parseDouble(mPsd.getRemainvalue()))+"");
					txt_depositevalue.setText(mPsd.getDrawvalue());
					
					break;
				case DataConstants.MAINACIVITY_INTEGRALLIST:
					RequestDailog.closeDialog();
					in = (ArrayList<MyIntegral>) msg.obj;
					list.addAll(in);
					adapter.notifyDataSetChanged();
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		listview = null;
	}
	
	//自定义Handler发送的Runnable实现类
	class RefredshDataRunnable implements Runnable{
		
		private boolean isLoadMore;
		public RefredshDataRunnable(ArrayList<MyIntegral> list,boolean isLoadMore){
			this.isLoadMore = isLoadMore;
		}
		
		@Override
		public void run() {
			// TODO 在UI线程中执行的方法：刷新数据
			RequestDailog.showDialog(mContext, "请稍后");
			if(!isLoadMore){
				list.clear();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", "1");
				map.put("pagesize", "20");
				String json = JsonUtils.toJson(map);
				DataParser.getMyPsdJson(mContext,Request.Method.GET, HttpUtils.url_integralList(json), null, handler,1);
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", (++i)+"");
				map.put("pagesize", "20");
				String json = JsonUtils.toJson(map);
				DataParser.getMyPsdJson(mContext,Request.Method.GET, HttpUtils.url_integralList(json), null, handler,1);
			}
			
			ToolsUtil.print("----", ""+list.size());
			
			//通知下拉刷新控件，数据已加载完成
			listview.onRefreshComplete();
		}
	}
		

}
