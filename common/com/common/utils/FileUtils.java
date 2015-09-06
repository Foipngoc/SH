package com.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
	/**
	 * 检查目录是否存在，不存在创建之
	 */
	public static void mkdir(String dirpath) {
		File dir = new File(dirpath);

		if (!dir.exists() && !dir.isDirectory())
			dir.mkdirs();
	}

	/**
	 * 检查同名文件是否存在
	 */
	public static boolean ifFileExist(String filepath) {
		File file = new File(filepath);
		return file.exists();
	}

	/**
	 * 检查文件名为filepath，并且md5为filemd5的文件是否存在
	 */
	public static boolean ifFileExist(String filepath, String filemd5) {
		if (filepath == null || filemd5 == null)
			return false;

		// 如果文件不存在，直接返回false
		if (!ifFileExist(filepath))
			return false;

		String md5 = FileMd5.getMd5ByFile(filepath);
		if (md5 != null
				&& filemd5.toLowerCase(Locale.getDefault()).equals(
						md5.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;
	}

	/**
	 * 获取文件扩展名
	 */
	public static String getFileExtension(String fileName) {
		if (fileName != null && fileName.lastIndexOf(".") != -1
				&& fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	/**
	 * 获取文件名，不带扩展名
	 */
	public static String getFileNoExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 重命名文件名，在文件名和扩展名中添加token
	 */
	public static String renameFileName(String filename, String token) {
		String name = getFileNoExtension(filename);
		String ext = getFileExtension(filename);

		if (ext != null && !ext.equals("")) {
			return name + token + "." + ext;
		} else {
			return name + token;
		}
	}

	/**
	 * 写入文件，返回文件名 ,写入失败，返回null
	 */
	public static String writeToFile(File file, String path) {
		// 写入文件
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			File uploadFile = new File(path);
			out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new File(path).getName();
	}

	/**
	 * 判断相同文件是否存在，如果同名但不同文件存在，进行重命名并保存 返回文件名 ,写入失败，返回null
	 */
	public static String writeToFile(File file, String filemd5, String path) {
		if (!FileUtils.ifFileExist(path, filemd5)) { // 不存在一样的文件
			if (FileUtils.ifFileExist(path)) { // 不同文件但是文件名相同，需要进行重命名
				path = FileUtils
						.renameFileName(path, "" + new Date().getTime());
			}
			return writeToFile(file, path);
		}
		return new File(path).getName();
	}

	/**
	 * 检查文件的md5是否为参数md5对应的值
	 */
	public static boolean checkFileMd5(String filepath, String md5) {
		if (filepath == null || md5 == null)
			return false;
		String filemd5 = FileMd5.getMd5ByFile(filepath);
		if (filemd5 != null
				&& filemd5.toLowerCase(Locale.getDefault()).equals(
						md5.toLowerCase(Locale.getDefault())))
			return true;
		return false;
	}
}
