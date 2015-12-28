package com.zykj.aiguanzhu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.aiguanzhu.adapters.MainGridviewAdapter;
import com.zykj.aiguanzhu.custome.CustomDialog;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lc
 * @date 创建时间：2015-12-23 上午9:50:14
 * @version 1.0 
 * @Description 商户主界面
 */
public class MainActivity extends BaseActivity implements OnItemClickListener{

	private Context mContext = MainActivity.this;
	
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
	private ImageView head_img;
	
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
    	
    	rechargeLayout.setOnClickListener(this);
    	psdLayout.setOnClickListener(this);
    }
    
    public void initGrid(){

    	setTitleContent(R.drawable.title_back, 0,R.string.seller);
    	mLeftBtn.setOnClickListener(this);
    	
    	head_img = (ImageView) findViewById(R.id.main_img_headportrait);
    	head_img.setOnClickListener(this);
    	
    	gridview = (GridView) findViewById(R.id.main_gridview);
    	adapter = new MainGridviewAdapter(mContext, mImageRess);
    	
    	gridview.setAdapter(adapter);
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
		case R.id.main_headportrait:
			//TODO 登陆
			break;
		case R.id.activity_main_recharge:
			// TODO 充值
			Intent rechargeIntent = new Intent();
			intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			break;
		case R.id.activity_main_psd:
			// 积分
			Intent psdIntent = new Intent();
			intentJump(psdIntent,MyPsdActivity.class,DataConstants.MAINACTIVITY_PSD);
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
		case 1:  // 1 代表订单验证码项
			CustomDialog.Builder builder = new CustomDialog.Builder(mContext);  
	        builder.setTitle("请输入订单验证码"); 
	        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //设置你的操作事项  
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
		case 2: // 2 我的关注
			Intent attentionIntent = new Intent();
			intentJump(attentionIntent,AttentionActivity.class,position);
			break;
		case 3: // 3 预约用户
			Intent reserationUsersIntent = new Intent();
			intentJump(reserationUsersIntent,ReserationActivity.class,position);
			break;
		case 4: // 4 预约确定
			Intent reserationCommitIntent = new Intent();
			intentJump(reserationCommitIntent,ReserationCommitActivity.class,position);
			break;
		case 5: // 5 我的邀请
			Intent inviteIntent = new Intent();
			intentJump(inviteIntent,InviteActivity.class,position);
			break;
		case 6: // 6 卡券充值
			Intent rechargeIntent = new Intent();
			intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			break;
		case 7: // 7 卡券数据
			Intent cartDataIntent = new Intent();
			intentJump(cartDataIntent,CartDataActivity.class,position);
			break;
		case 8: // 8 卡券核销
			Intent cartCheckIntent = new Intent();
			intentJump(cartCheckIntent, CartCheckActivity.class,position);
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

}
