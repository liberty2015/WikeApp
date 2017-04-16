package com.liberty.libertylibrary.widget.NestListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/1/9.
 */

public abstract class BaseNestedAdapter<T> {
    private List<T> mDatas;
//    private int mItemLayoutId;
    private Context context;

    public BaseNestedAdapter(Context context, List<T> mDatas){
        this.mDatas=mDatas;
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    public abstract void onBind(int position, NestFullViewHolder holder);

    public abstract View onCreateView(ViewGroup parent, int position);

//    public int getmItemLayoutId() {
//        return mItemLayoutId;
//    }

    public abstract int getItemCount();


    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

//    public void setmItemLayoutId(int mItemLayoutId) {
//        this.mItemLayoutId = mItemLayoutId;
//    }

    public int getViewType(int position){
        return 0;
    }

    public T getItem(int position){
        return mDatas.get(position);
    }
}
