package cn.yinhf.android.example.tbs;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by yinhf on 2016/12/10.
 */

public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "onCoreInitFinished:");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.i(TAG, "onViewInitFinished: " + b);
            }
        });
    }
}
