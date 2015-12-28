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
	
//////////////////////////////////商户版主页 START ///////////////////////////////
	/**
	 * 充值
	 */
	public static final int MAINACTIVITY_RECHARGE = 0x0001001;
	/**
	 * 积分
	 */
	public static final int MAINACTIVITY_PSD = 0x0001002;
//////////////////////////////////商户版主页 END ////////////////////////////////
//////////////////////////////////关注用户 START ///////////////////////////////
	/**
	 * 关注用户解析数据标识
	 */
	public static final int ACTION_SEND_ATTENTIONUSER = 0x0002001;
//////////////////////////////////关注用户 END ////////////////////////////////
	
}
