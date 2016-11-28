
import java.sql.Timestamp;

import cn.fjzxdz.frame.controller.global.Constants;
import cn.fjzxdz.frame.database.core.Procedure;
import cn.fjzxdz.frame.database.core.ProcedureResult;


/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务
 * Java数据服务可能由调用多个存储过程数据服务组成
 */
public class {module_name}DataService {

	//Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult get{module_name}List(String queryId, {query_params} Integer page, Integer pageSize, String operatorId, String operatorAddress) throws Exception {

		return this.sp_{table_name_lowercase}_query(queryId, {query_params_name} page, pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult new{module_name}({management_params} String operatorId, String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_INSERT, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult delete{module_name}(String id, Integer recordVersion, String operatorId, String operatorAddress) throws Exception {

		{management_params_set_null}

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_DELETE, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult get{module_name}(String id, String operatorId, String operatorAddress) throws Exception {

		return this.sp_{table_name_lowercase}_read(id, operatorId, operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult update{module_name}(String id, {management_params} String operatorId, String operatorAddress, Integer recordVersion) throws Exception {

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_UPDATE, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}
	//Java数据服务 -end-

	//存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_{table_name_lowercase}_management(String action,String id, {management_params} String operatorId, String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure("sp_{table_name_lowercase}_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		{management_procedure_params_add}

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_{table_name_lowercase}_query(String queryId, {query_params} Integer page, Integer pageSize, String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_{table_name_lowercase}_query");

		procedure.addParameterString("queryId", queryId);

		{query_procedure_params_add}

		procedure.addParameterInteger("page", page);//0代表全页
		procedure.addParameterInteger("pageSize", Constants.COMMON_PAGE_SIZE);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	/**
	 * 单条记录查询存储过程
	 */
	private ProcedureResult sp_{table_name_lowercase}_read(String id, String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_{table_name_lowercase}_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	//存储过程数据服务 -end-

}
