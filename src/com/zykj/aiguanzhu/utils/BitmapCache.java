package com.zykj.aiguanzhu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * @author  lc 
 * @date 创建时间：2015-12-28 下午3:12:41 
 * @version 1.0 
 * @Description 图片缓存
 */
public class BitmapCache implements ImageCache {  
	  private Context mContext;
    private LruCache<String, Bitmap> cache;

	public BitmapCache(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public BitmapCache() {  
        cache = new LruCache<String, Bitmap>(8 * 1024 * 1024) {  
            @Override  
            protected int sizeOf(String key, Bitmap bitmap) {  
                return bitmap.getRowBytes() * bitmap.getHeight();  
            }  
        };  
    }  
  
    @Override  
    public Bitmap getBitmap(String url) {  
        return cache.get(url);  
    }  
  
    @Override  
    public void putBitmap(String url, Bitmap bitmap) {  
        cache.put(url, bitmap);  
        ImageUtils.saveBitmap2(bitmap, url, mContext);
    }
    
    public void putBitmap(String url, Bitmap bitmap,Context mContext) {  
        cache.put(url, bitmap);  
    }


}  
