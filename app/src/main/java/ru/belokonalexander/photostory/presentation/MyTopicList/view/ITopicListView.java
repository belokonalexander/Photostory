package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mikepenz.fastadapter.IItem;

import java.util.List;



/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicListView extends MvpView {

    @StateStrategyType(value = AddToEndStrategy.class)
    void afterLoadMoreTopics(List<IItem> part);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTopic(IItem item);
}
