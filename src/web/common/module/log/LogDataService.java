package web.common.module.log;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.ProcedureResult;
import lyu.klt.frame.utils.Utils;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class LogDataService {

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getLogList(String queryId, String queryFileName,
			Timestamp queryFileModifyTimeStart,
			Timestamp queryFileModifyTimeEnd, Integer queryFileSizeStart,
			Integer queryFileSizeEnd, String queryFileType,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		return this.getLogList(queryFileModifyTimeStart,
				queryFileModifyTimeEnd, page, pageSize);
	}

	// Java数据服务 -end-

	private ProcedureResult getLogList(Timestamp queryFileModifyTimeStart,
			Timestamp queryFileModifyTimeEnd, Integer page, Integer pageSize) {

		List<File> list = this.getFileList(queryFileModifyTimeStart,
				queryFileModifyTimeEnd);
		int recordSize = list.size();
		List<File> pageList = this.getPageFileList(list, page, pageSize);
		List<Map<String, String>> resultPageList = this
				.getResultPageList(pageList);

		ProcedureResult pr = new ProcedureResult();
		pr.setList(resultPageList);
		pr.setMsg("数据读取成功");
		pr.setRecordSize(recordSize);

		return pr;
	}

	private List<Map<String, String>> getResultPageList(List<File> pageList) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (File file : pageList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", file.getName());
			map.put("fileModifyTime",
					Utils.formatDatetime(new Date(file.lastModified())));
			map.put("fileSize", Utils.getFileSize(file.length()));
			String fileExtName = Utils.getFileNameExt(file.getName());
			if (fileExtName == null)
				fileExtName = "";

			map.put("fileType", fileExtName);

			resultList.add(map);
		}

		return resultList;
	}

	private List<File> getPageFileList(List<File> list, Integer page,
			Integer pageSize) {
		int startIndex = (page - 1) * pageSize;
		int endIndex = page * pageSize;

		List<File> resultList = new ArrayList<File>();
		for (int i = startIndex; i < endIndex; i++) {
			if (i < list.size()) {
				resultList.add(list.get(i));
			}
		}

		return resultList;
	}

	private List<File> getFileList(Timestamp queryFileModifyTimeStart,
			Timestamp queryFileModifyTimeEnd) {
		queryFileModifyTimeEnd = Utils.addOneDay(queryFileModifyTimeEnd);

		String logFileBaseDirectory = System
				.getProperty(Constants.LOG4J_LOG_DIR);
		String directory = String.format("%s/WEB-INF/logs",
				logFileBaseDirectory);

		File dir = new File(directory);
		List<File> list = new ArrayList<File>();
		for (File file : dir.listFiles()) {
			if (queryFileModifyTimeStart == null
					&& queryFileModifyTimeEnd == null)
				list.add(file);
			else if (queryFileModifyTimeStart != null
					&& file.lastModified() >= queryFileModifyTimeStart
							.getTime() && queryFileModifyTimeEnd == null)
				list.add(file);
			else if (queryFileModifyTimeStart == null
					&& queryFileModifyTimeEnd != null
					&& file.lastModified() < queryFileModifyTimeEnd.getTime())
				list.add(file);
			else if (queryFileModifyTimeStart != null
					&& file.lastModified() >= queryFileModifyTimeStart
							.getTime() && queryFileModifyTimeEnd != null
					&& file.lastModified() < queryFileModifyTimeEnd.getTime())
				list.add(file);
		}

		Collections.sort(list, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				long result = ((File) file1).lastModified()
						- ((File) file2).lastModified();
				if (result >= 0)
					return -1;
				else
					return 0;
			}
		});

		return list;
	}

}
