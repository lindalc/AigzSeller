package com.zykj.aiguanzhu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.toolbox.Volley;
import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.CustomProgressDialog;
import com.zykj.aiguanzhu.utils.LoadingProgressDialog;
import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author lc
 * @date 创建时间：2015-12-23 上午9:50:14
 * @version 1.0 
 * @Description  基础Activity 
 */
public class BaseActivity extends Activity implements OnClickListener {

	private Context mContext = BaseActivity.this;
	private SharedPreferences defalut_sp;;
	private SharedPreferences appoint_sp;
	
	private Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		defalut_sp= getSharedPreferences(DataConstants.config, Context.MODE_PRIVATE);
	}
	
    /**
     * 无纸化使用 (自定义超时时间)
     *
     * @param text
     * @param time
     * @return
     */
    public boolean showProgress(String text, long time) {
        if (LoadingProgressDialog.getDialog() != null && LoadingProgressDialog.isShowing())
            return false;
        LoadingProgressDialog.show(this, text, true, true);
        timer = new Timer();
        timer.schedule(new doWork(), time);
        return true;
    }

    public void dismissProgress() {
        if (LoadingProgressDialog.getDialog() != null)
            LoadingProgressDialog.dismiss();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }


    public class doWork extends TimerTask {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(getApplicationContext(), "网络超时", Toast.LENGTH_SHORT).show();
            }
        };

        @Override
        public void run() {
            if (LoadingProgressDialog.getDialog() != null) {
                LoadingProgressDialog.dismiss();
            }

            if (timer != null) {
                handler.sendEmptyMessage(1);
            }
            timer = null;
        }
    }
    
    /**+++ for title bar +++*/
	protected ImageView mLeftIcon, mRightBtn,mSearchBtn,mAddBtn,mMoreBtn;
	//protected LinearLayout mRightBtn;
	protected TextView titileTextView,mFristTitlte,mTrowTitle,mRightTextBtn;
    protected LinearLayout mLeftBtn,mCenterLayout;
    /**
	 * 标题栏
	 */
    protected RelativeLayout rLayout;
    
    /**
     * 白色标题
     * @param left_src_id 左边图标
     * @param right_src_id 右边图标
     * @param title_id 居中标题
     */
    protected void setTitleContent(int left_src_id, int right_src_id, int title_id){
		mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
		mLeftIcon = (ImageView)findViewById(R.id.left_icon);
		mRightBtn = (ImageView)findViewById(R.id.right_btn);
		mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
		//mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
		titileTextView = (TextView)findViewById(R.id.title);

		if (left_src_id != 0) {
			mLeftIcon.setImageResource(left_src_id);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
			mRightBtn.setVisibility(View.VISIBLE);
		}

		if (title_id != 0) {
			titileTextView.setText(title_id);
		}
	}
    
    /**
     * 白色标题
     * @param left_src_id 左边图标
     * @param right_src_id 右边图标
     * @param title_id 居中标题
     */
    protected void setTitleContent(int left_src_id, int right_src_id, int title_id,int i){
		mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
		mLeftIcon = (ImageView)findViewById(R.id.left_icon);
		mRightBtn = (ImageView)findViewById(R.id.right_btn);
		mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
		//mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
		titileTextView = (TextView)findViewById(R.id.title);

		if (left_src_id != 0) {
			mLeftIcon.setImageResource(left_src_id);
		}

		if (right_src_id != 0) {
			mRightTextBtn.setText(right_src_id);
			mRightBtn.setVisibility(View.VISIBLE);
		}

		if (title_id != 0) {
			titileTextView.setText(title_id);
		}
	}
    
    protected void setTitleContent(int left_src_id, int right_src_id, int title_id,boolean right){
		mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
		mLeftIcon = (ImageView)findViewById(R.id.left_icon);
		mRightBtn = (ImageView)findViewById(R.id.right_btn);
		mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
		//mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
		titileTextView = (TextView)findViewById(R.id.title);

		if (left_src_id != 0) {
			mLeftIcon.setImageResource(left_src_id);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
			mRightBtn.setVisibility(View.VISIBLE);
		}

		if (title_id != 0) {
			titileTextView.setText(title_id);
			titileTextView.setTextColor(Color.WHITE);
		}
	}
    
    /**
     * 橘色标题
     * @param left_src_id 左边图标
     * @param title_id 居中标题
     */
    protected void setTitleContent(int left_src_id,  int title_id){
		mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
		mLeftIcon = (ImageView)findViewById(R.id.left_icon);
		mRightBtn = (ImageView)findViewById(R.id.right_btn);
		mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
		//mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
		titileTextView = (TextView)findViewById(R.id.title);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);

		if (left_src_id != 0) {
			mLeftIcon.setImageResource(left_src_id);
		}

		if (title_id != 0) {
			titileTextView.setText(title_id);
			titileTextView.setTextColor(Color.WHITE);
		}
	}
    
    public void setListener(View... view) {
		for (int i = 0; i < view.length; i++) {
			view[i].setOnClickListener(this);
		}
	}
    
    public String getSharedPreferenceValue(String key) {
		String value = defalut_sp.getString(key, "");
		return value;
	}

	public void putSharedPreferenceValue(String key, String value) {
		SharedPreferences.Editor editor;
		editor = defalut_sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getSharedPreferenceValue(String tag, String key) {
		appoint_sp = getSharedPreferences(tag, Context.MODE_PRIVATE);
		String value = appoint_sp.getString(key, "");
		return value;
	}

	public void putSharedPreferenceValue(String tag, String key, String value) {
		appoint_sp = getSharedPreferences(tag, 0);
		SharedPreferences.Editor editor;
		editor = appoint_sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
    
    /*
	 * 按钮点击事件
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
			InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
		}
		                  
	}

}
