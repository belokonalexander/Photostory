package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicContentView;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicContentPresenter extends MvpPresenter<ITopicContentView> {

    public static final String TAG = "ContentPresenter";

    private Topic currentTopic;

    public TopicContentPresenter(Topic currentTopic) {
        this.currentTopic = currentTopic;

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onDataChanged();
    }

    public void setData(Topic topic){
        currentTopic = topic;
        onDataChanged();
    }

    public void onDataChanged(){
        getViewState().fillContent(currentTopic);
    }
}
