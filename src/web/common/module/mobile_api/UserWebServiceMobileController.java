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
		String userPhoneNumble = null;
		String userSex = null;
		String userEmail = null;
		String photo = null;

		if (jsonData != null) {
			userId = jsonData.getString("userId");
			userName = jsonData.getString("userName");
			userPassword = jsonData.getString("userPassword");
			userBirthday = jsonData.getString("userBirthday");
			userPhoneNumble = jsonData.getString("userPhoneNumble");
			userSex = jsonData.getString("userSex");
			userEmail = jsonData.getString("userEmail");
			photo =jsonData.getString("userPhoto");
		}

		String path = URLDecoder.decode(Initializer.class.getResource("/").getPath(), "UTF-8");
		String photoURL = path + "/image/userphoto/"+photo;

		ProcedureResult pr = UserWebServiceMobileController.updateUserInformation_db_procedure(userId, userName,
				userPassword, userBirthday, userPhoneNumble, userSex, userEmail, photoURL);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult updateUserInformation_db_procedure(String userId, String userName, String userPassword,
			String userBirthday, String userPhoneNumble, String userSex, String userEmail, String userPhoto)
			throws Exception {

		return UserWebServiceOperations.updateInformation_db_procedure(userId, userName, userPassword, userBirthday,
				userPhoneNumble, userSex, userEmail, userPhoto);
	}


	
	
}
