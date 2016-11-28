
import java.sql.Timestamp;

import cn.fjzxdz.frame.controller.global.Constants;
import cn.fjzxdz.frame.database.core.Procedure;
import cn.fjzxdz.frame.database.core.ProcedureResult;


/**
 * �ṩ��ģ����������ݷ��񣬰���Java���ݷ���ʹ洢�������ݷ���
 * Java���ݷ�������ɵ��ö���洢�������ݷ������
 */
public class {module_name}DataService {

	//Java���ݷ��� -begin-
	/**
	 * ͨ���б��ѯ
	 */
	public ProcedureResult get{module_name}List(String queryId, {query_params} Integer page, Integer pageSize, String operatorId, String operatorAddress) throws Exception {

		return this.sp_{table_name_lowercase}_query(queryId, {query_params_name} page, pageSize, operatorId, operatorAddress);
	}

	/**
	 * ͨ������������¼
	 */
	public ProcedureResult new{module_name}({management_params} String operatorId, String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_INSERT, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}

	/**
	 * ͨ��ɾ��������¼
	 */
	public ProcedureResult delete{module_name}(String id, Integer recordVersion, String operatorId, String operatorAddress) throws Exception {

		{management_params_set_null}

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_DELETE, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}

	/**
	 * ͨ�õ�����¼��ѯ
	 */
	public ProcedureResult get{module_name}(String id, String operatorId, String operatorAddress) throws Exception {

		return this.sp_{table_name_lowercase}_read(id, operatorId, operatorAddress);
	}

	/**
	 * ͨ���޸ĵ�����¼
	 */
	public ProcedureResult update{module_name}(String id, {management_params} String operatorId, String operatorAddress, Integer recordVersion) throws Exception {

		return this.sp_{table_name_lowercase}_management(Procedure.ACTION_UPDATE, id, {management_params_name} operatorId, operatorAddress, recordVersion);
	}
	//Java���ݷ��� -end-

	//�洢�������ݷ��� -begin-
	/**
	 * ����ɾ���Ĵ洢����
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
	 * �б��ѯ�洢����
	 */
	private ProcedureResult sp_{table_name_lowercase}_query(String queryId, {query_params} Integer page, Integer pageSize, String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_{table_name_lowercase}_query");

		procedure.addParameterString("queryId", queryId);

		{query_procedure_params_add}

		procedure.addParameterInteger("page", page);//0����ȫҳ
		procedure.addParameterInteger("pageSize", Constants.COMMON_PAGE_SIZE);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	/**
	 * ������¼��ѯ�洢����
	 */
	private ProcedureResult sp_{table_name_lowercase}_read(String id, String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_{table_name_lowercase}_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	//�洢�������ݷ��� -end-

}
