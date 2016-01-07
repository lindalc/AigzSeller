package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zykj.aiguanzhu.R;
import com.zykj.aiguanzhu.eneity.MyIntegral;

/**
 * @author  lc 
 * @date 创建时间：2015-12-30 上午9:20:15 
 * @version 1.0 
 * @Description 积分变化适配器
 */
public class MyIntegralAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<MyIntegral> list;
	
	public MyIntegralAdapter(Context mContext, ArrayList<MyIntegral> list) {
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
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_myintegral_listview_item, parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder();
			
			holder.txt_chargevalue = (TextView) view.findViewById(R.id.mypsdchargevalue);
			holder.txt_depositevalue = (TextView) view.findViewById(R.id.mypsddepositvalue);
			holder.txt_invitevalue = (TextView) view.findViewById(R.id.mypsdinvitevalue);
			holder.txt_spendvalue = (TextView) view.findViewById(R.id.mypsdspendvalue);
		}
		
		MyIntegral integral = list.get(position);
		
		holder.txt_chargevalue.setText(integral.getChargevalue());
		holder.txt_depositevalue.setText(integral.getDrawvalue());
		holder.txt_invitevalue.setText(integral.getInvitatvalue());
		holder.txt_spendvalue.setText(integral.getRebatevalue());
		
		return view;
	}
	
	class ViewHolder{
		TextView txt_chargevalue,txt_invitevalue,txt_spendvalue,txt_depositevalue;
	}

}
