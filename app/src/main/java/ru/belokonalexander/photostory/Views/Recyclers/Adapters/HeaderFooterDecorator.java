/*
package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultFooter;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultHeader;

*/
/**
 * Created by Alexander on 04.05.2017.
 *//*


public class HeaderFooterDecorator<T> implements IAdapter<T> {

    CommonAdapter<T> commonAdapter;

    public final int FOOTER = 101;
    public final int HEADER = 102;

    public HeaderFooterDecorator(CommonAdapter<T> adapter) {
        this.commonAdapter = adapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if(viewType==FOOTER) {
            return new DefaultFooter( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_view,parent,false));
        }
        else if(viewType==HEADER)
            return new DefaultHeader( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_view,parent,false));
        else
            return commonAdapter.onCreateVH(parent,viewType);
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DefaultFooter || holder instanceof DefaultHeader)
            return;

        commonAdapter.onBindVH(holder,position);
    }

    @Override
    public int getElementsCount() {
        return commonAdapter.getElementsCount() + 2;
    }




    @Override
    public T getItem(int pos) {
        //добавляется хедер
        Log.e("TAG", "POSITION: " + pos);
        pos--;
        return commonAdapter.getItem(pos);
    }


    @Override
    public int getItemType(int position) {
        if(position==getFooterPosition())
            return FOOTER;
        else if(position==getHeaderPosition())
            return HEADER;
        else
            return commonAdapter.getItemType(position);
    }

    @Override
    public RecyclerView.Adapter getInnerAdapter() {
        return commonAdapter;
    }

    private int getHeaderPosition(){
        return getElementsCount()-1;
    }

    private int getFooterPosition(){
        return 0;
    }
}
*/
