package com.liberty.wikepro.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.liberty.wikepro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/22.
 */

public class LinearListCreator implements View.OnClickListener{
    private LinearLayout rootView;
    private List<View> views;
    private int headerCount;
    private int normalCount;
    private int lineCount;
    private int spaceCount;

    private Context mContext;
    private @LayoutRes int itemLayoutId;
    private @IdRes int LayoutId;
    private ViewGroup baseView;
    private boolean hasLine;
    private SparseArray<View> viewHashMap;

    private static final int NORMALVIEW=0x00100000;
    private static final int LINEVIEW=0x00200000;
    private static final int SPACEVIEW=0x00300000;
    private static final int HEADERVIEW=0x00400000;

    private OnClickCallback clickCallback;

    private ViewGroup.LayoutParams defaultParams;

    private OnInflaterCallback inflaterCallback;

    public LinearListCreator(Context context){
        this.mContext=context;
        views=new ArrayList<>();
        rootView=new LinearLayout(context);
        viewHashMap=new SparseArray<View>();
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(params);
    }

    LinearLayout.LayoutParams generateLayoutParam(int width,int height) {
        return new LinearLayout.LayoutParams(width,height);
    }

    private void generateDefaultParams(ViewGroup.LayoutParams params){
        defaultParams=params;
    }

    @Override
    public void onClick(View v) {
        int i = views.indexOf(v);
        if (headerCount>0){
            i-=headerCount;
        }
        if (clickCallback!=null){
            clickCallback.onClick(i,v);
        }
    }

    public void setClickCallback(OnClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setInflaterCallback(OnInflaterCallback inflaterCallback) {
        this.inflaterCallback = inflaterCallback;
    }

    public static class Builder{
        LinearListCreator creator=null;

        public Builder(Context context,@LayoutRes int layoutId){
            creator=new LinearListCreator(context);
            creator.itemLayoutId=layoutId;
        }

        public Builder addView(View view){
            int size=creator.normalCount++;
            int key=NORMALVIEW|size;
            creator.viewHashMap.put(key,view);
            return this;
        }

        public Builder withLayoutId(@LayoutRes int layoutId){
            creator.itemLayoutId= layoutId;
            return this;
        }

        public Builder withBaseView(ViewGroup baseView){
            creator.baseView=baseView;
            return this;
        }

        public Builder forViews(int size,LinearLayout.LayoutParams params){
            creator.defaultParams=params;
            int count=creator.normalCount;
            for (int i=0;i<size;i++){
                int key=NORMALVIEW|(count++);
                View item=LayoutInflater.from(creator.mContext)
                        .inflate(creator.itemLayoutId,null);
                item.setLayoutParams(params);
//                creator.views.add(item);
                creator.viewHashMap.put(key,item);
                creator.normalCount++;
            }
//            creator.normalCount=count;
            return this;
        }

        public Builder needLine(boolean hasLine){
            creator.hasLine=hasLine;
            return this;
        }

        public Builder spaceView(){
            View space=new View(creator.mContext);

            space.setBackgroundColor(Color.parseColor("#d9d9d9"));
            float height=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,creator.mContext.getResources().getDisplayMetrics());
            space.setLayoutParams(
                    creator.generateLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));
            int key=SPACEVIEW|(creator.normalCount++);
            creator.viewHashMap.put(key,space);
            creator.spaceCount++;
//            creator.views.add(space);
            return this;
        }



        public Builder addHeader(View header){
            int key=HEADERVIEW|creator.headerCount;
            creator.viewHashMap.put(key,header);
//            creator.views.add(creator.headerCount,header);
            creator.headerCount++;
            return this;
        }

        public Builder inflater(OnInflaterCallback callback){
//            int size=creator.headerCount;
//            for (int i=0;i<creator.views.size()-size;i++){
//                callback.onInflater(i,creator.views.get(i));
//            }
            creator.setInflaterCallback(callback);
            int size=creator.headerCount;

            if (creator.inflaterCallback!=null){
                for (int i=0;i<creator.normalCount;i++){
                    int key=NORMALVIEW|i;
                    View item=creator.viewHashMap.get(key);
                    if (item!=null){
                        creator.inflaterCallback.onInflater(i,item);
                    }
                }
            }
//            if (creator.clickCallback!=null){
//                for (int i=0;i<creator.views.size()-size;i++){
//
//                }
//            }
            return this;
        }

        public Builder withClickCallback(final OnClickCallback clickCallback){
            creator.setClickCallback(clickCallback);
            int size=creator.headerCount;
            if (creator.inflaterCallback!=null){
                for (int i=0;i<creator.normalCount;i++){
                    int key=NORMALVIEW|i;
                    final int position=i;
                    final View item=creator.viewHashMap.get(key);
                    if (item!=null){
                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickCallback.onClick(position,item);
                            }
                        });
                    }
                }
            }
            return this;
        }

        public Builder defaultLayoutParams(LinearLayout.LayoutParams params){
            creator.defaultParams=params;
            return this;
        }

        public Builder build(){
            for (int i=0;i<creator.headerCount;i++){
                int key=HEADERVIEW|i;
                creator.rootView.addView(creator.viewHashMap.get(key));
            }
            if (creator.hasLine){
                View line=new View(creator.mContext);
                line.setLayoutParams(creator.generateLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,
                                creator.mContext.getResources().getDisplayMetrics())));
                line.setBackgroundColor(creator.mContext.getResources().getColor(R.color.lineColor));
                creator.rootView.addView(line);
                for (int i=0;i<creator.normalCount;i++){
                    int key=NORMALVIEW|i;
                    View item=creator.viewHashMap.get(key);
                    if (item==null){
                        key=SPACEVIEW|i;
                        item=creator.viewHashMap.get(key);
                    }
//                    creator.rootView.addView(creator.views.get(i));
                    creator.rootView.addView(item);
                    View line1=new View(creator.mContext);
                    line1.setLayoutParams(creator.generateLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,
                                    creator.mContext.getResources().getDisplayMetrics())));
                    line1.setBackgroundColor(creator.mContext.getResources().getColor(R.color.lineColor));
                    creator.rootView.addView(line1);
                }
            }else {
                for (int i=0;i<creator.normalCount;i++){
//                    creator.rootView.addView(creator.views.get(i),creator.defaultParams);
                    int key=NORMALVIEW|i;
                    View item=creator.viewHashMap.get(key);
                    if (item==null){
                        key=SPACEVIEW|i;
                        item=creator.viewHashMap.get(key);
                    }
                    creator.rootView.addView(item);
                }
            }

            creator.baseView.addView(creator.rootView,creator.generateLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

            return this;
        }

        public LinearLayout create(){
            return creator.rootView;
        }
    }

    public interface OnClickCallback{
        void onClick(int position,View v);
    }

    public interface OnInflaterCallback{
        void onInflater(int position,View v);
    }

}
