/**     
*/
package web.common.module.mobile_api;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
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
import web.common.module.db_operations.DataWebServiceOperations;
import web.common.module.db_operations.UserWebServiceOperations;

/**
 * @ClassName: UserLoginWebServiceMobileController
 * @Description: TODO(用户各类显示数据操作)
 * @author 康良涛
 * @date 2016年11月27日 上午11:40:57
 * 
 */

@WebServiceController("DataWebServiceMobile")
public class DataWebServiceMobileController {
	private static DataWebServiceMobileController DataWebServiceMobileController = new DataWebServiceMobileController();
	private String path;

	/**
	 * 
	 * @Title: login @author 康良涛 @Description: TODO() @param @param
	 *         jsonDataStr @param @return @param @throws Exception @return
	 *         String @throws
	 */

	// @Decrypt(handler = DecrptHandler.class)

	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getTrainingData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		ProcedureResult pr_titleName = DataWebServiceMobileController.getTrainingData_titleName_db_procedure();
		List<TrainingTitleNamePo> trainingTitleNamePoList = pr_titleName.getListAsObject(TrainingTitleNamePo.class);

		JSONArray result = new JSONArray();

		for (TrainingTitleNamePo trainingTitleNamePo : trainingTitleNamePoList) {

			String titleName = trainingTitleNamePo.getTitleName();

			ProcedureResult pr = DataWebServiceMobileController.getTrainingData_db_procedure(titleName);

			JSONObject obj = new JSONObject();

			obj.put("trianingList", pr.getListAsJSONArray());
			if (pr.getListAsJSONArray().length() != 0) {
				result.put(obj);
			}
		}

		WebServiceMobileMessage msg = new WebServiceMobileMessage();

		msg.put(Constants.LIST, result);
		return msg.toString();
	}

	public ProcedureResult getTrainingData_db_procedure(String titleName) throws Exception {

		return DataWebServiceOperations.getTrainingData_db_procedure(titleName);
	}

	public ProcedureResult getTrainingData_titleName_db_procedure() throws Exception {

		return DataWebServiceOperations.getTrainingData_titleName_db_procedure();
	}

	/**
	 * 
	* @Title: queryDietData 
	* @author 康良涛 
	* @Description: TODO(查询食品总数据) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryDietData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		ProcedureResult pr_titleName = DataWebServiceMobileController.getdietData_titleName_db_procedure();
		List<DietTitleNamePo> dietTitleNamePoList = pr_titleName.getListAsObject(DietTitleNamePo.class);

		JSONArray result = new JSONArray();

		for (DietTitleNamePo trainingTitleNamePo : dietTitleNamePoList) {

			String titleName = trainingTitleNamePo.getTitleName();

			ProcedureResult pr = DataWebServiceMobileController.queryDietData_db_procedure(titleName);

			JSONObject obj = new JSONObject();

			obj.put("dietDataPoList", pr.getListAsJSONArray());
			if (pr.getListAsJSONArray().length() != 0) {
				result.put(obj);
			}
		}

		WebServiceMobileMessage msg = new WebServiceMobileMessage();

		msg.put(Constants.LIST, result);
		return msg.toString();
	}

	public ProcedureResult queryDietData_db_procedure(String titleName) throws Exception {

		return DataWebServiceOperations.queryDietData_db_procedure(titleName);
	}

	public ProcedureResult getdietData_titleName_db_procedure() throws Exception {

		return DataWebServiceOperations.getdietData_titleName_db_procedure();
	}

	/**
	 * 
	* @Title: getDietData_foodMateria 
	* @author 康良涛 
	* @Description: TODO(查询食品原料) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getDietData_foodMateria(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String dietName = null;
		if (jsonData != null) {
			dietName = jsonData.getString("dietName");
		}

		ProcedureResult pr = DataWebServiceMobileController.getDietData_foodMateria_db_procedure(dietName);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getDietData_foodMateria_db_procedure(String dietName) throws Exception {

		return UserWebServiceOperations.getDietData_foodMateria_db_procedure(dietName);
	}

	/**
	 * 
	* @Title: getDietDetaile_step 
	* @author 康良涛 
	* @Description: TODO(查询食品制作步骤) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getDietDetaile_step(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String dietName = null;
		if (jsonData != null) {
			dietName = jsonData.getString("dietName");
		}

		ProcedureResult pr = DataWebServiceMobileController.getDietDetaile_step_db_procedure(dietName);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getDietDetaile_step_db_procedure(String dietName) throws Exception {

		return UserWebServiceOperations.getDietDetaile_step_db_procedure(dietName);
	}

	/**
	 * 
	* @Title: getDietData_with_dinneTime_dietType 
	* @author 康良涛 
	* @Description: TODO(根据就餐时间和就餐类型返回对应食品数据) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getDietData_with_dinneTime_dietType(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String dinneTime = null;
		String dietType = null;
		if (jsonData != null) {
			dinneTime = jsonData.getString("dinneTime");
			dietType = jsonData.getString("dietType");
		}

		ProcedureResult pr = DataWebServiceMobileController.getDietData_with_dinneTime_dietType_db_procedure(dinneTime,
				dietType);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getDietData_with_dinneTime_dietType_db_procedure(String dinneTime, String dietType)
			throws Exception {

		return UserWebServiceOperations.getDietData_with_dinneTime_dietType_db_procedure(dinneTime, dietType);
	}
	
	
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getMusicData_with_musicType(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String musicType = null;
		if (jsonData != null) {
			musicType = jsonData.getString("musicType");
		}

		ProcedureResult pr = DataWebServiceMobileController.getMusicData_with_musicType_db_procedure(musicType);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getMusicData_with_musicType_db_procedure(String musicType)
			throws Exception {

		return UserWebServiceOperations.getMusicData_with_musicType_db_procedure(musicType);
	}

}
