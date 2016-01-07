package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2016-1-5 下午4:19:28 
 * @version 1.0 
 * @Description 卡券数据实体类
 * {
    "status":{
        "succeed":1,
        "errcode":"0000",
        "errdesc":"卡券分析"
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
