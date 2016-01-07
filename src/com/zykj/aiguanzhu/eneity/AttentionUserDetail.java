package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-28 下午2:54:37 
 * @version 1.0 
 * @Description 用户资料实体类
 *  {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"用户资料"
    },
    "data":{
        "sign":"美丽世界的孤儿",
        "mail":"",
        "birthday":"",
        "sex":"男",
        "weixin":"yipin",
        "address":"蜀山天机宫",
        "headportain":"20151207/20151207141310290.png",
        "name":"lixiaoyao",
        "userid":1,
        "blog":"11",
        "intime":"2015-08-15 11:03:45",
        "qq":"",
        "mobile":"15165561652"
    }
}
 */
public class AttentionUserDetail {
	private String sign;
	private String sex;
	private String birthday;
	private String address;
	private String headportain;
	private String name;
	private String intime;
	private String mobile;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
