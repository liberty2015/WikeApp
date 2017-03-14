package com.liberty.wikepro.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.liberty.libertylibrary.util.MyLogger;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.util.NetChangeObserver;
import com.liberty.wikepro.util.NetStateReceiver;
import com.liberty.wikepro.util.NetUtils;
import com.liberty.wikepro.util.StatusBarCompat;
import com.liberty.wikepro.view.widget.ProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;



/**
 * Created by LinJinFeng on 2016/12/23.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mCommonToolbar;

    private Unbinder mUnbinder;

    private Context mContext;

    protected View statusBarView=null;

    protected int statusBarColor=0;

    protected MyLogger logger=MyLogger.getLogger(BaseActivity.class);

    protected NetChangeObserver mNetChangeObserver;

    /**
     * 加载对话框
     */
    private ProgressDialog dialog;


    public void showToast(String text){
        if (!TextUtils.isEmpty(text)){
            Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        if (statusBarColor==0){
            statusBarView= StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }else if (statusBarColor!=-1){
            statusBarView=StatusBarCompat.compat(this,statusBarColor);
        }
//        transparent19and20();
        mContext=this;
        mUnbinder= ButterKnife.bind(this);
        mCommonToolbar= ButterKnife.findById(this, R.id.toolBar);
        logger.d("toolbar==null"+(mCommonToolbar==null));
        setActivityComponent(WikeApplication.getInstance().mAppComponent);
        initData();
        initViews();
        if (mCommonToolbar!=null){
            setSupportActionBar(mCommonToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initToolbar();
        }
        mNetChangeObserver=new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisconnect() {
                onNetworkDisconnected();
            }
        };

    }

    protected void transparent19and20() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    protected abstract void initToolbar();

    protected abstract void initData();

    protected abstract void initViews();

    protected abstract int setLayoutId();

    public ProgressDialog getDialog(){
        if (dialog==null){
            dialog= ProgressDialog.getInstance(this);
        }
        return dialog;
    }

    public void hideDialog(){
        if (dialog!=null){
            dialog.hide();
        }
    }

    public void showDialog(){
        getDialog().show();
    }

    public void dismissDialog(){
        if (dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }

    protected void hideStatusBar(){
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);
        if (statusBarView!=null){
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void showStatusBar(){
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.flags &=~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);
        if (statusBarView!=null){
            statusBarView.setBackgroundColor(statusBarColor);
        }
    }

    public void startOtherActivity(Class activity){
        startActivity(new Intent(this,activity));
    }

    public View f(@IdRes int id){
        return findViewById(id);
    }

    protected void setActivityComponent(ApplicationComponent component){

    }

    protected void onNetworkConnected(NetUtils.NetType netType){}

    protected void onNetworkDisconnected(){}

}
