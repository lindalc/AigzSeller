package com.zykj.aiguanzhu.parser;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-23 ����10:31:47 
 * @version 1.0 
 * @Description ������<br/>
 * 							  �༭����ʱ�����ע��
 */
public class DataConstants {
////////////////////////////////// Demo START ///////////////////////////////
    /**
     * 0x000������ 1������ģ�� 001�������ģ���ڵ�����Ҫ�ĳ�������
     */
//    public static final int PARSER_MAIN_WELCOME = 0x0001001;
//////////////////////////////////Demo END ////////////////////////////////
	
	/**
	 * ���ٵ�ǰҳ��֪ͨ
	 */
	public static final String ACTION_DESTROY_CURRENT_ACTIVITY = "com.zykj.aiguanzhu.intent.action.destroy.current.activity";
	
	public static final String ERROR = "{\"code\":400,\"message\":\"����ʧ��\",\"datas\":null}";
	public static final String jsonData = "datas";
	
//////////////////////////////////�̻�����ҳ START ///////////////////////////////
	/**
	 * ��ֵ
	 */
	public static final int MAINACTIVITY_RECHARGE = 0x0001001;
	/**
	 * ����
	 */
	public static final int MAINACTIVITY_PSD = 0x0001002;
//////////////////////////////////�̻�����ҳ END ////////////////////////////////
//////////////////////////////////��ע�û� START ///////////////////////////////
	/**
	 * ��ע�û��������ݱ�ʶ
	 */
	public static final int ACTION_SEND_ATTENTIONUSER = 0x0002001;
//////////////////////////////////��ע�û� END ////////////////////////////////
	
}
