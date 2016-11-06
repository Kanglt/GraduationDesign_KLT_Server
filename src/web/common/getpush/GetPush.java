package web.common.getpush;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.database.config.ConfigConnection;
import lyu.klt.frame.utils.Utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

/**个推工具类
 * @author Administrator
 *
 */
public class GetPush {
	// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private  String appId = "hk1xzl6bh96bNAkX7qQpV4";
	private  String appKey = "Xzs4GC8f907rqmdBPURZL5";
	private  String masterSecret = "IXpDEQSXYZ78kg0Hmtg285";
	private static  String CID = "650bf90660f18674389149da4c6a6eea";
	// 别名推送方式
	// static String Alias = "";
	private  String host = "http://sdk.open.api.igexin.com/apiex.htm";
	//通知的图标,要在APP本地放一个这个名称的图片
	private  String logoName="";
	private  String logoURL="";
	//点击通知跳转的URL
	private  String cilckUrl="";
	
	
	private static Log log = LogFactory.getLog(GetPush.class);
	private static GetPush instance = null;

	public static GetPush getInstance() {
		if (instance == null)
			instance = new GetPush();
		return instance;
	}

	private GetPush() {
		Properties config = new Properties();
		FileInputStream fis = null;
		try {
			String path = ConfigConnection.class.getResource("/").getPath()
					+ "../config_getpush.prop";
			fis = new FileInputStream(URLDecoder.decode(path, "UTF-8"));
			config.load(fis);

			appId = config.getProperty("appId");
			appKey = config.getProperty("appKey");
			masterSecret = config.getProperty("masterSecret");
			host = config.getProperty("host");
			logoName = config.getProperty("logoName");
			cilckUrl = config.getProperty("cilckUrl");
			logoURL = config.getProperty("logoURL");
		} catch (Exception e) {
			log.error(Utils.getOriginalMessageFromException(e), e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					log.error(Utils.getOriginalMessageFromException(e), e);
				}
			}
		}
	}

	private  LinkTemplate getLinkTemplateinkTemplate(String appId,
			String appKey, String title, String text, String logoName,
			String logoUrl, String url) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(text);
		// 配置通知栏图标
		template.setLogo(logoName);
		// 配置通知栏网络图标，填写图标URL地址
		template.setLogoUrl(logoUrl);
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		template.setUrl(url);
		return template;
	}


	
	/**指定推送个人消息
	 * @param cid
	 * @param title
	 * @param text
	 */
	public  void pushSingleMessage(String cid,String title,String text){
		IGtPush push = new IGtPush(GetPush.getInstance().host, GetPush.getInstance().appKey, GetPush.getInstance().masterSecret);
		LinkTemplate template = getLinkTemplateinkTemplate(GetPush.getInstance().appId, GetPush.getInstance().appKey,
				title, text,GetPush.getInstance().logoName,GetPush.getInstance().logoURL,GetPush.getInstance().cilckUrl);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(GetPush.getInstance().appId);
		target.setClientId(cid);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
	}
	
	/** 推送APP消息，所有个推用户都可以收到消息
	 * @param title
	 * @param text
	 */
	public void pushAppMessage(String title,String text){
		try {
			IGtPush push = new IGtPush(GetPush.getInstance().host, GetPush.getInstance().appKey, GetPush.getInstance().masterSecret);
			LinkTemplate template = getLinkTemplateinkTemplate(GetPush.getInstance().appId, GetPush.getInstance().appKey,
					title, text,GetPush.getInstance().logoName,GetPush.getInstance().logoURL,GetPush.getInstance().cilckUrl);
	        AppMessage message = new AppMessage();
	        message.setData(template);

	        message.setOffline(true);
	        //离线有效时间，单位为毫秒，可选
	        message.setOfflineExpireTime(24 * 1000 * 3600);
	        //推送给App的目标用户需要满足的条件
	        AppConditions cdt = new AppConditions(); 
	        List<String> appIdList = new ArrayList<String>();
	        appIdList.add(GetPush.getInstance().appId);
	        message.setAppIdList(appIdList);
	        //手机类型
	        List<String> phoneTypeList = new ArrayList<String>();
	        //省份
	        List<String> provinceList = new ArrayList<String>();
	        //自定义tag
	        List<String> tagList = new ArrayList<String>();

	        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
	        cdt.addCondition(AppConditions.REGION, provinceList);
	        cdt.addCondition(AppConditions.TAG,tagList);
	        message.setConditions(cdt); 

	        IPushResult ret = push.pushMessageToApp(message,"message_toApp");
	        System.out.println(ret.getResponse().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		GetPush.getInstance().pushSingleMessage(CID,"测试标题", "测试内容");
		GetPush.getInstance().pushAppMessage("测试标题1", "测试内容1");
	}

}
