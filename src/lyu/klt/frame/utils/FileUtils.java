/**
 * 
 */
package lyu.klt.frame.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Jam 2016年3月31日 上午11:30:16
 * 
 */
public class FileUtils {

	public static void saveFile(String directory, String fileName, byte[] data)
			throws Exception {
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();

		String filePath = String.format("%s/%s", directory, fileName);
		File file = new File(filePath);

		OutputStream os = new FileOutputStream(file);
		try {
			os.write(data);
		} finally {
			if (os != null) {
				os.flush();
				os.close();
			}
		}
	}
}