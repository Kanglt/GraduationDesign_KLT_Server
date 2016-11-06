package web.common.handler;

import lyu.klt.frame.controller.method.parameter.annotation.IDecryptHandler;
import lyu.klt.frame.controller.security.AESTool;

public class DecrptHandler implements IDecryptHandler{

	@Override
	public String decrypt(String encryptedText) throws Exception {
		//解密数据
		return AESTool.getInstance().decrypt(encryptedText);
	}

}
