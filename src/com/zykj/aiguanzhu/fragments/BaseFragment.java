package com.zykj.aiguanzhu.fragments;

import android.support.v4.app.Fragment;

/**
 * @author  lc 
 * @date 创建时间：2015-12-23 上午11:12:21 
 * @version 1.0 
 * @Description 基础Fragment
 */
public abstract class BaseFragment extends Fragment {
    
	// 得到页面对应的标题(如果接口中有从接口中获取,如果没有则在xml或者类中自定义)
	public abstract String getFragmentTitle();

}

