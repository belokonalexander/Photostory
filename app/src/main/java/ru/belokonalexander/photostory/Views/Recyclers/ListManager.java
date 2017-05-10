package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;

/**
 * Created by Alexander on 09.05.2017.
 */

public class ListManager<T> {

    //private Observable<List<T>> provider;
    private LoadingAction<T> loadingAction;
    private DisposableObserver<List<T>> receiver;

    private DataContainer<T> listMeta = new DataContainer<>();

    public ListManager(LoadingAction<T> loadingAction) {
        this.loadingAction = loadingAction;
    }



    public interface LoadingAction<T>{
        Observable<List<T>> provideData(int pageSize, int offset);
        void updateListView(List<T> part, ProviderInfo info);
    }

    public void execute(ProviderInfo.UpdateMode inputUpdateMode){

        DataContainer<T> inputData;

        if(inputUpdateMode== ProviderInfo.UpdateMode.UPDATE)
            inputData = DataContainer.cloneState(listMeta);
        else inputData = new DataContainer<>();

        if(inputData.isAllDataWasObtaining())
            return;

        if(receiver!=null && !receiver.isDisposed())
            receiver.dispose();

        receiver = createDisposable(loadingAction, inputUpdateMode,inputData);

        loadingAction.provideData(inputData.getPageSize(),inputData.getOffset()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(receiver);

    }

    private DisposableObserver<List<T>> createDisposable(LoadingAction<T> viewResponse, ProviderInfo.UpdateMode inputUpdateMode, DataContainer<T> inputData) {
        return SimpleDisposableObserver.create(new SimpleDisposableObserver.OnNext<List<T>>() {
            @Override
            public void onNext(List<T> topics) {
                listMeta = inputData;
                ProviderInfo providerInfo = (inputUpdateMode == ProviderInfo.UpdateMode.REWRITE) ? listMeta.rewrite(topics) : listMeta.addPart(topics);
                viewResponse.updateListView(topics, providerInfo);
            }
        });
    }

}
