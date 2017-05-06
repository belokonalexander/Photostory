package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 05.05.2017.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<T> data;

    OnClickListener<T> onClickListener;

    public interface OnClickListener<T>{
        void onClick(T item);
    }

    public CommonAdapter() {
        data = new ArrayList<>();
    }

    public final void addData(List<T> part){
        int was = data.size()+1;
        data.addAll(part);
        notifyDataItemRangeInserted(was,part.size());
    }

    public void notifyDataItemRangeInserted(int positionStart, int itemCount){
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void setOnClickListener(OnClickListener<T> onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public T getItem(int position){
        return data.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVH(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindVH(holder,position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }

    abstract void onBindVH(RecyclerView.ViewHolder holder, int position);
    abstract RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType);
    abstract int getItemType(int position);


}
