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
	/**
	 * �洢��sharePerfence
	 */
	public static final String config = "config";
	/**
	 * ��ǰ���Ƿ��Ѿ����й�ָ��
	 */
	public static final String IS_INTRO = "is_intro";
	/**
	 * ��ǰӦ���д洢�İ汾��
	 */
	public static final String VERSION = "version";
	/**
	 * mob������֤APP Key
	 * 
	 */
	public static final String APPKey_mob = "deadc288e612";
	/**
	 * mob������֤SECRE
	 */
	public static final String APP_SECRE = "4e3cb936a3af97ad37e776eb37820832";
	
//////////////////////////////////�̻�����ҳ START ///////////////////////////////
	/**
	 * ��½
	 */
	public static final int MAINACTIVITY_LOGIN = 0x0001001;
	/**
	 * ��ֵ
	 */
	public static final int MAINACTIVITY_RECHARGE = 0x0001002;
	/**
	 * ����
	 */
	public static final int MAINACTIVITY_PSD = 0x0001003;
	/**
	 * ��֤��
	 */
	public static final int MAINACTIVITY_CODE = 0x0001004;
	public static final int MAINACTIVITY_CODE_FAULT = 0x00010041;
	/**
	 * ��ע�û�
	 */
	public static final int MAINACTIVITY_ATTENTION = 0x0001005;
	/**
	 * �û�����
	 */
	public static final int MAINACTIVITY_ATTENTIONDEAIL = 0x0001006;
	/**
	 * �ҵ�����
	 */
	public static final int MAINACTIVITY_INVITE = 0x0001007;
	/**
	 * ԤԼ�û�
	 */
	public static final int MAINACTIVITY_RESERATIONUSER = 0x0001008;
	/**
	 * ԤԼ����
	 */
	public static final int MAINACTIVITY_RESERATIONDETAIL = 0x0001009;
	/**
	 * ��ȯ���� 
	 */
	public static final int MAINACTIVITY_CARTDATA = 0x0001012;
	/**
	 * ��ȯ���� 
	 */
	public static final int MAINACTIVITY_CARTCHECK = 0x0001011;
//////////////////////////////////�̻�����ҳ END ////////////////////////////////
//////////////////////////////////���� START ///////////////////////////////
	/**
	 * ���ֱ仯
	 */
	public static final int MAINACIVITY_INTEGRALLIST = 0x0003001;
	/**
	 * ���ֳ�ֵ
	 */
	public static final int MAINACTIVITY_INTEGRALRECHARGE = 0x0003002;
//////////////////////////////////���� END ////////////////////////////////
//////////////////////////////////��ע�û� START ///////////////////////////////
	/**
	 * ��ע�û��������ݱ�ʶ
	 */
	public static final int ACTION_SEND_ATTENTIONUSER = 0x0005001;
//////////////////////////////////��ע�û� END ////////////////////////////////
//////////////////////////////////ԤԼ START ///////////////////////////////
	/**
	 * ɾ��ԤԼ
	 */
	public static final int RESERATION_DELETE = 0x0008001;
	/**
	 * ȷ��ԤԼ
	 */
	public static final int RESERATION_COMMIT = 0x0008002;
	/**
	 * ȡ��ԤԼ
	 */
	public static final int RESERATION_CANCEL = 0x0008003;
	
	
	/**
	 * ԤԼ����
	 */
	public static final int RESERATION_DETAIL = 0x0009001;
//////////////////////////////////ԤԼ END ///////////////////////////////
	
//////////////////////////////////���� START ///////////////////////////////
	/**
	 * ȷ�϶���
	 */
	public static final int DINGDAN_CONFIRM = 0x0012001;
//////////////////////////////////���� END ///////////////////////////////
}
