package com.zykj.aiguanzhu.custome;



import android.app.AlertDialog;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zykj.aiguanzhu.R;


/**
 * 涓?浜泆i涓璂ialog涓殑浣跨敤
 * 
 * @author bin
 * 
 */
public class UIDialog {
	public static AlertDialog dialog;

	/** 3鎸夐敭鎸夐挳dialog */
	public static void ForThreeBtn(Context context, String[] showtxt,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** 涓昏灏辨槸鍦ㄨ繖閲屽疄鐜拌繖绉嶆晥鏋滅殑.
		// 璁剧疆绐楀彛鐨勫唴瀹归〉闈?,shrew_exit_dialog.xml鏂囦欢涓畾涔塿iew鍐呭
		window.setContentView(R.layout.dialog_picture);

		Button m_btn_1 = (Button) window.findViewById(R.id.dialog_modif_1);
		Button m_btn_2 = (Button) window.findViewById(R.id.dialog_modif_2);
		Button m_btn_3 = (Button) window.findViewById(R.id.dialog_modif_3);

		m_btn_1.setText(showtxt[0]);
		m_btn_2.setText(showtxt[1]);
		m_btn_3.setText(showtxt[2]);

		m_btn_1.setOnClickListener(lisener);
		m_btn_2.setOnClickListener(lisener);
		m_btn_3.setOnClickListener(lisener);
	}
	
	/** notic提示dialog */
	public static void ForNotic(Context context, String text,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.dialog_notic);

		Button m_btn_1 = (Button) window.findViewById(R.id.dialog_notic_1);
		if (lisener == null) {
			lisener = new OnClickListener() {
				public void onClick(View v) {
					UIDialog.closeDialog();
				}
			};
		}
		m_btn_1.setOnClickListener(lisener);
		// 设置内容
		TextView m_notic_content = (TextView) window
				.findViewById(R.id.dialog_notic_text);
		m_notic_content.setText(text);
	}
	
	/**
	 * 鍏抽棴dialog
	 */
	public static void closeDialog() {
		if (dialog == null || !dialog.isShowing()) {
			return;
		}
		dialog.dismiss();

	}
}
