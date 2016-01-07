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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-25 ����4:24:37
 * @version 1.0 
 * @Description ԤԼ�û�
 */
public class ReserationActivity extends BaseActivity {

	private Context mContext = ReserationActivity.this;
	private int curPosition;
	private int rstate;
	String merchantid;
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	
	/**
	 *  ԤԼ�û���list����
	 */
	private ReserationAdapter adapterReseration;
	private ArrayList<ReserationUser> listReseration;
	private PullToRefreshListView  listView;
	
	private int i = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		merchantid = getSharedPreferenceValue("merchantid");
		initReserationUserData();
	}

	/**
	 *  ԤԼ
	 */
	public void initReserationUserData(){
		setTitleContent(R.drawable.title_orange_back, R.string.reserationusers);
		mLeftBtn.setOnClickListener(this);
		
		listView = (PullToRefreshListView) findViewById(R.id.activity_concemuser_listview);
		listReseration = new ArrayList<ReserationUser>();


		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		initPTR2();//��ʼ������ˢ��,�����������
		setPullDownLayout();//�����������ֵ������Ϣ
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(arg2-1).getRstate();
				int reserationid = listReseration.get(arg2-1).getReseratid();
				Intent intent = new Intent(mContext,ReserationDetailActivity.class);
				intent.putExtra("rstate", rstate);
				intent.putExtra("reserationid", reserationid);
				startActivity(intent);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(mContext, "ֻ��ȡ����ԤԼ����ִ�г���ɾ������", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		RequestDailog.showDialog(mContext, "���Ժ�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("pagenumber", i+"");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
	}
	
	private void setPullDownLayout() {
		// TODO Auto-generated method stub
		//��ȡ��������
		ILoadingLayout proxy = listView.getLoadingLayoutProxy(true,false);
		proxy.setPullLabel("����ˢ��");
		proxy.setReleaseLabel("�ſ��Լ���...");
		proxy.setRefreshingLabel("����������....");
		proxy.setLastUpdatedLabel("���ĸ���ʱ��:"+DateFormat.getDateFormat(getApplicationContext()).format(new Date()));
		proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.a));
	}

	private void initPTR2() {
		// TODO Auto-generated method stub
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO�����¼�
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
				// TODO �����¼�
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
	ArrayList<ReserationUser> list;
	private Handler mHandler = new Handler();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_RESERATIONUSER:
				RequestDailog.closeDialog();
				list = (ArrayList<ReserationUser>) msg.obj;
				
				for(int i=0;i<list.size();i++){
					if(list.get(i).getRstate()==1 || list.get(i).getRstate() == 3){
						listReseration.add(list.get(i));
					}
				}
				
				adapterReseration.notifyDataSetChanged();
				
				break;
			default:break;
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
	
	//�Զ���Handler���͵�Runnableʵ����
	class RefredshDataRunnable implements Runnable{
		
		private boolean isLoadMore;
		public RefredshDataRunnable(ArrayList<ReserationUser> list,boolean isLoadMore){
			this.isLoadMore = isLoadMore;
		}
		
		@Override
		public void run() {
			// TODO ��UI�߳���ִ�еķ�����ˢ������
			RequestDailog.showDialog(mContext, "���Ժ�");
			if(!isLoadMore){
				listReseration.clear();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid",merchantid);
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
			
			//֪ͨ����ˢ�¿ؼ��������Ѽ������
			listView.onRefreshComplete();
		}
	}

}
