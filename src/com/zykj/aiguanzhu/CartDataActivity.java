package com.zykj.aiguanzhu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.zykj.aiguanzhu.custome.BarChartView;
import com.zykj.aiguanzhu.custome.HistogramView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_data);
		
		setTitleContent(R.drawable.title_orange_back, R.string.cartdata);
		mLeftBtn.setOnClickListener(this);
//		for (String tem : LABLE_TEXT) {
//			optionsX.add(tem);
//		}
//		for (String tem : Y_TEXT) {
//			optionsY.add(tem);
//		}
		layoutViewContent = (LinearLayout) findViewById(R.id.barview_content);
		layoutViewContent.removeAllViews();
		view = new HistogramView(mContext);
//		view.initData(first, second, optionsX,optionsY, "");
		layoutViewContent.addView(view);
		layoutViewContent.setBackgroundColor(0xffffffff);
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
