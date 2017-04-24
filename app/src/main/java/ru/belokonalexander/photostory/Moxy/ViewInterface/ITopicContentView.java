package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.belokonalexander.photostory.Models.Topic;

/**
 * Created by Alexander on 22.04.2017.
 */


public interface ITopicContentView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void fillContent(Topic topic);

}
