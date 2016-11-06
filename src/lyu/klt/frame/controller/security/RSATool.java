package lyu.klt.frame.controller.security;

import lyu.klt.frame.security.Coder;
import lyu.klt.frame.security.DESCoder;
import lyu.klt.frame.security.RSACoder;

public class RSATool {

	private static String PUBLIC_KEY;
	private static String PRIVATE_KEY;

	private static String DES_KEY;

	static {
		PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwWmUPy4MM7YgFiZIoTaIVDfshM+jhpoVpvDXn"
				+ "ADUsjfrKAdCDGjbEAiNzXQC+fPjcc2AtS8w2ZDaqhX7UmWz0IsRna5u2AJw0T9a3YDic/coj6ZTP"
				+ "vYSooR/SBYVFDw6F/vfARsXrW7WZ0+AGSi1UcNIQ6mPzkFn5eBy/g38InQIDAQAB";

		PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALBaZQ/LgwztiAWJkihNohUN+yEz"
				+ "6OGmhWm8NecANSyN+soB0IMaNsQCI3NdAL58+NxzYC1LzDZkNqqFftSZbPQixGdrm7YAnDRP1rdg"
				+ "OJz9yiPplM+9hKihH9IFhUUPDoX+98BGxetbtZnT4AZKLVRw0hDqY/OQWfl4HL+DfwidAgMBAAEC"
				+ "gYA663iodk4qm5G+h+AVJxrn/evRhA5ilcgtM3yu2Rrq7+TCaSjJJJD3m99MQ95jxk0/Gi0f9Rsd"
				+ "cQ3ymvdtAHWt7XsdC1rYbUJe5p0TMKWLBiKPcK0pS3b/olK2aIYpGaDWuZKqF1+aSzjmWR61KuWr"
				+ "AOmrX4SuomhjRvkyGGxc4QJBANyjn/Q/BDj7d3KbYYEZY2sdeAZ0v29Lcob4vTVmQsli2AKPUjM9"
				+ "xcwoTYNFrvs97MeV0eR+5ekm3gMv2/MK3nsCQQDMnc2oR/q7/0BEQjxdGjmHqnR6UH2jbb2duetu"
				+ "+vrKvtmLT2nv4DxBmgAP0aOc0Kg3KfUGRKps2LAWl3sRpBXHAkEAmdXD6sodKZW7PL81hourE74i"
				+ "N24jI3gZTB4V3iuEmWE/dtly2KNTS/s1wDdPv4D3nFjLVWNc7ejZlqNB2f1UDQJBAJlcLShTyrnd"
				+ "Nm3ytwuQ/E2SlHqYmZJETjpJfwxPUGllYefoPZ5skr0Yj7NLjTWbrniqGdv420N5pYFJSBSQx0EC"
				+ "QHQeUZ2i+MidGD2XqUrsaGEnQtxJp3hyzgsCuBk6RFnJlzv9wAHK5u/QjFVHI7OR5CdlhQ2Snvkx"
				+ "lxKL7ON2Hao=";

		DES_KEY = "lLoIdi8OYWs=";
	}

	public static String encrypt(String input) throws Exception {
		byte[] inputData = input.getBytes();
		inputData = DESCoder.encrypt(inputData, DES_KEY);

		return Coder.encryptBASE64(inputData);
	}

	public static String decrypt(String input) throws Exception {
		byte[] outputData = DESCoder.decrypt(Coder.decryptBASE64(input),
				DES_KEY);
		return new String(outputData);

	}

	public static String decryptWithSign(String input) throws Exception {
		String[] parts = input.split(":");
		String sign = parts[0];
		String encodedString = parts[1];
		byte[] encodedData = Coder.decryptBASE64(encodedString);
		boolean status = RSACoder.verify(encodedData, PUBLIC_KEY, sign);
		if (!status)
			return null;

		byte[] decodedData = RSACoder.decryptByPublicKey(encodedData,
				PUBLIC_KEY);
		return new String(decodedData);
	}

	public static String encryptWithSign(String input) throws Exception {
		byte[] data = input.getBytes();
		byte[] encodedData = RSACoder.encryptByPrivateKey(data, PRIVATE_KEY);

		String encodedString = Coder.encryptBASE64(encodedData);
		String sign = RSACoder.sign(encodedData, PRIVATE_KEY);

		// linux下换行是：\n windows下换行是：\r\n mac下换行是：\r
		// 以下代码是为了兼容各个操作系统，因为cookie中不可以保存控制符
		sign = sign.replaceAll("\r", "");
		sign = sign.replaceAll("\n", "");
		encodedString = encodedString.replaceAll("\r", "");
		encodedString = encodedString.replaceAll("\n", "");

		return sign + ":" + encodedString;
	}

	public static void main(String[] args) throws Exception {
//		String user = "root";
//		String password = "root";
		String user = "sa";
		String password = "119406";
		String userEncrypted = RSATool.encrypt("user" + user).replaceAll("=",
				"_");
		String passwordEncrypted = RSATool.encrypt("password" + password)
				.replaceAll("=", "_");
		System.out.println(String.format("user=%spassword=%s", userEncrypted,
				passwordEncrypted));
	}
}
