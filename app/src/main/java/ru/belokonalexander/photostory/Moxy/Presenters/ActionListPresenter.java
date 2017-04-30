package ru.belokonalexander.photostory.Moxy.Presenters;

import android.os.Handler;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.Views.Recyclers.ActionRecyclerView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationProvider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationSlider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;
import ru.belokonalexander.photostory.Views.Recyclers.UpdateMode;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Alexander on 25.04.2017.
 */

@InjectViewState
public class ActionListPresenter extends MvpPresenter<VIActionList> {


    private SolidProvider provider;

    //private boolean isExecuting = false;

    private final int CLICK_DELAY = 50;

    private boolean mainClickExecuting = false;

    public <T> ActionListPresenter() {

    }

    public void setProvider(SolidProvider provider) {
        this.provider = provider;
    }

    public SolidProvider getProvider() {
        return provider;
    }

    private OnDataContentChangeListener onDataContentChangeListener;

    private CommonAdapter.OnClickListener onClickListener;

    SimpleAsyncTask lastTask;

    public void getData(UpdateMode updateMode){


        if(updateMode!=UpdateMode.ADD && lastTask!=null)
            lastTask.interrupt();


        if(lastTask==null || !lastTask.isRunning()) {

            Log.e("TAG", "Данные подгружаются... " + provider);

            lastTask = SimpleAsyncTask.create(() -> provider.getData(), result -> {
                getViewState().update(result, updateMode);
            });

            lastTask.execute();
        }

    }



    public void setOnDataContentChangeListener(OnDataContentChangeListener onDataContentChangeListener) {
        this.onDataContentChangeListener = onDataContentChangeListener;
    }

    public void setOnClickListener(CommonAdapter.OnClickListener onClickListener) {

        this.onClickListener = item -> {
            if(mainClickExecuting)
                return;
            mainClickExecuting = true;
            new Handler().postDelayed(() -> {
                onClickListener.onClick(item);
                mainClickExecuting = false;
            }, CLICK_DELAY);
        };

        getViewState().setClickListener(this.onClickListener);
    }

    public void onDisableEmptyController(){
        if(onDataContentChangeListener!=null)
            onDataContentChangeListener.onFilled();
        getViewState().disableEmptyController();
    }

    public void onEnableEmptyController(){
        if(onDataContentChangeListener!=null)
            onDataContentChangeListener.onEmpty();
        getViewState().enableEmptyController();
    }

    public int getPageSize() {
        if(provider instanceof PaginationProvider)
            return ((PaginationProvider)provider).getPageSize();
        else return 0;
    }

    public void setOffset(int offset) {
        if(provider instanceof PaginationProvider)
            ((PaginationProvider) provider).setOffset(offset);
    }

    public interface OnDataContentChangeListener{
        void onEmpty();
        void onFilled();
    }

}
