package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-24 ����2:47:19 
 * @version 1.0 
 * @Description ԤԼ����ʵ���� <br/> ԤԼ״̬rstate(0-ԤԼ����,1-ԤԼͬ�� 2-ԤԼȡ�� 3-ԤԼ���)
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":""
    },
    "data":{
        "goodid":5,
        "username":"lixiaoyao",
        "personNum":3,
        "receiveaddress":"��ɽ������",
        "goodname":"��ɽ��̬���",
        "datetime":"2015-11-20",
        "otherinfo":"��Ҫ�л������",
        "reserationid":1,
        "mobile":"15165561652",
        "rstate":0
    }
}
 */
public class ReserationDetail {
	
	private int goodid;
	private String username;
	private int personNum;
	private String receiveaddress;
	private String goodname;
	private String datetime;
	private String otherinfo;
	private int reserationid;
	private String mobile;
	private int rstate;
	
	
	
	public int getGoodid() {
		return goodid;
	}
	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public String getReceiveaddress() {
		return receiveaddress;
	}
	public void setReceiveaddress(String receiveaddress) {
		this.receiveaddress = receiveaddress;
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
	public String getOtherinfo() {
		return otherinfo;
	}
	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}
	public int getReserationid() {
		return reserationid;
	}
	public void setReserationid(int reserationid) {
		this.reserationid = reserationid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getRstate() {
		return rstate;
	}
	public void setRstate(int rstate) {
		this.rstate = rstate;
	}
	
	
}
