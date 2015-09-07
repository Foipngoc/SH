package com.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 获得文件的md5值
 * 
 * @author DJ
 * 
 */
public class FileMd5 {

	public static String getMd5ByFile(InputStream in) {
		String value = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int length;
			byte[] buffer = new byte[10240];
			while ((length = in.read(buffer)) != -1) {
				md5.update(buffer, 0, length);
			}
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static String getMd5ByFile(FileInputStream in) {
		String value = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int length;
			byte[] buffer = new byte[10240];
			while ((length = in.read(buffer)) != -1) {
				md5.update(buffer, 0, length);
			}
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static String getMd5ByFile(File file) {
		try {
			return getMd5ByFile(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static String getMd5ByFile(String filename) {
		return getMd5ByFile(new File(filename));
	}
}
