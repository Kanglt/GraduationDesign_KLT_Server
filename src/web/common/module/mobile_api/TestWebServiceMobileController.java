/**     
*/
package web.common.module.mobile_api;


import java.sql.Timestamp;

import javax.jws.WebService;

import org.json.JSONException;
import org.json.JSONObject;


import lyu.klt.frame.controller.annotation.Access;
import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.MultiLanguage;
import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.controller.method.parameter.annotation.Decrypt;
import lyu.klt.frame.controller.security.AESTool;
import lyu.klt.frame.database.core.ProcedureResult;
import web.common.access.mobileuser.MobileUserAccessHandler;
import web.common.access.user.UserAccessHandler;
import web.common.handler.DecrptHandler;
import web.common.handler.WebServiceMobileMessage;
import web.common.module.db_operations.Test_db_operations;

/** 
* @ClassName: Test_mobile 
* @Description: TODO(移動端測試接口) 
* @author 康良涛 
* @date 2016年11月27日 上午11:40:57 
*  
*/

@WebServiceController("TestMobile")
public class TestWebServiceMobileController {
	private static TestWebServiceMobileController testWebServiceMobileController = new TestWebServiceMobileController();
	
	/**
	 * 
	* @Title: gettestListForMobile 
	* @author 康良涛 
	* @Description: TODO(测试移动端与服务器的连通) 
	* @param @param jsonDataStr
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	
	//@Decrypt(handler = DecrptHandler.class)

	@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getTestListForMobile(@Decrypt(handler = DecrptHandler.class)String jsonDataStr) throws Exception {

		JSONObject jsonData = new JSONObject(jsonDataStr);
		
		String departmentId = null;
		String userId = null;
		if(jsonData != null){
			userId = jsonData.getString("userId");
		}
		
		String queryName = null;
		String queryAddress = null;

		String operatorId = null;
		String operatorAddress = null;

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
		
		ProcedureResult pr = testWebServiceMobileController
				.getTest_db_procedure();

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		return msg.toString();
	}
	
	public ProcedureResult getTest_db_procedure() throws Exception {

		return Test_db_operations.test_db_procedure();
	}

	
	
}

