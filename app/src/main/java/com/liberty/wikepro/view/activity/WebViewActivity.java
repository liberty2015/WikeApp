package com.liberty.wikepro.view.activity;

import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;

import com.liberty.libertylibrary.widget.CustomWebView;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by liberty on 2017/4/4.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    CustomWebView webView;

    @Override
    protected void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Uri data = getIntent().getData();
        String path=data.getPath().substring(1);
        webView.withWebviewChromeClient()
                .withWebViewClient()
                .withJavaScriptEnabled(true)
                .withWebViewTitleReceiver(new CustomWebView.WebViewTitleReceiver() {
                    @Override
                    public void getTitle(String title) {
                        mCommonToolbar.setTitle(title);
                    }
                });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN){
                    if (keyCode==KeyEvent.KEYCODE_BACK){
                        if (webView.canGoBack()){
                            webView.goBack();
                        }else {
                            finish();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        webView.loadUrl(path);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.webview_layout;
    }
}
