package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Models.Tmp;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.RxHelpers.SimpleDisposableObserver;
import ru.belokonalexander.photostory.Views.Recyclers.DataContainer;
import ru.belokonalexander.photostory.Views.Recyclers.ListManager;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

//todo как передать функцию для пагинации
    private ListManager<Topic> listManager = new ListManager<>(Tmp.getAppTopics(), this::updateTopicListView);

    public TopicListPresenter() {

    }

    public void loadNextPart(ProviderInfo.UpdateMode inputUpdateMode){
        listManager.execute(inputUpdateMode);
    }

    public void updateTopicListView(List<Topic> topics, ProviderInfo providerInfo){
        getViewState().showNextPart(topics, providerInfo);
    }




}
