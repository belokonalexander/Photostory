package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mikepenz.fastadapter.IItem;

import java.util.List;
import java.util.Set;

import ru.belokonalexander.photostory.Moxy.Strategy.AddToEndSingleStrategyByTag;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListManager;

/**
 * Created by Alexander on 07.06.2017.
 */

public interface IListView extends MvpView{

    @StateStrategyType(value = SkipStrategy.class)
    void afterLoadMore(List<IItem> part);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showList(List<IItem> data);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void itemClick(IItem item);

    @StateStrategyType(value = SkipStrategy.class)
    void deleteItem(Set<Integer> itemsPositions);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void changeOnSelectTopicState();

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "recyclerState")
    void disableLoadMore(ComplexListManager.LazyLoadingStopCause cause);

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "recyclerState")
    void enableLoadMore();

 /*   @StateStrategyType(value = AddToEndSingleStrategy.class)
    void select(IItem iitem);*/

}
