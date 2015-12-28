package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import com.zykj.aiguanzhu.ConUserDetailActivity;
import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.parser.DataConstants;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 下午4:56:52 
 * @version 1.0 
 * @Description 
 */
public class ConcernuserListViewAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<AttentionUser> list;
	private int curPosition;
	
	public ConcernuserListViewAdapter(Context mContext,
			ArrayList<AttentionUser> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(list.size()>0){
			count  = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		curPosition = position;
		
		if(convertView!=null){
			view = convertView;
		}else{
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_attention_listview_item,parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder==null){
			holder = new  ViewHolder();
			
			holder.txt_username = (TextView) view.findViewById(R.id.concernuser_listview_item_user_name);
			holder.txt_intruce = (TextView) view.findViewById(R.id.concernuser_listview_item_intruce);
			holder.txt_date = (TextView) view.findViewById(R.id.concernuser_listview_item_date);
			holder.img_head = (ImageView) view.findViewById(R.id.concernuser_listview_item_header_icon);
			holder.img_rightarrow = (ImageView) view.findViewById(R.id.concernuser_listview_item_rightarrwo);
			holder.img_rightarrow.setVisibility(View.VISIBLE);
			
			view.setTag(holder);
		}
		
		/**
		 * 假数据  架构中没写图片处理以及缓存,所以可以用Picasso.with(mContext).fron(string).into(holder.img_head);
		 */
		holder.txt_username.setText("赵晓欢");
		holder.txt_intruce.setText("爱关注,爱生活,做最好的自己!");
		holder.txt_date.setText("2015-3-18");
		holder.img_head.setImageResource(R.drawable.main_concern_head);
		
		holder.img_rightarrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,ConUserDetailActivity.class);
				int id = list.get(curPosition).getUserid();
				Bundle bundle = new Bundle();
				bundle.putInt("id", id);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_username,txt_intruce,txt_date;
		ImageView img_head,img_rightarrow;
	}
	
}
