package lyu.klt.frame.utils;

import java.util.Date;


public class DateSeqNo {

	private String currentDay;
	private int index;

	public static final int INDEX_LENGTH = 4;

	public DateSeqNo() {
		super();
		this.currentDay = Utils.formatDateSeqNo(new Date());
		this.index = 1;
	}

	synchronized public String getNewSeqId(String prefix) {
		if (!Utils.formatDateSeqNo(new Date()).equals(this.currentDay)) {
			return null;
		} else {
			return prefix + this.currentDay + this.getIndex();
		}
	}

	synchronized private String getIndex() {
		String result = String.valueOf(this.index);
		int loop = INDEX_LENGTH - result.length();
		for (int i = 0; i < loop; i++)
			result = "0" + result;
		this.index++;
		return result;
	}
}
