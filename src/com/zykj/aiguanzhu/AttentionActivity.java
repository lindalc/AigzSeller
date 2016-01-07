package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zykj.aiguanzhu.adapters.ConcernuserListViewAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
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
	
	String merchantid;
	
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	

	private PullToRefreshListView listView;
	/**
	 * ��ע�û���list����
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
	 *  ��Ӧ����ConUserDetailActivity
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
		
		
		initPTR2();//��ʼ������ˢ��,�����������
		setPullDownLayout();//�����������ֵ������Ϣ
		
		RequestDailog.showDialog(this, "���Ժ�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("pagenumber", i+"");
		map.put("pagesize", "10");
		json = JsonUtils.toJson(map);
		DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
					final int cur = arg2;
					ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
			        builder.setTitle("��ܰ����!");
			        builder.setMessage("�Ƿ�ȷ��ɾ���˹�ע��");
			        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                //������Ĳ�������  
			            	dialog.dismiss();
			            	listAttention.remove(listAttention.get(cur-1));
			            	adapterAttention.notifyDataSetChanged();
			            }  
			        });  
			  
			        builder.setNegativeButton("",  
			                new android.content.DialogInterface.OnClickListener() {  
			                    public void onClick(DialogInterface dialog, int which) {  
			                        dialog.dismiss();
			                    }  
			                });  
			  
			        builder.create().show();  
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,ConUserDetailActivity.class);
				ToolsUtil.print("----", "itemposition"+arg2);
				ToolsUtil.print("----", "list.get(arg2-1).getUserid()"+list.get(arg2-1).getUserid());
				intent.putExtra("id", listAttention.get(arg2-1).getUserid());
				intent.putExtra("name", listAttention.get(arg2-1).getName());
				mContext.startActivity(intent);
			}
		});
		
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
				list = (ArrayList<AttentionUser>) msg.obj;
				
				listAttention.addAll(list);
				adapterAttention.notifyDataSetChanged();
				break;
			}
		}
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
		public RefredshDataRunnable(ArrayList<AttentionUser> list,boolean isLoadMore){
			this.isLoadMore = isLoadMore;
		}
		
		@Override
		public void run() {
			// TODO ��UI�߳���ִ�еķ�����ˢ������
			RequestDailog.showDialog(mContext, "���Ժ�");
			if(!isLoadMore){
				listAttention.clear();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", "1");
				map.put("pagesize", "10");
				json = JsonUtils.toJson(map);
				DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
			}else{
				listAttention.clear();
				int j = 10;
				j+=10;
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantid", merchantid);
				map.put("pagenumber", i+"");
				map.put("pagesize", j+"");
				json = JsonUtils.toJson(map);
				DataParser.getAttention(mContext, Request.Method.GET, HttpUtils.url_attention(json), null, handler);
			}
			
			ToolsUtil.print("----", "listAttention"+listAttention.size());
			ToolsUtil.print("----", "list"+list.size());
			
			//֪ͨ����ˢ�¿ؼ��������Ѽ������
			listView.onRefreshComplete();
		}
	}
	
}
