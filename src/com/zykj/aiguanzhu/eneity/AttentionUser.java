package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-23 下午5:16:03 
 * @version 1.0 
 * @Description 关注用户实体类
 */

/**
 * {
"status":{
    "succeed":1,
    "errcode":"0000",
    "errdesc":"请求成功"
},
"data":[
    {
        "sign":"我的个性签名",
        "weixin":"1234567890",
        "address":"山东省临沂市",
        "headportain":"20151207/20151207141945054.png",
        "name":"你好",
        "userid":5,
        "intime":"2015-09-08 11:03:45",
        "qq":"(null)",
        "mobile":"15165519504"
    },
    {
        "sign":null,
        "weixin":null,
        "address":null,
        "headportain":"20151207/20151207142033056.png",
        "name":"15269914187",
        "userid":6,
        "intime":"2015-10-07 14:08:45",
        "qq":null,
        "mobile":"15269914187"
    }
]
}
 */

public class AttentionUser {

	private String sign;
	private String weixin;
	private String address;
	private String headportain;
	private String name;
	private int userid;
	private String intime;
	private String mobile;
	
	public AttentionUser(String sign, String address, String headportain,
			String name, int userid, String intime) {
		super();
		this.sign = sign;
		this.address = address;
		this.headportain = headportain;
		this.name = name;
		this.userid = userid;
		this.intime = intime;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadportain() {
		return headportain;
	}
	public void setHeadportain(String headportain) {
		this.headportain = headportain;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
