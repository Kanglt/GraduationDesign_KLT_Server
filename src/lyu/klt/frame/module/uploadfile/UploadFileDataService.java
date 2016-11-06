package lyu.klt.frame.module.uploadfile;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import lyu.klt.frame.controller.global.Constants;
import lyu.klt.frame.database.core.Procedure;
import lyu.klt.frame.database.core.ProcedureResult;
import lyu.klt.frame.utils.Utils;

/**
 * 提供该模块的所有数据服务，包括Java数据服务和存储过程数据服务 Java数据服务可能由调用多个存储过程数据服务组成
 */
public class UploadFileDataService {

	public static String BASE_FILE_PATH = UploadFileDataService.class
			.getResource("/").getPath() + "../uploadFiles";

	public ProcedureResult saveFile(String fileName, File tempFile,
			String operatorId, String operatorAddress) throws Exception {

		String originalFileName = this.getFileName(fileName);
		String ext = this.getFileExtName(fileName);
		boolean image = this.isImage(tempFile);
		String referenceTableName = "";
		String referenceId = "";

		ProcedureResult pr = this.newUploadFile(originalFileName, ext, image,
				referenceTableName, referenceId, operatorId, operatorAddress);

		String fileDirectory = this.getFileDirectory();
		File dir = new File(fileDirectory);
		if (!dir.exists())
			dir.mkdirs();

		UploadFile uploadFile = pr.getRecordAsObject(UploadFile.class);
		String realFilePath = uploadFile.getRealFilePath(BASE_FILE_PATH);

		Utils.writeFile(realFilePath, tempFile);

		return pr;
	}

	private String getFileName(String fileName) {
		File f = new File(fileName);
		return f.getName();
	}

	private String getFileExtName(String fileName) {
		File f = new File(fileName);
		String shortName = f.getName();

		String[] parts = shortName.split("\\.");
		if (parts.length < 2)
			return "";
		else
			return parts[1];
	}

	private boolean isImage(File file) throws Exception {
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
		if (iter.hasNext()) {
			return true;
		}
		return false;
	}

	private String getFileDirectory() throws Exception {
		String directory = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return URLDecoder.decode(BASE_FILE_PATH + "/" + directory, "UTF-8");
	}

	// Java数据服务 -begin-
	/**
	 * 通用列表查询
	 */
	public ProcedureResult getUploadFileList(String queryId,
			String queryOriginalFileName, String queryExt, String queryImage,
			String queryReferenceTableName, String queryReferenceId,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		return this
				.sp_fjzx_frame_upload_file_query(queryId,
						queryOriginalFileName, queryExt, queryImage,
						queryReferenceTableName, queryReferenceId,
						queryUpdateBy, queryUpdateByAddress,
						queryUpdateTimeStart, queryUpdateTimeEnd,
						queryCreateBy, queryCreateByAddress,
						queryCreateTimeStart, queryCreateTimeEnd, page,
						pageSize, operatorId, operatorAddress);
	}

	/**
	 * 通用新增单条记录
	 */
	public ProcedureResult newUploadFile(String originalFileName, String ext,
			Boolean image, String referenceTableName, String referenceId,
			String operatorId, String operatorAddress) throws Exception {

		String id = null;
		Integer recordVersion = null;

		return this.sp_fjzx_frame_upload_file_management(
				Procedure.ACTION_INSERT, id, originalFileName, ext, image,
				referenceTableName, referenceId, operatorId, operatorAddress,
				recordVersion);
	}

	/**
	 * 通用删除单条记录
	 */
	public ProcedureResult deleteUploadFile(String id, Integer recordVersion,
			String operatorId, String operatorAddress) throws Exception {

		String originalFileName = null;
		String ext = null;
		Boolean image = null;
		String referenceTableName = null;
		String referenceId = null;

		return this.sp_fjzx_frame_upload_file_management(
				Procedure.ACTION_DELETE, id, originalFileName, ext, image,
				referenceTableName, referenceId, operatorId, operatorAddress,
				recordVersion);
	}

	/**
	 * 通用单条记录查询
	 */
	public ProcedureResult getUploadFile(String id, String operatorId,
			String operatorAddress) throws Exception {

		return this.sp_fjzx_frame_upload_file_read(id, operatorId,
				operatorAddress);
	}

	/**
	 * 通用修改单条记录
	 */
	public ProcedureResult updateUploadFile(String id, String originalFileName,
			String ext, Boolean image, String referenceTableName,
			String referenceId, String operatorId, String operatorAddress,
			Integer recordVersion) throws Exception {

		return this.sp_fjzx_frame_upload_file_management(
				Procedure.ACTION_UPDATE, id, originalFileName, ext, image,
				referenceTableName, referenceId, operatorId, operatorAddress,
				recordVersion);
	}

	// Java数据服务 -end-

	// 存储过程数据服务 -begin-
	/**
	 * 增、删、改存储过程
	 */
	private ProcedureResult sp_fjzx_frame_upload_file_management(String action,
			String id, String originalFileName, String ext, Boolean image,
			String referenceTableName, String referenceId, String operatorId,
			String operatorAddress, Integer recordVersion) throws Exception {

		Procedure procedure = new Procedure(
				"sp_fjzx_frame_upload_file_management");

		procedure.addParameterString("action", action);
		procedure.addParameterString("id", id);

		procedure.addParameterString("originalFileName", originalFileName);
		procedure.addParameterString("ext", ext);
		procedure.addParameterBoolean("image", image);
		procedure.addParameterString("referenceTableName", referenceTableName);
		procedure.addParameterString("referenceId", referenceId);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);
		procedure.addParameterInteger("recordVersion", recordVersion);

		return procedure.exec();
	}

	/**
	 * 列表查询存储过程
	 */
	private ProcedureResult sp_fjzx_frame_upload_file_query(String queryId,
			String queryOriginalFileName, String queryExt, String queryImage,
			String queryReferenceTableName, String queryReferenceId,
			String queryUpdateBy, String queryUpdateByAddress,
			Timestamp queryUpdateTimeStart, Timestamp queryUpdateTimeEnd,
			String queryCreateBy, String queryCreateByAddress,
			Timestamp queryCreateTimeStart, Timestamp queryCreateTimeEnd,
			Integer page, Integer pageSize, String operatorId,
			String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_fjzx_frame_upload_file_query");

		procedure.addParameterString("queryId", queryId);

		procedure.addParameterString("queryOriginalFileName",
				queryOriginalFileName);
		procedure.addParameterString("queryExt", queryExt);
		procedure.addParameterString("queryImage", queryImage);
		procedure.addParameterString("queryReferenceTableName",
				queryReferenceTableName);
		procedure.addParameterString("queryReferenceId", queryReferenceId);
		procedure.addParameterString("queryUpdateBy", queryUpdateBy);
		procedure.addParameterString("queryUpdateByAddress",
				queryUpdateByAddress);
		procedure.addParameterTimestamp("queryUpdateTimeStart",
				queryUpdateTimeStart);
		procedure.addParameterTimestamp("queryUpdateTimeEnd",
				queryUpdateTimeEnd);
		procedure.addParameterString("queryCreateBy", queryCreateBy);
		procedure.addParameterString("queryCreateByAddress",
				queryCreateByAddress);
		procedure.addParameterTimestamp("queryCreateTimeStart",
				queryCreateTimeStart);
		procedure.addParameterTimestamp("queryCreateTimeEnd",
				queryCreateTimeEnd);

		procedure.addParameterInteger("page", page);// 0代表全页
		procedure.addParameterInteger("pageSize", Constants.COMMON_PAGE_SIZE);

		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}

	/**
	 * 单条记录查询存储过程
	 */
	private ProcedureResult sp_fjzx_frame_upload_file_read(String id,
			String operatorId, String operatorAddress) throws Exception {

		Procedure procedure = new Procedure("sp_fjzx_frame_upload_file_read");

		procedure.addParameterString("id", id);
		procedure.addParameterString("operatorId", operatorId);
		procedure.addParameterString("operatorAddress", operatorAddress);

		return procedure.exec();
	}
	// 存储过程数据服务 -end-

}
