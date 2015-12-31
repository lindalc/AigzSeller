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
	
	/**
	 * EditText ���ֺͽ��
	 */
	private EditText et_psd,et_count;
	/**
	 * ��������Ļ��ֺͽ��
	 */
	private String str_psd,str_count;
	
	private Button recharge_btn;
	
	private RadioGroup radiogroup;
	private RadioButton btn_alipay,btn_wechat;
	
	private String charge;
	private static RequestQueue mRequestQueue; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		
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
		case R.id.recharge_btn:
			if(btn_alipay.isChecked()){
				
				Toast.makeText(mContext, "alipay", Toast.LENGTH_LONG).show();
			}else if(btn_wechat.isChecked()){
				
				str_psd = et_psd.getText().toString();
				str_count = et_count.getText().toString();
				
				if(str_count !=null || str_psd!=null){
					RequestDailog.showDialog(mContext, "������ת");
					Map<String, String> map = new HashMap<String, String>();
					map.put("merchantid", "1");
					map.put("channel", "wx");
					map.put("amount",str_count);
					String json = JsonUtils.toJson(map);
					mRequestQueue =  Volley.newRequestQueue(mContext);  
					   JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, HttpUtils.url_integralrecharge(json), null, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							
							charge = response.toString();
							if(null==charge){
				        		showMsg("�������", "����URL", "URL�޷���ȡcharge");
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
				            Toast.makeText(mContext, "��������ʧ�ܣ�������", Toast.LENGTH_LONG).show();
						}
					});
					mRequestQueue.add(jr);  
					
				}else{
					Toast.makeText(mContext, "�������ֵ���ֻ���", Toast.LENGTH_LONG).show();
				}
			}
			break;
		default:
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     //֧��ҳ�淵�ش���
	    if (requestCode == 1) {
	        if (resultCode == Activity.RESULT_OK) {
	            String result = data.getExtras().getString("pay_result");
	            /* ������ֵ
	             * "success" - payment succeed
	             * "fail"    - payment failed
	             * "cancel"  - user canceld
	             * "invalid" - payment plugin not installed
	             *
	             * ����������������� invalid������ UPPayAssistEx.installUPPayPlugin(this); ��װ������ȫ֧���ؼ���
	             */
	            String errorMsg = data.getExtras().getString("error_msg"); // ������Ϣ
	            String extraMsg = data.getExtras().getString("extra_msg"); // ������Ϣ
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
    	builder.setTitle("��ʾ");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
