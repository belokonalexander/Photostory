package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mikepenz.fastadapter.IItem;

import java.util.List;
import java.util.Set;


/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicListView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void afterLoadMoreTopics(List<IItem> part);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTopicList(List<IItem> part);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTopic(IItem item);

    @StateStrategyType(value = SkipStrategy.class)
    void deleteTopics(Set<Integer> itemsPositions);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void changeOnSelectTopicState();

}
