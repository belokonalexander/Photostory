package ru.belokonalexander.photostory.presentation.MyTopicList.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.ITopicListView;

/**
 * Created by Alexander on 16.05.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

    private IMyTopicListInteractor myTopicListInteractor;

    List<IItem> content = new ArrayList<>();

    public TopicListPresenter(IMyTopicListInteractor myTopicListInteractor) {
        this.myTopicListInteractor = myTopicListInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMore(0);
    }



    public void loadMore(int offset){
        myTopicListInteractor.getTopicsForList(offset).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessTopicList, this::handleFailureTopicList);
    };

    private void handleSuccessTopicList(List<IItem> topics){

        int wasSize = content.size();

        this.content.addAll(topics);

        if(wasSize==0)
            getViewState().showTopicList(content);
        else
            getViewState().afterLoadMoreTopics(topics);

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

      /*  for(int pos : topicsPositions){
            content.remove(pos);
        }*/

        getViewState().deleteTopics(topicsPositions);
    }
}
