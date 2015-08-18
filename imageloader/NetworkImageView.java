package com.kingoit.library.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by yhf on 2015/1/15.
 * 集成图片加载的ImageView
 */
public class NetworkImageView extends ImageView {
    static final String TAG = "NetworkImageView";
    private static final boolean DEBUG = false;

    private String mTargetUrl;
    //最后加载的图片信息
    private LoadInfo mLastLoadInfo;
    private ImageAware mImageAware;
    private int defaultImageRes;
    private Drawable defaultImageDrawable;
    private DisplayImageOptions mDisplayImageOptions = ImageLoaderHelper.getDefaultDisplayImageOptions();

    public NetworkImageView(Context context) {
        super(context);
        init();
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        defaultImageDrawable = getDrawable();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelLastLoadImage();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(isInEditMode()) {
            return;
        }
        tryLoadImage();
    }

    /**
     * 设置加载过程中显示的图片
     * @param resId 图片资源id, 0加载过程中不设置图片
     */
    public void setDefaultImageRes(int resId) {
        defaultImageRes = resId;
    }
    
    public void setDisplayImageOptions(DisplayImageOptions displayImageOptions) {
    	mDisplayImageOptions = displayImageOptions;
    }

    private ImageAware getImageAware() {
        if(mImageAware == null) {
            mImageAware = new ImageViewAware(this);
        }
        return mImageAware;
    }

    private void cancelLastLoadImage() {
        if(mLastLoadInfo != null && mLastLoadInfo.isLoading()) {
            ImageLoader.getInstance().cancelDisplayTask(getImageAware());
        }
        mLastLoadInfo = null;
    }

    /**
     * 取消图片加载并将要加载的目标url置为null
     */
    public void cancelLoadImage() {
        cancelLastLoadImage();
        mTargetUrl = null;
    }

    /**
     * 加载图片,必须在UI线程调用
     * @param url 要加载的图片url,传入空url会按照ImageLoader DisplayImageOptions配置加载对应图片
     */
    public void loadImage(String url) {
        mTargetUrl = url == null ? "" : url;
        tryLoadImage();
    }

    private void tryLoadImage() {
        String targetUrl = mTargetUrl;
        if(DEBUG) Log.d(TAG, "tryLoadImage mTargetUrl=" + targetUrl);
        if(targetUrl == null) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        ViewGroup.LayoutParams lyp = getLayoutParams();
        boolean isFullyWrapContent = lyp != null
                && lyp.height == ViewGroup.LayoutParams.WRAP_CONTENT
                && lyp.width == ViewGroup.LayoutParams.WRAP_CONTENT;
        if(width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }

        LoadInfo lastLoadInfo = mLastLoadInfo;
        if(lastLoadInfo != null && !lastLoadInfo.needLoad(targetUrl)) {
            return;
        }

        if(DEBUG) Log.i(TAG, "tryLoadImage start mTargetUrl=" + targetUrl
                + " width=" + width + " height=" + height);

        LoadInfo loadInfo = new LoadInfo(targetUrl);
        ImageLoader.getInstance().displayImage(targetUrl, getImageAware(), mDisplayImageOptions, loadInfo, null);
        if(loadInfo.isLoading()) {
            //设置加载中显示的图片
            if(defaultImageRes != 0) {
                setImageResource(defaultImageRes);
            } else {
                setImageDrawable(defaultImageDrawable);
            }
        }
        mLastLoadInfo = loadInfo;
    }

    private static class LoadInfo implements ImageLoadingListener {
        private String url;
        //0:loading 1:success 2:failed 3:canceled
        private int loadState;

        public LoadInfo(String url) {
            this.url = url;
        }

        public boolean needLoad(String newUrl) {
            return loadState > 1 || !url.equals(newUrl);
        }

        public boolean isLoading() {
            return loadState == 0;
        }

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            loadState = 2;
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            loadState = 1;
        }

        @Override
        public void onLoadingCancelled(String s, View view) {
            loadState = 3;
        }
    }
}
