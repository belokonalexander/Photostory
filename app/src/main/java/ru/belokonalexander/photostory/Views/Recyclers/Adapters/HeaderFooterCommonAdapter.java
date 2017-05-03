package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultFooter;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultHeader;


/**
 *  обобщенный адаптер
 */

public abstract class HeaderFooterCommonAdapter<T> extends CommonAdapter<T> {

    public final int FOOTER = 101;
    public final int HEADER = 102;



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DefaultFooter || holder instanceof DefaultHeader)
            return;

        super.onBindViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==FOOTER) {
            return new DefaultFooter( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_view,parent,false));
        }
        else if(viewType==HEADER)
            return new DefaultHeader( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_view,parent,false));
        else
            return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getFooterPosition()){
            return FOOTER;
        } else if(position==getHeaderPosition())
            return HEADER;
        else return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+2;
    }


    /*public class DefaultFooter extends RecyclerView.ViewHolder {
        public DefaultFooter(View itemView) {
            super(itemView);
        }
    }*/

   /* public class DefaultHeader extends RecyclerView.ViewHolder {
        public DefaultHeader(View itemView) {
            super(itemView);
        }
    }*/

    private int getHeaderPosition(){
        return getItemCount()-1;
    }

    private int getFooterPosition(){
        return 0;
    }

    boolean isNormalItem(int pos){
        return pos!=getHeaderPosition() && pos!=getFooterPosition();
    }

    @Override
    T getItem(int pos) {
        pos = pos - 1; //смещение из-за хедера

        return super.getItem(pos);
    }
}
