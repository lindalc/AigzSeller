package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

public class ForgetPwdActivity extends BaseActivity {

	private Context mContext = ForgetPwdActivity.this;
	
	private ImageView im_b412_back_btn;// ��������
	private TextView tv_getcode;//��ȡ��֤��
	private Button btn_confirm;//��ȡ��֤��
	private EditText et_phoneNumber;//�ֻ���
	private EditText et_confirmCode;//��֤��
	private EditText et_newPw;//����
	private EditText et_newPwAgain;//�ٴ���������
	
	private RequestQueue mRequestQueue; 
	
	public String mobile = null;
	public String password = null;
	public String verify_code = null;
	public String passwordAgain = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		
		initView();
//	 	������֤
		initSmsSDK();
		mRequestQueue = Volley.newRequestQueue(this);
	}

	private void initSmsSDK() {
		// TODO Auto-generated method stub
				// ��ʼ������SDK
						SMSSDK.initSDK(this, DataConstants.APPKey_mob, DataConstants.APP_SECRE);
						EventHandler eventHandler = new EventHandler() {
							public void afterEvent(int event, int result, Object data) {
									Message msg = new Message();
									msg.arg1 = event;
									msg.arg2 = result;
									msg.obj = data;
									handler.sendMessage(msg);
							};
						};
						// ע��ص������ӿ�
						SMSSDK.registerEventHandler(eventHandler);
		
	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//�ύ��֤��ɹ�
//					Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�+������������������������ֻ���+����+��֤�봫��������", Toast.LENGTH_SHORT).show();
					Map<String, String> map = new HashMap<String, String>();
					map.put("mobile", mobile);
					map.put("loginpwd", password);
					String json = JsonUtils.toJson(map);
					RequestDailog.showDialog(mContext, "����ע�ᣬ���Ժ�");
					JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_resetpsw(json),null,new Response.Listener<JSONObject>() {  
			            @Override  
			            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
			            	Log.e("response=", response+"");
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) //�ɹ�
								{
									Toast.makeText(mContext, "�޸�����ɹ��������µ�½", Toast.LENGTH_SHORT).show();
									finish();
								}else {//ʧ��,��ʾʧ����Ϣ
									String errdesc = status.getString("errdesc");
									Toast.makeText(mContext, errdesc, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }  
			        },new Response.ErrorListener() {  
			            @Override  
			            public void onErrorResponse(VolleyError error) {  
			            		RequestDailog.closeDialog();
			                ToolsUtil.print("----","ErrorResponse="+error.getMessage());
			                Toast.makeText(mContext, "��������ʧ�ܣ�������", Toast.LENGTH_SHORT).show();
			            }  
			        });  
			        mRequestQueue.add(jr); 
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					RequestDailog.closeDialog();
					Toast.makeText(getApplicationContext(), "��֤���Ѿ����ͣ����Ժ�", Toast.LENGTH_SHORT).show();
//					textView2.setText("��֤���Ѿ�����");
				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//����֧�ַ�����֤��Ĺ����б�
//					Toast.makeText(getApplicationContext(), "��ȡ�����б�ɹ�", Toast.LENGTH_SHORT).show();
//					countryTextView.setText(data.toString());
				}
			} else {
				((Throwable) data).printStackTrace();
//				int resId = getStringRes(MainActivity.this, "smssdk_network_error");
				RequestDailog.closeDialog();
				Toast.makeText(getApplicationContext(), "��֤�����", Toast.LENGTH_SHORT).show();
//				if (resId > 0) {
//					Toast.makeText(RegistActivity.this, resId, Toast.LENGTH_SHORT).show();
//				}
			}
			
		}
		
	};
	public void initView() {
		im_b412_back_btn = (ImageView) findViewById(R.id.im_b412_back_btn);
		tv_getcode = (TextView) findViewById(R.id.tv_getcode);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
		et_confirmCode = (EditText) findViewById(R.id.et_confirmCode);
		et_newPw = (EditText) findViewById(R.id.et_newPw);
		et_newPwAgain = (EditText) findViewById(R.id.et_newPwAgain);
		
		tv_getcode.bringToFront();
		setListener(im_b412_back_btn,tv_getcode,btn_confirm);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b412_back_btn:
			this.finish();
			break;
		case R.id.tv_getcode:// ��ȡ��֤��
			if(!TextUtils.isEmpty(et_phoneNumber.getText().toString())){
				mobile=et_phoneNumber.getText().toString().trim();
				RequestDailog.showDialog(this, "���ڷ�����֤�룬���Ժ�");
				SMSSDK.getVerificationCode("86",mobile);
			}else {
				Toast.makeText(this, "�绰����Ϊ��", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.btn_confirm:// ȷ��
			mobile = et_phoneNumber.getText().toString().trim();
			password = et_newPw.getText().toString().trim();
			passwordAgain = et_newPwAgain.getText().toString().trim();
			verify_code = et_confirmCode.getText().toString().trim();
			if (TextUtils.isEmpty(mobile)) {
				Toast.makeText(this, "�ֻ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
			}else if (TextUtils.isEmpty(verify_code)) {
				Toast.makeText(this, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(passwordAgain)) {
				Toast.makeText(this, "�ٴ��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
			}
			else if (!passwordAgain.equals(password)) {
				Toast.makeText(this, "�뱣����������������ͬ", Toast.LENGTH_SHORT).show();
			}else {
				SMSSDK.submitVerificationCode("86", mobile,verify_code);
			}
			break;
		// case R.id.im_denglu:
		// Intent itdenglu = new Intent();
		// itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
		// startActivity(itdenglu);
		// break;
		default:
			break;

		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
