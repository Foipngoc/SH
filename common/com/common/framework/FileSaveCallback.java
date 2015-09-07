package com.common.framework;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaveCallback {

	/**
	 * 保存文件，返回保存后的文件名，保存失败返回null
	 * 
	 * @param file
	 * @param orifilename
	 * @return
	 * @throws IOException
	 */
	public String saveFile(MultipartFile file, String orifilename)
			throws IOException;
}
