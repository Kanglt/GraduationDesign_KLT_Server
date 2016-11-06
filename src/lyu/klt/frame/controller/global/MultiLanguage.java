package lyu.klt.frame.controller.global;

public class MultiLanguage {

	private static IResourceLanguage RESOURCE = null;

	public static void init(IResourceLanguage resource) throws Exception {
		RESOURCE = resource;
	}

	public static String getResource(String code, String verifyText)
			throws Exception {
		return RESOURCE.getResource(code, verifyText);
	}
}