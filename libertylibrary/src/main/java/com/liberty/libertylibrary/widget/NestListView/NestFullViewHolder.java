package com.liberty.libertylibrary.widget.NestListView;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;


/**
 * Created by LinJinFeng on 2017/1/9.
 */

public class NestFullViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public NestFullViewHolder(Context context, View view){
        this.mContext=context;
        this.mViews=new SparseArray<>();
        this.mConvertView=view;
    }

    public <T extends View> T getView(@IdRes int id){
        View view=mViews.get(id);
        if (view==null){
            view=mConvertView.findViewById(id);
            mViews.put(id,view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
