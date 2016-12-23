/**     
*/
package web.common.module.mobile_api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lyu.klt.frame.controller.context.ControllerContext;
import lyu.klt.frame.utils.Utils;
import sun.misc.BASE64Decoder;

/**
 * @ClassName: UserPhotoUpdateDispatcher
 * @Description: TODO(用户头像上传)
 * @author 康良涛
 * @date 2016年12月20日 下午10:55:26
 * 
 */
public class UserPhotoUpdateDispatcher extends HttpServlet {

	private static Log log = LogFactory.getLog(UserPhotoUpdateDispatcher.class);


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(req);
		context.setResponse(resp);

		// // 文件存放位置：服务目录下的upload（）
		// String realPath = getServletContext().getRealPath("upload");
		// File dir = new File(realPath);
		// if (!dir.exists()) {
		// dir.mkdir();
		// }
		// System.out.println("temp=" + realPath);
		// String loadpath =getServletContext().getRealPath("image/user_photo");
		// // 上传文件存放目录
		// File dir2 = new File(loadpath);
		// if (!dir2.exists()) {
		// dir2.mkdir();
		// }
		// System.out.println("loadpath=" + loadpath);

		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		String photo = req.getParameter("photo");
		String photoName = req.getParameter("photoName");

		// 文件存放位置：服务目录下的upload（）
		String realPath = getServletContext().getRealPath("upload");
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		System.out.println("temp=" + realPath);
		String loadpath = getServletContext().getRealPath("image/user_photo"); // 上传文件存放目录
		File dir2 = new File(loadpath);
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		System.out.println("loadpath=" + loadpath);

		try {

			// 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密
			byte[] photoimg = new BASE64Decoder().decodeBuffer(photo);
			for (int i = 0; i < photoimg.length; ++i) {
				if (photoimg[i] < 0) {
					// 调整异常数据
					photoimg[i] += 256;
				}
			}
			


			// byte[] photoimg =
			// Base64.decode(photo);//此处不能用Base64.decode（）方法解密，我调试时用此方法每次解密出的数据都比原数据大
			// 所以用上面的函数进行解密，在网上直接拷贝的，花了好几个小时才找到这个错误（菜鸟不容易啊）
			System.out.println("图片的大小：" + photoimg.length);

			FileOutputStream out = new FileOutputStream(loadpath+"/"+photoName);

			out.write(photoimg);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);

		ControllerContext context = ControllerContext.getCurrentInstance();
		context.setRequest(req);
		context.setResponse(resp);

	}

}
