package ru.belokonalexander.photostory.presentation.MyTopicList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicModel;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.ITopicListView;

/**
 * Created by Alexander on 16.05.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

    private IMyTopicListInteractor myTopicListInteractor;

    public TopicListPresenter(IMyTopicListInteractor myTopicListInteractor) {
        this.myTopicListInteractor = myTopicListInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMore(0);
    }

    public void loadMore(int offset){
        myTopicListInteractor.getTopics(offset).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessTopicList, this::handleFailureTopicList);
    };

    private void handleSuccessTopicList(List<TopicModel> topics){
        getViewState().afterLoadMoreTopics(topics);
    }

    private void handleFailureTopicList(Throwable e){
        //todo
    }
}
