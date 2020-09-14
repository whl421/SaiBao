package com.cqmi.controller.orderi.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {
	/**
	 * 读取 properties资源文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getPatameters() {
		String path = Configs.getProFilePath();
		InputStream in = null;
		Properties pros = new Properties();
		try {
			in = new FileInputStream(path);
			pros.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pros;
	}

	public static String getProFilePath() {
		String urlPath = "";
		String classPath = Configs.class.getResource("").getPath();
		if (classPath.startsWith("file:/")) {
			urlPath = classPath.substring(6, classPath.indexOf("WEB-INF")) + "WEB-INF/classes/tplat-config.properties";
		} else if (classPath.startsWith("/")) {
			urlPath = classPath.substring(1, classPath.indexOf("WEB-INF")) + "WEB-INF/classes/tplat-config.properties";
		} else {
			urlPath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "WEB-INF/classes/tplat-config.properties";
		}
		urlPath = urlPath.replaceAll("/", "\\\\");
		return urlPath;
	}
}
