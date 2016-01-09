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
	private CustomImageView img;
	private TextView txt_name,txt_total,txt_spend,txt_count;
	
	/**
	 * ��ֵ�ͻ��ְ�ť
	 */
	private LinearLayout rechargeLayout,psdLayout;
	
	private String timeString;//�ϴ�ͷ����ֶ�
		
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
		case R.id.right_text_btn:
			// TODO �˳�
			QuitDialog.Builder builder = new QuitDialog.Builder(mContext);  
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
				UIDialog.ForThreeBtn(this, new String[]{"���", "����", "ȡ��"}, this);
			}
			break;
		case R.id.main_text_name:
			if(!isLoged()){
				Intent loginIntent = new Intent();
				loginIntent.setClass(mContext, LoginActivity.class);
				startActivityForResult(loginIntent, DataConstants.MAINACTIVITY_LOGIN);
			}else{
				Toast.makeText(mContext, "�鿴��������", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.dialog_modif_1:
			/*ѡ�����*/
			UIDialog.closeDialog();
			/**
			 * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬
			 * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ���
			 */
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			/**
			 * ������仰����������ʽд��һ����Ч���������
			 * intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI);
			 * intent.setType(""image/*");������������
			 * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д��
			 * ��"image/jpeg �� image/png�ȵ�����"
			 * ����ط�С���и����ʣ�ϣ�����ֽ���£������������URI������ΪʲôҪ��������ʽ��дѽ����ʲô����
			 */
			photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_2:
			/*�������*/
			UIDialog.closeDialog();
			/**
			 * ������仹�������ӣ����ÿ������չ��ܣ�����Ϊʲô�п������գ���ҿ��Բο����¹ٷ�
			 * �ĵ���you_sdk_path/docs/guide/topics/media/camera.html
			 * �Ҹտ���ʱ����Ϊ̫�������濴����ʵ�Ǵ�ģ�����������õ�̫���ˣ����Դ�Ҳ�Ҫ��Ϊ
			 * �ٷ��ĵ�̫���˾Ͳ����ˣ���ʵ�Ǵ�ģ�����ط�С��Ҳ���ˣ��������
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent shootIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			shootIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
							+ "/DCIM/Camera", timeString + ".jpg")));
			startActivityForResult(shootIntent, 2);
			// �������ָ������������պ����Ƭ�洢��·��
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
			// TODO ��ֵ
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.activity_main_psd:
			// ����
			if(isLoged()){
				Intent psdIntent = new Intent();
				intentJump(psdIntent,MyIntegralActivity.class,DataConstants.MAINACTIVITY_PSD);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
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
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
	        break;
		case 2: // 2 �ҵĹ�ע
			if(isLoged()){
				Intent attentionIntent = new Intent();
				intentJump(attentionIntent,AttentionActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 3: // 3 ԤԼ�û�
			if(isLoged()){
				Intent reserationCommitIntent = new Intent();
				intentJump(reserationCommitIntent,ReserationActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 4: // 4 ԤԼȷ��
			if(isLoged()){
				Intent reserationUsersIntent = new Intent();
				intentJump(reserationUsersIntent,ReserationCommitActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 5: // 5 �ҵ�����
			if(isLoged()){
				Intent inviteIntent = new Intent();
				intentJump(inviteIntent,InviteActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 6: // 6 ��ȯ��ֵ
			if(isLoged()){
				Intent rechargeIntent = new Intent();
				intentJump(rechargeIntent,RechargeActivity.class,DataConstants.MAINACTIVITY_RECHARGE);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 7: // 7 ��ȯ����
			if(isLoged()){
				Intent cartDataIntent = new Intent();
				intentJump(cartDataIntent,CartDataActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			}
			break;
		case 8: // 8 ��ȯ����
			if(isLoged()){
				Intent cartCheckIntent = new Intent();
				intentJump(cartCheckIntent, CartCheckActivity.class,position);
			}else{
				Toast.makeText(mContext, "���ȵ�¼", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(mContext, "��֤���������������", Toast.LENGTH_SHORT).show();
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
			/*�����ֱ�Ӵ�����ȡ*/
			try {
				Uri uri = data.getData();
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				Toast.makeText(this, "��û��ѡ���κ���Ƭ", Toast.LENGTH_SHORT).show();
			}
			break;
		case 2:
			/*����ǵ����������ʱ
			File temp = new File(Environment.getExternalStorageDirectory() + "/xiaoma.jpg");
			��ͼƬ�������ֺ�·��*/
			File temp = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			Uri uri = Uri.fromFile(temp);
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case 3:
			/**
			 * ȡ�òü����ͼƬ
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * ��ǰ����ʱ���ᱨNullException��С��ֻ ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ� �ط����жϴ����������
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
			// ����һ���ļ��ж��󣬸�ֵΪ�ⲿ�洢����Ŀ¼
			File sdcardDir = Environment.getExternalStorageDirectory();
			// �õ�һ��·����������sdcard���ļ���·��������
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				// �������ڣ�����Ŀ¼��������Ӧ��������ʱ�򴴽�
				path1.mkdirs();

			}
		}
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/**
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ�ģ�С����C C++
		 * ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô ��������...���
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//Drawable drawable = new BitmapDrawable(photo);
			/*����ע�͵ķ����ǽ��ü�֮���ͼƬ��Base64Coder���ַ���ʽ�� ������������QQͷ���ϴ����õķ������������*/
			savaBitmap(photo);
			// avatar_head_image.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * �����к��ͼƬ���浽����ͼƬ�ϣ�
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
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// ��Bitmap�����������
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
	 * ���·�����ͷ��
	 */
	private void uploadAvatar(File file) {
		try {
			RequestParams params = new RequestParams();
			params.put("merchantid", getSharedPreferenceValue("merchantid"));
			params.put("files", file);
			
			ToolsUtil.print("----", "++++++++++++++++++++++++++ �ɹ�");
			HttpUtils.postUserAvatar(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Toast.makeText(mContext, "ͷ���ϴ��ɹ�", Toast.LENGTH_SHORT).show();
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
