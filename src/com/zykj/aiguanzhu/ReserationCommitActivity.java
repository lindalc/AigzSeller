package com.zykj.aiguanzhu;

import java.util.ArrayList;

import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.ReserationUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
		
		initReserationCommitData();
	}

	/**
	 *  预约确定
	 */
	public void initReserationCommitData(){
		setTitleContent(R.drawable.title_orange_back, R.string.reserationusers);
		mLeftBtn.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listReseration = new ArrayList<ReserationUser>();
//需替换的数据Start
		ReserationUser aUser = new ReserationUser(null,"用户1",null,"蒙山生态火锅","2015-11-20",1,3);
		ReserationUser bUser = new ReserationUser(null,"用户2",null,"蒙山生态火锅","2015-11-20",1,0);
		listReseration.add(aUser);
		listReseration.add(bUser);
		
//End
		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(curPosition).getRstate();
				if(rstate == 0){  // TODO 解析数据后,把0和3换为解析出来的rstate 也就是状态码,如果是等待预约,则跳转页面,否则就弹出是否销毁    预约状态rstate(0-预约申请,1-预约同意 2-预约取消 3-预约完成)
					Intent intent = new Intent(mContext,ReserationDetailActivity.class);
				    intent.putExtra("reserationid", listReseration.get(curPosition).getReseratid());
					startActivity(intent);
				}else if(rstate ==3){
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
	}
	
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

}
