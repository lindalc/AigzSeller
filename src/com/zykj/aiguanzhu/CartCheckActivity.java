package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.zykj.aiguanzhu.adapters.CartCheckAdapter;
import com.zykj.aiguanzhu.eneity.CartCheck;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

/**
 * 
 * @author lc
 * @date ����ʱ�䣺2015-12-26 ����2:59:11
 * @version 1.0 
 * @Description ��ȯ����
 */
public class CartCheckActivity extends BaseActivity {

	private Context mContext = CartCheckActivity.this;

	
	/**
	 * ��ȯ����listview
	 */
	private ListView listview;
	private ArrayList<CartCheck> list;
	private CartCheckAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_check);
		
		initCartCheckData();
	}
	
	public void initCartCheckData(){
		setTitleContent(R.drawable.title_orange_back, R.string.cartcheck);
		mLeftBtn.setOnClickListener(this);
		
		listview = (ListView) findViewById(R.id.activity_cart_check_lisview);
		list = new ArrayList<CartCheck>();
		
		adapter = new CartCheckAdapter(mContext, list);
		listview.setAdapter(adapter);
		
		
		RequestDailog.showDialog(this, "���Ժ�");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		map.put("pagenumber", "1");
		map.put("pagesize", "20");
		String json = JsonUtils.toJson(map);
		DataParser.getCartCheck(mContext, Request.Method.GET, HttpUtils.url_cartcheck(json), null, handler);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DataConstants.MAINACTIVITY_CARTCHECK:
				list.clear();
				RequestDailog.closeDialog();
				ArrayList<CartCheck> cart = (ArrayList<CartCheck>) msg.obj;
				
				list.addAll(cart);
				adapter.notifyDataSetChanged();
				
				break;

			default:
				break;
			}
		};
	};
	
	/*
	 * ��ť����¼�
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
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		DataParser.cancel(mContext);
		listview = null;
		list = null;
		System.gc();
	}

}
