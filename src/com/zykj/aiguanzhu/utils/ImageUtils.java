package com.zykj.aiguanzhu.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.zykj.aiguanzhu.R;

/**
 * @author  lc 
 * @date 创建时间：2015-12-29 上午11:06:51 
 * @version 1.0 
 * @Description
 */
public class ImageUtils {
	static RequestQueue mQueue;
	
    /**

	* 保存图片到本地,这个是把图片压缩成字节流然后保存到本地，所以本地的图片是无法显示的
	* 
	* @param mBitmap
	* @param imageURL
	* @param cxt
	*/
	public static void saveBitmap(Bitmap mBitmap, String imageURL, Context cxt) {
	
	
		String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1); //传入一个远程图片的url，然后取最后的图片名字
		
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
	
	
		try {
			fos = cxt.openFileOutput(bitmapName, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		//这里是保存文件产生异常
		} finally {
			if (fos != null) {
				try {
					fos.close();
					} catch (IOException e) {
					//fos流关闭异常
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
					} catch (IOException e) {
						//oos流关闭异常
						e.printStackTrace();
					}
			}
		}
	}
	
	
	/**
	* 读取本地私有文件夹的图片
	* 
	* @param name
	* @param cxt
	* @return
	*/
	public static Bitmap getBitmap(String fileName, Context cxt) {
	String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
	FileInputStream fis = null;
	ObjectInputStream ois = null;
	try {
	fis = cxt.openFileInput(bitmapName);
	ois = new ObjectInputStream(fis);
	byte[] byteArray = (byte[]) ois.readObject();
	Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
	byteArray.length);
	return bitmap;
	} catch (Exception e) {
	e.printStackTrace();
	//这里是读取文件产生异常
	} finally {
	if (fis != null) {
	try {
	fis.close();
	} catch (IOException e) {
	//fis流关闭异常
	e.printStackTrace();
	}
	}
	if (ois != null) {
	try {
	ois.close();
	} catch (IOException e) {
	//ois流关闭异常
	e.printStackTrace();
	}
	}
	}
	//读取产生异常，返回null
	return null;
	}
	
	
	
	//通过这种方式保存在本地的图片，是可以看到的
	
	public static void saveBitmap2(Bitmap mBitmap, String imageURL, Context cxt) {
	
	
		String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
		ToolsUtil.print("----", "save    ---------    "+bitmapName);
		
		FileOutputStream fos = null;
	
	
		try {
			fos = cxt.openFileOutput(bitmapName, Context.MODE_PRIVATE);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			//这里是保存文件产生异常
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//fos流关闭异常
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static Bitmap getBitmap2(String fileName, Context cxt,int requestWidth,int requestHeight) {
		String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
		ToolsUtil.print("----", "get    ---------    "+bitmapName);
		FileInputStream fis = null;
		try {
			fis = cxt.openFileInput(bitmapName);
			
			byte[] b = new byte[fis.available()];
			Bitmap bitmap = null;
			fis.read(b);
			// TODO 锟斤拷锟侥硷拷锟斤拷锟捷★拷锟斤拷么锟斤拷锟斤拷要锟斤拷锟斤拷

            // 锟斤拷锟斤拷原始锟斤拷图片锟竭寸，锟斤拷锟斤拷Bitmap锟斤拷锟斤拷锟斤拷
            // 锟斤拷锟斤拷Bitmap锟斤拷锟缴ｏ拷锟角帮拷锟斤拷 图片原始锟斤拷撸锟斤拷锟斤拷锟斤拷锟斤拷桑锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟秸硷拷锟斤拷母锟斤拷纸冢锟揭诧拷锟斤拷锟紸RGB
//            ret = BitmapFactory.decodeByteArray(data,0,data.length);

            // 锟斤拷锟矫讹拷锟轿诧拷锟斤拷(锟斤拷小图片锟竭达拷姆锟绞�)

            // 1. 锟斤拷锟斤拷1  锟斤拷取原始图片锟侥匡拷锟斤拷锟较拷锟斤拷锟斤拷诮锟斤拷胁锟斤拷锟斤拷募锟斤拷锟�

            // 1.1 锟斤拷锟斤拷Options锟斤拷锟斤拷BitmapFactory 锟斤拷锟节诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷诧拷锟斤拷
            BitmapFactory.Options options = new BitmapFactory.Options();

            // 1.2 锟斤拷锟斤拷 inJustDecodeBounds 锟斤拷锟斤拷锟狡斤拷锟斤拷锟斤拷锟斤拷只锟斤拷锟斤拷图片锟斤拷叩幕锟饺★拷锟斤拷锟斤拷锟斤拷锟斤拷Bitmap
            //     锟斤拷占锟斤拷锟节达拷, 锟斤拷使锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟紹itmapFactory.decodeXxxxx锟斤拷锟斤拷锟狡的凤拷锟斤拷锟斤拷锟斤拷锟结返锟斤拷Bitmap
            options.inJustDecodeBounds = true;

            // 锟斤拷锟诫，使锟斤拷Options 锟斤拷锟斤拷锟斤拷锟矫斤拷锟诫方式
            BitmapFactory.decodeByteArray(b, 0, b.length, options);

            //-------------------------------------------------

            // 2. 锟斤拷锟斤拷2  锟斤拷锟斤拷图片锟斤拷锟斤拷实锟竭寸，锟诫当前锟斤拷要锟斤拷示锟侥尺寸，锟斤拷锟叫硷拷锟姐，锟斤拷锟斤拷图片锟斤拷锟斤拷锟绞★拷

            // 2.1
            int picH = options.outHeight;   //4000
            int picW = options.outWidth;    //6000

            // 2.2 准锟斤拷 锟斤拷示锟斤拷锟街伙拷锟较的尺寸。  256*128   128*64
            //     锟竭达拷锟角革拷锟捷筹拷锟斤拷锟斤拷要锟斤拷锟斤拷锟矫的★拷

            //maxWidth,maxHeight
            int reqW = requestWidth;
            int reqH = requestHeight;  //锟斤拷锟斤拷锟斤拷锟斤拷

            // 2.3 锟斤拷锟姐、锟斤拷锟斤拷 图片锟斤拷锟斤拷锟斤拷
            options.inSampleSize = calculateInSampleSize(options, reqW, reqH);    // 锟斤拷鹊锟� 1/16 锟竭度碉拷 1/16

            // 2.4 锟斤拷锟斤拷 锟斤拷锟诫， 实锟斤拷锟斤拷锟斤拷Bitmap
            options.inJustDecodeBounds = false;

            // 2.4.1 Bitmap.Config 锟斤拷说锟斤拷
            // 要锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟矫恳伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷兀锟绞癸拷锟絉GB_565 锟芥储锟斤拷式
            // 一锟斤拷锟斤拷锟截ｏ拷占锟斤拷锟斤拷锟斤拷锟街斤拷  锟斤拷ARGB_8888  小锟斤拷一锟斤拷
            // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷芄锟绞癸拷锟街革拷锟斤拷锟斤拷锟斤拷茫锟斤拷锟斤拷远锟绞癸拷锟紸RGB_8888
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            // 2.4.2 锟斤拷一锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷
            options.inPurgeable = true;

            // 2.5 使锟斤拷锟斤拷锟矫诧拷锟斤拷锟侥诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷,锟斤拷取 Bitmap
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			//这里是读取文件产生异常
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					//fis流关闭异常
					e.printStackTrace();
				}
			}
		}
	//读取产生异常，返回null
	return null;
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // 锟斤拷锟斤拷锟斤拷目锟饺★拷锟竭讹拷 >0 时锟津，斤拷锟斤拷锟斤拷锟脚ｏ拷
        // 锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷;
        if (reqWidth > 0 && reqHeight > 0) {
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }

        return inSampleSize;
    }
	
	
	/**
	* 判断本地的私有文件夹里面是否存在当前名字的文件
	*/
	public static boolean isFileExist(String fileName, Context cxt) {
		String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
		List<String> nameLst = Arrays.asList(cxt.fileList());
		if (nameLst.contains(bitmapName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static byte[] decodeBitmap(String path) {  
        BitmapFactory.Options opts = new BitmapFactory.Options();  
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高  
        BitmapFactory.decodeFile(path, opts);  
        opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 800);  
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true  
        opts.inPurgeable = true;  
        opts.inInputShareable = true;  
        opts.inDither = false;  
        opts.inPurgeable = true;  
        opts.inTempStorage = new byte[16 * 1024];  
        FileInputStream is = null;  
        Bitmap bmp = null;  
        ByteArrayOutputStream baos = null;  
        byte[] by = null;
        try {  
            is = new FileInputStream(path);  
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);  
            double scale = getScaling(opts.outWidth * opts.outHeight,  
                    1024 * 600);  
            Bitmap bmp2 = Bitmap.createScaledBitmap(bmp,  
                    (int) (opts.outWidth * scale),  
                    (int) (opts.outHeight * scale), true);  
            bmp.recycle();  
            baos = new ByteArrayOutputStream();  
            bmp2.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
            bmp2.recycle();  
            by = baos.toByteArray();
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
            	if(is != null){
            		is.close();  
            	}
            	if(baos !=null ){
            		baos.close();  
            	}
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            System.gc();  
        }  
        return by;  
    }  
  
    private static double getScaling(int src, int des) {  
        /** 
         * 48 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比 49 
         */  
        double scale = Math.sqrt((double) des / (double) src);  
        return scale;  
    }  
  
    public static int computeSampleSize(BitmapFactory.Options options,  
            int minSideLength, int maxNumOfPixels) {  
        int initialSize = computeInitialSampleSize(options, minSideLength,  
                maxNumOfPixels);  
  
        int roundedSize;  
        if (initialSize <= 8) {  
            roundedSize = 1;  
            while (roundedSize < initialSize) {  
                roundedSize <<= 1;  
            }  
        } else {  
            roundedSize = (initialSize + 7) / 8 * 8;  
        }  
  
        return roundedSize;  
    }  
  
    private static int computeInitialSampleSize(BitmapFactory.Options options,  
            int minSideLength, int maxNumOfPixels) {  
        double w = options.outWidth;  
        double h = options.outHeight;  
  
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math  
                .sqrt(w * h / maxNumOfPixels));  
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(  
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));  
  
        if (upperBound < lowerBound) {  
            return lowerBound;  
        }  
  
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
            return 1;  
        } else if (minSideLength == -1) {  
            return lowerBound;  
        } else {  
            return upperBound;  
        }  
    }   
	
	public ImageUtils(Context mContext,ImageView imageView,String url,int width,int height){}
	
	public static void load(Context mContext,ImageView imageView,String url,int width,int height){
		mQueue = Volley.newRequestQueue(mContext);  ;  
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache(){
			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return super.getBitmap(url);
			}
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				super.putBitmap(url, bitmap);
			}
		});
		
		ImageListener listener = ImageLoader.getImageListener(imageView,  
		        R.drawable.main_icon_headportrait, R.drawable.main_icon_headportrait);
		
		imageLoader.get(url,  
	            listener, width, height);  
		
	}
	
	public static void cancel(Context mContext){
		mQueue.cancelAll(mContext);
	}
	

	
}
