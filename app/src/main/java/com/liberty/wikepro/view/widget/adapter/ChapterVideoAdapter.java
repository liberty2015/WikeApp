package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.view.widget.HistoryListItemView;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class ChapterVideoAdapter extends BaseRecyclerAdapter<itemType> {

    private final static int CHAPTER=0x11;
    private final static int VIDEO=0x12;

    public ChapterVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case CHAPTER:{
                return new BaseHolder<Chapter>(viewGroup, R.layout.cvideo_item_2) {
                    @Override
                    public void setData(Chapter item) {
                        super.setData(item);
                        HistoryListItemView listPoint=getView(R.id.listPoint);
                        int adapterPosition = this.getAdapterPosition();
                        int layoutPosition = this.getLayoutPosition();
                        Log.d("xxxxxxx","Chapter  adapterPosition="+adapterPosition+"  layoutPosition="+layoutPosition);
                        if (adapterPosition==1){
                            listPoint.setHeader(true);
                        }else if (adapterPosition==(ChapterVideoAdapter.this.getDataList().size()-1)){
                            listPoint.setFooter(true);
                        }
                    }
                };
            }
            case VIDEO:{
                return new BaseHolder<CVideo>(viewGroup,R.layout.cvideo_item) {
                    @Override
                    public void setData(CVideo item) {
                        super.setData(item);
                        int adapterPosition = this.getAdapterPosition();
                        int layoutPosition = this.getLayoutPosition();
                        Log.d("xxxxxxx","Video  adapterPosition="+adapterPosition+"  layoutPosition="+layoutPosition);
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
