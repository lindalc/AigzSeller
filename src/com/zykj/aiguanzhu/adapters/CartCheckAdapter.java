package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.eneity.CartCheck;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author  lc 
 * @date 创建时间：2015-12-26 下午3:33:49 
 * @version 1.0 
 * @Description 卡券核销Adapter
 */
public class CartCheckAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<CartCheck> list;
	
	public CartCheckAdapter(Context mContext, ArrayList<CartCheck> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(list.size()>0){
			count = list.size();
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
		if(convertView != null){
			view = convertView;
		}else{
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_cart_check_listview_item, parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder();
			
			holder.txt_cartname = (TextView) view.findViewById(R.id.activity_cartcheck_listview_item_txt_cartname);
			holder.txt_name = (TextView) view.findViewById(R.id.activity_cartcheck_listview_item_txt_name);
			holder.txt_state = (TextView) view.findViewById(R.id.activity_cartcheck_listview_item_txt_state);
			holder.img_headportait = (ImageView) view.findViewById(R.id.activity_cartcheck_listview_item_img_headporait);
			holder.layout = (LinearLayout) view.findViewById(R.id.activity_cartcheck_listview_item_layout);
			holder.gray = (TextView) view.findViewById(R.id.activity_cartcheck_listview_item_gray);
			
			view.setTag(holder);
		}
		
		CartCheck cartCheck = list.get(position);
		
		holder.layout.setBackgroundResource(R.color.orange);
		holder.txt_cartname.setText(cartCheck.getCartname());
		holder.txt_name.setText(cartCheck.getName());
		holder.txt_state.setText(cartCheck.getState());
		holder.img_headportait.setImageResource(R.drawable.main_concern_head);
		if(position == list.size()-1){
			holder.gray.setVisibility(View.GONE);
		}else{
			holder.gray.setVisibility(View.VISIBLE);
		}
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_cartname,txt_name,txt_state,gray;
		ImageView img_headportait;
		LinearLayout layout;
	}

}
