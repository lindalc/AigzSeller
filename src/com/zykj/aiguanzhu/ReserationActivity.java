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

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:37
 * @version 1.0 
 * @Description 预约确定
 */
public class ReserationActivity extends BaseActivity {

	private Context mContext = ReserationActivity.this;
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
		setContentView(R.layout.activity_reseration);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initReserationUserData();
	}

	/**
	 *  预约
	 */
	public void initReserationUserData(){
		setTitleContent(R.drawable.title_orange_back, R.string.reserationusers);
		mLeftBtn.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listReseration = new ArrayList<ReserationUser>();


		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(curPosition).getRstate();
				if(rstate ==3){
					ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
			        builder.setTitle("温馨提醒!");
			        builder.setMessage("是否确定删除此条预约信息");
			        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                //设置你的操作事项  
			                listReseration.remove(curPosition);
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
			}
		});
		
		RequestDailog.showDialog(mContext, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		DataParser.getReserationUser(mContext, Request.Method.GET, HttpUtils.url_reserationUser(json), null, handler);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_RESERATIONUSER:
				RequestDailog.closeDialog();
				
				ArrayList<ReserationUser> list = (ArrayList<ReserationUser>) msg.obj;
				
				for(int i=0;i<list.size();i++){
					if(list.get(i).getRstate()==1){
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

}
