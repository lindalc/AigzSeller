package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-24 ����11:59:59 
 * @version 1.0 
 * @Description ԤԼ�û�ʵ����<br/>     ԤԼ״̬rstate(0-ԤԼ����,1-ԤԼͬ�� 2-ԤԼȡ�� 3-ԤԼ���)
 {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"����ɹ�"
    },
    "data":[
        {
            "sign":"��������Ĺ¶�",
            "username":"lixiaoyao",
            "headportain":"20151207/20151207141310290.png",
            "goodname":"��ɽ��̬���",
            "datetime":"2015-11-20",
            "reseratid":1,
            "rstate":0
        },
        {
            "sign":"�ҵĸ���ǩ��",
            "username":"���",
            "headportain":"20151207/20151207141945054.png",
            "goodname":"��ɽ��̬���",
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
