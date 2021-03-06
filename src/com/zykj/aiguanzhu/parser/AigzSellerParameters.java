package com.zykj.aiguanzhu.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zykj.aiguanzhu.eneity.MorePicture;

import android.os.Bundle;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午10:53:35 
 * @version 1.0 
 * @Description  A list queue for saving keys and values. <br/>
 * 							   Using it to construct http header or get/post parameters.<br/>
 */
public class AigzSellerParameters {
	private Bundle mParameters = new Bundle();
	private List<String> mKeys = new ArrayList<String>();
	private HashMap<String,List<MorePicture>> picMap = new HashMap<String, List<MorePicture>>();
	
	
	
	public AigzSellerParameters(){
		
	}
	
	
	public void add(String key, String value){
		if(this.mKeys.contains(key)){	
			this.mParameters.putString(key, value);
		}else{
			this.mKeys.add(key);
			this.mParameters.putString(key, value);
		}
	}
	
	public void addPicture(String key, List<MorePicture> list){
		
		if(this.mKeys.contains(key)){	
			this.picMap.put(key, list);
		}else{
			this.mKeys.add(key);
			this.picMap.put(key, list);
		}
	}
	
	
	
	public void remove(String key){
		mKeys.remove(key);
		this.mParameters.remove(key);
	}
	
	public void remove(int i){
		String key = this.mKeys.get(i);
		this.mParameters.remove(key);
		mKeys.remove(key);
	}
	
	
	public int getLocation(String key){
		if(this.mKeys.contains(key)){
			return this.mKeys.indexOf(key);
		}
		return -1;
	}
	
	public String getKey(int location){
		if(location >= 0 && location < this.mKeys.size()){
			return this.mKeys.get(location);
		}
		return "";
	}
	
	public List<MorePicture> getPicList(String key){
		if (key!=null && !key.equals("")) {
			return picMap.get(key);
		}
		return null;
	}
	
	
	public String getValue(String key){
		String rlt = this.mParameters.getString(key);
		return rlt;
	}
	
	public String getValue(int location){
		String key = this.mKeys.get(location);
		String rlt = this.mParameters.getString(key);
		return rlt;
	}
	
	
	public int size(){
		return mKeys.size();
	}
	
	public void addAll(AigzSellerParameters parameters){
		for(int i = 0; i < parameters.size(); i++){
			this.add(parameters.getKey(i), parameters.getValue(i));
		}
		
	}
	
	public void clear(){
		this.mKeys.clear();
		this.mParameters.clear();
	}
}
