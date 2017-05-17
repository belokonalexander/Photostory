package ru.belokonalexander.photostory.RxHelpers;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 09.05.2017.
 */

public class SimpleDisposableObserver<T> extends DisposableObserver<T> {

    private SimpleDisposableObserver(OnNext<T> onNextTask) {
        this.onNextTask = onNextTask;
    }

    private SimpleDisposableObserver(OnNext<T> onNextTask, OnComplete onComplete) {
        this.onNextTask = onNextTask;
        this.onCompleteTask = onComplete;
    }

    @Override
    public void onNext(@NonNull T t) {
        if(onNextTask !=null)
            onNextTask.onNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(onErrorTask !=null)
            onErrorTask.onError(e);
    }

    @Override
    public void onComplete() {
        this.dispose();
        if(onCompleteTask !=null)
            onCompleteTask.onComplete();
    }

    public static<T> SimpleDisposableObserver<T> create(OnNext<T> onNextTask){
        return new SimpleDisposableObserver<>(onNextTask);
    }

    public static<T> SimpleDisposableObserver<T> create(OnNext<T> onNextTask, OnComplete onComplete){
        return new SimpleDisposableObserver<>(onNextTask, onComplete);
    }


    private OnComplete onCompleteTask;
    private OnError onErrorTask;
    private OnNext<T> onNextTask;


    public interface OnComplete{
        void onComplete();
    }

    public interface OnError{
        void onError(Throwable e);
    }

    public interface OnNext<T>{
        void onNext(T t);
    }

}
