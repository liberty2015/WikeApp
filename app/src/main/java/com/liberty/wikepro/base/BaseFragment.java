package com.liberty.wikepro.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liberty.wikepro.view.widget.ProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by LinJinFeng on 2016/12/23.
 */

public abstract class BaseFragment extends Fragment {

    protected View parentView;

    protected FragmentActivity appCompatActivity;

    private ProgressDialog dialog;

    private Unbinder mUnbinder;

    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView=inflater.inflate(getLayoutResId(),container,false);
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder= ButterKnife.bind(this,view);
        attachView();
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    public abstract void attachView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appCompatActivity= (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        appCompatActivity=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public FragmentActivity getHoldActivity(){
        return appCompatActivity;
    }

    public ProgressDialog getDialog(){
        if (dialog==null){
            dialog= ProgressDialog.getInstance(appCompatActivity);
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

    public <V extends View> V findView(@IdRes int id){
        return (V) parentView.findViewById(id);
    }
}
