package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pingplusplus.android.PaymentActivity;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RechargeActivity extends BaseActivity {

	private Context mContext = RechargeActivity.this;
	String merchantid;
	/**
	 * EditText 积分和金额
	 */
	private EditText et_psd,et_count;
	/**
	 * 接收输入的积分和金额
	 */
	private String str_psd,str_count;
	
	private Button recharge_btn;
	
	private RadioGroup radiogroup;
	private RadioButton btn_alipay,btn_wechat;
	
	private String charge;
	private static RequestQueue mRequestQueue; 
	private TextWatcher tw1, tw2;
	private int v1 = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		merchantid = getSharedPreferenceValue("merchantid");
		initRechargeData();
	}
	
	public void initRechargeData(){
		setTitleContent(R.drawable.title_back, 0, R.string.recharge);
		mLeftBtn.setOnClickListener(this);
		
		et_psd = (EditText) findViewById(R.id.activity_recharge_psd);
		et_count = (EditText) findViewById(R.id.activity_recharge_count);

		recharge_btn = (Button) findViewById(R.id.recharge_btn);
		recharge_btn.setOnClickListener(this);
		
		radiogroup = (RadioGroup) findViewById(R.id.activity_recharge_radiogroup);
		btn_alipay = (RadioButton) findViewById(R.id.recharge_alipay);
		btn_wechat = (RadioButton) findViewById(R.id.recharge_wechat);
		
		tw1 = new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,int count) {
		    	et_count.removeTextChangedListener(tw2);
		        int et1val = 0;
		        try {
		            et1val =Integer.parseInt(s.toString());
		        } catch (Exception ex) {
		            et1val = 0;
		        }
		        int et2val = et1val * v1;
		        et_count.setText("" + et2val);
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		        }
		    @Override
		    public void afterTextChanged(Editable s) {
		    	et_count.addTextChangedListener(tw2);
		    }
	    };
	    
	    tw2 = new TextWatcher() {
	        @Override
	        public void onTextChanged(CharSequence s, int start, int before,int count) {
	        	et_psd.removeTextChangedListener(tw1);
	            int et2val = 0;
	            try {
	                et2val = Integer.parseInt(s.toString());
	            } catch (Exception ex) {
	                et2val = 0;
	            }
	            int et1val = et2val * v1;
	            et_psd.setText("" + et1val);
	        }
	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
	        }
	        @Override
	        public void afterTextChanged(Editable s) {
	        	et_psd.addTextChangedListener(tw1);
	        }
        };
     
        et_psd.addTextChangedListener(tw1);
        et_count.addTextChangedListener(tw2);
		
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
		case R.id.recharge_btn:
			if(btn_alipay.isChecked()){
				
				Toast.makeText(mContext, "alipay", Toast.LENGTH_SHORT).show();
			}else if(btn_wechat.isChecked()){
				
				str_psd = et_psd.getText().toString();
				str_count = et_count.getText().toString();
				
				if(str_count !=null || str_psd!=null){
					RequestDailog.showDialog(mContext, "正在跳转");
					Map<String, String> map = new HashMap<String, String>();
					map.put("merchantid", merchantid);
					map.put("channel", "wx");
					map.put("amount",str_count);
					String json = JsonUtils.toJson(map);
					mRequestQueue =  Volley.newRequestQueue(mContext);  
					   JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, HttpUtils.url_integralrecharge(json), null, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							
							charge = response.toString();
							ToolsUtil.print("----", "charge = " + charge);
							if(null==charge){
				        		showMsg("请求出错", "请检查URL", "URL无法获取charge");
				        		return;
				        	}
							RequestDailog.closeDialog();
							Intent intent = new Intent();
							String packageName = getPackageName();
							ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
							intent.setComponent(componentName);
							intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
							startActivityForResult(intent, 1);
						}
						   
					   }, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							RequestDailog.closeDialog();
				            ToolsUtil.print("----","ErrorResponse="+error.getMessage());
				            Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_SHORT).show();
						}
					});
					mRequestQueue.add(jr);  
					
				}else{
					Toast.makeText(mContext, "请输入充值积分或金额", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     //支付页面返回处理
	    if (requestCode == 1) {
	        if (resultCode == Activity.RESULT_OK) {
	            String result = data.getExtras().getString("pay_result");
	            /* 处理返回值
	             * "success" - payment succeed
	             * "fail"    - payment failed
	             * "cancel"  - user canceld
	             * "invalid" - payment plugin not installed
	             *
	             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
	             */
	            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
	            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
	            showMsg(result, errorMsg, extraMsg);
	        }
	    }
	}
	
	public void showMsg(String title, String msg1, String msg2) {
    	String str = title;
    	if (null !=msg1 && msg1.length() != 0) {
    		str += "\n" + msg1;
    	}
    	if (null !=msg2 && msg2.length() != 0) {
    		str += "\n" + msg2;
    	}
    	AlertDialog.Builder builder = new Builder(mContext);
    	builder.setMessage(str);
    	builder.setTitle("提示");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
