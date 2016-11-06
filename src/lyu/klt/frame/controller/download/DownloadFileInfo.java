package lyu.klt.frame.controller.download;

import java.io.File;

public class DownloadFileInfo {
	private String fileName;
	private File file;

	public DownloadFileInfo(String fileName, File file) {
		super();
		this.fileName = fileName;
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
