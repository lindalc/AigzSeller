package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date ����ʱ�䣺2016-1-5 ����4:19:28 
 * @version 1.0 
 * @Description ��ȯ����ʵ����
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"��ȯ����"
    },
    "data":[
        {
            "num":0,
            "date":20160099
        },
        {
            "num":0,
            "date":20160100
        }
    ]
}
 */
public class CartData {
	
	private int num;
	private Long date;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	
	
}
