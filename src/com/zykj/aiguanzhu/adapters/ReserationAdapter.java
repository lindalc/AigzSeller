package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.ConUserDetailActivity;
import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.adapters.ConcernuserListViewAdapter.ViewHolder;
import com.zykj.aiguanzhu.eneity.ReserationUser;
import com.zykj.aiguanzhu.utils.HttpUtils;

/**
 * @author  lc 
 * @date 创建时间：2015-12-24 上午11:58:39 
 * @version 1.0 
 * @Description 预约用户Adapter
 */
public class ReserationAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ReserationUser> list;
	
	public ReserationAdapter(Context mContext, ArrayList<ReserationUser> list) {
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
		holder.txt_intruce.setTextColor(Color.BLACK);
		holder.txt_date.setText("预约时间:"+reserationUser.getDatetime());
		holder.txt_date.setTextColor(Color.GRAY);
		if(reserationUser.getRstate() == 0){
			holder.txt_right.setText("等待确定");
		}else if(reserationUser.getRstate() == 1){
			holder.txt_right.setText("已经确定");
		}
		Picasso.with(mContext).load(HttpUtils.img_url+reserationUser.getHeadportain()).placeholder(R.drawable.main_icon_headportrait).resize(70, 70).into(holder.img_head);
		
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_username,txt_intruce,txt_date,txt_right;
		ImageView img_head;
	}

}
