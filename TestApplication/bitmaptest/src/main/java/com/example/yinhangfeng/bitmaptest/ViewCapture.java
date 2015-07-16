package com.example.yinhangfeng.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by yhf on 2015/7/3.
 */
public class ViewCapture {

    private Bitmap cache;
    private Canvas canvas = new Canvas();

    private Bitmap obtainBitmap(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap cache = this.cache;
        if(cache == null) {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        int cacheWidth = cache.getWidth();
        int cacheHeight = cache.getHeight();
        if(width == cacheWidth && height == cacheHeight) {
            this.cache = null;
            return cache;
        }
        if(Build.VERSION.SDK_INT >= 19 && width * height * 4 <= this.cache.getAllocationByteCount()) {
            try {
                cache.reconfigure(width, height, Bitmap.Config.ARGB_8888);
                this.cache = null;
                return cache;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public Bitmap getViewBitmap(View view) {
        Bitmap bm = obtainBitmap(view);
        if(Build.VERSION.SDK_INT >= 12) {
            bm.setHasAlpha(false);
        }
        bm.eraseColor(Color.WHITE);
        Canvas canvas = this.canvas;
        canvas.setBitmap(bm);
        canvas.save();
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        canvas.restore();
        canvas.setBitmap(null);
        return bm;
    }

    public void recycleBitmap(Bitmap bm) {
        if(bm == null || !bm.isMutable()) {
            return;
        }
        cache = bm;
    }
}
