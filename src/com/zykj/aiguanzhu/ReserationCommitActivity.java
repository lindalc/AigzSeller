package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.zykj.aiguanzhu.AttentionActivity.RefredshDataRunnable;
import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.eneity.ReserationUser;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:26
 * @version 1.0 
 * @Description 预约确定
 */
public class ReserationCommitActivity extends BaseActivity {

	private Context mContext = ReserationCommitActivity.this;
	private int curPosition;
	private int rstate;
	String merchantid;
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	
	/**
	 *  预约用户的list数据
	 */
	private ReserationAdapter adapterReseration;
	private ArrayList<ReserationUser> listReseration;
	private PullToRefreshListView listView;
	
	private int i = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration_commit);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		merchantid = getSharedPreferenceValue("merchantid");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initReserationCommitData();
	}

	/**
	 *  预约确定
	 */
	public void initReserationCommitData(){
		setTitleContent(R.drawable.title_orange_back, R.string.reserationcommit);
		mLeftBtn.setOnClickListener(this);
		
		listView = (PullToRefreshListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listReseration = new ArrayList<ReserationUser>();

		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		initPTR2();//初始化下拉刷新,上拉加载组件
		setPullDownLayout();//设置下拉布局的相关信息
		
		RequestDailog.showDialog(mContext, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("pagesize", "10");
		map.put("pagenumber", i+"");
		map.put("merchantid", merchantid);
		String json = JsonUtils.toJson(map);
		DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int cur = arg2;
				rstate = listReseration.get(arg2).getRstate();
				if(rstate == 2){
					ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
			        builder.setTitle("温馨提醒!");
			        builder.setMessage("是否确定删除此条预约信息");
			        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                //设置你的操作事项  
			                Map<String, String> map = new HashMap<String, String>();
			        		map.put("reserationid", listReseration.get(curPosition).getReseratid()+"");
			        		String json1 = JsonUtils.toJson(map);
			        		DataParser.getReserationDelete(mContext, Request.Method.GET, HttpUtils.url_reserationDelete(json1), null, handler);
			        		
			        		listReseration.remove(cur);
			        		adapterReseration.notifyDataSetChanged();
			            }  
			        });  
			  
			        builder.setNegativeButton("",  
			                new android.content.DialogInterface.OnClickListener() {  
			                    public void onClick(DialogInterface dialog, int which) {  
			                        dialog.dismiss();
			                    }  
			                });  
			  
			        builder.create().show();  
				}else{
					Toast.makeText(mContext, "只有取消的预约才能执行长按删除操作", Toast.LENGTH_LONG).show();
				}
				return true;
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(arg2-1).getRstate();
				Intent intent = new Intent(mContext,ReserationDetailActivity.class);
				intent.putExtra("rstate", rstate);
				ToolsUtil.print("----", "rstate = "+rstate);
			    intent.putExtra("reserationid", listReseration.get(arg2-1).getReseratid());
				startActivity(intent);
			}
		});
	}
	
	private void setPullDownLayout() {
		// TODO Auto-generated method stub
		//获取下拉布局
		ILoadingLayout proxy = listView.getLoadingLayoutProxy(true,false);
		proxy.setPullLabel("下拉刷新");
		proxy.setReleaseLabel("放开以加载...");
		proxy.setRefreshingLabel("玩命加载中....");
		proxy.setLastUpdatedLabel("最后的更新时间:"+DateFormat.getDateFormat(getApplicationContext()).format(new Date()));
		proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.a));
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
							Thread.sleep(1000);
							
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
							Thread.sleep(1000);
							
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
	private ArrayList<ReserationUser> list;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_RESERATIONUSER:
				RequestDailog.closeDialog();
				list = (ArrayList<ReserationUser>) msg.obj;
				
				for(int i=0;i<list.size();i++){
					if(list.get(i).getRstate()!=3){
						listReseration.add(list.get(i));
					}
				}
				adapterReseration.notifyDataSetChanged();
				
				break;
			case DataConstants.RESERATION_DELETE:
				// TODO 删除预约
				String errdesc = (String) msg.obj;
				Toast.makeText(mContext, errdesc, Toast.LENGTH_SHORT).show();
				
				//TODO 删除
//				DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
				break;
			default:break;
			}
		};
	};
	
	 /*
	 * 按钮点击事件
	 * (non-Javadoc)
	 * @see com.zykj.aiguanzhu.BaseActivity#onClick(android.view.View)
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
		public RefredshDataRunnable(ArrayList<ReserationUser> list,boolean isLoadMore){
			this.isLoadMore = isLoadMore;
		}
		
		@Override
		public void run() {
			// TODO 在UI线程中执行的方法：刷新数据
			RequestDailog.showDialog(mContext, "请稍后");
			if(!isLoadMore){
				listReseration.clear();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", "1");
				map.put("pagesize", "10");
				String json = JsonUtils.toJson(map);
				DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", (++i)+"");
				map.put("pagesize", "10");
				String json = JsonUtils.toJson(map);
				DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
			}
			
			ToolsUtil.print("----", ""+listReseration.size());
			
			//通知下拉刷新控件，数据已加载完成
			listView.onRefreshComplete();
		}
	}

}
