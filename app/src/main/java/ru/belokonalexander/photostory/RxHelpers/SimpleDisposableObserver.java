package ru.belokonalexander.photostory.RxHelpers;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Alexander on 09.05.2017.
 */

public class SimpleDisposableObserver<T> extends DisposableObserver<T> {

    public SimpleDisposableObserver(OnNext<T> onNextTask) {
        this.onNextTask = onNextTask;
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
        if(onCompleteTask !=null)
            onCompleteTask.onComplete();
        this.dispose();
    }

    public static<T> DisposableObserver<T> create(OnNext<T> onNextTask){
        return new SimpleDisposableObserver<>(onNextTask);
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
