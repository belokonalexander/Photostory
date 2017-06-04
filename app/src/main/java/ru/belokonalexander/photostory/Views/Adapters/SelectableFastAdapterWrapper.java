package ru.belokonalexander.photostory.Views.Adapters;

import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

/**
 * Created by Alexander on 04.06.2017.
 */

public class SelectableFastAdapterWrapper<Item extends IItem> {

    private FastItemAdapter<Item> adapter  = new FastItemAdapter<>();

    Item currentSelectedItem;

    FastAdapter.OnClickListener<Item> onClickListener;

    public SelectableFastAdapterWrapper(FastItemAdapter<Item> adapter) {
        this.adapter = adapter;
        initBehaviour();
    }

    public FastItemAdapter<Item> getFastItemAdapter() {
        return adapter;
    }

    private void initBehaviour(){

        adapter.withSelectable(true);
        adapter.withSelectWithItemUpdate(true);
        adapter.withSelectOnLongClick(true);
        adapter.withMultiSelect(true);

        adapter.withOnClickListener(new FastAdapter.OnClickListener<Item>() {
            @Override
            public boolean onClick(View v, IAdapter<Item> adapter, Item item, int position) {
                if(adapter.getFastAdapter().getSelectedItems().size()>0){
                    adapter.getFastAdapter().toggleSelection(position);
                } else {
                    if(!item.equals(currentSelectedItem)) {

                        if(currentSelectedItem!=null) {
                            int oldItemPosition = adapter.getFastAdapter().getPosition(currentSelectedItem);
                            if (oldItemPosition >= 0) {

                                ((ISelectedItem) currentSelectedItem).withSingleSelected(false);
                                adapter.getFastAdapter().notifyItemChanged(oldItemPosition);
                            }
                        }

                        currentSelectedItem = item;
                        ((ISelectedItem)currentSelectedItem).withSingleSelected(true);
                        adapter.getFastAdapter().notifyItemChanged(position);

                        if (onClickListener != null)
                            onClickListener.onClick(v, adapter, item, position);
                    }
                }
                return false;
            }
        });

    }

    public void setClickListener(FastAdapter.OnClickListener<Item> clickListener) {
        this.onClickListener = clickListener;
    }
}
