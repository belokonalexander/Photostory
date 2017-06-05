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

    private ISelectedItem currentSelectedItem;

    private boolean selectMainItem = false;

    private FastAdapter.OnClickListener<Item> onClickListener;

    public SelectableFastAdapterWrapper(FastItemAdapter<Item> adapter, boolean selectMainItem) {
        this.adapter = adapter;
        this.selectMainItem = selectMainItem;
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

                    if(adapter.getFastAdapter().getSelectedItems().size()>0 && item.isSelectable()){
                        adapter.getFastAdapter().toggleSelection(position);
                        return false;
                    }

                if (onClickListener != null)
                    onClickListener.onClick(v, adapter, item, position);

                return false;
            }
        });

    }

    public void select(Item item){
        if(selectMainItem && item instanceof ISelectedItem && !item.equals(currentSelectedItem)) {

            if(currentSelectedItem!=null) {
                int oldItemPosition = getFastItemAdapter().getPosition((Item) currentSelectedItem);
                if (oldItemPosition >= 0) {

                    currentSelectedItem.withSingleSelected(false);
                    getFastItemAdapter().notifyItemChanged(oldItemPosition);
                }
            }

            currentSelectedItem = (ISelectedItem) item;
            currentSelectedItem.withSingleSelected(true);
            getFastItemAdapter().notifyItemChanged(getFastItemAdapter().getPosition(item));

        }
    }

    public void setClickListener(FastAdapter.OnClickListener<Item> clickListener) {
        this.onClickListener = clickListener;
    }


}
