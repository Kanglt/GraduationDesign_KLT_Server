/**     
*/
package web.common.module.mobile_api;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.download.DownloadFileInfo;
import lyu.klt.frame.controller.download.IDownloadHandler;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.method.parameter.annotation.Decrypt;
import lyu.klt.frame.controller.servlet.Initializer;
import lyu.klt.frame.database.core.ProcedureResult;
import lyu.klt.frame.utils.FileUtils;
import web.common.access.mobileuser.MobileUserAccessHandler;
import web.common.handler.DecrptHandler;
import web.common.handler.WebServiceMobileMessage;
import web.common.module.db_operations.DataWebServiceOperations;
import web.common.module.db_operations.UserWebServiceOperations;

/**
 * @ClassName: UserLoginWebServiceMobileController
 * @Description: TODO(移動端登入接口)
 * @author 康良涛
 * @date 2016年11月27日 上午11:40:57
 * 
 */

@WebServiceController("UserWebServiceMobile")
public class UserWebServiceMobileController  {
	private static UserWebServiceMobileController UserWebServiceMobileController = new UserWebServiceMobileController();
	private String path;

	/**
	 * 
	 * @Title: login @author 康良涛 @Description: TODO(用户登入) @param @param
	 * jsonDataStr @param @return @param @throws Exception @return
	 * String @throws
	 */

	// @Decrypt(handler = DecrptHandler.class)

	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String login(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String departmentId = null;
		String userId = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
		}

		// String queryName = null;
		// String queryAddress = null;
		//
		// String operatorId = null;
		// String operatorAddress = null;
		//
		// Integer pageSize = Constants.COMMON_PAGE_SIZE;
		//
		// String queryId = null;
		// String queryUpdateBy = null;
		// String queryUpdateByAddress = null;
		// Timestamp queryUpdateTimeStart = null;
		// Timestamp queryUpdateTimeEnd = null;
		// String queryCreateBy = null;
		// String queryCreateByAddress = null;
		// Timestamp queryCreateTimeStart = null;
		// Timestamp queryCreateTimeEnd = null;

		ProcedureResult pr = UserWebServiceMobileController.getUserLogin_db_procedure(userId);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult getUserLogin_db_procedure(String userId) throws Exception {

		return UserWebServiceOperations.getUserLogin_db_procedure(userId);
	}

	/**
	 * 
	 * @Title: getUserInformation @author 康良涛 @Description:
	 *         TODO(获取用户信息) @param @param
	 *         jsonDataStr @param @return @param @throws Exception @return
	 *         String @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getUserInformation(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String departmentId = null;
		String userId = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
		}

		// String queryName = null;
		// String queryAddress = null;
		//
		// String operatorId = null;
		// String operatorAddress = null;
		//
		// Integer pageSize = Constants.COMMON_PAGE_SIZE;
		//
		// String queryId = null;
		// String queryUpdateBy = null;
		// String queryUpdateByAddress = null;
		// Timestamp queryUpdateTimeStart = null;
		// Timestamp queryUpdateTimeEnd = null;
		// String queryCreateBy = null;
		// String queryCreateByAddress = null;
		// Timestamp queryCreateTimeStart = null;
		// Timestamp queryCreateTimeEnd = null;

		ProcedureResult pr = UserWebServiceMobileController.getUserInformation_db_procedure(userId);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult getUserInformation_db_procedure(String userId) throws Exception {

		return UserWebServiceOperations.getUserInformation_db_procedure(userId);
	}

	/**
	 * 
	 * @Title: updateUserInformation @author 康良涛 @Description:
	 *         TODO(修改用户信息) @param @param
	 *         jsonDataStr @param @return @param @throws Exception @return
	 *         String @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String updateUserInformation(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String userName = null;
		String userPassword = null;
		String userBirthday = null;
		String userAge = null;
		String userPhoneNumble = null;
		String userSex = null;
		String userEmail = null;
		String photo = null;

		if (jsonData != null) {
			userId = jsonData.getString("userId");
			userName = jsonData.getString("userName");
			userPassword = jsonData.getString("userPassword");
			userBirthday = jsonData.getString("userBirthday");
			userAge= jsonData.getString("userAge");
			userPhoneNumble = jsonData.getString("userPhoneNumble");
			userSex = jsonData.getString("userSex");
			userEmail = jsonData.getString("userEmail");
			photo =jsonData.getString("userPhoto");
		}

		String path = URLDecoder.decode(Initializer.class.getResource("/").getPath(), "UTF-8");
		String photoURL = path + "/image/userphoto/"+photo;

		ProcedureResult pr = UserWebServiceMobileController.updateUserInformation_db_procedure(userId, userName,
				userPassword, userBirthday,userAge, userPhoneNumble, userSex, userEmail, photoURL);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult updateUserInformation_db_procedure(String userId, String userName, String userPassword,
			String userBirthday, String userAge, String userPhoneNumble, String userSex, String userEmail, String userPhoto)
			throws Exception {

		return UserWebServiceOperations.updateInformation_db_procedure(userId, userName, userPassword, userBirthday,userAge,
				userPhoneNumble, userSex, userEmail, userPhoto);
	}


	
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryUserHomePageInfo(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		String userId=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");		
		}

		ProcedureResult pr_userInfo = UserWebServiceMobileController.getUserInformation_db_procedure(userId);
		ProcedureResult pr_userFocusNum = UserWebServiceMobileController.queryUserFocusNum_db_procedure(userId);
		ProcedureResult pr_userFansNum = UserWebServiceMobileController.queryUserFansNum_db_procedure(userId);
		ProcedureResult pr_userDynamicNum = UserWebServiceMobileController.queryUserDynamicNum_db_procedure(userId);
		
		JSONObject json_userInfo=pr_userInfo.getRecordAsJSONObject();
		JSONObject json_userFocusNum=pr_userFocusNum.getRecordAsJSONObject();
		JSONObject json_userFansNum=pr_userFansNum.getRecordAsJSONObject();
		JSONObject json_userDynamicNum=pr_userDynamicNum.getRecordAsJSONObject();
		
		json_userInfo.put("focusNum", json_userFocusNum.get("focusNum"));
		json_userInfo.put("fansNum", json_userFansNum.get("fansNum"));
		json_userInfo.put("dynamicNum", json_userDynamicNum.get("dynamicNum"));
		
		
		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, json_userInfo);
		return msg.toString();
	}

	public ProcedureResult queryUserFocusNum_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserFocusNum_db_procedure(userId);
	}
	
	public ProcedureResult queryUserFansNum_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserFansNum_db_procedure(userId);
	}
	public ProcedureResult queryUserDynamicNum_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserDynamicNum_db_procedure(userId);
	}
	
	/**
	 * 
	* @Title: queryUserFocusInfo 
	* @author 康良涛 
	* @Description: TODO(查询用户关注列表) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryUserFocusInfo(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		String userId=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");		
		}

		ProcedureResult pr = UserWebServiceMobileController.queryUserFocusInfo_db_procedure(userId);
		
		
		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult queryUserFocusInfo_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserFocusInfo_db_procedure(userId);
	}
	/**
	 * 
	* @Title: queryUserFansInfo 
	* @author 康良涛 
	* @Description: TODO(查询用户粉丝列表) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryUserFansInfo(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		String userId=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");		
		}

		ProcedureResult pr = UserWebServiceMobileController.queryUserFansInfo_db_procedure(userId);
		
		
		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}
	public ProcedureResult queryUserFansInfo_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserFansInfo_db_procedure(userId);
	}
	/**
	 * 
	* @Title: queryIsFocus 
	* @author 康良涛 
	* @Description: TODO(判断是否关注对方) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryIsFocus(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		String userId=null;
		String focusId=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");		
			focusId = jsonData.getString("focusId");	
		}

		ProcedureResult pr = UserWebServiceMobileController.queryIsFocus_db_procedure(userId,focusId);
		
		
		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}
	public ProcedureResult queryIsFocus_db_procedure(String userId,String focusId) throws Exception {

		return DataWebServiceOperations.queryIsFocus_db_procedure(userId,focusId);
	}
	
	/**
	 * 
	* @Title: deleteUserFocus 
	* @author 康良涛 
	* @Description: TODO(取消对对方的关注) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String deleteUserFocus(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		String userId=null;
		String focusId=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");		
			focusId = jsonData.getString("focusId");	
		}

		ProcedureResult pr = UserWebServiceMobileController.deleteUserFocus_db_procedure(userId,focusId);
		
		
		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}
	public ProcedureResult deleteUserFocus_db_procedure(String userId,String focusId) throws Exception {

		return DataWebServiceOperations.deleteUserFocus_db_procedure(userId,focusId);
	}
}
