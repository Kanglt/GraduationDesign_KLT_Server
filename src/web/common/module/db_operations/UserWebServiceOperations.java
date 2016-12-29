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
 * @Description: TODO(用户操作)
 * @author 康良涛
 * @date 2016年11月27日 上午10:42:46
 * 
 */
public class UserWebServiceOperations {

	public static ProcedureResult getUserLogin_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("userInformation");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}
	
	public static ProcedureResult getDietData_foodMateria_db_procedure(String dietName) throws Exception {

		Procedure procedure = new Procedure("queryDietData_foodMateria");

		procedure.addParameterString("dietName", dietName);

		return procedure.exec();
	}
	
	public static ProcedureResult getDietDetaile_step_db_procedure(String dietName) throws Exception {

		Procedure procedure = new Procedure("queryDietDetaile_step");

		procedure.addParameterString("dietName", dietName);

		return procedure.exec();
	}

	public static ProcedureResult getUserInformation_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("userInformation");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}

	public static ProcedureResult updateInformation_db_procedure(String userId, String userName, String userPassword,
			String userBirthday,String userAge, String userPhoneNumble, String userSex, String userEmail,String userPhoto) throws Exception {

		Procedure procedure = new Procedure("updateUserInformation");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("userName", userName);
		procedure.addParameterString("userPassword", userPassword);
		procedure.addParameterString("userBirthday", userBirthday);
		procedure.addParameterString("userAge", userAge);
		procedure.addParameterString("userPhoneNumble", userPhoneNumble);
		procedure.addParameterString("userSex", userSex);
		procedure.addParameterString("userEmail", userEmail);
		procedure.addParameterString("userPhoto", userPhoto);

		return procedure.exec();
	}
	
	public static ProcedureResult getDietData_with_dinneTime_dietType_db_procedure(String dinneTime,String dietType) throws Exception {

		Procedure procedure = new Procedure("queryDietData_with_dinneTime_dietType");

		procedure.addParameterString("dinneTime", dinneTime);
		procedure.addParameterString("dietType", dietType);


		return procedure.exec();
	}
	
	public static ProcedureResult getMusicData_with_musicType_db_procedure(String musicType) throws Exception {

		Procedure procedure = new Procedure("queryMusicData");

		procedure.addParameterString("musicType", musicType);


		return procedure.exec();
	}
	
	public static ProcedureResult getTotalTraining_db_procedure() throws Exception {

		Procedure procedure = new Procedure("queryTotalTraining");



		return procedure.exec();
	}
	
	
	public static ProcedureResult addTraining_db_procedure(String userId,String category) throws Exception {

		Procedure procedure = new Procedure("addTraining");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("category", category);

		return procedure.exec();
	}
	public static ProcedureResult deleteUserTraining_db_procedure(String userId,String category) throws Exception {

		Procedure procedure = new Procedure("deleteUserTraining");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("category", category);

		return procedure.exec();
	}
	
	public static ProcedureResult addUserTrainingRecord_db_procedure(String userId,String trainingDate,String trainingCalories,String trainingTime,String category) throws Exception {

		Procedure procedure = new Procedure("addUserTrainingRecord");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("trainingDate", trainingDate);
		procedure.addParameterInteger("trainingCalories", Integer.parseInt(trainingCalories));
		procedure.addParameterInteger("trainingTime", Integer.parseInt(trainingTime));
		procedure.addParameterString("category", category);

		return procedure.exec();
	}
	
	public static ProcedureResult getUserBodyData_db_procedure(String userId,String dataType) throws Exception {

		Procedure procedure = new Procedure("queryUserBodyData");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("dataType", dataType);

		return procedure.exec();
	}
	
	public static ProcedureResult addUserBodyData_db_procedure(String userId,String dataType,String data,String height,String weight,String date) throws Exception {

		Procedure procedure = new Procedure("addUserBodyData");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("dataType", dataType);
		procedure.addParameterString("data", data);
		procedure.addParameterString("height", height);
		procedure.addParameterString("weight", weight);
		procedure.addParameterString("date", date);

		return procedure.exec();
	}
}
