package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicItemPresenter extends MvpPresenter<ITopicView> {

    static int count = 0;

    Topic topic;

    public TopicItemPresenter(Topic topic) {
        Logger.logThis(" Иниуиализация презентера: " + ++count);
        this.topic = topic;
    }

    public void startTask(){
        Observable.intervalRange(0,100,0,500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getViewState().updateProgress(aLong.intValue());
                    }
                });

    }

    public void onClick() {
        Logger.logThis(" Click: " + topic.getTitle());
    }
}
