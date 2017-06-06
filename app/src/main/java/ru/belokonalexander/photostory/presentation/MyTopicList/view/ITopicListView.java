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

    /**
     * два метода отличаются стратегией и выполняют одну и ту же функцию - добавление данных в список
     * но после пересоздания view, будет выполнен только @showTopicList, который в параметре @data
     * содержит ссылку на весь список, в отличие от @afterLoadMoreTopics
     */

    @StateStrategyType(value = SkipStrategy.class)
    void afterLoadMoreTopics(List<IItem> data);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTopicList(List<IItem> part);




    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTopic(IItem item);

    @StateStrategyType(value = SkipStrategy.class)
    void deleteTopics(Set<Integer> itemsPositions);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void changeOnSelectTopicState();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void enableLoadMore(boolean booleanWrapper);

}
