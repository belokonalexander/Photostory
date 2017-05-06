package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultFooter;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.DefaultHeader;

/**
 * Created by Alexander on 05.05.2017.
 */

public abstract class HeaderFooterAdapter<T> extends CommonAdapter<T> {

    private final int FOOTER = 101;
    private final int HEADER = 102;

    //прианаччен ли адаптер к ресайклу
    private boolean attached = false;

    @Override
    public int getItemCount() {

        int addition = 0;
        if(headerIsEnabled)
            addition++;
        if(footerIsEnabled)
            addition++;

        return super.getItemCount() + addition;
    }

    @Override
    public T getItem(int position) {
        if(headerIsEnabled)
            position--;
        return super.getItem(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DefaultFooter || holder instanceof DefaultHeader){
           //пропускаем биндинг при заполнении футера и хедера - планируется, что уже готовая вью будет передана
        }
            else super.onBindViewHolder(holder, position);
    }

    private View headerView;
    private View footerView;

    private boolean footerIsEnabled;
    private boolean headerIsEnabled;





    /**
     * скрывается если только есть вьюха на хедер и она показана
     */
    public void hideHeader(){
        if(headerView!=null && headerIsEnabled) {
            notifyItemRemoved(getHeaderPosition());
            headerIsEnabled = false;
        }
    }

    /**
     * показывается если только есть вьюха на хедер и она скрыта
     */
    public void showHeader(){
        if(headerView!=null && !headerIsEnabled) {

            notifyItemInserted(getHeaderPosition());
            headerIsEnabled = true;
        }
    }

    public void hideFooter(){
        if(footerView!=null && footerIsEnabled) {

            notifyItemRemoved(getFooterPosition());
            footerIsEnabled = false;
        }
    }

    public void showFooter(){
        if(footerView!=null && !footerIsEnabled) {

            notifyItemInserted(getFooterPosition());
            footerIsEnabled = true;
        }
    }

    /**
     * прикрепляет вьюху в хедер
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        if(attached) {
            //если уже показывается список, то выполняем обновление
            updateHeader();
        }
        else headerIsEnabled = true;
    }

    private void updateHeader(){
        //если хедер был показан, то выполняется его обновление
        if(headerIsEnabled){
            notifyItemChanged(getHeaderPosition());
        } else
            //если хедер не показан, то показываем его
            showHeader();
    };

    private void updateFooter(){
        if(footerIsEnabled){
            notifyDataSetChanged();
        } else
            showFooter();
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        if(attached)
            updateFooter();
        else
            footerIsEnabled = true;
    }

    //Добавляет сдвиг в зависимости от header - footer
    @Override
    public void notifyDataItemRangeInserted(int positionStart, int itemCount) {
        int shift = 0;
        if(headerIsEnabled)
            shift++;

        notifyItemRangeInserted(positionStart+shift, itemCount);
    };

    /*@Override
    public void addData(List<T> part) {
        int was = data.size();

    }*/

    //TODO notify data override



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==FOOTER) {
                return new DefaultFooter(footerView);
        }
        else if(viewType==HEADER) {
                return new DefaultHeader(headerView);
        }

        return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(!attached)
            attached = true;
    }

    @Override
    public int getItemViewType(int position) {

        if(footerIsEnabled && position==getFooterPosition())
            return FOOTER;
        else if(headerIsEnabled && position==getHeaderPosition())
            return HEADER;
        else
            return super.getItemViewType(position);
    }

    private int getFooterPosition(){
        return getItemCount()-1;
    }

    private int getHeaderPosition(){
        return 0;
    }

}
