package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Type;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public class KindAdapter extends BaseRecyclerAdapter<Type> {
    public KindAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        return new BaseHolder<Type>(viewGroup, R.layout.kind_item) {
            @Override
            public void setData(Type item) {
                super.setData(item);
                Glide.with(getmContext())
                        .load(item.getTdev())
                        .into((ImageView) getView(R.id.kind_img));
            }
        };
    }
}
