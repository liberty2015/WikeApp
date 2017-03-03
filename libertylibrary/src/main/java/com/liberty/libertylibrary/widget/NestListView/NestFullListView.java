package com.liberty.libertylibrary.widget.NestListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/1/9.
 * Version:0.1
 */

public class NestFullListView extends LinearLayout {

    private LayoutInflater inflater;

    private BaseNestedAdapter adapter;

    private List<NestFullViewHolder> mVHCaches;

    private List<View> headers=new ArrayList<>();

    private List<View> footers=new ArrayList<>();

    public NestFullListView(Context context) {
        this(context, null);
    }

    public NestFullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        inflater= LayoutInflater.from(context);
        setOrientation(VERTICAL);
        mVHCaches=new ArrayList<>();
    }

    public void setAdpter(BaseNestedAdapter adpter){
        this.adapter=adpter;
        updateUI();
    }

    public void updateUI(){
        if (adapter!=null){
            if (adapter.getmDatas()!=null
                    &&!adapter.getmDatas().isEmpty()){
                if (adapter.getItemCount()<getChildCount()){
                    removeViews(adapter.getItemCount(),getChildCount());
                    while (mVHCaches.size()>adapter.getItemCount()){
                        mVHCaches.remove(mVHCaches.size()-1);
                    }
                }
                for (int i=0;i<adapter.getItemCount();i++){
                    NestFullViewHolder holder;
                    if (mVHCaches.size()-1>=i){
                        holder=mVHCaches.get(i);
                    }else {
                        holder=new NestFullViewHolder(getContext(),adapter.onCreateView(this,i));
                        mVHCaches.add(holder);
                    }
                    adapter.onBind(i,holder);
                    if (holder.getmConvertView().getParent()==null){
                        this.addView(holder.getmConvertView());
                    }
                }
            }else {
                removeAllViews();
            }
        }else {
            removeAllViews();
        }
    }

    public void addHeader(View view){
        headers.add(view);
        this.addView(view,headers.size()-1);
    }


}
