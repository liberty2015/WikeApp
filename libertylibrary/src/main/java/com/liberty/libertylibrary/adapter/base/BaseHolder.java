package com.liberty.libertylibrary.adapter.base;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LinJinFeng on 2016/12/23.
 */

public abstract class BaseHolder<M> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews=new SparseArray<>();

    private View mConvertView;

    private int layoutId;

    public BaseHolder(View itemView) {
        super(itemView);
        mConvertView=itemView;
    }

    public BaseHolder(ViewGroup viewGroup,@LayoutRes int res){
        super(LayoutInflater.from(viewGroup.getContext()).inflate(res,viewGroup,false));
        mConvertView=itemView;
        layoutId=res;
    }

    public <V extends View> V getView(@IdRes int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (V) view;
    }

    public void setData(M item){

    }

    public int getLayoutId() {
        return layoutId;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
