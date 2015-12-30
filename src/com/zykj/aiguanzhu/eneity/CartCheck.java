package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-26 下午3:37:23 
 * @version 1.0 
 * @Description 卡券核销实体类
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"卡券核销"
    },
    "data":[
        {
            "createtime":"2015-12-28 20:16:05",
            "id":12,
            "couponname":"双旦狂欢",
            "couponimage":"20151217/20151217162456260.jpg",
            "couponnum":null,
            "memberid":42,
            "state":0,
            "membername":"17086393171",
            "couponcolor":"#3D78DA",
            "effecttime":"2016-01-02 00:00:00"
        },
        {
            "createtime":"2015-12-21 11:03:45",
            "id":1,
            "couponname":"满100减30",
            "couponimage":"20151205/20151205094258037.jpg",
            "couponnum":"1511271030238761",
            "memberid":1,
            "state":1,
            "membername":"lixiaoyao",
            "couponcolor":"#DE9C33",
            "effecttime":"2015-10-10 11:03:45"
        },
        {
            "createtime":"2015-12-17 14:30:13",
            "id":10,
            "couponname":"满100减30",
            "couponimage":"20151205/20151205094258037.jpg",
            "couponnum":"1511271030238770",
            "memberid":6,
            "state":4,
            "membername":"15269914187",
            "couponcolor":"#DE9C33",
            "effecttime":"2015-10-10 11:03:45"
        }
    ]
}
 */
public class CartCheck {

	private String couponcolor;
	private String couponimage;
	private String couponname;
	private String membername;
	private String state;
	
	
	public String getCouponcolor() {
		return couponcolor;
	}
	public void setCouponcolor(String couponcolor) {
		this.couponcolor = couponcolor;
	}
	public String getCouponimage() {
		return couponimage;
	}
	public void setCouponimage(String couponimage) {
		this.couponimage = couponimage;
	}
	public String getCouponname() {
		return couponname;
	}
	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
