package com.yinhangfeng.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yinhangfeng.testlibrary.BaseTestActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GridRecyclerViewActivity extends BaseTestActivity {

    private static List<String> data;
    static {
        Random random = new Random();
        data = new ArrayList<String>();
        String str;
        for(int i = 0; i < 50; ++i) {
            str = "ITEM:" + i;
            int lines = random.nextInt(5);
            for(int j = 0; j < lines; ++j) {
                str += "\nXXX";
            }
            data.add(str);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);

        VerticalGridView verticalGridView = (VerticalGridView) findViewById(R.id.vertical_grid_view);
        verticalGridView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        verticalGridView.setLayoutManager(layoutManager);
        verticalGridView.setNumColumns(3);

        verticalGridView.setAdapter(new MyAdapter());
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
