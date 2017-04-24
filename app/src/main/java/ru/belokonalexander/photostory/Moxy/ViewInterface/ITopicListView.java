package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.belokonalexander.photostory.Models.Topic;

/**
 * Created by Alexander on 22.04.2017.
 */


public interface ITopicListView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void showTopic(Topic topic);


}
