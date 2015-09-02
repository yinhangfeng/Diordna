package com.github.yinhangfeng.typefacetest;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by yhf on 2015/9/2.
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate Typeface.DEFAULT=" + Typeface.DEFAULT + " Typeface.DEFAULT_BOLD=" + Typeface.DEFAULT_BOLD + " Typeface.SANS_SERIF=" + Typeface.SANS_SERIF + " Typeface.SERIF=" + Typeface.SERIF + " Typeface.MONOSPACE=" + Typeface.MONOSPACE);

        Typeface typefaceSimkai = Typeface.createFromAsset(getAssets(), "simkai.ttf");
        Log.e(TAG, "onCreate typeface simkai.ttf " + typefaceSimkai);

//        //用反射方式在Typeface中增加一种字体类型(API 21以上有效)
//        try {
//            //sSystemFontMap Field是API 21 添加的
//            Field sSystemFontMapField = Typeface.class.getDeclaredField("sSystemFontMap");
//            sSystemFontMapField.setAccessible(true);
//            Map<String, Typeface> sSystemFontMap = (Map<String, Typeface>) sSystemFontMapField.get(null);
//            sSystemFontMap.put("simkai", typefaceSimkai);
//
//            Typeface simkaiNormal = Typeface.create("simkai.ttf", Typeface.NORMAL);
//            Typeface simkaiBold = Typeface.create("simkai.ttf", Typeface.BOLD);
//            Typeface simkaiItalic = Typeface.create("simkai.ttf", Typeface.ITALIC);
//            Typeface simkaiBoldItalic = Typeface.create("simkai.ttf", Typeface.BOLD_ITALIC);
//
//            Log.e(TAG, "onCreate familyName simkaiNormal=" + simkaiNormal + " simkaiBold=" + simkaiBold + " simkaiItalic=" + simkaiItalic + " simkaiBoldItalic=" + simkaiBoldItalic);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }

        //用反射修改Typeface的DEFAULT字体 将Typeface中所有常量字体都设置
        //将Typeface.SERIF 通过反射设为自己想要的字体 在主题中设置默认typeface(<item name="android:typeface">serif</item>)为 serif 达到简单的修改全局字体的目的
        try {
            Field field_sTypefaceCache = Typeface.class.getDeclaredField("sTypefaceCache");
            field_sTypefaceCache.setAccessible(true);
            Object sTypefaceCache = field_sTypefaceCache.get(null);
            Method method_clear = sTypefaceCache.getClass().getDeclaredMethod("clear");
            method_clear.invoke(sTypefaceCache);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Typeface simkaiBold = Typeface.create(typefaceSimkai, Typeface.BOLD);
        Typeface simkaiItalic = Typeface.create(typefaceSimkai, Typeface.ITALIC);
        Typeface simkaiBoldItalic = Typeface.create(typefaceSimkai, Typeface.BOLD_ITALIC);
        try {
            Field field_DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            field_DEFAULT.setAccessible(true);
            field_DEFAULT.set(null, typefaceSimkai);

            Field field_DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            field_DEFAULT_BOLD.setAccessible(true);
            field_DEFAULT_BOLD.set(null, simkaiBold);

            Field field_sDefaults = Typeface.class.getDeclaredField("sDefaults");
            field_sDefaults.setAccessible(true);
            Typeface[] sDefaults = (Typeface[]) field_sDefaults.get(null);
            sDefaults[0] = typefaceSimkai;
            sDefaults[1] = simkaiBold;
            sDefaults[2] = simkaiItalic;
            sDefaults[3] = simkaiBoldItalic;

            Field field_SANS_SERIF = Typeface.class.getDeclaredField("SANS_SERIF");
            field_SANS_SERIF.setAccessible(true);
            field_SANS_SERIF.set(null, typefaceSimkai);

            Field field_SERIF = Typeface.class.getDeclaredField("SERIF");
            field_SERIF.setAccessible(true);
            field_SERIF.set(null, typefaceSimkai);

            Field field_MONOSPACE = Typeface.class.getDeclaredField("MONOSPACE");
            field_MONOSPACE.setAccessible(true);
            field_MONOSPACE.set(null, typefaceSimkai);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
