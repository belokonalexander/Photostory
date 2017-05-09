package ru.belokonalexander.photostory.Moxy.Presenters;

import android.os.Handler;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Tmp;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.DataContainer;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

    DataContainer<Topic> data = new DataContainer<>();

    SimpleAsyncTask partLoadingTask;

    public TopicListPresenter() {

    }

    DisposableObserver<List<Topic>> topicListConsumer;

    public void selectTopic(Topic topic) {
        getViewState().showTopic(topic);
    }

    public void loadNextPart(ProviderInfo.UpdateMode inputUpdateMode){

        DataContainer<Topic> inputData;

        if(inputUpdateMode== ProviderInfo.UpdateMode.UPDATE)
            inputData = DataContainer.cloneState(data);
        else inputData = new DataContainer<>();

        if(inputData.isAllDataWasObtaining())
            return;


        if(topicListConsumer!=null){
            if(!topicListConsumer.isDisposed())
                topicListConsumer.dispose();
        }

        Observable<List<Topic>> topicsGetter = Tmp.getAppTopics(inputData.getPageSize(),inputData.getOffset());
        topicListConsumer = getTopicDisposable(inputUpdateMode,inputData);
        topicsGetter.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(topicListConsumer);



    }

    private DisposableObserver<List<Topic>> getTopicDisposable(ProviderInfo.UpdateMode inputUpdateMode, DataContainer<Topic> inputData){

        return new DisposableObserver<List<Topic>>() {
            @Override
            public void onNext(List<Topic> topics) {
                data = inputData;
                ProviderInfo providerInfo = (inputUpdateMode == ProviderInfo.UpdateMode.REWRITE) ? data.rewrite(topics) : data.addPart(topics);
                getViewState().showNextPart(topics, providerInfo);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
    }



}
