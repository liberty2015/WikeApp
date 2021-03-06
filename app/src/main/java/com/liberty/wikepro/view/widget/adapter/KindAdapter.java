package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.model.bean.direction;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.util.ImageUtil;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public class KindAdapter extends BaseRecyclerAdapter<itemType> {

    private static final int TYPE_HEAD=0x11;
    private static final int TYPE_NORMAL=0x12;

    public KindAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
//        return new BaseHolder<Type>(viewGroup, R.layout.kind_item) {
//            @Override
//            public void setData(Type item) {
//                super.setData(item);
//                Glide.with(getmContext())
//                        .load(item.getTdev())
//                        .into((ImageView) getView(R.id.kind_img));
//            }
//        };
        switch (viewType){
            case TYPE_HEAD:{
                return new BaseHolder<direction>(viewGroup, R.layout.common_header) {
                    @Override
                    public void setData(direction item) {
                        super.setData(item);

                        TextView header=getView(R.id.header);
                        header.setTextSize(16);
                        header.setText(item.getName());
                    }
                };
            }
            case TYPE_NORMAL:{
                return new BaseHolder<Type>(viewGroup,R.layout.kind_item) {
                    @Override
                    public void setData(Type item) {
                        super.setData(item);
                        ImageView kindImg=getView(R.id.kind_img);
                        ImageUtil.getCircleImageIntoImageView(getmContext(),
                                kindImg, item.getTdev(),false);
                        TextView kindName=getView(R.id.kind_name);
                        kindName.setText(item.getName());
                    }
                };
            }
        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        itemType itemType=getItem(position);
        if (itemType instanceof direction){
            return TYPE_HEAD;
        }else if (itemType instanceof Type){
            return TYPE_NORMAL;
        }
        return super.getViewType(position);
    }

    public class MultiSpan extends GridHeaderSpan{
        int maxCount;

        public MultiSpan(int maxCount) {
            super(maxCount);
            this.maxCount=maxCount;
        }

        @Override
        public int getSpanSize(int position) {
            int count= super.getSpanSize(position);
//            Log.d("xxxxxxx","count="+count+"position="+position);
            if (count==1){
                int size=getHeaderCount();
                itemType type=getItem(size>0?position-size:position);
                if (type instanceof direction){
                    count=maxCount;
                }else if (type instanceof Type){
                    count=1;
                }
            }
            return count;
        }
    }

    @Override
    public MultiSpan obtainGridHeaderSpan(int maxCount) {
        return new MultiSpan(maxCount);
    }
}
