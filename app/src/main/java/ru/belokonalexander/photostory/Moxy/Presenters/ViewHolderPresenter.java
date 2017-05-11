package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.RxHelpers.HashedTask;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;

/**
 * Created by Alexander on 11.05.2017.
 */

public class ViewHolderPresenter<T extends MvpView> extends MvpPresenter<T> {


     boolean viewIsAttached = false;

    /**
     * список задач на бэкграунде
     */
    private HashSet<HashedTask> tasks = new HashSet<>();


     protected void addTask(HashedTask task){
         tasks.add(task);
     }

     public boolean presenterCanBeRemoved(){
         for(HashedTask task : tasks){
             if(!task.getTask().isDisposed())
                 return false;
         }

         return !viewIsAttached;
     }

    @Override
    public void attachView(T view) {
        viewIsAttached = true;
        super.attachView(view);
    }

    @Override
    public void detachView(T view) {
        viewIsAttached = false;


        super.detachView(view);
    }

    @Override
    public void onDestroy() {
        Logger.logThis(" DESTROY PRESENTER");
        super.onDestroy();
    }


}
