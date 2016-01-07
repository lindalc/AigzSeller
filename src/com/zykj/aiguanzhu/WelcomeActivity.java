package com.zykj.aiguanzhu;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.zykj.aiguanzhu.parser.DataConstants;
import com.zykj.aiguanzhu.utils.ToolsUtil;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				// Intent intent = new Intent(Welcome.this, MainActivity.class);
				// startActivity(intent);

				String is_intro = getSharedPreferenceValue(DataConstants.IS_INTRO);
				boolean should_intro = false;
				int version = ToolsUtil.getAppVersion(WelcomeActivity.this);
				String save_version = getSharedPreferenceValue(DataConstants.VERSION);
				int save_version_int = save_version.equals("") ? -1 : Integer
						.parseInt(save_version);

				if (is_intro.length() > 0 && version == save_version_int) {// 已经进行过指引,且版本号符合
					should_intro = false;
				} else {
					should_intro = true;
				}

				if (should_intro) {
					Intent intent = new Intent(WelcomeActivity.this,IntroActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
				}
				finish();

			}
		};
		timer.schedule(task, 2000);
	}

}
