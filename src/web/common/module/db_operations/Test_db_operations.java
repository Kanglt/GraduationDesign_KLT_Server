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
* @ClassName: Test_db_operations 
* @Description: TODO(数据库连接测试) 
* @author 康良涛 
* @date 2016年11月27日 上午10:42:46 
*  
*/
public class Test_db_operations {
	
	public String test_db(){	
	try{
		Connection conn=ConnectionAgent.getInstance().getConnection();// 获取数据库链接 
		String query="SELECT * FROM [user]";// 创造SQL语句 
		Statement stmt=conn.createStatement();// 执行SQL语句
		ResultSet rs=stmt.executeQuery(query);
		while(rs.next()){
			System.out.println(rs.getString("userName")+":"+rs.getString(2));
			//密码字段的编号从1开始，密码排第二位
		}
		System.out.println("查询数据成功");
		rs.close();
		stmt.close();
		conn.close();
		return rs.getString("userName")+":"+rs.getString(2);
	}catch(Exception e){
		e.printStackTrace();
	}
	return "false";
	}
	
	/**
	 * 
	* @Title: test_db_procedure 
	* @author 康良涛 
	* @Description: TODO(用存儲過程測試) 
	* @param @return
	* @return String
	* @throws
	 */
	public static  ProcedureResult test_db_procedure()throws Exception{	

		Procedure procedure = new Procedure("StuProc");

		//procedure.addParameterString("id", "");


		return procedure.exec();
	}
}
