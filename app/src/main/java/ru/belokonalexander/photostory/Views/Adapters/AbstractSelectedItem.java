package ru.belokonalexander.photostory.Views.Adapters;

import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.IClickable;
import com.mikepenz.fastadapter.items.AbstractItem;

/**
 * Created by Alexander on 04.06.2017.
 */

public abstract class AbstractSelectedItem <Item extends ISelectedItem & IClickable, VH extends RecyclerView.ViewHolder> extends AbstractItem<Item, VH> implements ISelectedItem<Item,VH>{

    protected boolean mSingleSelection = false;

    @Override
    public boolean isSingleSelected() {
        return mSingleSelection;
    }

    @Override
    public Item withSingleSelected(boolean singleSelected) {

        mSingleSelection = singleSelected;

        return (Item) this;
    }
}
