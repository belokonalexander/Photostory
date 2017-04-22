package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.belokonalexander.photostory.Models.Topic;

/**
 * Created by Alexander on 22.04.2017.
 */


public interface IMainActivityView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "navigation")
    void showTopic(Topic topic);

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "navigation")
    void returnToRoot();

}
