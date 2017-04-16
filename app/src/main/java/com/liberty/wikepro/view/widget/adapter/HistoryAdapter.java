package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.DateWrapper;
import com.liberty.wikepro.model.bean.history;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.view.widget.HistoryListItemView;

import java.text.SimpleDateFormat;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class HistoryAdapter extends BaseRecyclerAdapter<itemType> {

    private static final int DATE=0x11;
    private static final int ITEM=0x12;
    private SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");

    public HistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case DATE:{
                return new BaseHolder<DateWrapper>(viewGroup, R.layout.history_head) {
                    @Override
                    public void setData(DateWrapper item) {
                        super.setData(item);
                        ((TextView)getView(R.id.timeHead)).setText(format.format(item.getDate()));
                        HistoryListItemView itemView=getView(R.id.circlePoint);
                        if (getAdapterPosition()==0){
                            itemView.setHeader(true);
                        }
                    }
                };
            }
            case ITEM:{
                return new BaseHolder<history>(viewGroup,R.layout.history_item) {
                    @Override
                    public void setData(history item) {
                        super.setData(item);
                        ((TextView)getView(R.id.courseName)).setText(item.getCourse());
                        ((TextView)getView(R.id.cvideoName)).setText(item.getCvideo());
                    }
                };
            }
        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        itemType item = getItem(position);
        if (item instanceof DateWrapper){
            return DATE;
        }else if (item instanceof history){
            return ITEM;
        }
        return super.getViewType(position);
    }
}
