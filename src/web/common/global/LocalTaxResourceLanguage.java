package web.common.global;

import java.util.HashMap;
import java.util.List;

import lyu.klt.frame.controller.annotation.ResourceLanguage;
import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.controller.global.IResourceLanguage;
import lyu.klt.frame.database.core.ProcedureResult;
import lyu.klt.frame.module.systemcode.SystemCodeDataService;


@ResourceLanguage
public class LocalTaxResourceLanguage implements IResourceLanguage{

	private SystemCodeDataService systemCodeDataService = new SystemCodeDataService();
	private static HashMap<String, String> mResource = null;
	
	@Override
	public String getResource(String code, String verifyText) throws Exception {
		if(mResource == null)
			initResource();
		if(mResource.containsKey(code))
			return mResource.get(code);
		else 
			return verifyText;
	}
	
	private void initResource() {
	
		int pageSize = Constants.COMMON_PAGE_SIZE;
		int page = 0;// 全页
		String queryValue = "";
		String queryParentId = "";
		String operatorId = "";
		String positionId = "";
		String operatorAddress = "";
		try {
			ProcedureResult pr = this.systemCodeDataService.getComponentSelectList(
					"ERROR_CODE", queryParentId, queryValue, page, pageSize,
					positionId, operatorId, operatorAddress);
			List<SystemCodePo> systemCodePos = pr.getListAsObject(SystemCodePo.class);
			mResource = new HashMap<>();
			for (int i = 0; i < systemCodePos.size(); i++) {
				mResource.put(systemCodePos.get(i).getCodeValue(), systemCodePos.get(i).getCodeName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
