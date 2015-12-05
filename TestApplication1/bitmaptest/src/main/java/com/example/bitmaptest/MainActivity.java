package com.example.bitmaptest;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.commonlibrary.BaseTestActivity;

public class MainActivity extends BaseTestActivity {
    
    private ImageView imageView;
    private Bitmap bitmap;
    private Canvas canvas = new Canvas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        imageView = (ImageView) findViewById(R.id.image);
        bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void test1() {
        canvas.setBitmap(bitmap);
        canvas.drawColor(0xAA00FA9A);
        Paint paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint1.setTextSize(50);
        canvas.drawText("ABCEDFGHIJ", 0, 100, paint1);
        canvas.setBitmap(null);
        imageView.setImageBitmap(bitmap);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void test2() {
        imageView.setImageBitmap(null);
        bitmap.reconfigure(200, 200, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0);//重要
        imageView.setImageBitmap(bitmap);
    }
}
