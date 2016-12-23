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
	
	

	public static ProcedureResult getUserInformation_db_procedure(String userId) throws Exception {

		Procedure procedure = new Procedure("userInformation");

		procedure.addParameterString("userId", userId);

		return procedure.exec();
	}

	public static ProcedureResult updateInformation_db_procedure(String userId, String userName, String userPassword,
			String userBirthday, String userPhoneNumble, String userSex, String userEmail,String userPhoto) throws Exception {

		Procedure procedure = new Procedure("updateUserInformation");

		procedure.addParameterString("userId", userId);
		procedure.addParameterString("userName", userName);
		procedure.addParameterString("userPassword", userPassword);
		procedure.addParameterString("userBirthday", userBirthday);
		procedure.addParameterString("userPhoneNumble", userPhoneNumble);
		procedure.addParameterString("userSex", userSex);
		procedure.addParameterString("userEmail", userEmail);
		procedure.addParameterString("userPhoto", userPhoto);

		return procedure.exec();
	}
}
