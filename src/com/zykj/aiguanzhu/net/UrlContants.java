package com.zykj.aiguanzhu.net;

import com.zykj.aiguanzhu.utils.ToolsUtil;

/**
 * @author Administrator ������·��
 */
public class UrlContants {

	public static final String SERVERIP = "115.28.67.86:8080";

	public static final String BASE_URL = "http://115.28.67.86:8080/aigz/";

	public static final String IMAGE_URL = "http://115.28.67.86:8080/aigz/upload/";

	public static final String ORDERPAY = "http://121.42.194.222/pingxx/api/pay.php";// ֧��

	public static final String BASEURL = BASE_URL + "%s";								

	public static final String jsonData = "datas";

	public static final String ERROR = "{\"code\":400,\"message\":\"����ʧ��\",\"datas\":null}";

	public static final String ZERODATA = "{\"code\":200,\"message\":\"û������\",\"datas\":\"\"}";

	public static final String GETADSLIST = "c=public&a=getAdsList&type=slideFocus";// �ֲ�ͼ

	//http://121.42.194.222/api.php?c=public&a=getArticleInfo&type=about&id=1  
		
	public static final String GETABOUTUS = "c=public&a=getArticleInfo";// ��������

	public static final String GETORDERLIST = "c=info&a=getOrderList";// ��ȡ����

	public static final String GETORDER = "c=info&a=getOrder";// ��ȡ��������

	public static final String LOGIN = "c=user&a=login";// ��¼

	public static final String REGISTER = "c=user&a=reg";// ע��

	public static final String RESETPASSWORD = "c=user&a=resetPassword";// ��������

	public static final String RESETUSERNAME = "c=user&a=resetUsername";// �޸��ǳ�

	public static final String LIKELIST = "c=info&a=getLikeList";// ����ϲ��

	public static final String POSIUSERAVATAR = "mdata/uploadAvatar";// �ϴ�ͷ��

	public static final String GETCATEGORY = "c=info&a=getcategory";// ��ȡ�������Ƶꡢ���̷���

	public static final String GETRESTAURANTS = "c=info&a=getlist";// ��ȡ�������Ƶꡢ�����б�

	public static final String GETGOODLIST = "c=info&a=getgoodslist";// ��ȡ�������Ƶꡢ������Ʒ

	public static final String COMMENTLIST = "c=info&a=getCommentsList";// ��ȡ�����б�

	// public static final String CARLIST = "c=car&a=getlist";//��ȡ�б�

	// public static final String CARINFO = "c=car&a=getinfo";//��ȡ�б�

	public static final String NEEDCAR = "c=car&a=needcar";// ��Ҫƴ��

	public static final String GETCARLIST = "c=car&a=getlist";// ƴ���б�

	public static final String OFFERCAR = "c=car&a=offercar";// ��Ҫƴ��

	public static final String POSTCARORDER = "c=car&a=carorder";// ��Ҫƴ��

	public static final String GETAREA = "c=public&a=getArea";// ��ȡ�����б�

	public static final String SUBMIT = "c=info&a=postOrder";// �ύ�������Ƶ�

	public static final String GETGOODS = "c=info&a=getGoods";// ��ȡ��Ʒ����

	public static final String GETBIANMINLIST = "c=services&a=getList";// ��ȡ�����б�

	public static final String GETZHAOPINCATEGORY = "c=hr&a=getCategory";// ��ȡ��Ƹ���

	public static final String GETZHAOPINLIST = "c=hr&a=getList";// ��ȡ��Ƹ�б�

	public static final String SUBMITZHAOPININFO = "c=hr&a=postInfo";// ������Ƹ��Ϣ

	public static final String GETZHAOPININFO = "c=hr&a=getInfo";// ��ȡ��Ƹ����

	public static final String GETHOUSELIST = "c=house&a=getList";// ��ȡ�����б�

	public static final String SUBMITHOUSEINFO = "c=house&a=postInfo";// ����������Ϣ

	public static final String GETHOUSEINFO = "c=house&a=getInfo";// ��ȡ��������

	public static final String SUBMITSUPPLYDEMANDINFO = "c=offer&a=postInfo";// ����������Ϣ

	public static final String GETSUPPLYDEMANDLIST = "c=offer&a=getList";// ��ȡ�����б�

	public static final String GETSUPPLYDEMANDINFO = "c=offer&a=getInfo";// ��ȡ��������

	public static final String GOODSCOMMENTSLIST = "c=info&a=getGoodsCommentsList&type=shop";// ��ȡ��Ʒ�����б�

	public static final String SUBMITSHOPORDER = "c=info&a=postShopOrder";// �ύ�Ź�

	public static final String GETLOGINURL = "c=points&a=getLoginUrl";// �����̳�

	public static final String GETDRAWURL = "c=points&a=getDrawUrl";// ���˴�齱

	public static final String USERSIGN = "c=sign&a=signIn";// �Ƿ�ǩ��

	public static final String POSTTIXIANLIST = "c=user&a=posttixian";// �Ƿ�ǩ��

	public static final String UPDATEORDER = "c=info&a=updateorder";// �Ƿ�ǩ��

	public static final String POSTCOMMENTS = "c=info&a=postComments";// �ύ����

	public static final String DELORDER = "c=info&a=delOrder";// ɾ������

	public static final String ADDCOLLECTION = "c=user&a=addcollection";// ����ղ�

	public static final String GETCOLLECTIONLIST = "c=user&a=getCollectionList";// ��ȡ�ղ��б�

	public static final String DELETECOLLECTIONLIST = "c=user&a=deleteCollectionList";// ��ȡ�ղ��б�

	public static final String GETCOLLECTIONINFO = "c=info&a=getCollectionInfo";// ��ȡ�ղ�����

	public static final String UPLOADONE = "c=public&a=upload_one";// ͼƬ�����ϴ�

	public static final String SHOPDETAIL = "c=info&a=getinfo";// ��ȡ�Ƶ�����

	public static final String DELCOLLECTIONINFO = "c=user&a=delCollectionInfo";// ��ȡ�Ƶ�����

	public static String getUrl(String token) {
		if (token == null || token.equals("")) {
			ToolsUtil.print("----", "token is null ++++++++++++");
			return BASE_URL;
		}
		return String.format(BASEURL, token);
	}
}
