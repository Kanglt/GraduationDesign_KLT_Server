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
public class DataWebServiceMobileController  {
	private static DataWebServiceMobileController DataWebServiceMobileController = new DataWebServiceMobileController();
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
	public String getTrainingData(@Decrypt(handler = DecrptHandler.class) String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);

		ProcedureResult pr_titleName = DataWebServiceMobileController.getTrainingData_titleName_db_procedure();
		List<TrainingTitleNamePo> trainingTitleNamePoList = pr_titleName
				.getListAsObject(TrainingTitleNamePo.class);
		
		JSONArray result = new JSONArray();
		
		for (TrainingTitleNamePo trainingTitleNamePo : trainingTitleNamePoList) {

			String titleName = trainingTitleNamePo.getTitleName();

			
			ProcedureResult pr = DataWebServiceMobileController.getTrainingData_db_procedure(titleName);

			JSONObject obj = new JSONObject();
			
			obj.put("trianingList", pr.getListAsJSONArray());
			if(pr.getListAsJSONArray().length()!=0){
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

	

	
	
}
