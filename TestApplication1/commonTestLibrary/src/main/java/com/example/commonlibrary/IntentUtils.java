package com.example.commonlibrary;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

public class IntentUtils {

    /**
     * 获取拍照Intent
     *
     * @param file 保存文件
     */
    public static Intent getCaptureImageIntent(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return intent;
    }

    /**
     * 获取录音Intent
     */
    public static Intent getCaptureAudioIntent() {
        return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
    }

    /**
     * 获取播放音频Intent
     *
     * @param file 音频文件
     */
    public static Intent getOpenAudioIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(Uri.fromFile(file), "audio/*");
        return intent;
    }

    /**
     * 获取录制视频Intent
     *
     * @param file 视频保存文件
     */
    public static Intent getCaptureVideoIntent(File file) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        return intent;
    }

    /**
     * 获取打开视频Intent
     *
     * @param file 视频文件
     */
    public static Intent getOpenVideoIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(Uri.fromFile(file), "video/*");
        return intent;
    }

    /**
     * 检测是否存在处理该intent的Activity
     * 不存在则Toast msg
     *
     * @param intent  目标Intent
     * @param context 上下文
     * @param msg     不存在时的Toast消息
     */
    public static boolean haveResolveActivity(Intent intent, Context context, String msg) {
        if(intent.resolveActivity(context.getPackageManager()) == null) {
            if(!TextUtils.isEmpty(msg)) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    /**
     * 启动intent对应Activity
     * 如果不存在对应Activity 则Toast msg
     */
    public static void startOrToast(Intent intent, Context context, String msg) {
        if(intent.resolveActivity(context.getPackageManager()) == null) {
            if(!TextUtils.isEmpty(msg)) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * startForResult启动intent对应Activity
     * 如果不存在对应Activity 则Toast msg
     */
    public static void startForResultOrToast(Intent intent, int requestCode, Activity activity, String msg) {
        if(intent.resolveActivity(activity.getPackageManager()) == null) {
            if(!TextUtils.isEmpty(msg)) {
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            }
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 在Fragment中startForResult启动intent对应Activity
     * 如果不存在对应Activity 则Toast msg
     */
    public static void startForResultOrToast(Intent intent, int requestCode, Fragment fragment, String msg) {
        if(intent.resolveActivity(fragment.getActivity().getPackageManager()) == null) {
            if(!TextUtils.isEmpty(msg)) {
                Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * android获取一个用于打开Word文件的intent
     *
     * @param uri 文件uri
     */
    public static Intent getWordFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * android获取一个用于打开Excel文件的intent
     */
    public static Intent getExcelFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * android获取一个用于打开PPT文件的intent
     */
    public static Intent getPptFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public static void startVideoActivity(Context context, Uri uri) {
        Intent intent = getVideoFileIntent(uri);
        try {
            context.startActivity(intent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(context, "请安装Video应用.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取一个用于打开视频文件的intent
     */
    public static Intent getVideoFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);

        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    public static void startAudioActivity(Context context, Uri uri) {
        Intent intent = getAudioFileIntent(uri);
        try {
            context.startActivity(intent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(context, "请安装音频应用.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * android获取一个用于打开音频文件的intent
     */
    public static Intent getAudioFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

}
