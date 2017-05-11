package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Strategy.AddToEndSingleStrategyByTag;
import ru.belokonalexander.photostory.Moxy.Strategy.AddToEndWithCompressor;


/**
 * Created by Alexander on 22.04.2017.
 */


public interface ITopicView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void showTopic(Topic topic);

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "select")
    void selectItem();

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "select")
    void deselectItem();

    @StateStrategyType(value = AddToEndWithCompressor.class, tag = "task$do")
    void updateTask(Integer position);

    @StateStrategyType(value = AddToEndWithCompressor.class, tag = "task$stop")
    void finishUpdateTask();
}
