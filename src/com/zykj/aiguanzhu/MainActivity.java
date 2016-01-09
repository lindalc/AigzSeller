package com.zykj.aiguanzhu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.example.qr_codescan.MipcaActivityCapture;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.zykj.aiguanzhu.adapters.MainGridviewAdapter;
import com.zykj.aiguanzhu.custome.CustomDialog;
import com.zykj.aiguanzhu.custome.CustomImageView;
import com.zykj.aiguanzhu.custome.QuitDialog;
import com.zykj.aiguanzhu.custome.UIDialog;
import com.zykj.aiguanzhu.eneity.Dingdan;
import com.zykj.aiguanzhu.eneity.SellerInfo;
import com.zykj.aiguanzhu.net.HttpErrorHandler;
import com.zykj.aiguanzhu.net.UrlContants;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.parser.DataParser;
import com.zykj.aiguanzhu.utils.HttpUtils;
import com.zykj.aiguanzhu.utils.JsonUtils;
import com.zykj.aiguanzhu.utils.ToolsUtil;
import com.zykj.aiguanzhu.utils.isTopURL;

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
	private CustomImageView img;
	private TextView txt_name,txt_total,txt_spend,txt_count;
	
	/**
	 * 充值和积分按钮
	 */
	private LinearLayout rechargeLayout,psdLayout;
	
	private String timeString;//上传头像的字段
		
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
//    	head_img = (LinearLayout) findViewById(R.id.main_headportrait);
//    	head_img.setOnClickListener(this);
    	rechargeLayout.setOnClickListener(this);
    	psdLayout.setOnClickListener(this);
    	
    	img = (CustomImageView) findViewById(R.id.main_img_headportrait);
    	img.setOnClickListener(this);
    	txt_name = (TextView) findViewById(R.id.main_text_name);
    	txt_name.setOnClickListener(this);
    	txt_total = (TextView) findViewById(R.id.main_txt_total_income);
    	txt_spend = (TextView) findViewById(R.id.main_txt_coupon);
    	txt_count = (TextView) findViewById(R.id.main_txt_couponcount);
    	
    	
    }
    
    public void initGrid(){
    	
    	if (isLoged()) {
    		setTitleContent(0, R.string.quit,R.string.seller,1);
    		mRightTextBtn.setVisibility(View.VISIBLE);
    		mRightTextBtn.setOnClickListener(this);
    	}else{
    		setTitleContent(0, 0,R.string.seller);
    	}
    	
    	
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
		case R.id.right_text_btn:
			// TODO 退出
			QuitDialog.Builder builder = new QuitDialog.Builder(mContext);  
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
	    			
	    			mRightTextBtn.setVisibility(View.GONE);
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
		case R.id.main_img_headportrait:
			if(!isLoged()){
				Intent loginIntent = new Intent();
				loginIntent.setClass(mContext, LoginActivity.class);
				startActivityForResult(loginIntent, DataConstants.MAINACTIVITY_LOGIN);
			}else{
				UIDialog.ForThreeBtn(this, new String[]{"相册", "拍照", "取消"}, this);
			}
			break;
		case R.id.main_text_name:
			if(!isLoged()){
				Intent loginIntent = new Intent();
				loginIntent.setClass(mContext, LoginActivity.class);
				startActivityForResult(loginIntent, DataConstants.MAINACTIVITY_LOGIN);
			}else{
				Toast.makeText(mContext, "查看个人资料", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.dialog_modif_1:
			/*选择相册*/
			UIDialog.closeDialog();
			/**
			 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
			 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
			 */
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			/**
			 * 下面这句话，与其它方式写是一样的效果，如果：
			 * intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI);
			 * intent.setType(""image/*");设置数据类型
			 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
			 * ："image/jpeg 、 image/png等的类型"
			 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
			 */
			photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_2:
			/*点击拍照*/
			UIDialog.closeDialog();
			/**
			 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
			 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
			 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
			 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent shootIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			shootIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
							+ "/DCIM/Camera", timeString + ".jpg")));
			startActivityForResult(shootIntent, 2);
			// 下面这句指定调用相机拍照后的照片存储的路径
			/*
			 * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
			 * .fromFile(new File(Environment
			 * .getExternalStorageDirectory()+"/DCIM",
			 * "testpic.jpg"))); startActivityForResult(intent, 2);
			 */
			break;
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		case R.id.activity_main_recharge:
			// TODO 充值
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.activity_main_psd:
			// 积分
			if(isLoged()){
				Intent psdIntent = new Intent();
				intentJump(psdIntent,MyIntegralActivity.class,DataConstants.MAINACTIVITY_PSD);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
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
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
	        break;
		case 2: // 2 我的关注
			if(isLoged()){
				Intent attentionIntent = new Intent();
				intentJump(attentionIntent,AttentionActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 3: // 3 预约用户
			if(isLoged()){
				Intent reserationCommitIntent = new Intent();
				intentJump(reserationCommitIntent,ReserationActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 4: // 4 预约确定
			if(isLoged()){
				Intent reserationUsersIntent = new Intent();
				intentJump(reserationUsersIntent,ReserationCommitActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 5: // 5 我的邀请
			if(isLoged()){
				Intent inviteIntent = new Intent();
				intentJump(inviteIntent,InviteActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 6: // 6 卡券充值
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 7: // 7 卡券数据
			if(isLoged()){
				Intent cartDataIntent = new Intent();
				intentJump(cartDataIntent,CartDataActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
			}
			break;
		case 8: // 8 卡券核销
			if(isLoged()){
				Intent cartCheckIntent = new Intent();
				intentJump(cartCheckIntent, CartCheckActivity.class,position);
			}else{
				Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
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
				Dingdan dingdan = (Dingdan) msg.obj;
				
				ToolsUtil.print("----", "123123");
				Intent dingdanIntent = new Intent();
				dingdanIntent.putExtra("id", dingdan.getId());
				dingdanIntent.putExtra("i", 0);
				intentJump(dingdanIntent, DingDanQueRenActivity.class, -2);
				
				break;
			case DataConstants.MAINACTIVITY_CODE_FAULT:
				String errdesc = (String) msg.obj;
				Toast.makeText(mContext, errdesc, Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(mContext, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
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
	public String ErweimaUrl = null;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			/*如果是直接从相册获取*/
			try {
				Uri uri = data.getData();
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_SHORT).show();
			}
			break;
		case 2:
			/*如果是调用相机拍照时
			File temp = new File(Environment.getExternalStorageDirectory() + "/xiaoma.jpg");
			给图片设置名字和路径*/
			File temp = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			Uri uri = Uri.fromFile(temp);
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case 3:
			/**
			 * 取得裁剪后的图片
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String result = bundle.getString("result");
				if (isTopURL.isURL(result)) {
					ErweimaUrl = bundle.getString("result");
					
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(ErweimaUrl);
					intent.setData(content_url);
					startActivity(intent);
						
				} else {
					ErweimaUrl = bundle.getString("result");
					Intent intent = new Intent();
					intent.setClass(mContext, B4_3_KaQuanInfoActivity.class);
					startActivity(intent);
				}
				// Toast.makeText(this, "result="+bundle.getString("result"),
				// Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();

			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/**
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//Drawable drawable = new BitmapDrawable(photo);
			/*下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似*/
			savaBitmap(photo);
			// avatar_head_image.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 将剪切后的图片保存到本地图片上！
	 */
	public void savaBitmap(Bitmap bitmap) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
		String cutnameString = dateFormat.format(date);
		String filename = Environment.getExternalStorageDirectory().getPath() + "/" + cutnameString + ".jpg";
		File f = new File(filename);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		img.setImageBitmap(bitmap);
		uploadAvatar(f);
	}

	/**
	 * 更新服务器头像
	 */
	private void uploadAvatar(File file) {
		try {
			RequestParams params = new RequestParams();
			params.put("merchantid", getSharedPreferenceValue("merchantid"));
			params.put("files", file);
			
			ToolsUtil.print("----", "++++++++++++++++++++++++++ 成功");
			HttpUtils.postUserAvatar(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Toast.makeText(mContext, "头像上传成功", Toast.LENGTH_SHORT).show();
					ToolsUtil.print("----", "json = " + json);
					String imgurl = json.getJSONObject(UrlContants.jsonData).getString("avatar");
					SellerInfo seller = new SellerInfo();
					seller.setImgpath(UrlContants.IMAGE_URL+imgurl);
					setResult(RESULT_OK);
					finish();
				}
			}, params);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
