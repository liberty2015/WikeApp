package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;

/**
 * Created by liberty on 2017/3/28.
 */

public class RightPanelAdapter extends BaseRecyclerAdapter<itemType> {

    private final static int CHAPTER=0x11;
    private final static int VIDEO=0x12;

    public RightPanelAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case CHAPTER:{
                return new BaseHolder<Chapter>(viewGroup, R.layout.chapter_item) {
                    @Override
                    public void setData(Chapter item) {
                        super.setData(item);
                        ((TextView)getView(R.id.chapter_name)).setText(item.getChname());
                    }
                };
            }
            case VIDEO:{
                return new BaseHolder<CVideo>(viewGroup,R.layout.cvideo_item) {
                    @Override
                    public void setData(CVideo item) {
                        super.setData(item);
                        TextView videoName=(TextView)getView(R.id.video_name);
                        videoName.setText(item.getVname());
                        videoName.setTextColor(Color.parseColor("#cccccc"));
                        getView(R.id.historyItem).setVisibility(View.GONE);


                    }
                };
            }
        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        itemType item = getItem(position);
        if (item instanceof Chapter){
            return CHAPTER;
        }else if (item instanceof CVideo){
            return VIDEO;
        }
        return super.getViewType(position);
    }
}
