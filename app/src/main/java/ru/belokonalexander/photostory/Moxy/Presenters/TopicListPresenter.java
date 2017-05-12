package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observable;
import ru.belokonalexander.photostory.Models.Tmp;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.ListManager;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {


    public TopicListPresenter() {

    }

    @Override
    protected void onFirstViewAttach() {
        topicListLoadMore(ProviderInfo.UpdateMode.UPDATE);
    }

    private ListManager<Topic> listManager = new ListManager<>(new ListManager.LoadingAction<Topic>() {
        @Override
        public Observable<List<Topic>> provideData(int pageSize, int offset) {
            return Tmp.getAppTopics(pageSize,offset);
        }

        @Override
        public void updateListView(List<Topic> part, ProviderInfo.UpdateMode updateMode) {
            updateTopicListView(part, updateMode);
        }

        @Override
        public void updateListViewState(ProviderInfo info) {
            getViewState().updateListState(info);
        }
    });


    public void topicListLoadMore(ProviderInfo.UpdateMode inputUpdateMode){
        listManager.execute(inputUpdateMode);
    }

    private void updateTopicListView(List<Topic> topics, ProviderInfo.UpdateMode updateMode){
        if(updateMode== ProviderInfo.UpdateMode.REWRITE)
            getViewState().refreshList(topics);
        else if(updateMode == ProviderInfo.UpdateMode.UPDATE){
            getViewState().showNextPart(topics);
        }
    }


    public void showTopic(Topic item) {
        getViewState().showTopic(item);
    }
}
