package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



/**
 *  обобщенный адаптер
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<T> data;

    abstract  RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType);
    abstract  void onBindVH(RecyclerView.ViewHolder holder, int position);

    public void setData(List<T> data) {
        this.data = data;
        Log.e("TAG", "LIST: " + data.size());
    }


    CommonAdapter() {

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return onCreateVH(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            onBindVH(holder, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    OnClickListener<T> onClickListener;

    public void setOnClickListener(OnClickListener<T> onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener<T>{
        void onClick(T item);
    }

    T getItem(int pos){
        return data.get(pos);
    }

}
