package com.liberty.libertylibrary.widget.NestListView;//package com.accurme.accurmedoctor.view.widget.NestListView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by LinJinFeng on 2017/1/9.
// */
//
//public abstract class HeaderFooterAdapter extends BaseNestedAdapter {
//
//    private List<View> headers=new ArrayList<>();
//
//    private List<View> footers=new ArrayList<>();
//
//    public HeaderFooterAdapter(int mItemLayoutId, List mDatas) {
//        super(mItemLayoutId, mDatas);
//    }
//
//    @Override
//    public View onCreateViewHolder(int position) {
//
//        return LayoutInflater.from();
//    }
//
//    public void addHeader(View view){
//        headers.add(view);
//    }
//
//    public void addFooter(View view){
//        footers.add(view);
//    }
//
//    @Override
//    public int getItemCount() {
//        return getmDatas().size()+headers.size()+footers.size();
//    }
//}
