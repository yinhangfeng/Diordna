package com.example.yinhf.okhttptest;

import android.os.Bundle;
import android.util.Log;

import com.example.commonlibrary.BaseTestActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    private com.squareup.okhttp.OkHttpClient client2;
    private OkHttpClient client;
    private static final String TEST_BASE_URL = "http://172.18.255.57:3003/";//"http://192.168.7.105:3003/";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            HttpResponseCache.install(new File(getCacheDir(), "okhttpcache"), 100 * 1024 * 1024);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Cache cache = new Cache(new File(getCacheDir(), "okhttpcache"), 100 * 1024 * 1024);
        client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        com.squareup.okhttp.Cache cache2 = new com.squareup.okhttp.Cache(new File(getCacheDir(), "okhttpcache2"), 100 * 1024 * 1024);
        client2 = new com.squareup.okhttp.OkHttpClient();
        client2.setCache(cache2);
    }

    @Override
    protected void test1() throws Exception {
        Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().maxStale(20, TimeUnit.SECONDS).build())
                //.cacheControl(CacheControl.FORCE_CACHE)
                .url(TEST_BASE_URL + "demo/xhrtest")
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Response networkResponse = response.networkResponse();
                Response cacheResponse = response.cacheResponse();
                Log.d(TAG, "onResponse:\n" +
                        "\nresponse: " + response +
                        "\nnetworkResponse: " + networkResponse +
                        "\ncacheResponse: " + cacheResponse +
                        "\ncode: " + response.code() +
                        "\nheaders:\n" + response.headers() +
                        "\nbody.string:\n" + response.body().string());
            }
        });
    }

    @Override
    protected void test2() throws Exception {
        Cache cache = client.cache();
        if(cache != null) {
            Log.i(TAG, "cache:\ndirectory:" + cache.directory() + "\n" +
                    "hitCount:" + cache.hitCount() + "\n" +
                    "maxSize:" + cache.maxSize() + "\n" +
                    "networkCount:" + cache.networkCount() + "\n" +
                    "requestCount:" + cache.requestCount() + "\n" +
                    "size:" + cache.size() + "\n" +
                    "writeSuccessCount:" + cache.writeSuccessCount() + "\n" +
                    "writeAbortCount:" + cache.writeAbortCount());
        }
    }

    @Override
    protected void test3() throws Exception {
        Cache cache = client.cache();
        if(cache != null) {
            cache.evictAll();
        }
    }

    @Override
    protected void test4() throws Exception {
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                //.cacheControl(new CacheControl.Builder().maxStale(10, TimeUnit.SECONDS).build())
                //.cacheControl(CacheControl.FORCE_CACHE)
                .url(TEST_BASE_URL + "demo/xhrtest")
                .build();
        client2.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                Log.d(TAG, "onResponse:\n" +
                        "code: " + response.code() +
                        "\nheaders:\n" + response.headers() +
                        "\nbody.string:\n" + response.body().string());
            }
        });
    }

    @Override
    protected void test5() throws Exception {

    }
}
