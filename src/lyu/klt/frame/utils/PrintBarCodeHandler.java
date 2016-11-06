package lyu.klt.frame.utils;


import javax.servlet.http.HttpServletRequest;

import lyu.klt.frame.database.core.ProcedureResult;

public class PrintBarCodeHandler {
	
	//CaseDetailRelatedObjectDataService caseDetailRelatedObjectDataService = new CaseDetailRelatedObjectDataService();
	
	public String printCaseRelatedObjectBarcode(HttpServletRequest request, String code, String count)
			throws Exception {
		try {
			String host = request.getRemoteAddr();

			StringBuffer buffer = new StringBuffer();
			String[] temp = { "" };
			String tempCode[] = (null != code && !"".equals(code)) ? code.split(",") : temp;
			for (int i = 0; i < tempCode.length; i++) {
				String printCode = tempCode[i];
				// 改打印信息为，案件编号
				String caseCode = "";
				// 经办单位
				String departmentName = "";
				// 持有人
				String holder = "";//涉案财物表没有这个字段(暂时不加信息)
				// 经办人
				String cbrName = "";//(暂时不加信息)
				
				String operatorId=null;
				String operatorAddress=null;
				//ProcedureResult pr = caseDetailRelatedObjectDataService.getCaseDetailRelatedObjectByObjectCode(printCode, operatorId, operatorAddress);
				ProcedureResult pr = new ProcedureResult();
				caseCode=pr.getRecordAsJSONObject().getString("caseCode");
				departmentName=pr.getRecordAsJSONObject().getString("departmentName");
				
				buffer.append(printCode + "-" + caseCode + "-" + departmentName + "-"
						+ holder + "-" + cbrName + ",");
			}

			System.out.println("打印信息======"
					+ buffer.substring(0, buffer.length() - 1));

			String team = buffer.substring(0, buffer.length() - 1);
			String sbs = new String(team.getBytes("GB2312"), "GB2312");

			PrintClient.printFromServer("127.0.0.1", sbs, count);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	public String printEquipmentBarcode(HttpServletRequest request,
			String equipmentCode, String identityIdName, String count) throws Exception{
		
		try {
			String host = request.getRemoteAddr();
			String code = equipmentCode;
			if(!"".equals(identityIdName)){
				code=identityIdName;
			}
			
			StringBuffer buffer = new StringBuffer();
			String[] temp = { "" };
			String tempCode[] = (null != code && !"".equals(code)) ? code.split(",") : temp;
			for (int i = 0; i < tempCode.length; i++) {
				String printCode = tempCode[i];
				String caseCode="";
				String departmentName="";
				String holder="";
				String cbrName="";
				//buffer.append(printCode + ",");
				buffer.append(printCode + "-" + caseCode + "-" + departmentName + "-"
						+ holder + "-" + cbrName + ",");
				
			}
			
			System.out.println("打印信息======"
					+ buffer.substring(0, buffer.length() - 1));
			String team = buffer.substring(0, buffer.length() - 1);
			String sbs = new String(team.getBytes("GB2312"), "GB2312");
			PrintClient.printFromServer("127.0.0.1", sbs, count);
			
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		
		
	}

}
