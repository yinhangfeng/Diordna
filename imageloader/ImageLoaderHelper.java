package com.kingoit.library.imageloader;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderHelper {
    private static final int CORE_POOL_SIZE = Math.min(Runtime.getRuntime().availableProcessors() + 1, 3);
    private static final int CONNECT_TIMEOUT = 10 * 1000;
    private static final int READ_TIMEOUT = 60 * 1000;
    
    private static DisplayImageOptions defaultDisplayImageOptions;
    private static DisplayImageOptions displayImageOptionsNoFileCache;
	
    /**
     * 初始化ImageLoader
     */
	public static void init(Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int maxSize = displayMetrics.widthPixels;
		if(maxSize < displayMetrics.heightPixels) {
			maxSize = displayMetrics.heightPixels;
		}
		if(maxSize > 2048) {
			maxSize = 2048;
		}
		defaultDisplayImageOptions = new DisplayImageOptions.Builder()
		//缓存到内存
		.cacheInMemory(true)
		//缓存到文件
		.cacheOnDisk(true)
		.build();
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
		//按CPU核心数决定图片加载线程池的核心线程数
		.threadPoolSize(CORE_POOL_SIZE)
		//线程优先级，设为低于普通，减少图片加载时对UI流畅度的影响
		.threadPriority(Thread.NORM_PRIORITY - 2)
		//设置最多使用15%的APP可用内存用于缓存Bitmap
		.memoryCacheSizePercentage(15)
		//文件缓存文件名采用MD5
		//.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		//使用NbImageDownloader图片加载器，具有加载视频文件第一帧的功能 设置了默认超时时间
		.imageDownloader(new KoImageDownloader(context, CONNECT_TIMEOUT, READ_TIMEOUT))
		.defaultDisplayImageOptions(defaultDisplayImageOptions)
		//设定图片在内存中的最大宽高为屏幕宽高的大值
		.memoryCacheExtraOptions(maxSize, maxSize)
		.build();
		ImageLoader.getInstance().init(configuration);
	}
	
	public static DisplayImageOptions getDefaultDisplayImageOptions() {
		return defaultDisplayImageOptions;
	}
	
	public static DisplayImageOptions getDisplayImageOptionsNoFileCache() {
		if(displayImageOptionsNoFileCache == null) {
			displayImageOptionsNoFileCache = new DisplayImageOptions.Builder()
			//缓存到内存
			.cacheInMemory(true)
			//不缓存到文件
			.cacheOnDisk(false)
			.build();
		}
		return displayImageOptionsNoFileCache;
	}
	
	/**
	 * 删除一个图片Uri对应的所有大小规格的内存缓存
	 * @param uri 图片加载uri
	 */
	@SuppressWarnings("deprecation")
	public static void removeImageMemoryCacheByUri(String uri) {
		if(TextUtils.isEmpty(uri)) {
			return;
		}
		MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
		for(String memoryKey : memoryCache.keys()) {
			if(memoryKey.startsWith(uri)) {
				memoryCache.remove(memoryKey);
			}
		}
	}
}
