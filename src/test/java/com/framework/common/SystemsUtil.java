package com.framework.common;

import com.framework.common.TestDriverFactory;
import com.framework.common.PropertiesUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * SystemsUtil class supports verification of environments and available ports.
 * 
 */

public class SystemsUtil {
	private static String SERVER;
	private static String CONNECT_SERVER;
	private static int PORT = 80;
	private static int SOCKET = 80;
	private static int TIMEOUT = 10000;

	/**
	 * Use to verify readiness of specific TCP and UDP ports and servers.
	 * 
	 * @param int port
	 * @param String server
	 * @return boolean
	 */
	public synchronized boolean verifySystemReadiness(int port, String server)
			throws MalformedURLException, UnknownHostException,
			NoSuchAlgorithmException, KeyManagementException, IOException {
		boolean status = false;
		setURL(server, 1);
		setPort(port);
		if (isConnected() == true) {
			if ((isPortAvailable() == true) && (isServerReady() == true)) {
				status = true;
			}
		}
		return status;
	}

	/**
	 * Use to verify readiness of port 80 (default) and the server defined under
	 * configuration property file.
	 * 
	 * @param int port
	 * @param String server            
	 * @return boolean
	 */
	public synchronized boolean verifySystemReadiness()
			throws MalformedURLException, UnknownHostException,
			NoSuchAlgorithmException, KeyManagementException, IOException {
		boolean status = false;
		setURL(TestDriverFactory.createTestDriver().getTestUrl(), 2);
		if (isConnected() == true) {
			if ((isPortAvailable() == true) && (isServerReady() == true)) {
				status = true;
			}
		}
		return status;
	}

	/**
	 * Use to verify readiness of a specific TCP or UDP ports.
	 * 
	 * @param int port
	 * @return boolean
	 */
	public synchronized boolean verifyPortReadiness(int port)
			throws UnknownHostException, IOException {
		boolean status = false;
		setPort(port);
		if (isPortAvailable() == true)
			status = true;
		return status;
	}

	/**
	 * Use to verify readiness of a server.
	 * 
	 * @param String server
	 * @return boolean
	 */
	public synchronized boolean verifyServerReadiness(String server)
			throws UnknownHostException, NoSuchAlgorithmException,
			KeyManagementException, IOException {
		boolean status = false;
		setURL(server, 1);
		if (isConnected() == true) {
			if (isServerReady() == true) {
				status = true;
			}
		}
		return status;
	}

	/**
	 * Use to verify readiness of a server.
	 * 
	 * @return boolean
	 */
	public synchronized boolean verifyServerReadiness()
			throws UnknownHostException, NoSuchAlgorithmException,
			KeyManagementException, IOException {
		boolean status = false;
		setURL(TestDriverFactory.createTestDriver().getTestUrl(), 2);
		if (isConnected() == true) {
			if (isServerReady() == true) {
				status = true;
			}
		}
		return status;
	}

	private void setURL(String url, int option) throws MalformedURLException {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		String optionSet = null;
		if (option == 1) {
			optionSet = (String) propertiesUtil.getProperty(url + "Url");
		} else if (option == 2) {
			optionSet = url;
		}
		CONNECT_SERVER = optionSet;
		SERVER = scrubURL(optionSet, option);
	}

	private void setPort(int port) {
		PORT = port;
	}

	private String scrubURL(String url, int option) {
		int indexEnd = 0;
		String cleanedURL = null;
		if (option == 1) {
			indexEnd = url.toString().lastIndexOf(':');
			cleanedURL = url.substring(0, indexEnd);
		} else if (option == 2) {
			cleanedURL = url;
		}
		indexEnd = cleanedURL.toString().lastIndexOf('/');
		int indexLength = cleanedURL.length();
		cleanedURL = cleanedURL.substring(indexEnd + 1, indexLength);
		return cleanedURL;
	}

	private synchronized boolean isConnected() throws NoSuchAlgorithmException,
			KeyManagementException, IOException {
		boolean status = false;

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		URL url = new URL(CONNECT_SERVER);
		url.openConnection();
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setConnectTimeout(TIMEOUT);
		urlConn.connect();
		if (HttpURLConnection.HTTP_OK == urlConn.getResponseCode())
			status = true;
		return status;
	}

	private synchronized boolean isPortAvailable() throws UnknownHostException,
			IOException {
		boolean status = false;
		InetAddress localport = InetAddress.getByName("localhost");
		ServerSocket localserver = new ServerSocket(PORT, SOCKET, localport);
		System.out
				.println("INFO: Verified open port availability (DOMAIN/IP:PORT) >> "
						+ localserver.getLocalSocketAddress());
		if (localserver.getLocalSocketAddress() != null)
			status = true;
		return status;
	}

	private boolean isServerReady() throws UnknownHostException {
		boolean status = false;
		InetSocketAddress address = new InetSocketAddress(
				InetAddress.getByName(SERVER), PORT);
		System.out.println("INFO: Verified server availability (DOMAIN/IP) >> "
				+ address.getAddress());
		if (address.getAddress() != null)
			status = true;
		return status;
	}
}