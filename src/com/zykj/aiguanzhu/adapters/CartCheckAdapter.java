package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.custome.CustomImageView;
import com.zykj.aiguanzhu.eneity.CartCheck;
import com.zykj.aiguanzhu.utils.HttpUtils;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
			holder.img_headportait = (CustomImageView) view.findViewById(R.id.activity_cartcheck_listview_item_img_headporait);
			holder.layout = (RelativeLayout) view.findViewById(R.id.activity_cartcheck_listview_item_layout);
			holder.gray = (TextView) view.findViewById(R.id.activity_cartcheck_listview_item_gray);
			
			view.setTag(holder);
		}
		
		CartCheck cartCheck = list.get(position);
		String state = cartCheck.getState();
		
		holder.layout.setBackgroundColor(android.graphics.Color.parseColor(cartCheck.getCouponcolor()));
		holder.txt_cartname.setText(cartCheck.getCouponname());
		holder.txt_name.setText(cartCheck.getMembername());
		switch(Integer.parseInt(state)){
		case 0:
			holder.txt_state.setText("未使用");
			break;
		case 1:
			holder.txt_state.setText("已使用");
			break;
		case 2:
			holder.txt_state.setText("赠送中");
			break;
		case 3:
			holder.txt_state.setText("已过期");
			break;
		case 4:
			holder.txt_state.setText("未支付");
			break;
		default:
			break;
		}
		
		Picasso.with(mContext).load(HttpUtils.img_url+cartCheck.getCouponimage()).resize(70, 70).placeholder(R.drawable.main_icon_headportrait).into(holder.img_headportait);
		
		if(position == list.size()-1){
			holder.gray.setVisibility(View.GONE);
		}else{
			holder.gray.setVisibility(View.VISIBLE);
		}
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_cartname,txt_name,txt_state,gray;
		CustomImageView img_headportait;
		RelativeLayout layout;
	}

}
