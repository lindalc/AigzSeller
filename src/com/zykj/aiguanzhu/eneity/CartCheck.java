package com.zykj.aiguanzhu.eneity;
/**
 * @author  lc 
 * @date 创建时间：2015-12-26 下午3:37:23 
 * @version 1.0 
 * @Description 卡券核销实体类
 */
public class CartCheck {

	private String backgroundimg;
	private String head;
	private String cartname;
	private String name;
	private String state;
	
	public CartCheck(String backgroundimg,String head, String cartname, String name, String state) {
		super();
		this.backgroundimg = backgroundimg;
		this.head = head;
		this.cartname = cartname;
		this.name = name;
		this.state = state;
	}
	
	public String getBackgroundimg() {
		return backgroundimg;
	}

	public void setBackgroundimg(String backgroundimg) {
		this.backgroundimg = backgroundimg;
	}

	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getCartname() {
		return cartname;
	}
	public void setCartname(String cartname) {
		this.cartname = cartname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
