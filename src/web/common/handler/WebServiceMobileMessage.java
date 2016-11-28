package web.common.handler;

import lyu.klt.frame.controller.handler.WebServiceMessage;
import lyu.klt.frame.controller.security.AESTool;

public class WebServiceMobileMessage extends WebServiceMessage{

	public WebServiceMobileMessage() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
			return AESTool.getInstance().encrypt(super.toString());
			//return super.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return super.toString();
		}
	}
	
}
