package com.zykj.aiguanzhu.custome;

import com.zykj.aiguanzhu.R;

import android.view.View;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-26 ����9:31:18 
 * @version 1.0 
 * @Description �Զ�����״ͼ
 */
public class HistogramView extends View {


    private Paint titlePaint;// �����ı��Ļ���
    private Paint paint;// ���λ��� ��״ͼ����ʽ��Ϣ
//    private int[] progress = { 30, 20, 35, 10, 39, 39, 39 };// 7
                                                                            // ������ʾ������״������
    private int[] progress = null;
    private final int TRUE = 1;// ����״ͼ����ʾ����
    private int[] text;// ���õ���¼�����ʾ��һ����״����Ϣ
    private Bitmap bitmap;
    // ��������������
    private String[] ySteps;
    // ������ײ���������
    private String[] xWeeks = null;
    private int flag;// �Ƿ�ʹ�ö���
 
    private Bitmap bm;
    private BitmapDrawable bmDrawable;
 
    public HistogramView(Context context,int[] num,String[] date) {
        super(context);
        progress = num;
        this.xWeeks = date;
        init();
    }
 
    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    private void init() {
 
        ySteps = new String[] { "40��", "35��", "30��", "25��", "20��","10��","5��" };
//        xWeeks = new String[] { "3.18", "3.19", "3.20", "3.21", "3.22", "3.23", "3.24" };
        text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
 
        titlePaint = new Paint();
        paint = new Paint();
 
        // ������������ɫ
        titlePaint.setColor(Color.GRAY);
 
        // ���ػ�ͼ
        bitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.gray);
    }
 
 
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        int width = getWidth();
        int height = getHeight()-dp2px(50);
 
        int leftHeight = height-dp2px(5);// ������ܵ� ��Ҫ���ֵĸ߶ȣ�
 
        int hPerHeight = leftHeight / 7;// �ֳ�7����
 
        // ���� Y ������
        titlePaint.setTextAlign(Align.RIGHT);
        titlePaint.setTextSize(sp2px(12));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.STROKE);
        // �����󲿵�����
        for (int i = 0; i < ySteps.length; i++) {
            canvas.drawText(ySteps[i], dp2px(25), dp2px(13) + i * hPerHeight,
                    titlePaint);
        }
 
        // ���� X �� ������
        int xAxisLength = width - dp2px(30);
        int columCount = xWeeks.length + 1;
        int step = xAxisLength / columCount;
 
        // ���õײ�������
        for (int i = 0; i < columCount - 1; i++) {
            // text, baseX, baseY, textPaint
            canvas.drawText(xWeeks[i], dp2px(30) + step * (i + 1), height
                    + dp2px(20), titlePaint);
        }
 
        // ���ƾ���
        if (progress != null && progress.length > 0) {
            for (int i = 0; i < progress.length; i++) {// ѭ��������7����״ͼ�λ�����
                int value = progress[i];
                paint.setAntiAlias(true);// �����Ч��
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(sp2px(15));// �����С
                paint.setColor(Color.GRAY);// ������ɫ
                Rect rect = new Rect();// ��״ͼ����״
 
                rect.left = step * (i + 1);
                rect.right = dp2px(30) + step * (i + 1);
                int rh = (int)(leftHeight - leftHeight * (value / 45.0));
                rect.top = rh;
                rect.bottom = height;
                
 
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.gray);
                bmDrawable = new BitmapDrawable(getResources(), bm);
                canvas.drawBitmap(bmDrawable.getBitmap(), null, rect, paint);
//                canvas.drawBitmap(bitmap, null, rect, paint);
                
                // �Ƿ���ʾ��״ͼ�Ϸ�������
//                if (this.text[i] == TRUE) {
//                    canvas.drawText(value + "", dp2px(15) + step * (i + 1)
//                            - dp2px(15), rh + dp2px(5), paint);
//                }
 
            }
        }
 
    }
 
    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }
    
    private int dp2px(double value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }
 
    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }
 
    /**
     * ���õ���¼����Ƿ���ʾ����
     */
    public boolean onTouchEvent(MotionEvent event) {
        int step = (getWidth() - dp2px(30)) / 8;
        int x = (int) event.getX();
        for (int i = 0; i < 7; i++) {
            if (x > (dp2px(15) + step * (i + 1) - dp2px(15))
                    && x < (dp2px(15) + step * (i + 1) + dp2px(15))) {
                text[i] = 1;
                for (int j = 0; j < 7; j++) {
                    if (i != j) {
                        text[j] = 0;
                    }
                }
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    invalidate();
                } else {
                    postInvalidate();
                }
            }
        }
        return super.onTouchEvent(event);
    }
 
    public static Bitmap drawableToBitmap(Drawable drawable) {
    	 
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2,
                drawable.getIntrinsicHeight() / 2);
        drawable.draw(canvas);
 
        return bitmap;
 
    }
   
}
