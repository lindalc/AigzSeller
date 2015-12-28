package com.zykj.aiguanzhu;

import java.util.ArrayList;

import com.zykj.aiguanzhu.adapters.CartCheckAdapter;
import com.zykj.aiguanzhu.eneity.CartCheck;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * 
 * @author lc
 * @date 创建时间：2015-12-26 下午2:59:11
 * @version 1.0 
 * @Description 卡券核销
 */
public class CartCheckActivity extends BaseActivity {

	private Context mContext = CartCheckActivity.this;

	
	/**
	 * 卡券核销listview
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
		CartCheck c1 = new CartCheck(null,null, R.string.cartcheck+"", R.string.cartcheck+"", R.string.cartcheck+"");
		CartCheck c2 = new CartCheck(null,null, R.string.cartcheck+"", R.string.cartcheck+"", R.string.cartcheck+"");
		list.add(c1);
		list.add(c2);
		adapter = new CartCheckAdapter(mContext, list);
		listview.setAdapter(adapter);
		
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
