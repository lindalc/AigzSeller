package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.qr_codescan.MipcaActivityCapture;
import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.adapters.MainGridviewAdapter;
import com.zykj.aiguanzhu.custome.CustomDialog;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lc
 * @date 创建时间：2015-12-23 上午9:50:14
 * @version 1.0 
 * @Description 商户主界面
 */
public class MainActivity extends BaseActivity implements OnItemClickListener{

	private Context mContext = MainActivity.this;
	
	private final static int SCANNIN_GREQUEST_CODE = 101;
	
	//gridview的声明以及写死的数据
	private GridView gridview;
	private MainGridviewAdapter adapter;
	private static int[] mImageRess = new int[] {
		R.drawable.main_detailicon_scanzxing,
		R.drawable.main_detailicon_identitycode,
		R.drawable.main_detailicon_concern,
		R.drawable.main_detailicon_ordercustome,
		R.drawable.main_detailicon_orderconfirm,
		R.drawable.main_detailicon_invite,
		R.drawable.main_detailicon_cartrecharge,
		R.drawable.main_detailicon_cartdata,
		R.drawable.main_detailicon_cartdestory,
		};
	
	// 头像
	private LinearLayout head_img;
	private ImageView img;
	private TextView txt_name,txt_total,txt_spend,txt_count;
	
	/**
	 * 充值和积分按钮
	 */
	private LinearLayout rechargeLayout,psdLayout;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initData();
        initGrid();
        
        gridview.setOnItemClickListener(this);
    }
    
    public void initData(){
    	rechargeLayout = (LinearLayout) findViewById(R.id.activity_main_recharge);
    	psdLayout = (LinearLayout) findViewById(R.id.activity_main_psd);
    	head_img = (LinearLayout) findViewById(R.id.main_headportrait);
    	head_img.setOnClickListener(this);
    	rechargeLayout.setOnClickListener(this);
    	psdLayout.setOnClickListener(this);
    	
    	img = (ImageView) findViewById(R.id.main_img_headportrait);
    	txt_name = (TextView) findViewById(R.id.main_text_name);
    	txt_total = (TextView) findViewById(R.id.main_txt_total_income);
    	txt_spend = (TextView) findViewById(R.id.main_txt_coupon);
    	txt_count = (TextView) findViewById(R.id.main_txt_couponcount);
    	
    	
    }
    
    public void initGrid(){
    	
    	if (isLoged()) {
    		setTitleContent(R.drawable.title_back, R.string.quit,R.string.seller,1);
    		mRightTextBtn.setOnClickListener(this);
    	}else{
    		setTitleContent(R.drawable.title_back, 0,R.string.seller);
    	}
    	mLeftBtn.setOnClickListener(this);
    	
    	
    	gridview = (GridView) findViewById(R.id.main_gridview);
    	adapter = new MainGridviewAdapter(mContext, mImageRess);
    	
    	gridview.setAdapter(adapter);
    	
    	if (isLoged()) {
			//请求头像，如果没有头像，提示用户上传头像
    		txt_total.setText("总收入:"+getSharedPreferenceValue("totalIncome"));
    		ToolsUtil.print("----", "total="+getSharedPreferenceValue("totalIncome"));
    		txt_spend.setText("消费券:"+getSharedPreferenceValue("rebaterate"));
    		txt_count.setText("销量券:"+getSharedPreferenceValue("sellnum"));
			txt_name.setText(getSharedPreferenceValue("name"));
			Picasso.with(mContext).load(HttpUtils.img_url+getSharedPreferenceValue("imgpath")).placeholder(R.drawable.main_icon_headportrait).error(R.drawable.main_icon_headportrait).into(img);
		}
    }
    
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
		case R.id.right_text_btn:
			// TODO 退出
			ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
	        builder.setTitle("温馨提醒!");
	        builder.setMessage("您确定要退出该账号吗");
	        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //设置你的操作事项  
	                putSharedPreferenceValue("isLoged", "0");
	    			txt_total.setText("总收入:"+0.0);
	        		txt_spend.setText("消费券:"+0.0);
	        		txt_count.setText("销量券:"+0.0);
	    			txt_name.setText(R.string.unlogin_user_name);
	    			img.setImageResource(R.drawable.main_icon_headportrait);
	            }  
	        });  
	  
	        builder.setNegativeButton("",  
	                new android.content.DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int which) {  
	                        dialog.dismiss();
	                    }  
	                });  
	  
	        builder.create().show();  
			
			break;
		case R.id.main_headportrait:
			if(!isLoged()){
				Intent loginIntent = new Intent();
				loginIntent.setClass(mContext, LoginActivity.class);
				startActivityForResult(loginIntent, DataConstants.MAINACTIVITY_LOGIN);
			}else{
				Toast.makeText(mContext, "已登录", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.activity_main_recharge:
			// TODO 充值
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.activity_main_psd:
			// 积分
			if(isLoged()){
				Intent psdIntent = new Intent();
				intentJump(psdIntent,MyPsdActivity.class,DataConstants.MAINACTIVITY_PSD);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * GridView 点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		switch(position){
		case 0: // 0 二维码
			if(isLoged()){
				Intent intent = new Intent();
				intent.setClass(mContext, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 1:  // 1 订单验证码项
			if(isLoged()){
				final CustomDialog.Builder builder = new CustomDialog.Builder(mContext);  
		        builder.setTitle("请输入订单验证码"); 
		        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		                //设置你的操作事项  
		                Map<String, String> map = new HashMap<String, String>();
		                ToolsUtil.print("----", "message = "+builder.getMessage());
		        		map.put("code", builder.getMessage());
		        		String json = JsonUtils.toJson(map);
		                DataParser.getYanZhengMa(mContext,Request.Method.GET,HttpUtils.url_yanzhengma(json),null,handler);
		            }  
		        });  
		  
		        builder.setNegativeButton("",  
		                new android.content.DialogInterface.OnClickListener() {  
		                    public void onClick(DialogInterface dialog, int which) {  
		                        dialog.dismiss();
		                    }  
		                });  
		  
		        builder.create().show();  
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
	        break;
		case 2: // 2 我的关注
			if(isLoged()){
				Intent attentionIntent = new Intent();
				intentJump(attentionIntent,AttentionActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 3: // 3 预约用户
			if(isLoged()){
				Intent reserationUsersIntent = new Intent();
				intentJump(reserationUsersIntent,ReserationCommitActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 4: // 4 预约确定
			if(isLoged()){
				Intent reserationCommitIntent = new Intent();
				intentJump(reserationCommitIntent,ReserationActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 5: // 5 我的邀请
			if(isLoged()){
				Intent inviteIntent = new Intent();
				intentJump(inviteIntent,InviteActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 6: // 6 卡券充值
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 7: // 7 卡券数据
			if(isLoged()){
				Intent cartDataIntent = new Intent();
				intentJump(cartDataIntent,CartDataActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case 8: // 8 卡券核销
			if(isLoged()){
				Intent cartCheckIntent = new Intent();
				intentJump(cartCheckIntent, CartCheckActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}
	
	public void intentJump(Intent intent,Class cla,int position){
		intent.setClass(mContext, cla);
		intent.putExtra("number",position);
		startActivity(intent);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DataConstants.MAINACTIVITY_CODE:
				String errdesc = (String) msg.obj;
				
				if(errdesc.equals("确认订单成功")){
					Intent dingdanIntent = new Intent();
					intentJump(dingdanIntent, DingDanQueRenActivity.class, -2);
				}else{
					Toast.makeText(mContext, errdesc, Toast.LENGTH_LONG).show();
				}
				
				break;
			default:
				break;
			}
		};
	};
	
	public  boolean isLoged() {
		String isLoged = null;
		if (getSharedPreferenceValue("isLoged")!=null) {
			isLoged = getSharedPreferenceValue("isLoged");
			if (isLoged.equals("1")) {
				return true;
			}else {
				return false;
			}
		}else {
			putSharedPreferenceValue("isLoged", "0");
			return false;
		}
	}

}
