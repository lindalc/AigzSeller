package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.zykj.aiguanzhu.eneity.ReserationDetail;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;

/**
 * @author lc
 * @date 创建时间：2015-12-25 下午4:24:13
 * @version 1.0 
 * @Description 预约详情
 */
public class ReserationDetailActivity extends BaseActivity {

	private Context mContext = ReserationDetailActivity.this;
	private int reserationid;
	
	private TextView txt_username,txt_otherinfo,txt_action,txt_datetime,txt_personnumber,txt_mobile;
	private ImageView img_agree,img_refuse;
	
	private LinearLayout layout;
	private int rstate;
	private ReserationDetail reserationDetail;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration_detail);
		
		rstate = getIntent().getIntExtra("rstate", -1);
		
		reserationid = getIntent().getIntExtra("reserationid", -1); 
		
		initData();
	}
	
	public void initData(){
		setTitleContent(R.drawable.title_back, 0,R.string.reserationdetail );
		mLeftBtn.setOnClickListener(this);
		
		txt_username = (TextView) findViewById(R.id.activity_reserationdetail_username);
		txt_otherinfo = (TextView) findViewById(R.id.activity_reserationdetail_otherinfo);
		txt_action = (TextView) findViewById(R.id.activity_reserationdetail_action);
		txt_datetime = (TextView) findViewById(R.id.activity_reserationdetail_datetime);
		txt_personnumber = (TextView) findViewById(R.id.activity_reserationdetail_personnumber);
		txt_mobile = (TextView) findViewById(R.id.activity_reserationdetail_mobile);
		layout = (LinearLayout) findViewById(R.id.activity_reseration_detail_layout);
		
		if(rstate == 0){
			layout.setVisibility(View.VISIBLE);
			
			img_agree = (ImageView) findViewById(R.id.activity_reserationdetail_agree);
			img_refuse = (ImageView) findViewById(R.id.activity_reserationdetail_refuse);
			
			img_agree.setOnClickListener(this);
			img_refuse.setOnClickListener(this);
		}
		
		RequestDailog.showDialog(this, "正在登陆请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("reserationid", "1");
		String json = JsonUtils.toJson(map);
		DataParser.getReserationDetail(mContext, Request.Method.GET, HttpUtils.url_reserationDetail(json), null, handler);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DataConstants.MAINACTIVITY_RESERATIONDETAIL:
				RequestDailog.closeDialog();
				reserationDetail = (ReserationDetail) msg.obj;
				
				txt_username.setText(reserationDetail.getUsername());
				txt_otherinfo.setText(reserationDetail.getOtherinfo());
				txt_action.setText(reserationDetail.getGoodname());
				txt_datetime.setText(reserationDetail.getDatetime());
				txt_personnumber.setText(reserationDetail.getPersonNum());
				txt_mobile.setText(reserationDetail.getMobile());
				
				break;
			case DataConstants.RESERATION_DETAIL:
				RequestDailog.closeDialog();
				String errdesc = (String) msg.obj;
				Toast.makeText(mContext, errdesc, Toast.LENGTH_LONG).show();
				layout.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		};
	};
	
	/*
	 * 按钮点击事件
	 * (non-Javadoc)
	 * @see com.weiyuan.BaseActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_btn:
			this.finish();
			break;
		case R.id.activity_reserationdetail_agree:
			Map<String, String> mapagree = new HashMap<String, String>();
			mapagree.put("reserationid", reserationDetail.getReserationid()+"");
			mapagree.put("rstate", "1");
			String jsonagree = JsonUtils.toJson(mapagree);
			DataParser.getReserationUpdate(mContext, Request.Method.GET, HttpUtils.url_reserationUpdatel(jsonagree), null, handler);
			break;
		case R.id.activity_reserationdetail_refuse:
			Map<String, String> map = new HashMap<String, String>();
			map.put("reserationid",reserationDetail.getReserationid()+"");
			map.put("rstate", "2");
			String json = JsonUtils.toJson(map);
			DataParser.getReserationUpdate(mContext, Request.Method.GET, HttpUtils.url_reserationUpdatel(json), null, handler);
			break;
		default:
			break;
		}
	}
}
