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
 * �?些ui中Dialog中的使用
 * 
 * @author bin
 * 
 */
public class UIDialog {
	public static AlertDialog dialog;

	/** 3按键按钮dialog */
	public static void ForThreeBtn(Context context, String[] showtxt,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页�?,shrew_exit_dialog.xml文件中定义view内容
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
	
	/** notic��ʾdialog */
	public static void ForNotic(Context context, String text,
			OnClickListener lisener) {
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		// *** ��Ҫ����������ʵ������Ч����.
		// ���ô��ڵ�����ҳ��,shrew_exit_dialog.xml�ļ��ж���view����
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
		// ��������
		TextView m_notic_content = (TextView) window
				.findViewById(R.id.dialog_notic_text);
		m_notic_content.setText(text);
	}
	
	/**
	 * 关闭dialog
	 */
	public static void closeDialog() {
		if (dialog == null || !dialog.isShowing()) {
			return;
		}
		dialog.dismiss();

	}
}
