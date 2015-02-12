package com.yinhangfeng.viewpagertest;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhangfeng.testlibrary.BaseTestActivity;


public class MainActivity extends BaseTestActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new MyAdapter());
    }

    @Override
    protected void test1() {
        //一次跳跃区域过大中间会空白
        viewPager.setCurrentItem(8);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = getLayoutInflater().inflate(R.layout.item_view_pager, null);
            TextView textView = (TextView) v.findViewById(R.id.text_view);
            textView.setText("" + position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.3f;
        }
    }
}
