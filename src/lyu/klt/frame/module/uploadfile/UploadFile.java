package lyu.klt.frame.module.uploadfile;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UploadFile {

	private String id;
	private String originalFileName;
	private String ext;
	private boolean image;
	private String referenceTableName;
	private String referenceId;
	private String updateBy;
	private String updateByAddress;
	private Timestamp updateTime;
	private String createBy;
	private String createByAddress;
	private Timestamp createTime;
	private int recordVersion;

	public UploadFile() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateByAddress() {
		return updateByAddress;
	}

	public void setUpdateByAddress(String updateByAddress) {
		this.updateByAddress = updateByAddress;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateByAddress() {
		return createByAddress;
	}

	public void setCreateByAddress(String createByAddress) {
		this.createByAddress = createByAddress;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(int recordVersion) {
		this.recordVersion = recordVersion;
	}

	public String getRealFilePath(String baseFilePath) throws Exception {
		String subDir = new SimpleDateFormat("yyyyMMdd")
				.format(this.createTime);
		String fileName = String.format("%s/%s/%s.%s", baseFilePath, subDir,
				this.id, this.ext);
		return URLDecoder.decode(fileName, "UTF-8");
	}

}
