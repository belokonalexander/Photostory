package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Events.ListEvent;
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

    private Topic topic;

    public TopicItemPresenter(Topic topic) {
        this.topic = topic;
        EventBus.getDefault().register(this);
    }


    public void selectItem() {
        deselectItems();
        getViewState().selectItem();
    }


    @Subscribe
    public void deselect(ListEvent event){
        //Logger.logThis();
        if(!event.getTopicId().equals(topic.getId())){
           getViewState().deselectItem();
        }
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
