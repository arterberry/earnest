package com.framework.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtil class loads configuration property file.
 * 
 */
public class PropertiesUtil {

	private Properties prop = null;
	private String configPath = "/config.properties";

	public PropertiesUtil(String p_configPath) {
		configPath = p_configPath;
		init();
	}

	public PropertiesUtil() {
		init();
	}

	public synchronized void init() {
		if (prop == null) {
			loadProp();
		}
	}

	private synchronized void loadProp() {
		prop = new Properties();
		InputStream in = null;
		try {
			in = PropertiesUtil.class.getResourceAsStream(configPath);
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public synchronized void refresh() {
		loadProp();
	}

	/**
	 * Retrieves property from properties file.
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getProperty(String key) {
		if (prop == null) {
			return null;
		} else {
			return prop.getProperty(key);
		}
	}
}
