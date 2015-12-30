package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.custome.CustomImageView;
import com.zykj.aiguanzhu.eneity.AttentionUserDetail;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.BitmapCache;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.ImageUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;

/**
 * @author lc
 * @date 创建时间：2015-12-24 上午10:50:39
 * @version 1.0 
 * @Description 个人详情
 */
public class ConUserDetailActivity extends BaseActivity {

	private Context mContext = ConUserDetailActivity.this;
	private Intent intent;
	private String name;
	
	 private CustomImageView img_head;
	 private TextView txt_name,txt_nickname,txt_sex,txt_birth,txt_address;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_con_user_detail);
		
		intent = getIntent();
		name = intent.getStringExtra("name");
		initData();
	}
	
	public void initData(){
		
		setTitleContent(R.drawable.title_back, 0, R.string.attention_detail_title);
		mLeftBtn.setOnClickListener(this);
		
		img_head = (CustomImageView) findViewById(R.id.con_user_detail_headporait);
		txt_name = (TextView)findViewById(R.id.con_user_detail_name);
		txt_nickname = (TextView) findViewById(R.id.con_user_detail_nickname);
		txt_sex = (TextView) findViewById(R.id.con_user_detail_sex);
		txt_birth = (TextView) findViewById(R.id.con_user_detail_birth);
		txt_address = (TextView) findViewById(R.id.con_user_detail_address);
		RequestDailog.showDialog(this, "请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", ""+intent.getIntExtra("id", 1));
		String json = JsonUtils.toJson(map);
		DataParser.getAttentionDetail(mContext, Request.Method.GET, HttpUtils.url_attentionDetail(json), null, handler);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DataConstants.MAINACTIVITY_ATTENTIONDEAIL:
				RequestDailog.closeDialog();
				AttentionUserDetail aUser = (AttentionUserDetail) msg.obj;
				
				Picasso.with(mContext).load(HttpUtils.img_url+aUser.getHeadportain()).placeholder(R.drawable.main_icon_headportrait).resize(70, 70) .centerCrop().into(img_head);;
				txt_name.setText(name);
				txt_nickname.setText(aUser.getName());
				txt_sex.setText(aUser.getSex());
				txt_birth.setText(aUser.getBirthday());
				txt_address.setText(aUser.getAddress());
				
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
		default:
			break;
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		DataParser.cancel(mContext);
		img_head = null;
		System.gc();
	}
}
