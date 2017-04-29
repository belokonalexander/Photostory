package ru.belokonalexander.photostory.Moxy.ViewInterface;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.UpdateMode;

/**
 * Created by Alexander on 25.04.2017.
 */

public interface VIActionList<T> extends MvpView {


    void update(List<T> data, UpdateMode updateMode);

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "showControllerState")
    void enableEmptyController();

    @StateStrategyType(value = AddToEndSingleStrategyByTag.class, tag = "showControllerState")
    void disableEmptyController();

}
