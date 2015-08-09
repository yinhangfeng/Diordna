package com.kingoit.library.imageloader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 可指定图片大小的ImageViewAware，用于在加载图片时不想按照View的大小decode图片的情况
 */
public class SizeImageViewAware extends ImageViewAware {
	private int width;
	private int height;

	public SizeImageViewAware(ImageView imageView, int width, int height) {
		super(imageView);
		this.width = width;
		this.height = height;
	}

	public SizeImageViewAware(ImageView imageView, int width, int height,
			boolean checkActualViewSize) {
		super(imageView, checkActualViewSize);
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {
		return width <=0 ? super.getWidth() : width;
	}

	@Override
	public int getHeight() {
		return height <= 0 ? super.getHeight() : height;
	}
}
