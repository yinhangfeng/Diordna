package com.yinhangfeng.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhangfeng.testlibrary.BaseTestActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    private static List<String> data;
    static {
        Random random = new Random();
        data = new ArrayList<String>();
        String str;
        for(int i = 0; i < 50; ++i) {
            str = "ITEM:" + i;
            int lines = random.nextInt(5);
            for(int j = 0; j < lines; ++j) {
                str += "|XXXX";
            }
            data.add(str);
        }
    }

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position % 3 + 1;
//            }
//        });
//        layoutManager.setReverseLayout(true);

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getItemAnimator().setSupportsChangeAnimations(true);

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void test1() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void test2() {
        for(int i = 5; i < 5 + 7; ++i) {
            data.set(i, data.get(i) + "\nCHANGExxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
        adapter.notifyItemRangeChanged(5, 7);
    }

    @Override
    protected void test3() {
        data.add(10, "Inserted");
        data.add(10, "Inserted");
        data.add(10, "Inserted");
        adapter.notifyItemRangeInserted(10, 3);
    }

    @Override
    protected void test4() {
        data.remove(15);
        data.remove(15);
        data.remove(15);
        adapter.notifyItemRangeRemoved(15, 3);
    }

    @Override
    protected void test5() {
        int i = 1;
        String temp = data.get(i);
        for(; i < 3; ++i) {
            data.set(i, data.get(i + 1));
        }
        data.set(i, temp);
        adapter.notifyItemMoved(1, 3);
    }

    @Override
    protected void test6() {
    }

    @Override
    protected void test7() {
        super.test7();
    }

    @Override
    protected void test8() {
        super.test8();
    }

    @Override
    protected void test9() {
        super.test9();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private static final String TAG = "MyAdapter";

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = getLayoutInflater().inflate(R.layout.item_recycler_view1, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            Log.i(TAG, "onBindViewHolder position:" + i + " data:" + data.get(i));
            viewHolder.text1.setText(data.get(i));
            if(i % 2 == 0) {
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
            } else {
                viewHolder.itemView.setBackgroundColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            //Log.i(TAG, "getItemViewType");
            return super.getItemViewType(position);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            //Log.i(TAG, "onAttachedToRecyclerView");
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            //Log.i(TAG, "onDetachedFromRecyclerView");
        }

        @Override
        public void onViewAttachedToWindow(MyViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            //Log.i(TAG, "onViewAttachedToWindow");
        }

        @Override
        public void onViewDetachedFromWindow(MyViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            //Log.i(TAG, "onViewDetachedFromWindow");
        }

        @Override
        public void onViewRecycled(MyViewHolder holder) {
            super.onViewRecycled(holder);
            //Log.i(TAG, "onViewRecycled");
        }

    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text1;

        public MyViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
        }

    }

}
