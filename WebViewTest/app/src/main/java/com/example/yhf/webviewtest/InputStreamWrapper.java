package com.example.yhf.webviewtest;

import com.example.yhf.webviewtest.util.L;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yhf on 2015/7/16.
 */
public class InputStreamWrapper extends FilterInputStream {
    private final String TAG = toString();

    private String url;

    protected InputStreamWrapper(InputStream in, String url) {
        super(in);
        this.url = url;
    }

    @Override
    public int read() throws IOException {
        L.i(TAG, "read url=", url, ", tid=", Thread.currentThread().getId());
        return super.read();
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        L.i(TAG, "read buffer len=", buffer.length, " url=", url, ", tid=", Thread.currentThread().getId());
        return super.read(buffer);
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        L.i(TAG, "read buffer len=", buffer.length, ", byteOffset=" + byteOffset + ", byteCount=" + byteCount + " url=", url, ", tid=", Thread.currentThread().getId());
        return super.read(buffer, byteOffset, byteCount);
    }
}
