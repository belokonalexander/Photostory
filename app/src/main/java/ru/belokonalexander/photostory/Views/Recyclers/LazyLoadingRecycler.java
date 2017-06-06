package ru.belokonalexander.photostory.Views.Recyclers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Alexander on 06.05.2017.
 */



public class LazyLoadingRecycler extends RecyclerView {

    private double LOAD_BORDER = 0; //px - количество пикселов до конца списка, перед началом подгрузки

  /*  SwipeRefreshLayout refreshLayout;
*/
    private boolean loadingIsDisable = true;

    private int previousSuccessTotalScroll = -1;
    private long lastPendingTime = -1;
    private long updateStep = 1000; //ms

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
            LOAD_BORDER = .3 * getHeight();
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

        int totalScrollSize = computeVerticalScrollRange();

        if(totalScrollSize<=0){
            return;
        }

        //текущая позиция скроллера - OFFSET
        int currentScrollOffset = computeVerticalScrollOffset();

        //ОТСТУП СКРОЛЛЕРА + РАЗМЕР ЭКРАНА (ПО БОЛЬШЕЙ СТОРОНЕ ЭКРАНА) > МАКСИМАЛЬНАЯ ВЫСОТА - ОТСТУП ПОДГРУЗКИ
        boolean needToLoadNewData = currentScrollOffset + Math.max(getHeight(),getWidth()) > totalScrollSize - LOAD_BORDER;
        //Logger.logThis("height: " + getHeight() + " | scroll offset: " + currentScrollOffset + " | totalScroll:"  +  totalScrollSize + " | LOAD_BORDER " + LOAD_BORDER);

        if( (!canScrollVertically(1) || needToLoadNewData) && !loadingIsDisable ) {
                if(onGetDataListener!=null) {
                    /**
                     *  необходимо избежать момента, когда значение скроллинга не успевает измениться,
                     *  для этого игнорируем обновление (по totalScroll последнего успешного значения) +
                     *  добавляем таймаут, чтобы был успех по totalScroll последнего значения, но по времени, когда
                     *  скроллер уже должен был подстроиться под актуальные данные
                     */
                    if(totalScrollSize!=previousSuccessTotalScroll || (lastPendingTime>0 && lastPendingTime+updateStep<System.currentTimeMillis())) {
                        lastPendingTime = -1;
                        previousSuccessTotalScroll = totalScrollSize;
                        loadData();
                    } else {
                        lastPendingTime = System.currentTimeMillis();
                    }
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
        onScrollHeightController();
    }

  /*  public void unlockRefreshLoading(){
        refreshLayout.setRefreshing(false);
        //onScrollHeightController();
    }*/

    public void setOnGetDataListener(OnGetDataListener onGetDataListener) {
        this.onGetDataListener = onGetDataListener;
    }

    /*public void setOnRefreshListener(RefreshDataListener onRefreshListener) {
        //TODO presenter must resolve startRefresh and endRefresh
        this.onRefreshListener = onRefreshListener;
        if(refreshLayout!=null){
            refreshLayout.setOnRefreshListener(onRefreshListener::onRefresh);
                refreshLayout.setEnabled(true);
        } else {
           initRefresh(onRefreshListener);
        }
    }*/

    /*private void initRefresh(RefreshDataListener onRefreshListener) {
        if(refreshLayout==null) {
            ViewParent parent;
            parent = getParent();
            if (parent instanceof SwipeRefreshLayout) {
                refreshLayout = (SwipeRefreshLayout) parent;
                if (onRefreshListener == null) {
                        refreshLayout.setEnabled(false);
                }
                else refreshLayout.setOnRefreshListener(onRefreshListener::onRefresh);
            } else throw new Resources.NotFoundException("Parent refresh layout was not found");
        }
    }*/


    public interface OnGetDataListener{
        void getData();
    }



}
