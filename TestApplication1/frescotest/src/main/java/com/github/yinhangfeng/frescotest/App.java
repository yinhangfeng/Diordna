package com.github.yinhangfeng.frescotest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import okhttp3.OkHttpClient;

/**
 * Created by yhf on 2015/8/9.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, okHttpClient).build();

        Fresco.initialize(this, config);
    }
}
