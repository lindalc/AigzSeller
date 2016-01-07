package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.zykj.aiguanzhu.custome.HistogramView;
import com.zykj.aiguanzhu.eneity.CartData;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;

/**
 * @author  lc 
 * @date 创建时间：2015-12-26 上午9:31:18 
 * @version 1.0 
 * @Description 卡券数据
 */
public class CartDataActivity extends BaseActivity implements OnClickListener {

	private Context mContext = CartDataActivity.this;
	
	public static String LABLE_TEXT[] = { "", "3.19", "3.20", "3.21", "3.22","3.23","3.24","3.18"};
	public static String Y_TEXT[] = {"5人","10人","20人","25人","30人","35人","40人"};
	private LinearLayout layoutViewContent;
//	private double first[] = new double[]{39,39,39,39,39,39,39} ;
//	private double second[] = new double[] { 50, 60, 88, 91 };
//	private List<String> optionsX = new ArrayList<String>();
//	private List<String> optionsY = new ArrayList<String>();
//	private boolean isSingleView=true;
	private HistogramView view;
	
	private int[] num = null;
	private String[] date = null; 
	
	private String merchantid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_data);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		merchantid = getSharedPreferenceValue("merchantid");
		
		setTitleContent(R.drawable.title_orange_back, R.string.cartdatafresh,R.string.cartdata,"");
		mRightTextBtn.setOnClickListener(this);
		
		mLeftBtn.setOnClickListener(this);
//		for (String tem : LABLE_TEXT) {
//			optionsX.add(tem);
//		}
//		for (String tem : Y_TEXT) {
//			optionsY.add(tem);
//		}
		layoutViewContent = (LinearLayout) findViewById(R.id.barview_content);
		layoutViewContent.removeAllViews();
		
		initData();
		
		
	}
	
	public void initData(){
		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		String json = JsonUtils.toJson(map);
		DataParser.getCartData(mContext, Request.Method.GET, HttpUtils.url_cartdata(json), null, handler);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
				case DataConstants.MAINACTIVITY_CARTDATA:
					RequestDailog.closeDialog();
					ArrayList<CartData> cart = (ArrayList<CartData>) msg.obj;
					
					
					num = new int[cart.size()];
					date = new String[cart.size()];
					for(int i=0;i<num.length;i++){
						num[i] = cart.get(i).getNum();
						StringBuffer buffer = new StringBuffer();
						buffer.append(cart.get(i).getDate().toString().substring(4,6)).append(".").append(cart.get(i).getDate().toString().substring(6));
						date[i] = buffer.toString();
						buffer = null;
					}
					
					view = new HistogramView(mContext,num,date);
//					view.initData(first, second, optionsX,optionsY, "");
					layoutViewContent.addView(view);
					layoutViewContent.setBackgroundColor(0xffffffff);
					
					break;
				default :
					break;
			}
		};
	};

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
		case R.id.right_text_btn:
			RequestDailog.showDialog(this, "请稍后");
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchantid", merchantid);
			String json = JsonUtils.toJson(map);
			DataParser.getCartData(mContext, Request.Method.GET, HttpUtils.url_cartdata(json), null, handler);
			break;
		default:
			break;
		}
	}
	
}
