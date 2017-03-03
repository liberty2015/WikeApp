package com.liberty.libertylibrary.adapter.divider;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by LinJinFeng on 2016/10/31.
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;
    private SpanSizeLookup spanSizeLookup;
//    public MarginDecoration(Context context){
//        margin= (int) context.getResources().getDimension(R.dimen.item_margin);
//    }

//    public MarginDecoration(int margin){
//        this.margin=margin;
//    }

    public MarginDecoration(int margin,SpanSizeLookup spanSizeLookup){
        this.margin=margin;
        this.spanSizeLookup=spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position=parent.getChildLayoutPosition(view);
        RecyclerView.LayoutManager manager=parent.getLayoutManager();
        int size=((GridLayoutManager)manager).getSpanCount();
        if (spanSizeLookup.getSpanSize(position)==1){
            outRect.left=margin;
            outRect.right=margin/2;
            outRect.bottom=margin;
            if (position%size==0){
                outRect.left=margin/2;
                outRect.right=margin;
            }
        }
    }
}
