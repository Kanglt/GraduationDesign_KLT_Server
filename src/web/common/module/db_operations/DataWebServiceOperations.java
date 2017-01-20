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
}
