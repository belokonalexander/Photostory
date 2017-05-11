package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ru.belokonalexander.photostory.Events.ListEvent;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicView;
import ru.belokonalexander.photostory.RxHelpers.HashedTask;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicItemPresenter extends ViewHolderPresenter<ITopicView> {

    private Topic topic;

    public TopicItemPresenter(Topic topic) {
        Logger.logThis(" CREATE PRESENTER: ");
        this.topic = topic;
        EventBus.getDefault().register(this);
    }


    public void selectItem() {
        deselectItems();
        getViewState().selectItem();
    }

    public void startTask(){
        HashedTask<Integer> hashedTask = getTask();
        addTask(hashedTask);
        Observable.intervalRange(0,100,0,100, TimeUnit.MILLISECONDS)
            .map(Long::intValue)
            .subscribe(hashedTask.getTask());
    }

    @Subscribe
    public void deselect(ListEvent event){
        //Logger.logThis();
        if(!event.getTopicId().equals(topic.getId())){
           getViewState().deselectItem();
        }
    }



    public HashedTask<Integer> getTask(){
        return new HashedTask<>("Progress", SimpleDisposableObserver.create(new SimpleDisposableObserver.OnNext<Integer>() {
            @Override
            public void onNext(Integer integer) {
                getViewState().updateTask(integer);
            }
        }, new SimpleDisposableObserver.OnComplete() {
            @Override
            public void onComplete() {
                Logger.logThis(" FINISH TASK ");
                getViewState().finishUpdateTask();
            }
        }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void deselectItems() {
        EventBus.getDefault().post(new ListEvent(topic.getId()));
    }
}
