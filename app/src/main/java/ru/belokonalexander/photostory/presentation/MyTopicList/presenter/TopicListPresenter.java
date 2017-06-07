package ru.belokonalexander.photostory.presentation.MyTopicList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mikepenz.fastadapter.IItem;

import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.ITopicListView;

/**
 * Created by Alexander on 16.05.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView>  {


    private IMyTopicListInteractor myTopicListInteractor;

    public TopicListPresenter(IMyTopicListInteractor myTopicListInteractor) {
        this.myTopicListInteractor = myTopicListInteractor;
    }

    public void deleteTopics(Set<IItem> selected){
        Logger.logThis(" Удаляю: " + selected);

        myTopicListInteractor.deleteItems(selected)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessDelete, this::handleFailureDelete);

    }

    private void handleSuccessDelete(Set<IItem> iItems) {
        getViewState().delete(iItems);
    }

    private void handleFailureDelete(Throwable throwable) {

    }



}
