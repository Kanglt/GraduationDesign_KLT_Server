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
	
	
	/**
	 * 
	* @Title: getTotalTraining 
	* @author 康良涛 
	* @Description: TODO(获取全部训练) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getTotalTraining(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		

		ProcedureResult pr = DataWebServiceMobileController.getTotalTraining_db_procedure();

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getTotalTraining_db_procedure()
			throws Exception {

		return UserWebServiceOperations.getTotalTraining_db_procedure();
	}
	
	/**
	 * 
	* @Title: addTraining 
	* @author 康良涛 
	* @Description: TODO(添加训练) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String addTraining(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String category = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
			category = jsonData.getString("category");
		}

		ProcedureResult pr = DataWebServiceMobileController.addTraining_db_procedure(userId,category);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult addTraining_db_procedure(String userId,String category)
			throws Exception {

		return UserWebServiceOperations.addTraining_db_procedure(userId,category);
	}
	
	
	
	
	/**
	 * 
	* @Title: queryUserTrainingData 
	* @author 康良涛 
	* @Description: TODO(查询用户已添加训练) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryUserTrainingData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		
		String userId = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
		}
		
		ProcedureResult pr_titleName = DataWebServiceMobileController.queryUserTrainingData_db_procedure(userId);
		List<TrainingCategoryPo> trainingCategoryPoList = pr_titleName.getListAsObject(TrainingCategoryPo.class);

		JSONArray result = new JSONArray();

		for (TrainingCategoryPo trainingCategoryPo : trainingCategoryPoList) {

			String category = trainingCategoryPo.getCategory();

			ProcedureResult pr = DataWebServiceMobileController.queryTrainingData_db_procedure(category);

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

	public ProcedureResult queryTrainingData_db_procedure(String category) throws Exception {

		return DataWebServiceOperations.getTrainingData_with_category_db_procedure(category);
	}

	public ProcedureResult queryUserTrainingData_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserTrainingData_db_procedure(userId);
	}
	
	/**
	 * 
	* @Title: deleteTraining 
	* @author 康良涛 
	* @Description: TODO(删除用户训练) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String deleteUserTraining(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String category = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
			category = jsonData.getString("category");
		}

		ProcedureResult pr = DataWebServiceMobileController.deleteUserTraining_db_procedure(userId,category);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult deleteUserTraining_db_procedure(String userId,String category)
			throws Exception {

		return UserWebServiceOperations.deleteUserTraining_db_procedure(userId,category);
	}
	
	
	/**
	 * 
	* @Title: queryUserTrainingTotalRecord 
	* @author 康良涛 
	* @Description: TODO(获取用户训练记录) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryUserTrainingTotalRecord(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		
		String userId = null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
		}
		
		ProcedureResult pr = DataWebServiceMobileController.queryUserTrainingTotalRecord_db_procedure(userId);

		


		WebServiceMobileMessage msg = new WebServiceMobileMessage();

		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult queryUserTrainingTotalRecord_db_procedure(String userId) throws Exception {

		return DataWebServiceOperations.queryUserTrainingTotalRecord_db_procedure(userId);
	}

	
	
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String addUserTrainingRecord(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String category = null;
		String trainingDate= null;
		String trainingCalories= null;
		String trainingTime= null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
			category = jsonData.getString("category");
			trainingDate= jsonData.getString("trainingDate");
			trainingCalories= jsonData.getString("trainingCalories");
			trainingTime= jsonData.getString("trainingTime");
		}

		ProcedureResult pr = DataWebServiceMobileController.addUserTrainingRecord_db_procedure(userId,trainingDate,trainingCalories,trainingTime,category);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult addUserTrainingRecord_db_procedure(String userId,String trainingDate,String trainingCalories,String trainingTime,String category)
			throws Exception {

		return UserWebServiceOperations.addUserTrainingRecord_db_procedure(userId,trainingDate,trainingCalories,trainingTime,category);
	}
	
	
	
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String queryRecommendedTraining(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		
	
		ProcedureResult pr_titleName = DataWebServiceMobileController.queryRecommendedTraining_db_procedure();
		List<TrainingCategoryPo> trainingCategoryPoList = pr_titleName.getListAsObject(TrainingCategoryPo.class);

		JSONArray result = new JSONArray();

		for (TrainingCategoryPo trainingCategoryPo : trainingCategoryPoList) {

			String category = trainingCategoryPo.getCategory();

			ProcedureResult pr = DataWebServiceMobileController.queryTrainingData_db_procedure(category);

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


	public ProcedureResult queryRecommendedTraining_db_procedure() throws Exception {

		return DataWebServiceOperations.queryRecommendedTraining_db_procedure();
	}
	

	/**
	 * 
	* @Title: getUserBodyData 
	* @author 康良涛 
	* @Description: TODO(查询用户身体数据) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getUserBodyData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String dataType = null;

		if (jsonData != null) {
			userId = jsonData.getString("userId");
			dataType = jsonData.getString("dataType");
		
		}

		ProcedureResult pr = DataWebServiceMobileController.getUserBodyData_db_procedure(userId,dataType);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}

	public ProcedureResult getUserBodyData_db_procedure(String userId,String dataType)
			throws Exception {

		return UserWebServiceOperations.getUserBodyData_db_procedure(userId,dataType);
	}
	
	
	/**
	 * 
	* @Title: addUserBodyData 
	* @author 康良涛 
	* @Description: TODO(添加用户身体信息记录) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String addUserBodyData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr)
			throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		String userId = null;
		String dataType = null;
		String data= null;
		String height= null;
		String weight= null;
		String date=null;
		if (jsonData != null) {
			userId = jsonData.getString("userId");
			dataType = jsonData.getString("dataType");
			data= jsonData.getString("data");
			height= jsonData.getString("height");
			weight= jsonData.getString("weight");
			date= jsonData.getString("date");
		}

		ProcedureResult pr = DataWebServiceMobileController.addUserBodyData_db_procedure(userId,dataType,data,height,weight,date);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	public ProcedureResult addUserBodyData_db_procedure(String userId,String dataType,String data,String height,String weight,String date)
			throws Exception {

		return UserWebServiceOperations.addUserBodyData_db_procedure(userId,dataType,data,height,weight,date);
	}
}
