package ru.belokonalexander.photostory.Views.Adapters;

import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.IItem;

/**
 * Created by Alexander on 04.06.2017.
 */

public interface ISelectedItem<T, VH extends RecyclerView.ViewHolder> extends IItem <T,VH>{

    boolean isSingleSelected();

    T withSingleSelected(boolean singleSelected);

}
