package com.model.appVersionCheck;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class SDFileUtils {

	public static String getSDPATH() {
		return Environment.getExternalStorageDirectory() + "/";
	}

	public static boolean ifhasSDCard() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	public static File creatSDFile(String fileName) throws IOException {
		if (ifhasSDCard()) {
			File file = new File(getSDPATH() + fileName);
			file.createNewFile();
			return file;
		} else {
			return null;
		}
	}

	public static File creatSDDir(String dirName) {
		if (ifhasSDCard()) {
			File dir = new File(getSDPATH() + dirName);
			dir.mkdir();
			return dir;
		} else {
			return null;
		}
	}

	public static boolean isFileOrDirExist(String fileName) {
		if (ifhasSDCard()) {
			File file = new File(getSDPATH() + fileName);
			return file.exists();
		} else {
			return false;
		}
	}
}
