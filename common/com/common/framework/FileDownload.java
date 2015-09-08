package com.common.framework;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {
	/**
	 * 中文文件名编码，解决常用浏览器下载时无法解决获得下载文件名中文问题
	 * 
	 * @param filename
	 * @return
	 */
	public static String filenameEncode(HttpServletRequest request,
			String filename) {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		String rtn = "";
		try {
			String new_filename = URLEncoder.encode(filename, "UTF8");

			// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
			rtn = "filename=\"" + new_filename + "\"";

			if (userAgent != null) {
				userAgent = userAgent.toLowerCase();
				// IE浏览器，只能采用URLEncoder编码
				if (userAgent.indexOf("msie") != -1) {
					rtn = "filename=\"" + new_filename + "\"";
				}
				// Opera浏览器只能采用filename*
				else if (userAgent.indexOf("opera") != -1) {
					rtn = "filename*=UTF-8''" + new_filename;
				}
				// Safari浏览器，只能采用ISO编码的中文输出
				else if (userAgent.indexOf("safari") != -1) {

					rtn = "filename=\""
							+ new String(filename.getBytes("UTF-8"),
									"ISO8859-1") + "\"";
				}
				// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
				else if (userAgent.indexOf("applewebkit") != -1) {

					rtn = "filename=\"" + new_filename + "\"";
				}
				// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
				else if (userAgent.indexOf("mozilla") != -1) {
					rtn = "filename*=UTF-8''" + new_filename;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public static void download(String filepath, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		File file = new File(filepath);
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;"
				+ filenameEncode(request, file.getName())); // 转码之后下载的文件不会出现中文乱码
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream;charset=UTF-8");
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			fos = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int readlen = 0;

			while ((readlen = fis.read(buffer, 0, 1024)) > 0) {
				fos.write(buffer, 0, readlen);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}
}
