package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.model.bean.direction;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.view.widget.RecommendView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liberty on 2017/3/26.
 */

public class RecommendAdapter extends BaseRecyclerAdapter<itemType> {
    private static final int TYPE_HEAD=0x11;
    private static final int TYPE_NORMAL=0x12;

    private List<Type> types=new ArrayList<>();

    public List<Type> getTypes() {
        return types;
    }

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
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
                return new BaseHolder<Type>(viewGroup,R.layout.recommend_item) {
                    @Override
                    public void setData(final Type item) {
                        super.setData(item);

//                        ImageView kindImg=getView(R.id.kind_img);
//                        ImageUtil.getCircleImageIntoImageView(getmContext(),
//                                kindImg, WikeApi.getInstance().getImageUrl(item.getTdev()),false);
                        RecommendView recommendView=getView(R.id.recommend_img);
                        recommendView.setOnCheckListener(new RecommendView.OnCheckListener() {
                            @Override
                            public void onChecked(boolean checked) {
                                if (checked){
                                    types.add(item);
                                }else {
                                    types.remove(item);
                                }
                            }
                        });
                        ImageUtil.getCircleImageIntoImageView(getmContext(),recommendView.getKindImg(),
                                WikeApi.getInstance().getImageUrl(item.getTdev()),true);
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
            Log.d("xxxxxxx","count="+count+"position="+position);
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
