package com.example.bob.testlistener.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.base.BaseFragment;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2017/10/31.
 */

public class MineFragment extends BaseFragment{

    @BindView(R.id.tv_mine)
    TextView mTvMine;
    @BindView(R.id.tencent_web_view)
    WebView mWebView;

//    private String url = "http://www.91suke.com/s/b9271044";
//    private String url = "https://www.baidu.com/";
    private String url = "https://www.tmall.com/?pid=mm_26632258_3504122_48284354&ali_trackid=2:mm_26632258_3504122_48284354:1512353368_340_1378546438&clk1=57344ba60e5b1605c75d43a3c31160a9&upsid=57344ba60e5b1605c75d43a3c31160a9";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_mine,container,false);
        mContext = getActivity().getApplicationContext();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    private void initView(){
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    public void setText(String str){
        mTvMine.setText(str);
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            String newStr;
            if(s.startsWith("tbopen")){
                newStr = s.replaceFirst("tbopen","https");
                webView.loadUrl(newStr.toString());
                return true;
            }else if(s.startsWith("tmall")){
                newStr = s.replaceFirst("tmall","https");
                webView.loadUrl(newStr.toString());
                return true;
            }else{
                webView.loadUrl(s.toString());
                return true;
            }

//            CookieManager cookieManager = CookieManager.getInstance();
//            String cookies = cookieManager.getCookie(s);
//            PreferenceUtil.putString(mContext,"cookies",cookies);

        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            Snackbar.make(mWebView,"加载中",Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }

        public void synCookies(Context context, String url){
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
//            cookieManager.setCookie(url);
            CookieSyncManager.getInstance().sync();
        }
    }

    public void onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
        }
    }
}
