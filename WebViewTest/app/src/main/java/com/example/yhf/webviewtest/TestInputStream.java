package com.example.yhf.webviewtest;

import com.example.lib.util.L;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yinhf on 16/8/12.
 */
public class TestInputStream extends InputStream {
    private static final String TAG = "TestInputStream";

    @Override
    public int read() throws IOException {
        L.i(TAG, "read , tid=", Thread.currentThread().getId());
        throw new IOException();
        //throw new RuntimeException();
        //return 0;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        L.i(TAG, "read buffer len=", buffer.length, ", tid=", Thread.currentThread().getId());
        return super.read(buffer);
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        L.i(TAG, "read buffer len=", buffer.length, ", byteOffset=" + byteOffset + ", byteCount=" + byteCount + ", tid=", Thread.currentThread().getId());
        return super.read(buffer, byteOffset, byteCount);
    }
}
