package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.android.volley.Request;
import com.zykj.aiguanzhu.adapters.CartCheckAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.CartCheck;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;

/**
 * 
 * @author lc
 * @date ����ʱ�䣺2015-12-26 ����2:59:11
 * @version 1.0 
 * @Description ��ȯ����
 */
public class CartCheckActivity extends BaseActivity {

	private Context mContext = CartCheckActivity.this;
	String merchantid;
	
	/**
	 * ��ȯ����listview
	 */
	private ListView listview;
	private ArrayList<CartCheck> list;
	private CartCheckAdapter adapter;
	private CartCheckAdapter aadapter;
	
	/**
	 * ������
	 */
	private SearchView srv1;
	
	private int i = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_check);
		merchantid = getSharedPreferenceValue("merchantid");
		initCartCheckData();
	}
	
	public void initCartCheckData(){
		setTitleContent(R.drawable.title_orange_back, R.string.cartcheck);
		mLeftBtn.setOnClickListener(this);
		
		srv1 = (SearchView) findViewById(R.id.srv1);
		
		listview = (ListView) findViewById(R.id.activity_cart_check_lisview);
		list = new ArrayList<CartCheck>();
		
		adapter = new CartCheckAdapter(mContext, list);
		listview.setAdapter(adapter);
		
//		initPTR2();//��ʼ������ˢ��,�����������
//		setPullDownLayout();//�����������ֵ������Ϣ
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,B4_3_KaQuanInfoActivity.class);
				startActivity(intent);
			}
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int cur = arg2;
				String rstate = list.get(arg2).getState();
				if(rstate.equals("3")||rstate.equals("1")){
					ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
			        builder.setTitle("��ܰ����!");
			        builder.setMessage("�Ƿ�ȷ��ɾ��������ȯ");
			        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                //������Ĳ�������  
			        		
			        		list.remove(cur);
			        		adapter.notifyDataSetChanged();
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
					Toast.makeText(mContext, "ֻ����ʹ�ú��ѹ��ڵĿ�ȯ����ִ�г���ɾ������", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		
		srv1.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if (newText.length() != 0) {
					setFilterText(newText);
				} else {
					clearTextFilter();
				}
				return false;
			}
		});
		
		RequestDailog.showDialog(this, "���Ժ�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("pagenumber", "1");
		map.put("pagesize", "20");
		String json = JsonUtils.toJson(map);
		DataParser.getCartCheck(mContext, Request.Method.GET, HttpUtils.url_cartcheck(json), null, handler);
	}
	
//	private void setPullDownLayout() {
//		// TODO Auto-generated method stub
//		//��ȡ��������
//		ILoadingLayout proxy = listview.getLoadingLayoutProxy(true,false);
//		proxy.setPullLabel("����ˢ��");
//		proxy.setReleaseLabel("�ſ��Լ���...");
//		proxy.setRefreshingLabel("����������....");
//		proxy.setLastUpdatedLabel("���ĸ���ʱ��:"+DateFormat.getDateFormat(getApplicationContext()).format(new Date()));
//		proxy.setLoadingDrawable(getResources().getDrawable(R.drawable.default_ptr_rotate));
//	}
//
//	private void initPTR2() {
//		// TODO Auto-generated method stub
//		listview.setMode(Mode.BOTH);
//		listview.setOnRefreshListener(new OnRefreshListener2() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//				// TODO�����¼�
//				new Thread(){
//					public void run() {
//						try {
//							Thread.sleep(3000);
//							
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//						mHandler.post(new RefredshDataRunnable(cart,false));
//					};
//				}.start();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//				// TODO �����¼�
//				new Thread(){
//					public void run() {
//						try {
//							Thread.sleep(3000);
//							
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						mHandler.post(new RefredshDataRunnable(cart,true));
//						
//					};
//				}.start();
//			}
//		});
//	}
//	
//	private Handler mHandler = new Handler();
	private ArrayList<CartCheck> cart;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DataConstants.MAINACTIVITY_CARTCHECK:
				RequestDailog.closeDialog();
				cart = (ArrayList<CartCheck>) msg.obj;
				
				list.addAll(cart);
				adapter.notifyDataSetChanged();
				
				break;

			default:
				break;
			}
		};
	};
	
	private ArrayList<CartCheck> newList = new ArrayList<CartCheck>();
	
	public void setFilterText(String filterText) {
		newList.clear();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCouponname().contains(filterText) || list.get(i).getMembername().contains(filterText)) {
				newList.add(list.get(i));
			}
		}
		
		aadapter = new CartCheckAdapter(mContext,newList);
		listview.setAdapter(aadapter);
	}

	public void clearTextFilter() {
		listview.setAdapter(adapter);
	}
	
	/*
	 * ��ť����¼�
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
		list = null;
		listview = null;
	}
	
//	//�Զ���Handler���͵�Runnableʵ����
//	class RefredshDataRunnable implements Runnable{
//		
//		private boolean isLoadMore;
//		public RefredshDataRunnable(ArrayList<CartCheck> list,boolean isLoadMore){
//			this.isLoadMore = isLoadMore;
//		}
//		
//		@Override
//		public void run() {
//			// TODO ��UI�߳���ִ�еķ�����ˢ������
//			RequestDailog.showDialog(mContext, "���Ժ�");
//			if(!isLoadMore){
//				list.clear();
//				
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("merchantid", merchantid);
//				map.put("pagenumber", "1");
//				map.put("pagesize", "20");
//				String json = JsonUtils.toJson(map);
//				DataParser.getCartCheck(mContext, Request.Method.GET, HttpUtils.url_cartcheck(json), null, handler);
//			}else{
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("merchantid", merchantid);
//				map.put("pagenumber", (++i)+"");
//				map.put("pagesize", "20");
//				String json = JsonUtils.toJson(map);
//				DataParser.getCartCheck(mContext, Request.Method.GET, HttpUtils.url_cartcheck(json), null, handler);
//			}
//			
//			ToolsUtil.print("----", ""+list.size());
//			
//			//֪ͨ����ˢ�¿ؼ��������Ѽ������
//			listview.onRefreshComplete();
//		}
//	}

}
