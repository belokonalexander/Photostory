package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<IMainActivityView> {


    public MainActivityPresenter() {
    }


    public void selectTopic(long id) {

        //todo get data from db
        Topic topic = new Topic();

        getViewState().showTopic(topic);

    }

    public void clearNavigationState() {

        getViewState().returnToRoot();

    }

}
