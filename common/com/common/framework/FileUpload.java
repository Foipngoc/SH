package com.common.framework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.common.utils.FileUtils;

public class FileUpload {

	/**
	 * 文件上传
	 * 
	 * 将多个文件上传并保存到指定目录中去，如果目录中有同名的文件，则自动重命名
	 * 
	 * 返回所有上传文件最终保存后的文件名，如果文件名重复，则返回重命名后的文件名
	 */
	public static List<String> upload(HttpServletRequest request,
			HttpServletResponse response, final String savedir)
			throws IOException {
		return upload(request, response, new FileSaveCallback() {

			@Override
			public String saveFile(MultipartFile file, String orifilename)
					throws IOException {
				// 判断同名文件是否存在
				if (FileUtils.ifFileExist(savedir + "/" + orifilename)) {
					orifilename = FileUtils.renameFileName(orifilename, ""
							+ new Date().getTime());
				}
				File localFile = new File(savedir + "/" + orifilename);
				file.transferTo(localFile);
				return orifilename;
			}
		});
	}

	/**
	 * 文件上传
	 * 
	 * 将多个文件上传, callback允许自行进行保存,不能为空
	 * 
	 * 返回所有上传文件最终保存后的文件名，如果文件名重复，则返回重命名后的文件名
	 */
	public static List<String> upload(HttpServletRequest request,
			HttpServletResponse response, FileSaveCallback callback)
			throws IOException {
		List<String> filenames = new ArrayList<>();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName != null && !myFileName.trim().equals("")) {
						myFileName = callback.saveFile(file, myFileName);
						if (myFileName != null) {
							filenames.add(myFileName);
						}
					}
				}
			}
		}
		return filenames;
	}
}
