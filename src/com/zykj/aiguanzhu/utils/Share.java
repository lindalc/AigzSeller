package com.zykj.aiguanzhu.utils;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zykj.aiguanzhu.R;

/**
 * @author  lc 
 * @date ����ʱ�䣺2015-12-30 ����5:13:54 
 * @version 1.0 
 * @Description
 * ���ò��裺
 * 1.����invite����������Activity��String ShareContent,String ShareTitle,String ShareUrl
 *
 */
public class Share {
	private static UMSocialService mController;
	public static void invit(Activity mContext,String ShareContent,String ShareTitle,String ShareUrl) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				// ����������Activity��������³�Ա����
				mController = UMServiceFactory.getUMSocialService("com.umeng.share");
				// ���÷�������
				mController.setShareContent(ShareContent);
				// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
				mController.setShareMedia(new UMImage(mContext, 
				                                     R.drawable.ic_launcher));
				// ��Ӷ���
				SmsHandler smsHandler = new SmsHandler();
				smsHandler.addToSocialSDK();
				

//				// ���΢��ƽ̨
				String appId = "wx265008cc352f2d3c";
				String appSecret = "990e51a92e97e668da68513b4542dd59";
				UMWXHandler wxHandler = new UMWXHandler(mContext,appId,appSecret);
				wxHandler.addToSocialSDK();
				//����΢�ź��ѷ�������
				WeiXinShareContent weixinContent = new WeiXinShareContent();
				//���÷�������
				weixinContent.setShareContent(ShareContent);
				//����title
				weixinContent.setTitle(ShareTitle);
				//���÷���������תURL
				weixinContent.setTargetUrl(ShareUrl);
				//���÷���ͼƬ
				weixinContent.setShareImage(new UMImage(mContext, 
		                R.drawable.ic_launcher));
				mController.setShareMedia(weixinContent);
				
				// ���qq
				//����1Ϊ��ǰActivity�� ����2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
				UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mContext, "100424468","c7394704798a158208a74ab60104f0ba");
				qqSsoHandler.addToSocialSDK(); 
				
				QQShareContent qqShareContent = new QQShareContent();
				//���÷�������
				qqShareContent.setShareContent(ShareContent);
				//���÷���title
				qqShareContent.setTitle(ShareTitle);
				//���÷���ͼƬ
				qqShareContent.setShareImage(new UMImage(mContext, R.drawable.ic_launcher));
				//���õ���������ݵ���ת����
				qqShareContent.setTargetUrl(ShareUrl);
				mController.setShareMedia(qqShareContent);
				
				mController.getConfig().removePlatform( SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE);
				mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ,
		                SHARE_MEDIA.TENCENT, SHARE_MEDIA.SINA,SHARE_MEDIA.SMS);
				mController.openShare(mContext, false);
	}

}

