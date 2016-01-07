package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-28 下午3:59:23 
 * @version 1.0 
 * @Description 登陆后实体类 
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"商家信息"
    },
    "data":{
        "sellnum":5,
        "announcement":null,
        "remark":"优惠便利",
        "rebaterate":2.5,
        "mdesc":"商户描述信息2",
        "isrebate":0,
        "categoryid":37,
        "imgpath":"20151205/20151205093606813.jpg",
        "address":"北京朝阳区2",
        "totalIncome":0.41,
        "name":"东北麻辣烫",
        "sellprice":20,
        "telephone":"010-23456789-2",
        "opentime":"06:00-18:00"
    }
}
 */
public class SellerInfo {
	private String name;
	private String imgpath;
	private String totalIncome;
	private String sellnum;
	private String rebaterate;
	private int categoryid;
	
	
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSellnum() {
		return sellnum;
	}
	public void setSellnum(String sellnum) {
		this.sellnum = sellnum;
	}
	public String getRebaterate() {
		return rebaterate;
	}
	public void setRebaterate(String rebaterate) {
		this.rebaterate = rebaterate;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	
	
}
