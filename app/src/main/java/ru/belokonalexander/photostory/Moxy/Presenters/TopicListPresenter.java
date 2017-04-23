package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {



    public TopicListPresenter() {
    }

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    public void selectTopic(long id) {

        //todo get data from db
        Topic topic = new Topic();

        getViewState().showTopic(topic);

    }



}
