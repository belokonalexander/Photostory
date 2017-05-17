package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicModel;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicListView extends MvpView {

    void afterLoadMoreTopics(List<TopicModel> part);

}
