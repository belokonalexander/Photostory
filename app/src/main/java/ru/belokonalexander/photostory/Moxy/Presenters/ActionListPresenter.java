package ru.belokonalexander.photostory.Moxy.Presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.Views.Recyclers.ActionRecyclerView;
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

    private boolean isExecuting = false;

    public <T> ActionListPresenter(SolidProvider<T> provider) {
        this.provider = provider;
    }

    public interface OnDataContentChangeListener{
        void onEmpty();
        void onFilled();
    }

    ActionRecyclerView.OnDataContentChangeListener onDataContentChangeListener;


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

    public void getData(UpdateMode updateMode){

        isExecuting = true;

        SimpleAsyncTask.create(() -> provider.getData(), result -> {
            isExecuting = false;
            getViewState().update(result,updateMode);
        }).execute();




    }

}
