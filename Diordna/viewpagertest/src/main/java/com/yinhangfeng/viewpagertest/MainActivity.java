package com.yinhangfeng.viewpagertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhangfeng.testlibrary.BaseTestActivity;


public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    private MyViewPager viewPager;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (MyViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(32);
        viewPager.setAdapter(new MyAdapter());
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            @Override
            public void transformPage(View page, float position) {
                //Log.i(TAG, "transformPage position=" + position);
                //                int pageWidth = page.getWidth();
                //                int viewPageWidth = viewPager.getWidth();
                //
                ////                if (position < -1) { // [-Infinity,-1)
                ////                    // This page is way off-screen to the left.
                ////                    page.setAlpha(0);
                ////
                ////                } else
                //                if (position <= 0) { // [-1,0]
                //                    // Use the default slide transition when moving to the left page
                //                    page.setAlpha(1);
                //                    page.setTranslationX(0);
                //                    page.setScaleX(1);
                //                    page.setScaleY(1);
                //
                //                } else { // (0,1]
                //                    // Fade the page out.
                //                    page.setAlpha( -0.15f * position + 1);
                //
                //                    // Counteract the default slide transition
                //                    page.setTranslationX(viewPageWidth * -position + 0.15f * viewPageWidth * position);
                //
                //                    // Scale the page down (between MIN_SCALE and 1)
                //                    float scaleFactor = -0.15f * position + 1;
                ////                    if(position < 1) {
                ////                        scaleFactor = MIN_SCALE
                ////                                + (1 - MIN_SCALE) * (1 - Math.abs(position));
                ////                    } else {
                ////                        scaleFactor = MIN_SCALE;
                ////                    }
                //                    //page.setScaleX(scaleFactor);
                //                    page.setScaleY(scaleFactor);
                //
                //                }
                ////                else { // (1,+Infinity]
                ////                    // This page is way off-screen to the right.
                ////                    page.setAlpha(0);
                ////                }
            }
        });

//        viewPager.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG, "viewPager.postDelayed run");
//                viewPager.postDelayed(this, 200);
//            }
//        }, 200);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG, "handler.postDelayed run");
//                handler.postDelayed(this, 200);
//            }
//        }, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy ");
    }

    @Override
    protected void test1() {
        //一次跳跃区域过大中间会空白
        viewPager.setCurrentItem(25);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "instantiateItem() called with " + "container = " + container + ", position = " + position + "");
            MyFrameLayout v = (MyFrameLayout) getLayoutInflater().inflate(R.layout.item_view_pager, null);
            TextView textView = (TextView) v.findViewById(R.id.text_view);
            textView.setText("" + position);
            container.addView(v);
            v.setId(position);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

//        @Override
//        public float getPageWidth(int position) {
//            return 0.3f;
//        }
    }
}
