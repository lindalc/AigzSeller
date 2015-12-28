package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.adapters.ReserationAdapter.ViewHolder;
import com.zykj.aiguanzhu.eneity.ReserationUser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author  lc 
 * @date 创建时间：2015-12-25 下午4:27:14 
 * @version 1.0 
 * @Description
 */
public class InviteAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ReserationUser> list;
	
	public InviteAdapter(Context mContext, ArrayList<ReserationUser> list) {
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
			holder.txt_right = (TextView) view.findViewById(R.id.concernuser_listview_item_righttext);
			holder.txt_right.setVisibility(View.VISIBLE);
			
			view.setTag(holder);
		}
		
		ReserationUser reserationUser = list.get(position);
		
		holder.txt_username.setText(reserationUser.getUsername());
		holder.txt_intruce.setText(reserationUser.getGoodname());
		holder.txt_date.setText(reserationUser.getDatetime());
		if(reserationUser.getRstate() == 0){
			holder.txt_right.setText("等待确定");
		}else if(reserationUser.getRstate() == 3){
			holder.txt_right.setText("同意邀请");
		}
		holder.img_head.setImageResource(R.drawable.main_concern_head);
		
		
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_username,txt_intruce,txt_date,txt_right;
		ImageView img_head;
	}


}
