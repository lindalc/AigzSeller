package com.zykj.aiguanzhu.adapters;

import java.util.ArrayList;
import java.util.List;

import com.zykj.aiguanzhu.R;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 下午1:08:31 
 * @version 1.0 
 * @Description 
 */
public class MainGridviewAdapter extends BaseAdapter {

	private Context mContext;
	private int[] arr;

	public MainGridviewAdapter(Context mContext, int[] arr) {
		super();
		this.mContext = mContext;
		this.arr = arr;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(arr.length>0){
			count = arr.length;
		}
		return count ; 
	}

	@Override
	public Object getItem(int position) {
		return arr[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View ret = null;
		
		if(convertView != null){
			ret = convertView;
		}else{
			ret = LayoutInflater.from(mContext).inflate(R.layout.activity_main_gridview_item, parent,false);
		}
		
		ImageView img = (ImageView) ret.findViewById(R.id.activity_main_gridview_item_img);
		img.setBackgroundResource(arr[position]);
		
		return ret;
	}

}
