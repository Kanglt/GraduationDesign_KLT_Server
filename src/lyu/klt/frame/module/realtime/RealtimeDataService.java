package lyu.klt.frame.module.realtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jam 2016年4月1日 下午5:07:53
 * 
 */
public class RealtimeDataService {

	private static List<String> MESSAGE_LIST;

	static {
		MESSAGE_LIST = new ArrayList<String>();
	}

	public void newMessage(String operatorId, String operatorAddress)
			throws Exception {
		MESSAGE_LIST.add(new Date().toString());
	}

	public String getMessage(String operatorId, String operatorAddress) {
		if (MESSAGE_LIST.size() <= 0)
			return null;
		String message = MESSAGE_LIST.get(0);
		MESSAGE_LIST.remove(0);
		return message;
	}

}
