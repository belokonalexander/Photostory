package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.belokonalexander.photostory.Moxy.ViewInterface.CounterView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class CounterPresenter extends MvpPresenter<CounterView> {
    private int mCount;

    public CounterPresenter() {
        getViewState().showCount(mCount);
    }

    public void onPlusClick() {
        mCount++;
        getViewState().showCount(mCount);
    }
}
