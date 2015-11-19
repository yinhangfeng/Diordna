package com.example.fkwebview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yinhf on 2015/11/11.
 */
public class WebViewFragment extends Fragment {

    private MainActivity mainActivity;
    private FKWebView webView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = (FKWebView) v.findViewById(R.id.web_view);
        mainActivity.initWebView(webView, true);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.deInitWebView(webView);
    }
}
