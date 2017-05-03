package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Search;

/**
 * Created by liberty on 2017/4/16.
 */

public class RecentAdapter extends BaseRecyclerAdapter<Search> {
    public RecentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        return new BaseHolder<Search>(viewGroup, R.layout.recent_item) {
                    @Override
                    public void setData(Search item) {
                        super.setData(item);
                        ((TextView)getView(R.id.query)).setText(item.getQuery());
                    }
                };
    }
}
