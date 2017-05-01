package ru.belokonalexander.photostory.Moxy.Presenters;

import android.os.Handler;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationProvider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;
import ru.belokonalexander.photostory.Views.Recyclers.MVP.UpdateMode;

/**
 * Created by Alexander on 25.04.2017.
 */

@InjectViewState
public class ActionListPresenter extends MvpPresenter<VIActionList> {


    private SolidProvider provider;

    private List data = new ArrayList();

    private final int CLICK_DELAY = 50;

    private boolean mainClickExecuting = false;

    private boolean allDataWasObtaining = false;

    public ActionListPresenter() {

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

        if(updateMode==UpdateMode.ADD && allDataWasObtaining)
            return;

        if(updateMode!=UpdateMode.ADD)
            allDataWasObtaining = false;

        if(updateMode!=UpdateMode.ADD && lastTask!=null) {
            lastTask.interrupt();
        }

        if(provider instanceof PaginationProvider) {
            if(updateMode==UpdateMode.ADD)
                ((PaginationProvider) provider).setOffset(data.size());
             else
                ((PaginationProvider) provider).setOffset(0);

        }

        /*if(lastTask!=null)
            Log.e("TAG", "Работает? - " + lastTask.isRunning());*/

        if(lastTask==null || !lastTask.isRunning()) {

            lastTask = SimpleAsyncTask.create(() -> provider.getData(), result -> {

                if(updateMode==UpdateMode.ADD)
                    data.addAll(result);
                else {
                    data.clear();
                    data.addAll(result);
                }

                //проверка - все ли данные получены
                if(result.size() < getPageSize()){
                    allDataWasObtaining= true;
                    getViewState().updateData(data, UpdateMode.FINISH);
                } else
                    getViewState().updateData(data, updateMode);

                if(data.size()==0)
                    getViewState().enableEmptyController();
                else getViewState().disableEmptyController();

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
        else return Integer.MAX_VALUE;
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
