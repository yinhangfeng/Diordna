package com.example.commonlibrary;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Bitmap处理相关工具
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    private static final boolean DEBUG = false;

    //默认decode宽高上限
    public static final int DEF_MAX_WITH = 1600;
    public static final int DEF_MAX_HEIGHT = 1600;

    /**
     * 计算decode时缩小比例(可防止图片太大内存溢出)
     */
    public static int calcInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        float outWidth = options.outWidth;
        float outHeight = options.outHeight;
        //Log.d(TAG, "calcInSampleSize outHeight="+outHeight+" outWidth="+outWidth);
        float ratio = 1;
        if (maxWidth > 0 && maxHeight <= 0) {
            ratio = outWidth / maxWidth;
        } else if (maxHeight > 0 && maxWidth <= 0) {
            ratio = outHeight / maxHeight;
        } else if (maxWidth > 0 && maxHeight > 0) {
            float widthRatio = outWidth / maxWidth;
            float heightRatio = outHeight / maxHeight;
            //Log.d(TAG, "calcInSampleSize widthRatio="+widthRatio+" heightRatio="+heightRatio);
            ratio = widthRatio > heightRatio ? widthRatio : heightRatio;
        }
        return ratio > 1 ? (int) Math.ceil(ratio) : 1;
    }

    private static int calcMaxSize(int size, int defMaxSize) {
        if(size <= 0) {
            return defMaxSize;
        }
        size <<= 1;
        if(size > defMaxSize) {
            return defMaxSize;
        }
        return size;
    }

    /**
     * 根据路径获得指定最大宽高的Bitmap
     */
    public static Bitmap getSmallBitmap(String filePath, int maxWidth, int maxHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calcInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩Bitmap到文件
     */
    public static boolean compressBitmapToFile(File file, Bitmap bitmap) {
        boolean success = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            success = bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch(Exception ignored) {
        }
        return success;
    }

    /**
     * 缩放裁剪图片，并输出到文件
     * @param srcImageFile 原文件
     * @param outFile 输出文件
     * @param width 输出图片宽 <=0不限制宽
     * @param height 输出图片高 <=0不限制高
     */
    public static boolean centerCropImage(File srcImageFile, File outFile,
                                          int width, int height) {
        if (srcImageFile == null || outFile == null) {
            throw new IllegalArgumentException(
                    "imageFile == null || outFile == null");
        }
        if (!srcImageFile.isFile()) {
            return false;
        }
        Bitmap bitmap = getSmallBitmap(srcImageFile.getAbsolutePath(),
                calcMaxSize(width, DEF_MAX_WITH),
                calcMaxSize(height, DEF_MAX_HEIGHT));
        if (bitmap == null) {
            return false;
        }
        Bitmap resultBitmap = centerCropBtimap(bitmap, width, height);
        if(bitmap != resultBitmap) {
            bitmap.recycle();
        }
        return compressBitmapToFile(outFile, resultBitmap);
    }

    /**
     * 缩放裁剪图片,结果与ImageView scaleType centerCrop相同
     * @param srcBitmap 原图片
     * @param width 输出图片宽 <=0不限制宽
     * @param height 输出图片高 <=0不限制高
     */
    public static Bitmap centerCropBtimap(Bitmap srcBitmap, int width, int height) {
        if(width <= 0 && height <= 0) {
            Log.w(TAG, "centerCropBtimap width <= 0 && height <= 0");
            return srcBitmap;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        if (DEBUG)
            Log.d(TAG, "centerCropBtimap width=" + width + "height=" + height
                    + " srcWidth=" + srcWidth + " srcHeight=" + srcHeight);
        if(srcWidth <= 0 || srcHeight <= 0 || srcBitmap.isRecycled()) {
            return srcBitmap;
        }
        float widthScale = 1;
        if(width > 0) {
            widthScale = (float) width / srcWidth;
        } else {
            width = srcWidth;
        }
        float heightScale = 1;
        if(height > 0) {
            heightScale = (float) height / srcHeight;
        } else {
            height = srcHeight;
        }
        float scale = Math.max(widthScale, heightScale);
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        resultBitmap.setDensity(srcBitmap.getDensity());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setFilterBitmap(true);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.translate(width / 2, height / 2);
        canvas.scale(scale, scale);
        canvas.drawBitmap(srcBitmap, -srcWidth / 2, -srcHeight / 2, paint);
        canvas.setBitmap(null);
        return resultBitmap;
    }

}
