package ru.belokonalexander.photostory.Views.Recyclers;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewParent;

import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 06.05.2017.
 */



public class LazyLoadingRecycler extends RecyclerView {

    private double LOAD_BORDER = 0; //px - количество пикселов до конца списка, перед началом подгрузкой

    SwipeRefreshLayout refreshLayout;

    private boolean loadingIsDisable = true;


    public LazyLoadingRecycler(Context context) {
        super(context);
    }

    public LazyLoadingRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LazyLoadingRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(LOAD_BORDER==0)
            LOAD_BORDER = .5 * getHeight();

        if(refreshLayout==null){
            ViewParent parent;
            parent = getParent();
            if(parent instanceof SwipeRefreshLayout) {
                refreshLayout = (SwipeRefreshLayout) parent;
                if(onRefreshListener==null)
                    refreshLayout.setEnabled(false);
                else refreshLayout.setOnRefreshListener(() -> onRefreshListener.onRefresh());
            }
            else throw new Resources.NotFoundException("Parent refresh layout was not found");
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollHeightController();
            }
        });



    }

    private void onScrollHeightController() {
        //максимальная ВЫСОТА  = (getHeight() + ScrollOffset)
        int totalScrollSize = computeVerticalScrollRange();

        if(totalScrollSize<=0){
            //скроллер не инициализирован
            return;
        }

        //текущая позиция скроллера - OFFSET
        int currentScrollOffset = computeVerticalScrollOffset();

        //ОТСТУП СКРОЛЛЕРА + РАЗМЕР ЭКРАНА (ПО БОЛЬШЕЙ СТОРОНЕ ЭКРАНА) > МАКСИМАЛЬНАЯ ВЫСОТА - ОТСТУП ПОДГРУЗКИ
        boolean needToLoadNewData = currentScrollOffset + Math.max(getHeight(),getWidth()) > totalScrollSize - LOAD_BORDER;
        //Logger.logThis("scroll: " + (currentScrollOffset + getHeight()) + " / "  +  (totalScrollSize - LOAD_BORDER) + " / ---- " + totalScrollSize + " - " + computeVerticalScrollExtent());


        if( (!canScrollVertically(1) || needToLoadNewData) && !loadingIsDisable) {

                if(onGetDataListener!=null) {
                    //задача разблокирования предоставляется поставщику
                    loadData();
                }
        }

    }

    OnGetDataListener onGetDataListener;


    public void loadData(){
        lockLazyLoading();
        onGetDataListener.getData();
    }

    public void lockLazyLoading(){
        loadingIsDisable = true;
    }

    public void unlockLazyLoading(){
        loadingIsDisable = false;

    }

    public void unlockRefreshLoading(){
        refreshLayout.setRefreshing(false);
    }

    public void setOnGetDataListener(OnGetDataListener onGetDataListener) {
        this.onGetDataListener = onGetDataListener;
    }

    public void setOnRefreshListener(RefreshDataListener onRefreshListener) {

        this.onRefreshListener = onRefreshListener;
        if(refreshLayout!=null){
            refreshLayout.setOnRefreshListener(() -> onRefreshListener.onRefresh());
            refreshLayout.setEnabled(true);
        }
    }



    public interface OnGetDataListener{
        void getData();
    }

    RefreshDataListener onRefreshListener;

    public interface RefreshDataListener{
        void onRefresh();
    }

}
