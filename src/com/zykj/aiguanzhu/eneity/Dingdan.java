package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-30 ����9:37:07 
 * @version 1.0 
 * @Description ȷ�϶���ʵ����
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"ȷ�϶����ɹ�"
    },
    "data":{
        "id":1,
        "price":0.01,
        "ordernum":"1449492768995381",
        "delivertytime":"2015-12-30 08:49:27"
    }
}
 */
public class Dingdan {
	
	private int id;
	private double price;
	private String ordernum;
	private String delivertytime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getDelivertytime() {
		return delivertytime;
	}
	public void setDelivertytime(String delivertytime) {
		this.delivertytime = delivertytime;
	}
	
	
}
