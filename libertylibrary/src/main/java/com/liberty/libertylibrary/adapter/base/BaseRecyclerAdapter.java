package com.liberty.libertylibrary.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.liberty.libertylibrary.adapter.DefaultEventDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by LinJinFeng on 2016/12/23.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected List<T> mObjects;

    private Context mContext;

    private EventDelegate mEventDelegate;

    protected ArrayList<ItemView> headers=new ArrayList<>();
    protected ArrayList<ItemView> footers=new ArrayList<>();


    public BaseRecyclerAdapter(Context context){
        init(context,new ArrayList<T>());
    }

    public BaseRecyclerAdapter(Context context,T[] objects){
        init(context, Arrays.asList(objects));
    }

    public BaseRecyclerAdapter(Context context,List<T> list){
        init(context,list);
    }

    public class GridHeaderSpan extends GridLayoutManager.SpanSizeLookup{

        private int maxCount;
        public GridHeaderSpan(int maxCount) {
            this.maxCount = maxCount;
        }

        @Override
        public int getSpanSize(int position) {
            if (headers.size()!=0){
                if (position<headers.size())return maxCount;
            }
            if (footers.size()!=0){
                int i=position-headers.size()-mObjects.size();
                if (i>=0){
                    return maxCount;
                }
            }
            return 1;
        }
    }

    public List<T> getDataList(){
        return mObjects;
    }

    public GridHeaderSpan obtainGridHeaderSpan(int maxCount){
        return new GridHeaderSpan(maxCount);
    }

    private void init(Context context,List<T> list){
        this.mObjects=list;
        this.mContext=context;
        lock=new ReentrantLock();
    }

    EventDelegate getEventDelegate(){
        if (mEventDelegate==null){
            mEventDelegate=new DefaultEventDelegate(this);
        }
        return mEventDelegate;
    }

    public View setMore(final int res, final OnLoadMoreListener listener) {
        FrameLayout container = new FrameLayout(getmContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getmContext()).inflate(res, container);
        getEventDelegate().setMore(container, listener);
        return container;
    }

    public View setMore(final View view, OnLoadMoreListener listener) {
        getEventDelegate().setMore(view, listener);
        return view;
    }

    public View setNoMore(final int res) {
        FrameLayout container = new FrameLayout(getmContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getmContext()).inflate(res, container);
        getEventDelegate().setNoMore(container);
        return container;
    }

    public View setNoMore(final View view) {
        getEventDelegate().setNoMore(view);
        return view;
    }

    public View setError(final int res) {
        FrameLayout container = new FrameLayout(getmContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getmContext()).inflate(res, container);
        getEventDelegate().setErrorMore(container);
        return container;
    }

    public View setError(final View view) {
        getEventDelegate().setErrorMore(view);
        return view;
    }

    public Context getmContext() {
        return mContext;
    }

    @Deprecated
    @Override
    public int getItemCount() {
        return headers.size()+mObjects.size()+footers.size();
    }

    public int getCount(){
        return mObjects.size();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=createSpViewByType(parent,viewType);
        if (view!=null){
            return new HeaderFooterHolder(view);
        }
        BaseHolder holder=onCreateHolder(parent,viewType);
        return holder;
    }

    public abstract BaseHolder onCreateHolder(ViewGroup viewGroup,int viewType);

    private View createSpViewByType(ViewGroup viewGroup,int viewType){
        for (ItemView itemView:headers){
            if (itemView.hashCode()==viewType){
                View view=itemView.onCreateView(viewGroup);
                StaggeredGridLayoutManager.LayoutParams params;
                if (view.getLayoutParams()!=null){
                    params=new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                }else {
                    params=new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                params.setFullSpan(true);
                view.setLayoutParams(params);
                return view;
            }
        }
        for (ItemView itemView:footers){
            if (itemView.hashCode()==viewType){
                View view=itemView.onCreateView(viewGroup);
                StaggeredGridLayoutManager.LayoutParams params;
                if (view.getLayoutParams()!=null){
                    params=new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                }else {
                    params=new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                params.setFullSpan(true);
                view.setLayoutParams(params);
                return view;
            }
        }
        return null;
    }

    @Override
    public final void onBindViewHolder(BaseHolder holder, int position) {
        holder.itemView.setId(position);
        if (headers.size()!=0&&position<headers.size()){
            headers.get(position).onBindView(holder.itemView);
            return;
        }
        int i=position-headers.size()-mObjects.size();
        if (footers.size()!=0&&i>=0){
            footers.get(i).onBindView(holder.itemView);
            return;
        }
        OnBindViewHolder(holder,position-headers.size());
    }

    public void OnBindViewHolder(BaseHolder holder,int position){
        holder.setData(getItem(position));
    }

    public T getItem(int position){
        return mObjects.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (headers.size()!=0){
            if (position<headers.size())
                return headers.get(position).hashCode();
        }
        if (footers.size()!=0){
            int i=position-headers.size()-mObjects.size();
            if (i>=0){
                return footers.get(i).hashCode();
            }
        }
        position-=headers.size();
        return getViewType(position);
    }

    public int getViewType(int position){
        return 0;
    }

    private Lock lock;

    public void add(T data){
        if (mEventDelegate!=null) mEventDelegate.addData(data==null?0:1);
        if (data!=null){
            try {
                lock.lock();
                mObjects.add(data);
            }finally {
                lock.unlock();
            }
        }
        notifyItemInserted(headers.size()+mObjects.size()-1);
    }

    public void addAll(Collection<? extends T> collection){
        if (mEventDelegate!=null)
            mEventDelegate.addData(collection==null?0:collection.size());
        if (collection!=null&&collection.size()!=0){
            try {
                lock.lock();
                mObjects.addAll(collection);
            }finally {
                lock.unlock();
            }
            int count=collection.size();
            notifyItemRangeInserted(headers.size()+getCount()-count+1,count);
        }
    }

    public void addAll(T[] items){
        if (mEventDelegate!=null)
            mEventDelegate.addData(items==null?0:items.length);
        if (items!=null&&items.length!=0){
            try {
                lock.lock();
                Collections.addAll(mObjects,items);
            }finally {
                lock.unlock();
            }
            int count=items.length;
            notifyItemRangeInserted(headers.size()+getCount()-count+1,count);
        }
    }

    public void remove(T data){
        int position=mObjects.indexOf(data);
        try {
            lock.lock();
            if (mObjects.remove(data)){
                notifyItemRemoved(headers.size()+position);
            }
        }finally {
            lock.unlock();
        }
    }

    public void remove(int position){
        try {
            lock.lock();
            mObjects.remove(position);
            notifyItemRemoved(headers.size()+position);
        }finally {
            lock.unlock();
        }
    }

    public void clear(){
        int count=mObjects.size();
        if (mEventDelegate!=null) mEventDelegate.clear();
        try {
            lock.lock();
            mObjects.clear();
            notifyItemRangeRemoved(headers.size(),count);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 让header和footer也加入到其中，达到解耦的目的
     */
    public interface ItemView{
        View onCreateView(ViewGroup viewGroup);

        void onBindView(View headerView);
    }

    /**
     * 以下是对header和footer的增加删除
     * @param view
     */
    public void addHeader(ItemView view){
        if (view==null) throw new NullPointerException("header can't be null");
        headers.add(view);
        notifyItemInserted(headers.size()-1);
    }

    public void addFooter(ItemView view){
        if (view==null) throw new NullPointerException("footer can't be null");
        footers.add(view);
        notifyItemInserted(headers.size()+getCount()+footers.size()-1);
    }

    public void removeHeader(ItemView view){
        int position=headers.indexOf(view);
        headers.remove(view);
        notifyItemRemoved(position);
    }

    public void removeFooter(ItemView view){
        int position=footers.indexOf(view);
        footers.remove(position);
        notifyItemRemoved(headers.size()+getCount()+position);
    }

    public void removeAllHeader(){
        int count =headers.size();
        headers.clear();
        notifyItemRangeRemoved(0,count);
    }

    public void removeAllFooter(){
        int count =footers.size();
        footers.clear();
        notifyItemRangeRemoved(headers.size()+getCount(),count);
    }

    public ItemView getHeader(int index){
        return headers.get(index);
    }

    public ItemView getFooter(int index){
        return footers.get(index);
    }

    public int getHeaderCount(){
        return headers.size();
    }

    public int getFooterCount(){
        return footers.size();
    }

    private class HeaderFooterHolder extends BaseHolder{

        HeaderFooterHolder(View itemView) {
            super(itemView);
        }
    }
}
