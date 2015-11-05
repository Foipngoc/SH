package com.common.utils;

import java.util.Date;
import java.util.Random;

public class RandomCode {
	private static String randomString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 图片上的字符串

	/**
	 * 生成验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String genRandomCode(int length) {
		return genRandomCode(randomString, length);
	}

	/**
	 * 使用种子生成验证码
	 * 
	 * @param seedstring
	 *            验证码种子
	 * @param length
	 * @return
	 */
	public static String genRandomCode(String seedstring, int length) {
		String code = "";
		Random random = new Random(new Date().getTime());
		for (int i = 0; i < length; i++) {
			code += String.valueOf(seedstring.charAt(random.nextInt(seedstring
					.length())));
		}
		return code;
	}
}
