package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.IDataContainer;

/**
 * Created by Alexander on 09.05.2017.
 */

public class ListManager<T> {


    private LoadingAction<T> loadingAction;
    private DisposableObserver<List<T>> receiver;

    private IDataContainer<T> dataStore = new DataContainer<>();

    public ListManager(LoadingAction<T> loadingAction) {
        this.loadingAction = loadingAction;
    }

    public ProviderInfo.UpdateMode lastOperation;

    public interface LoadingAction<T>{
        Observable<List<T>> provideData(int pageSize, int offset);
        void updateListView(List<T> part, ProviderInfo.UpdateMode updateMode);
        void updateListViewState(ProviderInfo info);
    }

    public void execute(ProviderInfo.UpdateMode inputUpdateMode){

        int offset;
        boolean allDataWasObtaining;


        if(inputUpdateMode== ProviderInfo.UpdateMode.UPDATE) {
            offset = dataStore.getOffset();
            allDataWasObtaining = dataStore.isAllDataWasObtaining();
        }
        else {
            offset = 0;
            allDataWasObtaining = false;
        }

        if(allDataWasObtaining)
            return;

        if(receiver!=null && !receiver.isDisposed()) {
            if(lastOperation != inputUpdateMode)
                receiver.dispose();
            else
                return;
        }

        receiver = createDisposable(loadingAction, inputUpdateMode);

        lastOperation = inputUpdateMode;

        loadingAction.provideData(dataStore.getPageSize(),offset).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(receiver);

    }

    private DisposableObserver<List<T>> createDisposable(LoadingAction<T> viewResponse, ProviderInfo.UpdateMode inputUpdateMode) {

            return SimpleDisposableObserver.create(new SimpleDisposableObserver.OnNext<List<T>>() {

                @Override
                public void onNext(List<T> topics) {

                    if(inputUpdateMode== ProviderInfo.UpdateMode.REWRITE)
                        dataStore.rewrite(topics);
                    else if(inputUpdateMode== ProviderInfo.UpdateMode.UPDATE)
                        dataStore.addPart(topics);


                    viewResponse.updateListView(dataStore.getLast(topics.size()),inputUpdateMode);
                }
            }, new SimpleDisposableObserver.OnComplete() {
                @Override
                public void onComplete() {
                    viewResponse.updateListViewState(new ProviderInfo(inputUpdateMode, dataStore.isAllDataWasObtaining()));
                }
            });
    }

}
