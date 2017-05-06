package ru.belokonalexander.photostory.Views.Recyclers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Alexander on 06.05.2017.
 */



public class LazyLoadingRecycler extends RecyclerView {

    private double LOAD_BORDER = 0; //px - количество пикселов до конца списка, перед началом подгрузкой

    /**
     * иногда при быстрой прокрутке, после загрузки данных, скроллер продолжает находиться в старом состоянии (как перед загрузкой)
     * из-за этого подгрузка может выполниться 2 раза подряд, добавление данного счетчика поможет избежать такой ситуации
     */
    //int preloadingIterations = 0;

    //public final int MIN_PRELOAD_SCROLL = 3;

    /**
     * запретить события для подгрузки
     * изначально подгрузка по скроллу запрещена, рекомендуется выполнить или Unlock или loadData
     */
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

        //ОТСТУП СКРОЛЛЕРА + РАЗМЕР ЭКРАНА > МАКСИМАЛЬНАЯ ВЫСОТА - ОТСТУП ПОДГРУЗКИ
        boolean needToLoadNewData = currentScrollOffset + getHeight() > totalScrollSize - LOAD_BORDER;


        if( (!canScrollVertically(1) || needToLoadNewData) && !loadingIsDisable) {

                if(onGetDataListener!=null) {
                    //задача разблокирования предоставляется поставщику
                    loadData();
                }
        }

    }

    OnGetDataListener onGetDataListener;


    private void loadData(){
        lockLazyLoading();
        onGetDataListener.getData();
    }

    public void lockLazyLoading(){
        loadingIsDisable = true;
    }

    public void unlockLazyLoading(){
        loadingIsDisable = false;
        //проверяем подгрузку по скроллу
        onScrollHeightController();
    }

    public void setOnGetDataListener(OnGetDataListener onGetDataListener) {
        this.onGetDataListener = onGetDataListener;
    }


    public interface OnGetDataListener{
        void getData();
    }

}
