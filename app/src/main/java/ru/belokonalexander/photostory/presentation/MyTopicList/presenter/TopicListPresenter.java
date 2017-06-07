package ru.belokonalexander.photostory.presentation.MyTopicList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mikepenz.fastadapter.IItem;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListManager;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListViewController;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.ITopicListView;

/**
 * Created by Alexander on 16.05.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> implements ComplexListViewController<IItem> {


    private IMyTopicListInteractor myTopicListInteractor;
    private ComplexListManager topicListManager;

    public TopicListPresenter(IMyTopicListInteractor myTopicListInteractor, Paginator paginator) {
        this.myTopicListInteractor = myTopicListInteractor;
        this.topicListManager = new ComplexListManager<>(paginator,this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        topicListManager.loadMore();
    }

    public ComplexListManager getTopicListManager(){
        return topicListManager;
    }


    @Override
    public Single<List<IItem>> getMoreFunction(Paginator paginator) {
        return myTopicListInteractor.getData(paginator);
    }

    @Override
    public void showPartialList(List<IItem> data) {
        getViewState().afterLoadMoreTopics(data);
    }

    @Override
    public void showFullList(List<IItem> content) {
        getViewState().showTopicList(content);
    }

    @Override
    public void enableLoadMore() {
        getViewState().enableLoadMore();
    }

    @Override
    public void disableLoadMore(ComplexListManager.LazyLoadingStopCause cause) {
        getViewState().disableLoadMore(cause);
    }


    @Override
    public void deleteItems(Set<Integer> positions) {
        getViewState().deleteTopics(positions);
    }


    public void selectTopic(IItem topicItem) {
        getViewState().showTopic(topicItem);
    }

    public void changeTopicSelectionState() {
        getViewState().changeOnSelectTopicState();
    }

}
