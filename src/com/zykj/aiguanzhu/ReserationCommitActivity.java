package com.zykj.aiguanzhu;

import java.util.ArrayList;

import com.zykj.aiguanzhu.adapters.ReserationAdapter;
import com.zykj.aiguanzhu.custome.ReserationDeleteDialog;
import com.zykj.aiguanzhu.eneity.ReserationUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lc
 * @date ����ʱ�䣺2015-12-25 ����4:24:26
 * @version 1.0 
 * @Description ԤԼȷ��
 */
public class ReserationCommitActivity extends BaseActivity {

	private Context mContext = ReserationCommitActivity.this;
	private int curPosition;
	private int rstate;
	
	/**
	 * ������
	 */
	private RelativeLayout rLayout;
	
	/**
	 *  ԤԼ�û���list����
	 */
	private ReserationAdapter adapterReseration;
	private ArrayList<ReserationUser> listReseration;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reseration_commit);
		rLayout = (RelativeLayout) findViewById(R.id.title_layout);
		rLayout.setBackgroundResource(R.drawable.title_orange);
		
		initReserationCommitData();
	}

	/**
	 *  ԤԼȷ��
	 */
	public void initReserationCommitData(){
		setTitleContent(R.drawable.title_orange_back, R.string.reserationusers);
		mLeftBtn.setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.activity_concemuser_listview);
		listView.setVisibility(View.VISIBLE);
		listReseration = new ArrayList<ReserationUser>();
//���滻������Start
		ReserationUser aUser = new ReserationUser(null,"�û�1",null,"��ɽ��̬���","2015-11-20",1,3);
		ReserationUser bUser = new ReserationUser(null,"�û�2",null,"��ɽ��̬���","2015-11-20",1,0);
		listReseration.add(aUser);
		listReseration.add(bUser);
		
//End
		adapterReseration = new ReserationAdapter(mContext,listReseration);
		listView.setAdapter(adapterReseration);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPosition = arg2;
				rstate = listReseration.get(curPosition).getRstate();
				if(rstate == 0){  // TODO �������ݺ�,��0��3��Ϊ����������rstate Ҳ����״̬��,����ǵȴ�ԤԼ,����תҳ��,����͵����Ƿ�����    ԤԼ״̬rstate(0-ԤԼ����,1-ԤԼͬ�� 2-ԤԼȡ�� 3-ԤԼ���)
					Intent intent = new Intent(mContext,ReserationDetailActivity.class);
				    intent.putExtra("reserationid", listReseration.get(curPosition).getReseratid());
					startActivity(intent);
				}else if(rstate ==3){
					ReserationDeleteDialog.Builder builder = new ReserationDeleteDialog.Builder(mContext);  
			        builder.setTitle("��ܰ����!");
			        builder.setMessage("�Ƿ�ȷ��ɾ������ԤԼ��Ϣ");
			        builder.setPositiveButton("", new DialogInterface.OnClickListener() {  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                //������Ĳ�������  
			                listReseration.remove(curPosition);
			                adapterReseration.notifyDataSetChanged();
			            }  
			        });  
			  
			        builder.setNegativeButton("",  
			                new android.content.DialogInterface.OnClickListener() {  
			                    public void onClick(DialogInterface dialog, int which) {  
			                        dialog.dismiss();
			                    }  
			                });  
			  
			        builder.create().show();  
				}
			}
		});
	}
	
	 /*
		 * ��ť����¼�
		 * (non-Javadoc)
		 * @see com.zykj.aiguanzhu.BaseActivity#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			super.onClick(v);
			switch (v.getId()) {
			case R.id.left_btn:
				this.finish();
				break;
			default:
				break;
			}
		}

}
