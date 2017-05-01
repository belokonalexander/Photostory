package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.arellomobile.mvp.MvpDelegate;

import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;


/**
 * класс для ленивого отображения списка с подгрузкой
 * @param <T> тип элемента списка
 */
public class LazyLoadingRecyclerViewMVP<T>  extends ActionRecyclerViewMVP<T> {

    private double LOAD_BORDER = 0; //px - количество пикселов до конца списка, перед началом подгрузкой

    /**
     * иногда при быстрой прокрутке, после загрузки данных, скроллер продолжает находиться в старом состоянии (как перед загрузкой)
     * из-за этого подгрузка может выполниться 2 раза подряд, добавление данного счетчика поможет избежать такой ситуации
     */
    int preloadingIterations = 0;

    public final int MIN_PRELOAD_SCROLL = 3;


    @Override
    public void init(Class adapterHolderClass, SolidProvider<T> solidProvider, MvpDelegate delegate) {

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollHeightController();
            }
        });

        super.init(adapterHolderClass, solidProvider, delegate);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(LOAD_BORDER==0)
            LOAD_BORDER = .5 * getHeight();
    }

    void onScrollHeightController(){

        //максимальная позиция скроллера
        int totalScrollSize = computeVerticalScrollRange()-getHeight();
        if(totalScrollSize<=0){
            //скроллер не инициализирован
            return;
        }
        //текущая позиция скроллера
        int currentScrollSize = computeVerticalScrollOffset();

        if( (!canScrollVertically(1) || currentScrollSize > totalScrollSize - LOAD_BORDER) /* && !allDataWasObtained && !loadingInProgress*/ && preloadingIterations > MIN_PRELOAD_SCROLL) {

            dataLoading(UpdateMode.ADD);
        }

        preloadingIterations++;
    }



    @Override
    public void updateData(List<T> data, UpdateMode updateMode) {
        preloadingIterations = 0;

        if(updateMode == UpdateMode.FINISH) {
            adapter.setDecoration(CommonAdapter.Decoration.SIMPLE);
            adapter.notifyDataSetChanged();
        } else if(adapter.getDecoration()!= CommonAdapter.Decoration.FOOTER)
            adapter.setDecoration(CommonAdapter.Decoration.FOOTER);

        super.updateData(data, updateMode);
    }



    @Override
    public void initData() {
        dataLoading(UpdateMode.INITIAL);
    }

    public LazyLoadingRecyclerViewMVP(Context context) {
        super(context);
    }

    public LazyLoadingRecyclerViewMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
