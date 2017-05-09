package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;

/**
 * Created by Alexander on 09.05.2017.
 */

public class ListManager<T> {

    private Observable<List<T>> provider;
    private ViewResponse<T> viewResponse;
    private DisposableObserver<List<T>> receiver;

    private DataContainer<T> listMeta = new DataContainer<>();

    public ListManager(Observable<List<T>> provider, ViewResponse<T> viewResponse) {
        this.provider = provider;
        this.viewResponse = viewResponse;
    }



    public interface ViewResponse<T>{
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

        receiver = createDisposable(viewResponse, inputUpdateMode,inputData);

        provider.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(receiver);

    }

    private DisposableObserver<List<T>> createDisposable(ViewResponse<T> viewResponse, ProviderInfo.UpdateMode inputUpdateMode, DataContainer<T> inputData) {
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
