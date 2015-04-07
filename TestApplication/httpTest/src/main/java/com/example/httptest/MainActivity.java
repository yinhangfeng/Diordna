package com.example.httptest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.commonlibrary.L;

import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

/**
 * setFixedLengthStreamingMode()
 * setChunkedStreamingMode()
 * HttpURLConnection.getOutputStream();返回对象为以下几个
 *  if (requestBodyOut != null) {
            // request body was already initialized by the predecessor HTTP engine
        } else if (fixedContentLength != -1) {
            writeRequestHeaders(fixedContentLength);
            //setFixedLengthStreamingMode() 必须事先知道要发送的数据大小,write直接写入socket无缓冲
            requestBodyOut = new FixedLengthOutputStream(requestOut, fixedContentLength);
        } else if (sendChunked) {
            writeRequestHeaders(-1);
            //setChunkedStreamingMode() 自带ByteArrayOutputStream 大小chunkLength的缓冲,保证每次写入socket数据>=min(chunkLength, 剩余长度)
            //但可能需要服务器支持,每段会写入头(16进制的本段大小\r\n)与尾(\r\n)，总传输数据量会略微增大
            requestBodyOut = new ChunkedOutputStream(requestOut, chunkLength);
        } else if (requestHeaders.getContentLength() != -1) {
            writeRequestHeaders(requestHeaders.getContentLength());
            requestBodyOut = new RetryableOutputStream(requestHeaders.getContentLength());
        } else {
        //write数据存入内部ByteArrayOutputStream 最后使用ByteArrayOutputStream.writeTo()一次写入socket
        //上传大文件可能造成OOM
            requestBodyOut = new RetryableOutputStream();
	}
	
	HttpURLConnection.disconnect()一定要调用
	
	libcore.net.http.HttpURLConnectionImpl
	https://android.googlesource.com/platform/libcore/+/android-cts-4.1_r2/luni/src/main/java/libcore/net/http
	
	com.squareup.okhttp.HttpResponseCache
	https://android.googlesource.com/platform/external/okhttp/+/android-4.4.4_r2.0.1/src/main/java/com/squareup/okhttp
 *
 */
public class MainActivity extends Activity {
	static final String TAG = "MainActivity";
	
	private static File appFile;
	static {
		appFile = new File(Environment.getExternalStorageDirectory(), "TEST/HttpTest");
		appFile.mkdirs();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		HttpResponseCache cache = HttpResponseCache.getInstalled();
	       if (cache != null) {
	           cache.flush();
	       }
	}

	public void test1(View v) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					HashMap<String, Object> param = new HashMap<String, Object>();
					param.put("aaa", "bbb");
					String result = HttpRequestUtils.post("http://www.baidu.com", param);
					Log.i(TAG, "result=" + result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}.execute();
	}

	public void test2(View v) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				Log.i(TAG, "downloadFile test");
				File downloadFile = new File(appFile, "abc.jpg");
				InputStream is = null;
				OutputStream os = null;
				HttpURLConnection conn = null;
				long start = SystemClock.elapsedRealtime();
				try {
					os = new FileOutputStream(downloadFile);
					URL url = new URL("http://c.hiphotos.baidu.com/image/pic/item/d000baa1cd11728b056c3843cbfcc3cec3fd2c09.jpg");
					conn = (HttpURLConnection) url.openConnection();
					Log.i(TAG, "conn=" + conn);
					conn.setUseCaches(true);
					conn.addRequestProperty("aaa", "bbb");
					//conn.addRequestProperty("Cache-Control", "no-cache");//强制不用缓存
					conn.addRequestProperty("Cache-Control", "max-age=0");
					//conn.addRequestProperty("Cache-Control", "only-if-cached");//只请求缓存，不请求网络
					//conn.setInstanceFollowRedirects(false);
					Log.i(TAG, "url=" + url);
					//Log.i(TAG, "=========request header============\n" + conn.getRequestProperties());
					conn.connect();
					try {
						Field httpEngineField = conn.getClass().getDeclaredField("httpEngine");
						httpEngineField.setAccessible(true);
						Object httpEngineObj = httpEngineField.get(conn);
						Field requestHeadersField = httpEngineObj.getClass().getDeclaredField("requestHeaders");
						requestHeadersField.setAccessible(true);
						Object requestHeadersObj = requestHeadersField.get(httpEngineObj);
						Field rawHeadersField = requestHeadersObj.getClass().getDeclaredField("headers");
						rawHeadersField.setAccessible(true);
						Object rawHeadersObj = rawHeadersField.get(requestHeadersObj);
						Method toMultimapMethod = rawHeadersObj.getClass().getDeclaredMethod("toMultimap");
						Log.i(TAG, "==========rawHeaders==========\n" + headerFieldsToString((Map<String, List<String>>) toMultimapMethod.invoke(rawHeadersObj)));
					} catch(Exception aaa) {
						Log.e(TAG, "aaa=" + Log.getStackTraceString(aaa));
					}
					Log.i(TAG, "=============\nconn.getURL()=" + conn.getURL());
					Log.i(TAG, "=========response header===========\n" + headerFieldsToString(conn.getHeaderFields()) + "\n========");
					
					HttpRequestUtils.logErrorMessageIfNecessary(conn);
					is = conn.getInputStream();
					byte[] buff = new byte[8192];
					int len;
					while((len = is.read(buff)) != -1) {
						os.write(buff, 0, len);
					}
				} catch (Exception e) {
					Log.e(TAG, "test2 e=" + e);
				} finally {
					if(is != null) {
						try {
							is.close();
						} catch(Exception e) {
							
						}
					}
					if(conn != null) {
						conn.disconnect();
					}
					if(os != null) {
						try {
							os.close();
						} catch(Exception e) {
							
						}
					}
					L.logTime(TAG, "downloadFile", start, SystemClock.elapsedRealtime());
				}
				return null;
			}
		}.execute();

	}
	
	private static String headerFieldsToString(Map<String, List<String>> headerFields) {
		return headerFields.toString().replace("], ", "],\n");
	}

	public void test3(View v) {
		HttpResponseCache cache = App.cache;
		Log.i(TAG, "cache=" + cache);
		Log.i(TAG, "HitCount=" + cache.getHitCount());
		Log.i(TAG, "NetworkCount=" + cache.getNetworkCount());
		Log.i(TAG, "RequestCount=" + cache.getRequestCount());
	}
}
