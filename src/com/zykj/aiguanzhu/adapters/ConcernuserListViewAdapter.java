package com.zykj.aiguanzhu.adapters;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.ConUserDetailActivity;
import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.eneity.AttentionUser;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.BitmapCache;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.ImageUtils;
import com.zykj.aiguanzhu.utils.ToolsUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		
		final AttentionUser aUser = list.get(position);
		holder.txt_username.setText(aUser.getName());
		holder.txt_intruce.setText(aUser.getSign());
		holder.txt_date.setText(aUser.getIntime());
		final ViewHolder h = holder;
		
		Picasso.with(mContext).load(HttpUtils.img_url+aUser.getHeadportain()).placeholder(R.drawable.main_icon_headportrait).resize(70, 70) .centerCrop().into(holder.img_head);
		
//		boolean isFileExist = ImageUtils.isFileExist(HttpUtils.img_url+aUser.getHeadportain(), mContext);
//		
//		Bitmap bitmap = null;
//		if (isFileExist) {
//			bitmap = ImageUtils.getBitmap2(HttpUtils.img_url+aUser.getHeadportain(), mContext,80,80);
//			
//			ToolsUtil.print("----", "bitmap = "+bitmap);
//			h.img_head.setImageBitmap(bitmap);
//		}else {
//			//如果有网络的话，再通过vollery调用
//			ImageUtils.load(mContext, h.img_head, HttpUtils.img_url+aUser.getHeadportain(), 80, 80);
//			ImageUtils.saveBitmap2(bitmap, HttpUtils.img_url+aUser.getHeadportain(), mContext);
//		}
//		
//		bitmap = null;
		
		
		holder.img_rightarrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,ConUserDetailActivity.class);
				intent.putExtra("id", aUser.getUserid());
				intent.putExtra("name", aUser.getName());
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
