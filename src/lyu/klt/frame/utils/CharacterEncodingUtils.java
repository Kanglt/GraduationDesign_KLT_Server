/**
 * 
 */
package lyu.klt.frame.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jam 2016年4月1日 上午8:49:12
 *
 */
public class CharacterEncodingUtils {

	public static String toUTF8String(String s) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(baos,"UTF-8");
		writer.write(s);
		writer.flush();
		
		return new String(baos.toByteArray());
	}
}