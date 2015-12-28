package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-24 上午11:59:59 
 * @version 1.0 
 * @Description 预约用户实体类<br/>     预约状态rstate(0-预约申请,1-预约同意 2-预约取消 3-预约完成)
 {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"请求成功"
    },
    "data":[
        {
            "sign":"美丽世界的孤儿",
            "username":"lixiaoyao",
            "headportain":"20151207/20151207141310290.png",
            "goodname":"蒙山生态火锅",
            "datetime":"2015-11-20",
            "reseratid":1,
            "rstate":0
        },
        {
            "sign":"我的个性签名",
            "username":"你好",
            "headportain":"20151207/20151207141945054.png",
            "goodname":"蒙山生态火锅",
            "datetime":"2015-11-20",
            "reseratid":5,
            "rstate":1
        },
        
    ]
}
 * 
 */
public class ReserationUser {
	
	private String sign;
	private String username;
	private String headportain;
    private String goodname;
    private String datetime;
    private int reseratid;
    private int rstate;
    
    
    
	public ReserationUser(String sign, String username, String headportain,
			String goodname, String datetime, int reseratid, int rstate) {
		super();
		this.sign = sign;
		this.username = username;
		this.headportain = headportain;
		this.goodname = goodname;
		this.datetime = datetime;
		this.reseratid = reseratid;
		this.rstate = rstate;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadportain() {
		return headportain;
	}
	public void setHeadportain(String headportain) {
		this.headportain = headportain;
	}
	public String getGoodname() {
		return goodname;
	}
	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getReseratid() {
		return reseratid;
	}
	public void setReseratid(int reseratid) {
		this.reseratid = reseratid;
	}
	public int getRstate() {
		return rstate;
	}
	public void setRstate(int rstate) {
		this.rstate = rstate;
	}

    
    
}
