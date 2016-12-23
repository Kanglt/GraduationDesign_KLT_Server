package web.common.module.carouselfigure;


import java.sql.Timestamp;

import org.json.JSONObject;

import lyu.klt.frame.controller.annotation.WebServiceController;
import lyu.klt.frame.controller.annotation.WebServiceMethod;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.method.parameter.annotation.Decrypt;
import lyu.klt.frame.database.core.ProcedureResult;
import web.common.handler.DecrptHandler;
import web.common.handler.WebServiceMobileMessage;

@WebServiceController("CarouselFigureMobile")
public class CarouselFigureWebServiceMobileController {

	private CarouselFigureDataService carouselFigureDataService = new CarouselFigureDataService();
	/**
	 * 
	 * @Title: getCarouselFigureListForMoblie   
	 * @Description: 查看轮播列表 上传一个类型
	 * @param: @param jsonDataStrString
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String      
	 * @throws
	 */
	//@Access(handler = MobileUserAccessHandler.class)
	@WebServiceMethod
	public String getCarouselFigureListForMoblie(@Decrypt(handler = DecrptHandler.class)String jsonDataStr)
			throws Exception {
		String queryModuleType="MAIN_SHOPPING"; //默认为主页的图片
		String operatorId =null;
		String operatorAddress ="";
		
		JSONObject jsonData = new JSONObject(jsonDataStr);
		 
		if(jsonData != null){
			 operatorId = jsonData.getString("operatorId");
			 queryModuleType = jsonData.getString("moduleType");
		}
		int page =0;
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
		String queryFileId = null;
		Integer querySeqNoStart = null;
		Integer querySeqNoEnd = null;

		ProcedureResult pr = this.carouselFigureDataService.getCarouselFigureList(
				queryId, queryFileId,queryModuleType,querySeqNoStart,querySeqNoEnd,queryUpdateBy,queryUpdateByAddress,
				queryUpdateTimeStart,queryUpdateTimeEnd,queryCreateBy,queryCreateByAddress,queryCreateTimeStart,queryCreateTimeEnd, 
				page, pageSize, operatorId, operatorAddress);

		WebServiceMobileMessage msg = new WebServiceMobileMessage();
		msg.put(Constants.LIST, pr.getListAsJSONArray());
		msg.setSizeInfo(page, pageSize, pr.getRecordSize());
		return msg.toString();
	}
}