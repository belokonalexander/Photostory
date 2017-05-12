package ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Alexander on 12.05.2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T model;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(T model){
        this.model = model;
        bindView(model);
    }

    protected abstract void bindView(T model);

}
