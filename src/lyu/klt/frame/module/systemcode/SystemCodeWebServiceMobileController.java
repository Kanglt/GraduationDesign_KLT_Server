package lyu.klt.frame.module.systemcode;


import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.database.core.ProcedureResult;


@WebServiceController("SystemCodeMobile")
public class SystemCodeWebServiceMobileController {

	private SystemCodeDataService systemCodeDataService = new SystemCodeDataService();

	/**
	 * @Title:  getComponentSelectList
	 * @Description:    TODO(获取选择框数据)    
	 * @CreateBy: 陈学博
	 * @CreateTime: 2016-7-27 上午9:29:55
	 * @UpdateBy: 陈学博 
	 * @UpdateTime:  2016-7-27 上午9:29:55    
	 * @param selectType
	 * @param queryParentId
	 * @param queryValue
	 * @param page
	 * @return
	 * @throws Exception  String 
	 * @throws 
	 */ 
	//@Access(handler = UserAccessHandler.class)
	@WebServiceMethod
	public String getComponentSelectList(String jsonDataStr) throws Exception {
		JSONObject jsonData = new JSONObject(jsonDataStr);
		String selectType = jsonData.getString("selectType");
		String queryParentId = "";
		String queryValue = "";
		Integer page = 0;
		
		String operatorId = "";
		String positionId = "";
		String operatorAddress = "";
		if(jsonData.has("queryParentId"))
			queryParentId = jsonData.getString("queryParentId");
		if(jsonData.has("queryValue"))
			queryValue = jsonData.getString("queryValue");
		if(jsonData.has("page"))
			page = jsonData.getInt("page");
		if(jsonData.has("operatorId"))
			operatorId = jsonData.getString("operatorId");
		if(jsonData.has("positionId"))
			positionId = jsonData.getString("positionId");
		
		/*UserAccessHandler handler = new UserAccessHandler();
		String operatorId = handler.getAccessInfo().getUser().getId();
		String positionId = handler.getAccessInfo().getPosition().getId();
		String operatorAddress = ControllerContext.getCurrentInstance()
				.getOperatorAddress();*/

		return this.getComponentSelectList(selectType, queryParentId,
				queryValue, page, positionId, operatorId, operatorAddress);
	}

	private String getComponentSelectList(String selectType,
			String queryParentId, String queryValue, Integer page,
			String positionId, String operatorId, String operatorAddress)
			throws Exception {

		int pageSize = Constants.COMMON_PAGE_SIZE;

		ProcedureResult pr = this.systemCodeDataService.getComponentSelectList(
				selectType, queryParentId, queryValue, page, pageSize,
				positionId, operatorId, operatorAddress);

		WebServiceMessage msg = new WebServiceMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		msg.setMessage(MultiLanguage.getResource("100056", "获取数据成功"));
		return msg.toString();
	}
}