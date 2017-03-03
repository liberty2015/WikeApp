package com.liberty.libertylibrary.adapter.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LinJinFeng on 2017/3/2.
 */

public class MarginLinearDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginLinearDecoration(int margin){
        this.margin=margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position=parent.getChildLayoutPosition(view);
        outRect.left=margin;
        outRect.right=margin;
        outRect.top=margin;
        outRect.bottom=margin;
    }
}
