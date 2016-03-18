package com.example.yhf.statusbartest;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * 1.使用windowTranslucentStatus与windowTranslucentNavigation的情况
 * 将View设为fitSystemWindow 且当前开启了状态栏或导航栏透明的话 paddingTop paddingBottom会被覆盖,(比如手机没有虚拟导航栏,但开启透明导航栏的话paddingBottom也会被设为0)
 * 2.使用       <item name="android:windowDrawsSystemBarBackgrounds">true</item>
 <item name="android:statusBarColor">@android:color/transparent</item>
 <item name="android:navigationBarColor">@android:color/transparent</item>
 的情况
 这种情况下根视图为DrawerLayout则设置fitSystemWindows之后貌似一样,其实关键还是DrawerLayout做了特殊处理,其在设置fitSystemWindows之后会设置
 drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
 导致window全屏,且处理了子元素fitSystemWindows相关的分发
 View的dispatchApplyWindowInsets相关API 具体作用概念还需要学习

 但上述两种情况对于popupwindow都无效,其内容不会出现在系统栏下面(还需要研究 WindowManager.LayoutParams相关)
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
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
            frameLayout.animate().translationY(-100).start();
            Log.i(TAG, "onOptionsItemSelected frameLayout.getPaddingTop()=" + frameLayout.getPaddingTop() + " frameLayout.getPaddingBottom()=" + frameLayout.getPaddingBottom());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test1Onclick(View v) {
        showPop();
    }

    private void showPop() {
        final PopupWindow mPopupWindow = new PopupWindow(this);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());

        View v = getLayoutInflater().inflate(R.layout.pop_test, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setContentView(v);
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
    }
}
