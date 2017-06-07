package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mikepenz.fastadapter.IItem;

import java.util.Set;


/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicListView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void delete(Set<IItem> selected);


}
