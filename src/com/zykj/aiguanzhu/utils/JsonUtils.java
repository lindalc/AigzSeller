package com.zykj.aiguanzhu.utils;

import java.util.Map;

import com.google.gson.Gson;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-28 ����10:03:08 
 * @version 1.0 
 * @Description
 */
public class JsonUtils {
	public static String toJson(Map<String, String> map)
	{
		Gson gson = new Gson();
		String json = gson.toJson(map);
		return json;
	}
	
	
}
