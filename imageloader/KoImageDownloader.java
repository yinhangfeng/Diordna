package com.kingoit.library.imageloader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 重写UIL的BaseImageDownloader, 添加对视频文件第一帧获取的功能
 */
public class KoImageDownloader extends BaseImageDownloader {
	static final String TAG = "NbImageDownloader";

	public KoImageDownloader(Context context) {
		super(context);
	}

	public KoImageDownloader(Context context, int connectTimeout,
			int readTimeout) {
		super(context, connectTimeout, readTimeout);
	}

	@Override
	protected InputStream getStreamFromFile(String imageUri, Object extra)
			throws IOException {
		//TODO 判断是否为视频文件的标准只是简单地判断扩展名是否为.mp4
		if(imageUri.endsWith(".mp4") || imageUri.endsWith(".MP4")) {
	        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	        Bitmap bitmap = null;
	        try {
	            retriever.setDataSource(Scheme.FILE.crop(imageUri));
	            bitmap = retriever.getFrameAtTime(-1);
	        } catch (Exception ex) {
	            throw new IOException(ex);
	        } finally {
	            try {
	                retriever.release();
	            } catch (Exception ig) {
	            }
	        }
	        if(bitmap == null) {
	        	throw new IOException("MediaMetadataRetriever failure");
	        }
	        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
	        if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)) {
	        	throw new IOException("bitmap.compress failure");
	        }
	        bitmap.recycle();
	        return new ByteArrayInputStream(baos.toByteArray());
		}
		return super.getStreamFromFile(imageUri, extra);
	}

}
