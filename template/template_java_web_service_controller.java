
import java.sql.Timestamp;

import web.common.access.user.UserAccessHandler;
import cn.fjzxdz.frame.controller.annotation.Access;
import cn.fjzxdz.frame.controller.annotation.WebServiceController;
import cn.fjzxdz.frame.controller.annotation.WebServiceMethod;
import cn.fjzxdz.frame.controller.context.ControllerContext;
import cn.fjzxdz.frame.controller.global.Constants;
import cn.fjzxdz.frame.controller.global.MultiLanguage;
import cn.fjzxdz.frame.controller.handler.WebServiceMessage;
import cn.fjzxdz.frame.controller.method.parameter.datatype.FormData;
import cn.fjzxdz.frame.database.core.ProcedureResult;

@WebServiceController("{module_name}")
public class {module_name}WebServiceController {

	private {module_name}DataService {module_name_lower_case_first_character}DataService = new {module_name}DataService();

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String get{module_name}List(FormData formData, Integer page)
			throws Exception {

		formData.log();
		
		{query_form_data_params}

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance().getOperatorAddress();

		Integer pageSize = Constants.COMMON_PAGE_SIZE;

		String queryId = null;
		String queryUpdateBy = null;
		String queryUpdateByAddress = null;
		Timestamp queryUpdateTimeStart = null;
		Timestamp queryUpdateTimeEnd = null;
		String queryCreateBy = null;
		String queryCreateByAddress = null;
		Timestamp queryCreateTimeStart = null;
		Timestamp queryCreateTimeEnd = null;

		ProcedureResult pr = this.{module_name_lower_case_first_character}DataService.get{module_name}List(queryId, {query_params_name} page, pageSize, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String get{module_name}(String id) throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance().getOperatorAddress();

		ProcedureResult pr = this.{module_name_lower_case_first_character}DataService.get{module_name}(id, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "读取成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String new{module_name}(FormData formData) throws Exception {

		formData.log();

		{management_form_data_params}

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance().getOperatorAddress();

		ProcedureResult pr = this.{module_name_lower_case_first_character}DataService.new{module_name}({management_params_name} operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String delete{module_name}(String id, Integer recordVersion)
			throws Exception {

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance().getOperatorAddress();

		this.{module_name_lower_case_first_character}DataService.delete{module_name}(id, recordVersion, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.setMessage(MultiLanguage.getResource("000000", "删除成功"));
		return msg.toString();
	}

	@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String update{module_name}(String id, Integer recordVersion,
			FormData formData) throws Exception {

		formData.log();

		{management_form_data_params}

		String operatorId = new UserAccessHandler().getAccessUser().getId();
		String operatorAddress = ControllerContext.getCurrentInstance().getOperatorAddress();

		ProcedureResult pr = this.{module_name_lower_case_first_character}DataService.update{module_name}(id, {management_params_name} operatorId, operatorAddress, recordVersion);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.RECORD, pr.getRecordAsJSONObject());
		msg.setMessage(MultiLanguage.getResource("000000", "修改成功"));
		return msg.toString();
	}
}