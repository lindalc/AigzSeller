package com.zykj.aiguanzhu.parser;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午10:31:47 
 * @version 1.0 
 * @Description 常量类<br/>
 * 							  编辑此类时请添加注释
 */
public class DataConstants {
////////////////////////////////// Demo START ///////////////////////////////
    /**
     * 0x000是总体 1代表大的模块 001代表这个模块内的所需要的常量排序
     */
//    public static final int PARSER_MAIN_WELCOME = 0x0001001;
//////////////////////////////////Demo END ////////////////////////////////
	
	/**
	 * 销毁当前页面通知
	 */
	public static final String ACTION_DESTROY_CURRENT_ACTIVITY = "com.zykj.aiguanzhu.intent.action.destroy.current.activity";
	
	public static final String ERROR = "{\"code\":400,\"message\":\"请求失败\",\"datas\":null}";
	public static final String jsonData = "datas";
	/**
	 * 存储的sharePerfence
	 */
	public static final String config = "config";
	/**
	 * 当前的是否已经进行过指引
	 */
	public static final String IS_INTRO = "is_intro";
	/**
	 * 当前应用中存储的版本号
	 */
	public static final String VERSION = "version";
	/**
	 * mob短信验证APP Key
	 * 
	 */
	public static final String APPKey_mob = "deadc288e612";
	/**
	 * mob短信验证SECRE
	 */
	public static final String APP_SECRE = "4e3cb936a3af97ad37e776eb37820832";
	
//////////////////////////////////商户版主页 START ///////////////////////////////
	/**
	 * 登陆
	 */
	public static final int MAINACTIVITY_LOGIN = 0x0001001;
	/**
	 * 充值
	 */
	public static final int MAINACTIVITY_RECHARGE = 0x0001002;
	/**
	 * 积分
	 */
	public static final int MAINACTIVITY_PSD = 0x0001003;
	/**
	 * 验证码
	 */
	public static final int MAINACTIVITY_CODE = 0x0001004;
	public static final int MAINACTIVITY_CODE_FAULT = 0x00010041;
	/**
	 * 关注用户
	 */
	public static final int MAINACTIVITY_ATTENTION = 0x0001005;
	/**
	 * 用户资料
	 */
	public static final int MAINACTIVITY_ATTENTIONDEAIL = 0x0001006;
	/**
	 * 我的邀请
	 */
	public static final int MAINACTIVITY_INVITE = 0x0001007;
	/**
	 * 预约用户
	 */
	public static final int MAINACTIVITY_RESERATIONUSER = 0x0001008;
	/**
	 * 预约详情
	 */
	public static final int MAINACTIVITY_RESERATIONDETAIL = 0x0001009;
	/**
	 * 卡券数据 
	 */
	public static final int MAINACTIVITY_CARTDATA = 0x0001012;
	/**
	 * 卡券核销 
	 */
	public static final int MAINACTIVITY_CARTCHECK = 0x0001011;
//////////////////////////////////商户版主页 END ////////////////////////////////
//////////////////////////////////积分 START ///////////////////////////////
	/**
	 * 积分变化
	 */
	public static final int MAINACIVITY_INTEGRALLIST = 0x0003001;
	/**
	 * 积分充值
	 */
	public static final int MAINACTIVITY_INTEGRALRECHARGE = 0x0003002;
//////////////////////////////////积分 END ////////////////////////////////
//////////////////////////////////关注用户 START ///////////////////////////////
	/**
	 * 关注用户解析数据标识
	 */
	public static final int ACTION_SEND_ATTENTIONUSER = 0x0005001;
//////////////////////////////////关注用户 END ////////////////////////////////
//////////////////////////////////预约 START ///////////////////////////////
	/**
	 * 删除预约
	 */
	public static final int RESERATION_DELETE = 0x0008001;
	/**
	 * 确认预约
	 */
	public static final int RESERATION_COMMIT = 0x0008002;
	/**
	 * 取消预约
	 */
	public static final int RESERATION_CANCEL = 0x0008003;
	
	
	/**
	 * 预约详情
	 */
	public static final int RESERATION_DETAIL = 0x0009001;
//////////////////////////////////预约 END ///////////////////////////////
	
//////////////////////////////////订单 START ///////////////////////////////
	/**
	 * 确认订单
	 */
	public static final int DINGDAN_CONFIRM = 0x0012001;
//////////////////////////////////订单 END ///////////////////////////////
}
