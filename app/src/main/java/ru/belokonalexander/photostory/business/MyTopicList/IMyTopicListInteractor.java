package ru.belokonalexander.photostory.business.MyTopicList;

import java.util.List;

import io.reactivex.Single;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicModel;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface IMyTopicListInteractor {

    Single<List<TopicModel>> getTopics(int offset);

}
