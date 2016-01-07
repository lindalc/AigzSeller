package com.zykj.aiguanzhu;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.aiguanzhu.utils.Share;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lss 2015年8月12日	卡券详情
 *
 */
public class B4_3_KaQuanInfoActivity extends BaseActivity {
	private ImageView im_b43s_back_btn;//返回
	private ImageView iv_share;//分享
//	private LinearLayout ll_wodeqianbao;//我的钱包
//	private LinearLayout ll_wodekaquan;//我的卡券
	
	private TextView tv_diyongquan;//商家名
	private TextView tv_kaquanname;//卡券名
	private TextView tv_effectivetime;//有效时间
	
	private RelativeLayout rl_youhuidetail0;//优惠详情栏
	private RelativeLayout rl_youhuidetail;//优惠详情栏
	private RelativeLayout rl_shiyongxuzhi;//使用须知
	private RelativeLayout rl_shiyongxuzhi1;//使用须知
	private TextView tv_youhuidetail;//优惠详情
	private TextView tv_shiyongxuzhi;//使用须知
	
	private ImageView iv_call;//拨打电话
	
	private String ShareContent ;
	private String ShareTitle;
	private String ShareUrl ;
	
//	private String couponname,name,effecttime,couponintroduct;//卡券名，商家名，有效时间,使用须知
	
	private int isShow = 0;
	private int isShow_s = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b43_kaquaninfo);
		initView();
		initShare();
		getIntentData();
		setData();
	}


	private void setData() {
		// TODO Auto-generated method stub
//		tv_diyongquan.setText(couponname);
//		tv_kaquanname.setText(name);
//		tv_effectivetime.setText("有效期："+effecttime);
	}


	private void getIntentData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
//		couponname = bundle.getString("couponname");
//		name = bundle.getString("name");
//		effecttime = bundle.getString("effecttime");
//		couponintroduct = bundle.getString("couponintroduct");
//		Log.e("data1", "couponname="+couponname+"name="+name+"effecttime="+effecttime);
		
	}


	public void initView() {
		im_b43s_back_btn = (ImageView) findViewById(R.id.im_b43s_back_btn);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		tv_diyongquan = (TextView) findViewById(R.id.tv_diyongquan);
		tv_kaquanname = (TextView) findViewById(R.id.tv_kaquanname);
		tv_effectivetime = (TextView) findViewById(R.id.tv_effectivetime);
		tv_youhuidetail = (TextView) findViewById(R.id.tv_youhuidetail);
		tv_shiyongxuzhi = (TextView) findViewById(R.id.tv_shiyongxuzhi);
		rl_youhuidetail0 = (RelativeLayout) findViewById(R.id.rl_youhuidetail0);
		rl_youhuidetail = (RelativeLayout) findViewById(R.id.rl_youhuidetail);
		rl_shiyongxuzhi = (RelativeLayout) findViewById(R.id.rl_shiyongxuzhi);
		rl_shiyongxuzhi1 = (RelativeLayout) findViewById(R.id.rl_shiyongxuzhi1);
		iv_call = (ImageView) findViewById(R.id.iv_call);
//		ll_wodeqianbao = (LinearLayout) findViewById(R.id.ll_wodeqianbao);
//		ll_wodekaquan = (LinearLayout) findViewById(R.id.ll_wodekaquan);

		setListener(im_b43s_back_btn,iv_share,rl_youhuidetail0,rl_shiyongxuzhi,iv_call);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b43s_back_btn:
			this.finish();			
		break;
		
		case R.id.rl_youhuidetail0:
			Log.e("isShow", isShow+"");
			if (isShow == 1){
				rl_youhuidetail.setVisibility(View.GONE);
				isShow = 0;
			}else {
				rl_youhuidetail.setVisibility(View.VISIBLE);
//				tv_youhuidetail.setText(couponname);
				isShow = 1;
			}
			break;
		case R.id.rl_shiyongxuzhi:
			Log.e("isShow", isShow+"");
			if (isShow_s == 1){
				rl_shiyongxuzhi1.setVisibility(View.GONE);
				isShow_s = 0;
			}else {
				rl_shiyongxuzhi1.setVisibility(View.VISIBLE);
//				tv_shiyongxuzhi.setText(couponintroduct);
				isShow_s = 1;
			}
			break;
		case R.id.iv_call:
			// 传入服务， parse（）解析号码
			ToolsUtil.Notic(this, "是否要给店家打电话", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(Intent.ACTION_CALL, Uri
			                    .parse("tel:" + "10086"));
			            // 通知activtity处理传入的call服务
			        startActivity(intent);	
				}
			});
           
			break;
		case R.id.iv_share:
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);		
			break;
		default:
			break;

		}
	}
	private void initShare() {
		ShareContent = "我在爱关注领取了优惠券，送给你了，快来领吧";
		ShareTitle = "优惠券";
		ShareUrl = "http://www.pgyer.com/ov1S";
	}
}
