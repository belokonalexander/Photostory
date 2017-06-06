package ru.belokonalexander.photostory.presentation.MyTopicList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.ITopicListView;

/**
 * Created by Alexander on 16.05.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

    private final Paginator paginator;
    private IMyTopicListInteractor myTopicListInteractor;

    private List<IItem> content = new ArrayList<>();

    public TopicListPresenter(IMyTopicListInteractor myTopicListInteractor, Paginator paginator) {
        this.myTopicListInteractor = myTopicListInteractor;
        this.paginator = paginator;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMore();
    }



    Disposable disposableLoadMore;

    public void loadMore(){

        if(disposableLoadMore==null || disposableLoadMore.isDisposed()) {
            paginator.setOffset(content.size());
            disposableLoadMore = myTopicListInteractor.getTopicsForList(paginator).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleSuccessTopicList, this::handleFailureTopicList);
        }
    };

    private void handleSuccessTopicList(List<IItem> topics){

        Logger.logThis(" Load DATA: " + paginator.getOffset());

        paginator.calculateAllDataWasObtaining(topics.size());
        int wasSize = content.size();

        content.addAll(topics);

        if(wasSize==0)
            getViewState().showTopicList(content);
        else
            getViewState().afterLoadMoreTopics(topics);


        getViewState().enableLoadMore(paginator.hasMore());
    }

    private void handleFailureTopicList(Throwable e){
        //todo
    }

    public void selectTopic(IItem topicItem) {
        getViewState().showTopic(topicItem);
    }

    public void changeTopicSelectionState() {
        getViewState().changeOnSelectTopicState();
    }

    public void deleteTopics(Set<Integer> topicsPositions) {

        int deletedItems = 0;
        for(int i = 0; i < content.size()-deletedItems; i++){
            if(topicsPositions.contains(i)){
                content.remove(i-deletedItems);
                deletedItems++;
            }
        }

        getViewState().deleteTopics(topicsPositions);
        getViewState().enableLoadMore(paginator.hasMore());
    }
}
