package com.example.httptest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.util.Log;

/**
 * 简单的HTTP请求
 * @author 殷杭锋
 */
public class HttpRequestUtils {
	private static final String TAG = "HttpRequestUtils";
	private static final boolean DEBUG = true;

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int BUFF_SIZE = 8 * 1024;// 8K字符 16KB
	private static final int CONNECT_TIMEOUT = 20 * 1000;// 20S
	private static final int READ_TIMEOUT = 40 * 1000;// 40S

	/**
	 * 拼装get请求url
	 */
	public static String getUrl(String url, HashMap<String, Object> params) {
		if (params == null) {
			return url;
		}
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		if (!iterator.hasNext()) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		sb.append(url.indexOf('?') == -1 ? '?' : '&');
		Entry<String, Object> entry;
		try {
			for (;;) {
				entry = iterator.next();
				sb.append(entry.getKey()).append('=');
				if (entry.getValue() != null) {
					sb.append(URLEncoder.encode(entry.getValue().toString(),
							DEFAULT_CHARSET));
				}
				if (!iterator.hasNext()) {
					break;
				}
				sb.append('&');
			}
		} catch (Exception ig) {
		}
		return sb.toString();
	}

	/**
	 * 拼装以/分隔的请求url
	 */
	public static String getUrlSlash(String url, String... params) {
		if (params == null || params.length == 0) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.charAt(url.length() - 1) != '/') {
			sb.append('/');
		}
		try {
			for (int i = 0, len = params.length;; ++i) {
				sb.append(URLEncoder.encode(params[i], DEFAULT_CHARSET));
				if (i >= len) {
					break;
				}
				sb.append('/');
			}
		} catch (Exception ig) {
		}
		return sb.toString();
	}

	/**
	 * 获取Content-Type: application/x-www-form-urlencoded
	 * 的POST请求body
	 * urlencod 以&分隔
	 */
	public static byte[] getPostEntityData(HashMap<String, Object> params) {
		if (params == null) {
			return null;
		}
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		if (!iterator.hasNext()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		Entry<String, Object> entry;
		try {
			for (;;) {
				entry = iterator.next();
				sb.append(entry.getKey()).append('=');
				if (entry.getValue() != null) {
					sb.append(URLEncoder.encode(entry.getValue().toString(),
							DEFAULT_CHARSET));
				}
				if (!iterator.hasNext()) {
					break;
				}
				sb.append('&');
			}
		} catch (Exception ig) {
		}
		if(DEBUG) Log.i(TAG, "getPostEntityData body str =" + sb.toString());
		return sb.toString().getBytes(Charset.forName(DEFAULT_CHARSET));
	}

	public static String inputStreamToString(InputStream is, String CharSetName)
			throws IOException {
		InputStreamReader isr = new InputStreamReader(is, CharSetName);
		try {
			StringBuilder sb = new StringBuilder(BUFF_SIZE);
			char[] buffer = new char[BUFF_SIZE];
			int len;
			while ((len = isr.read(buffer)) != -1) {
				sb.append(buffer, 0, len);
			}
			return sb.toString();
		} finally {
			try {
				isr.close();
			} catch (Exception ig) {
			}
		}
	}
	
	/**
	 * Log错误消息
	 */
	public static void logErrorMessageIfNecessary(HttpURLConnection conn) throws IOException {
		if(conn.getResponseCode() == 200) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("logErrorMessageIfNecessary conn.getResponseCode=")
		.append(conn.getResponseCode())
		.append(" conn.getResponseMessage=")
		.append(conn.getResponseMessage())
		.append("\n=====================error body begin=====================\n")
		.append(inputStreamToString(conn.getErrorStream(), DEFAULT_CHARSET))
		.append("\n=====================error body end=====================\n");
		Log.e(TAG, sb.toString());
	}

	public static String get(String url, HashMap<String, Object> params)
			throws IOException {
		url = getUrl(url, params);
		return get(url);
	}

	/**
	 * 以斜杠分隔参数的形式进行get请求
	 */
	public static String get(String url, String... params) throws IOException {
		url = getUrlSlash(url, params);
		return get(url);
	}

	public static String get(String url) throws IOException {
		if(DEBUG) Log.i(TAG, "GET=" + url);
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		try {
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			if(DEBUG) logErrorMessageIfNecessary(conn);
			return inputStreamToString(conn.getInputStream(), DEFAULT_CHARSET);
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * 以Content-Type: application/x-www-form-urlencoded进行post请求
	 */
	public static String post(String url, HashMap<String, Object> params)
			throws IOException {
		if(DEBUG) Log.i(TAG, "post url=" + url + " params=" + params);
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		try {
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// POST请求不能使用缓存
			conn.setUseCaches(false);
			// 设置允许重定向
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Charset", DEFAULT_CHARSET);
			conn.connect();
			byte[] postData = getPostEntityData(params);
			if (postData != null) {
				OutputStream os = conn.getOutputStream();
				os.write(postData);
				os.flush();
				os.close();
			}
			if(DEBUG) logErrorMessageIfNecessary(conn);
			return inputStreamToString(conn.getInputStream(), DEFAULT_CHARSET);
		} finally {
			conn.disconnect();
		}
	}
	
	/**
	 * 自定义header body进行post请求
	 */
	public static String post(String url, HashMap<String, Object> headers, String body)
			throws IOException {
		if(DEBUG) Log.i(TAG, "post url=" + url + "\nheaders:\n" + headers + "\nbody:\n" + body);
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		try {
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// POST请求不能使用缓存
			conn.setUseCaches(false);
			// 设置允许重定向
			conn.setInstanceFollowRedirects(true);
			//设置请求头
			if(headers != null) {
				for(Entry<String, Object> entry : headers.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue().toString());
				}
			}
			conn.connect();
			//设置请求body
			if (body != null) {
				OutputStream os = conn.getOutputStream();
				os.write(body.getBytes(DEFAULT_CHARSET));
				os.flush();
				os.close();
			}
			if(DEBUG) logErrorMessageIfNecessary(conn);
			return inputStreamToString(conn.getInputStream(), DEFAULT_CHARSET);
		} finally {
			conn.disconnect();
		}
	}

}
