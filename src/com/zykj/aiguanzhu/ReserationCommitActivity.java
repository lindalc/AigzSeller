package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
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
	
	/**
	 * 标题栏
	 */
	private RelativeLayout rLayout;
	
	/**
	 *  预约用户的list数据
	 */
	private ReserationAdapter adapterReseration;
	private ArrayList<ReserationUser> listReseration;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration_commit);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		
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
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listReseration = new ArrayList<ReserationUser>();

		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		RequestDailog.showDialog(mContext, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("pagesize", "10");
		map.put("pagenumber", "1");
		map.put("merchantid", "1");
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
				}
				return true;
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(curPosition).getRstate();
				Intent intent = new Intent(mContext,ReserationDetailActivity.class);
				intent.putExtra("rstate", rstate);
				ToolsUtil.print("----", "rstate = "+rstate);
			    intent.putExtra("reserationid", listReseration.get(arg2).getReseratid());
				startActivity(intent);
			}
		});
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_RESERATIONUSER:
				RequestDailog.closeDialog();
				listReseration.clear();
				ArrayList<ReserationUser>  list = (ArrayList<ReserationUser>) msg.obj;
				
				listReseration.addAll(list);
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
			listView = null;
			System.gc();
		}

}
