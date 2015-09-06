package com.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * 属性加载器
 * 
 * @author DongJun
 * 
 */
public class PropertyLoader {
	private static Logger log = LogUtils.getLogger(PropertyLoader.class);

	/**
	 * 解析流中的属性
	 * 
	 * @param inputFile
	 * @return
	 */
	public static Properties getProperties(Reader reader) {
		Properties properties;
		properties = new Properties();
		try {
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			log.debug("属性文件解析失败" + e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 从绝对路径加载属性文件
	 * 
	 * @param filepath
	 * @param charsetName
	 * @return
	 */
	public static Properties getPropertiesFromAbsolutePath(String filepath,
			String charsetName) {
		try {
			Reader reader = new InputStreamReader(
					new FileInputStream(filepath), "UTF-8");
			return getProperties(reader);
		} catch (Exception e) {
			log.debug("属性文件(" + filepath + ")加载失败" + e.getMessage());
			return null;
		}
	}

	/**
	 * 从classpath加载属性文件
	 * 
	 * @param filepath
	 * @param charsetName
	 * @return
	 */
	public static Properties getPropertiesFromClassPath(String filepath,
			String charsetName) {
		try {
			Reader reader = new InputStreamReader(PropertyLoader.class
					.getClassLoader().getResourceAsStream(filepath), "UTF-8");
			return getProperties(reader);
		} catch (Exception e) {
			log.debug("属性文件(" + filepath + ")加载失败" + e.getMessage());
			return null;
		}
	}
}
