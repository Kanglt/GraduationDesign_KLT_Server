/**     
*/
package web.common.module.db_operations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import lyu.klt.frame.database.ConnectionAgent;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;

/**
 * @ClassName: UserWebServiceOperations
 * @Description: TODO(数据操作)
 * @author 康良涛
 * @date 2016年11月27日 上午10:42:46
 * 
 */
public class DataWebServiceOperations {

	public static ProcedureResult getTrainingData_db_procedure(String titleName) throws Exception {

		Procedure procedure = new Procedure("readTrainingData");

		procedure.addParameterString("titleName", titleName);

		return procedure.exec();
	}
	
	public static ProcedureResult getTrainingData_titleName_db_procedure() throws Exception {

		Procedure procedure = new Procedure("readTrainingData_titleName");

	

		return procedure.exec();
	}
	
	public static ProcedureResult queryDietData_db_procedure(String titleName) throws Exception {

		Procedure procedure = new Procedure("queryDietData");

		procedure.addParameterString("titleName", titleName);

		return procedure.exec();
	}
	
	public static ProcedureResult getdietData_titleName_db_procedure() throws Exception {

		Procedure procedure = new Procedure("readDietData_titleName");

	

		return procedure.exec();
	}
	
	public static ProcedureResult getTrainingData_with_category_db_procedure(String category) throws Exception {

		Procedure procedure = new Procedure("queryTrainingData_category");

		procedure.addParameterString("category", category);
		
		return procedure.exec();
	}
	
	public static ProcedureResult queryTrainingNum_db_procedure(String category,String userId) throws Exception {

		Procedure procedure = new Procedure("queryTrainingDataTrainingNum");

		procedure.addParameterString("category", category);
		procedure.addParameterString("userId", userId);
		
		return procedure.exec();
	}
	
	public static ProcedureResult queryUserTrainingData_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserTraining");
		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryUserTrainingTotalRecord_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserTraingingTotalRecord");
		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryRecommendedTraining_db_procedure() throws Exception {

		Procedure procedure = new Procedure("queryRecommendedTraining");

		return procedure.exec();
	}
	
	public static ProcedureResult queryUserFocusDynamic_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserFocusDynamic");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	public static ProcedureResult queryHotDynamic_db_procedure() throws Exception {

		Procedure procedure = new Procedure("queryHotDynamic");


		return procedure.exec();
	}
	
	public static ProcedureResult addUserFocus_db_procedure(String userId,String focusId) throws Exception {

		Procedure procedure = new Procedure("addUserFocus");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("focusId", focusId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryUserFocusNum_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserFocusNum");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	public static ProcedureResult queryUserFansNum_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserFansNum");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	public static ProcedureResult queryUserDynamicNum_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserDynamicNum");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryUserFocusInfo_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserFocusInfo");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryUserFansInfo_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("queryUserFansInfo");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryIsFocus_db_procedure(String userId,String focusId) throws Exception {

		Procedure procedure = new Procedure("queryIsFocus");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("focusId", focusId);

		return procedure.exec();
	}
	public static ProcedureResult deleteUserFocus_db_procedure(String userId,String focusId) throws Exception {

		Procedure procedure = new Procedure("deleteUserFocus");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("focusId", focusId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryPersonalInfo_db_procedure(String userId,String focusId) throws Exception {

		Procedure procedure = new Procedure("queryPersonalInfo");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("focusId", focusId);

		return procedure.exec();
	}
	
	public static ProcedureResult queryPersonalDynamic_db_procedure(String userId,int dynamicId) throws Exception {

		Procedure procedure = new Procedure("queryPersonalDynamic");

		procedure.addParameterString("userId", userId);
		procedure.addParameterInteger("dynamicId", dynamicId);

		return procedure.exec();
	}
	
	public static ProcedureResult updateUserDynamicThumbUpNum_db_procedure(String userId,int dynamicId,String type) throws Exception {

		Procedure procedure = new Procedure("updateUserDynamicThumbUpNum");
		
		int isThumbUp=0;
		
		procedure.addParameterString("userId", userId);
		procedure.addParameterInteger("id", dynamicId);
		procedure.addParameterString("type", type);
		procedure.addParameterInteger("isThumbUp", isThumbUp);

		return procedure.exec();
	}
	
	public static ProcedureResult queryDynamicComments_db_procedure(String dynamicId) throws Exception {

		Procedure procedure = new Procedure("queryDynamicComments");

		procedure.addParameterInteger("dynamicId", Integer.parseInt(dynamicId));

		return procedure.exec();
	}
	
	public static ProcedureResult addDynamicComments_db_procedure(String dynamicId,String commentsUserId,String commentsText,String replyId,String replyName,String commentsUserName) throws Exception {

		Procedure procedure = new Procedure("addDynamicComments");

		procedure.addParameterInteger("dynamicId", Integer.parseInt(dynamicId));
		procedure.addParameterString("commentsUserId", commentsUserId);
		procedure.addParameterString("commentsText", commentsText);
		procedure.addParameterString("replyId", replyId);
		procedure.addParameterString("replyName", replyName);
		procedure.addParameterString("commentsUserName", commentsUserName);
		

		return procedure.exec();
	}
	
	public static ProcedureResult deleteDynamicComments_db_procedure(String dynamicId) throws Exception {

		Procedure procedure = new Procedure("deleteDynamicComments");

		procedure.addParameterInteger("dynamicId", Integer.parseInt(dynamicId));

		return procedure.exec();
	}
	
	public static ProcedureResult querySystemVersionInfomation_db_procedure(String systemPlatform) throws Exception {

		Procedure procedure = new Procedure("querySystemVersionInfomation");

		procedure.addParameterString("systemPlatform", systemPlatform);

		return procedure.exec();
	}
}
