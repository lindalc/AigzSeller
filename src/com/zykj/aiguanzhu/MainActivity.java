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
 * @date ����ʱ�䣺2015-12-23 ����9:50:14
 * @version 1.0 
 * @Description �̻�������
 */
public class MainActivity extends BaseActivity implements OnItemClickListener{

	private Context mContext = MainActivity.this;
	
	private final static int SCANNIN_GREQUEST_CODE = 101;
	
	//gridview�������Լ�д��������
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
	
	// ͷ��
	private LinearLayout head_img;
	private ImageView img;
	private TextView txt_name,txt_total,txt_spend,txt_count;
	
	/**
	 * ��ֵ�ͻ��ְ�ť
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
			//����ͷ�����û��ͷ����ʾ�û��ϴ�ͷ��
    		txt_total.setText("������:"+getSharedPreferenceValue("totalIncome"));
    		ToolsUtil.print("----", "total="+getSharedPreferenceValue("totalIncome"));
    		txt_spend.setText("����ȯ:"+getSharedPreferenceValue("rebaterate"));
    		txt_count.setText("����ȯ:"+getSharedPreferenceValue("sellnum"));
			txt_name.setText(getSharedPreferenceValue("name"));
			Picasso.with(mContext).load(HttpUtils.img_url+getSharedPreferenceValue("imgpath")).placeholder(R.drawable.main_icon_headportrait).error(R.drawable.main_icon_headportrait).into(img);
		}
    }
    
    /*
	 * ��ť����¼�
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
			// TODO �˳�
			ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
	        builder.setTitle("��ܰ����!");
	        builder.setMessage("��ȷ��Ҫ�˳����˺���");
	        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //������Ĳ�������  
	                putSharedPreferenceValue("isLoged", "0");
	    			txt_total.setText("������:"+0.0);
	        		txt_spend.setText("����ȯ:"+0.0);
	        		txt_count.setText("����ȯ:"+0.0);
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
				Toast.makeText(mContext, "�ѵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.activity_main_recharge:
			// TODO ��ֵ
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.activity_main_psd:
			// ����
			if(isLoged()){
				Intent psdIntent = new Intent();
				intentJump(psdIntent,MyPsdActivity.class,DataConstants.MAINACTIVITY_PSD);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * GridView ����¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		switch(position){
		case 0: // 0 ��ά��
			if(isLoged()){
				Intent intent = new Intent();
				intent.setClass(mContext, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 1:  // 1 ������֤����
			if(isLoged()){
				final CustomDialog.Builder builder = new CustomDialog.Builder(mContext);  
		        builder.setTitle("�����붩����֤��"); 
		        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		                //������Ĳ�������  
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
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
	        break;
		case 2: // 2 �ҵĹ�ע
			if(isLoged()){
				Intent attentionIntent = new Intent();
				intentJump(attentionIntent,AttentionActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 3: // 3 ԤԼ�û�
			if(isLoged()){
				Intent reserationUsersIntent = new Intent();
				intentJump(reserationUsersIntent,ReserationCommitActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 4: // 4 ԤԼȷ��
			if(isLoged()){
				Intent reserationCommitIntent = new Intent();
				intentJump(reserationCommitIntent,ReserationActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 5: // 5 �ҵ�����
			if(isLoged()){
				Intent inviteIntent = new Intent();
				intentJump(inviteIntent,InviteActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 6: // 6 ��ȯ��ֵ
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 7: // 7 ��ȯ����
			if(isLoged()){
				Intent cartDataIntent = new Intent();
				intentJump(cartDataIntent,CartDataActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
			}
			break;
		case 8: // 8 ��ȯ����
			if(isLoged()){
				Intent cartCheckIntent = new Intent();
				intentJump(cartCheckIntent, CartCheckActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_LONG).show();
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
				
				if(errdesc.equals("ȷ�϶����ɹ�")){
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
