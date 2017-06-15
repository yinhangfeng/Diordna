package com.github.yinhangfeng.frescotest;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        draweeView = (SimpleDraweeView) findViewById(R.id.avatar);

        Uri uri = Uri.parse("http://172.18.255.71:9000/test/map.jpg");

        draweeView.setImageURI(uri);

//        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
//        // 默认setUri 不会按照控件大小缩放图片 所以加载过大图片会失败
//        //controller.setUri(uri);
//        controllerBuilder.setImageRequest(ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new ResizeOptions(2500, 2000)).build());
//        controllerBuilder.setOldController(draweeView.getController());
//        controllerBuilder.setControllerListener(new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onSubmit(String id, Object callerContext) {
//                super.onSubmit(id, callerContext);
//                Log.i(TAG, "onSubmit: ");
//            }
//
//            @Override
//            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                super.onFinalImageSet(id, imageInfo, animatable);
//                if (imageInfo == null) {
//                    return;
//                }
//                Log.i(TAG, "onFinalImageSet: width:" + imageInfo.getWidth() + " height:" + imageInfo.getHeight() + " QualityInfo:" + imageInfo.getQualityInfo());
//            }
//
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//                super.onFailure(id, throwable);
//                Log.e(TAG, "onFailure: ", throwable);
//            }
//
//            @Override
//            public void onRelease(String id) {
//                super.onRelease(id);
//                Log.i(TAG, "onRelease: ");
//            }
//        });
//        draweeView.setController(controllerBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
