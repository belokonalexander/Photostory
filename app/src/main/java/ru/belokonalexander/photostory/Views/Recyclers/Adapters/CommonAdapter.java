package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 05.05.2017.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<T> data;

    MvpDelegate parentDelegate;

    OnClickListener<T> onClickListener;

    public interface OnClickListener<T>{
        void onClick(T item);
    }

    public CommonAdapter() {
        data = new ArrayList<>();
    }

    public CommonAdapter(MvpDelegate delegate) {
        this();
        parentDelegate = delegate;
    }

    public MvpDelegate getParentDelegate() {
        return parentDelegate;
    }

    public void setParentDelegate(MvpDelegate parentDelegate) {
        this.parentDelegate = parentDelegate;
    }

    public final void addData(List<T> part){
        int was = data.size()+1;
        data.addAll(part);
        notifyDataItemRangeInserted(was,part.size());
    }

    public final void addToTop(List<T> part){
        data.addAll(0, part);
        notifyDataItemRangeInserted(0, part.size());
    }

    public final void addToTop(T item){
        data.add(0, item);
        notifyDataItemRangeInserted(0, 1);
    }

    public void notifyDataItemRangeInserted(int positionStart, int itemCount){

        Logger.logThis(" Обновляю с позиции: " + positionStart + " / " + itemCount);


        if(itemCount>1)
            notifyItemRangeInserted(positionStart, itemCount);
        else notifyItemInserted(positionStart);
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
