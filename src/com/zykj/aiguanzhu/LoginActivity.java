package com.zykj.aiguanzhu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.aiguanzhu.eneity.SellerInfo;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.RequestDailog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * 
 * @author lc
 * @date 创建时间：2015-12-28 上午9:44:43
 * @version 1.0  
 * @Description 登陆
 */
public class LoginActivity extends BaseActivity {

	private TextView tv_b41_zhuce;//注册
	private TextView tv_forgetPassWord;//忘记密码
	private ImageView im_b541_back_btn;//返回
	private Button btn_login;
	private EditText et_login_name;//用户名／手机号
	private EditText et_passWord;//密码
	private RequestQueue mRequestQueue; 
	private Context mContext = LoginActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this);  
	}

	public void initView() {
		tv_b41_zhuce = (TextView) findViewById(R.id.tv_b41_zhuce);
		tv_forgetPassWord = (TextView) findViewById(R.id.tv_forgetPassWord);
		im_b541_back_btn = (ImageView) findViewById(R.id.im_b541_back_btn);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_passWord = (EditText) findViewById(R.id.et_passWord);
		setListener(tv_b41_zhuce,tv_forgetPassWord,im_b541_back_btn,btn_login);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_b41_zhuce://注册
			Intent itzhuce = new Intent();
			itzhuce.setClass(mContext, RegisterActivity.class);
			startActivity(itzhuce);
			break;
		case R.id.tv_forgetPassWord://忘记密码
			Intent itforgot = new Intent();
			itforgot.setClass(mContext, ForgetPwdActivity.class);
			startActivity(itforgot);
			break;
		case R.id.btn_login:
			
			String username = et_login_name.getText().toString().trim();
			String password = et_passWord.getText().toString().trim();
			if (TextUtils.isEmpty(username)) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			}else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			}else {
				RequestDailog.showDialog(this, "正在登陆请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("password", password);
				map.put("username", username);
//				map.put("mobile", username);
				String json = JsonUtils.toJson(map);
//				Tools.Log(json);
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_login(json),null,new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) //成功
								{
									JSONObject data = response.getJSONObject("data");
									String name = data.getString("name");
									String mobile = data.getString("telephone");
									String img = data.getString("imgpath");
									int merchantid = data.getInt("merchantid");
									Double totalIncome = data.getDouble("totalIncome");
									int sellnum = data.getInt("sellnum");
									int rebaterate = data.getInt("rebaterate");
									int categoryid = data.getInt("categoryid");
									SellerInfo seller = new SellerInfo();
									ToolsUtil.print("----", "merchantid = "+merchantid);
									putSharedPreferenceValue("merchantid", merchantid+"");
									putSharedPreferenceValue("name", name);
									putSharedPreferenceValue("isLoged", "1");
									putSharedPreferenceValue("imgpath", img);
									putSharedPreferenceValue("sellnum", sellnum+"");
									putSharedPreferenceValue("rebaterate", rebaterate+"");
									putSharedPreferenceValue("totalIncome", totalIncome+"");
									putSharedPreferenceValue("categoryid", categoryid+"");
									Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
									Intent itwode = new Intent(mContext,MainActivity.class);
									itwode.putExtra("imgpath", img);
									itwode.putExtra("totalIncome", totalIncome);
									itwode.putExtra("name", name);
									itwode.putExtra("sellnum", sellnum);
									itwode.putExtra("rebaterate", rebaterate);
									setResult(1, itwode);
									startActivity(itwode);
									
								}else {//失败,提示失败信息
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
		                Toast.makeText(mContext, "网络连接失败，请重试", Toast.LENGTH_SHORT).show();
		            }  
		        });  
		        mRequestQueue.add(jr);  
			}
			break;
		case R.id.im_b541_back_btn:
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
	    mRequestQueue.cancelAll(this);  
	}  


}
