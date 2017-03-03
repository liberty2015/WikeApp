package com.liberty.wikepro.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;
import com.liberty.libertylibrary.widget.ErrorEmptyLayout;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseRVFragment;
import com.liberty.wikepro.contact.KindContact;
import com.liberty.wikepro.model.bean.Type;
import com.liberty.wikepro.view.widget.adapter.KindAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KindFragment extends BaseRVFragment<KindContact.Presenter,Type> implements KindContact.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.common_list_fragment_layout;
    }

    @Override
    protected void initData() {
        initAdapter(new KindAdapter(getHoldActivity()),
                new OnRecyclerItemClickListener(list) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);

            }
        },true,false,new GridLayoutManager(getHoldActivity(),3));
    }

    @Override
    public void attachView() {

    }

    @Override
    public void showTypeList(List<Type> types) {

    }

    @Override
    public void showError() {
        errorEmptyLayout.setLayoutType(ErrorEmptyLayout.NETWORK_ERROR);
    }

    @Override
    public void complete() {
        refreshLayout.setRefreshing(false);
    }
}
