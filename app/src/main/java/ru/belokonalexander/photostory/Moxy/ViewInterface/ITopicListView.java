package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Strategy.AddToEndSingleStrategyByTag;
import ru.belokonalexander.photostory.Moxy.Strategy.TagAndTypeStrategy;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;


/**
 * Created by Alexander on 22.04.2017.
 */


public interface ITopicListView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void showTopic(Topic topic);

    @StateStrategyType(value = TagAndTypeStrategy.class, tag = "List$chain")
    void showNextPart(List<Topic> data);

    @StateStrategyType(value = TagAndTypeStrategy.class, tag = "List$single")
    void refreshList(List<Topic> data);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void updateListState(ProviderInfo updateMode);

}
