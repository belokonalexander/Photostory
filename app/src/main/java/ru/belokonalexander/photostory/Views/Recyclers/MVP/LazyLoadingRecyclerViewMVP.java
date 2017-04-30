package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.arellomobile.mvp.MvpDelegate;

import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.ActionRecyclerView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationProvider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;
import ru.belokonalexander.photostory.Views.Recyclers.UpdateMode;


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

    /**
     *  все данные из источника были получены
     */
    Boolean allDataWasObtained = false;

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

        if( (!canScrollVertically(1) || currentScrollSize > totalScrollSize - LOAD_BORDER) && !allDataWasObtained /*&& !loadingInProgress*/ && preloadingIterations > MIN_PRELOAD_SCROLL) {



            dataLoading(UpdateMode.ADD);
        }

        preloadingIterations++;
    }


    @Override
    public void onDataSizeChanged() {

        if(adapter.getRealItems()==0)
            enableEmptyController();
        else disableEmptyController();


        //данные обновились и список сдвинулся -> пробуем подгрузить данные и отменяем ограничение по минимальному порогу прокрутки
        preloadingIterations = MIN_PRELOAD_SCROLL + 1;
        onScrollHeightController();

    }


    @Override
    public void update(List<T> data, UpdateMode updateMode) {
        preloadingIterations = 0;
        //проверка - все ли данные отдал поставщик
        allDataWasObtained = data.size() < presenter.getPageSize();

        if(allDataWasObtained) {
            adapter.setDecoration(CommonAdapter.Decoration.SIMPLE);
            adapter.notifyDataSetChanged();
        } else if(adapter.getDecoration()!= CommonAdapter.Decoration.FOOTER)
            adapter.setDecoration(CommonAdapter.Decoration.FOOTER);

        super.update(data,updateMode);
    }



    @Override
    protected void dataLoading(UpdateMode updateMode) {

        if (updateMode == UpdateMode.ADD)
            presenter.setOffset(adapter.getRealItems());
        else presenter.setOffset(0);

        super.dataLoading(updateMode);
    }

    @Override
    public void initData() {
        dataLoading(UpdateMode.INITIAL);
    }

    @Override
    public void removeAll() {
        super.removeAll();
    }

    public LazyLoadingRecyclerViewMVP(Context context) {
        super(context);
    }

    public LazyLoadingRecyclerViewMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
