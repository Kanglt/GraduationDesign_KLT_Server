package lyu.klt.frame.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 唯一id生成器<br/>
 * 使用方法：调用IdGenerator.getNewId()生成一个22位的唯一id<br/>
 * 注意：该生成器生成的id是大小写敏感的，即大小写不同的id是不同的id,<font
 * color="red">需要注意数据库表中的id字段必须设置为区分大小写</font><br/>
 * 计算方法：先通过java.util.UUID获得UUID，然后用base64将UUID压缩为22位<br/>
 * base64的表示符由"0-9"、"a-z"、"A-Z"、"_"和"-"组成，共64个<br/>
 * 
 * @author Jam
 */
public class IdGenerator {
	private static String[] radix;

	static {
		radix = new String[64];
		// 填充"0-9"
		for (int i = 0; i < 10; i++) {
			radix[i] = String.valueOf(i);
		}
		// 填充"a-z"
		for (int i = 10; i < 36; i++) {
			radix[i] = ((char) (87 + i)) + "";
		}
		// 填充"A-Z"
		for (int i = 36; i < 62; i++) {
			radix[i] = ((char) (29 + i)) + "";
		}
		// 填充"_"
		radix[62] = "_";
		// 填充"~"
		radix[63] = "-";
	}

	/**
	 * 将不带"-"符号的UUID转换为二进制字符串，结果返回的字符串长度固定为132位
	 * 
	 * @param sUUID
	 * @return
	 */
	private static String getBinString(String sUUID) {
		String binString = "";
		for (int i = 0; i < sUUID.length(); i++) {
			String sub = sUUID.substring(i, i + 1);
			int value = Integer.valueOf(sub, 16).intValue();
			String s = Integer.toBinaryString(value);
			int length = s.length();
			for (int j = 0; j < 4 - length; j++)
				s = "0" + s;
			binString = binString + s;
		}
		binString = "0000" + binString;
		return binString;
	}

	/**
	 * 将二进制字符串转换为六十四进制字符串，结果返回的字符串固定为22位
	 * 
	 * @param binString
	 * @return
	 */
	private static String getBase64String(String binString) {
		String resultString = "";
		int length = binString.length();
		for (int i = 0; i < length; i = i + 6) {
			String s = binString.substring(i, i + 6);
			int index = Integer.valueOf(s, 2);
			resultString = resultString + radix[index];
		}
		return resultString;
	}

	/**
	 * 生成一个22位的唯一id，该id是大小写敏感的，即即大小写不同的id是不同的id
	 * 
	 * @return
	 */
	public static String getNewId() {
		// 获取不带"-"符号的32位UUID
		String sUUID = UUID.randomUUID().toString().replaceAll("-", "");
		// 转换为二进制字符串
		String binString = getBinString(sUUID);
		// 从二进制字符串计算返回六十四进制字符串
		String base64String = getBase64String(binString);
		return base64String;
	}

	public static String randomId(int size) {
		String id = "";
		for (int i = 0; i < size; i++) {
			Random rand = new Random();
			int index = rand.nextInt(26) + 36;
			id = id + radix[index];
		}
		return id;
	}

	private static DateSeqNo SEQNO;

	static {
		SEQNO = new DateSeqNo();
	}

	synchronized public static String getDateSeqNo(String prifix)
			throws Exception {
		int maxLength = 20 - DateSeqNo.INDEX_LENGTH + 8;
		if (prifix.length() > maxLength)
			throw new Exception("前缀长度不可以超过" + maxLength);

		String seqNo = SEQNO.getNewSeqId(prifix);
		if (seqNo == null) {
			SEQNO = new DateSeqNo();
			seqNo = SEQNO.getNewSeqId(prifix);
			if (seqNo == null)
				throw new Exception("无法取得流水号");
		}
		return seqNo;
	}
}
