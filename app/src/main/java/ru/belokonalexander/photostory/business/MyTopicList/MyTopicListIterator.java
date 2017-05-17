package ru.belokonalexander.photostory.business.MyTopicList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.ITopicRepository;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicModel;

/**
 * Created by Alexander on 16.05.2017.
 */

public class MyTopicListIterator implements IMyTopicListInteractor {

    ITopicRepository topicRepository;

    //todo mapper


    public MyTopicListIterator(ITopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Single<List<TopicModel>> getTopics(int offset) {
        return topicRepository.getTopics(offset).flatMap(new Function<List<Topic>, SingleSource<List<TopicModel>>>() {
            @Override
            public SingleSource<List<TopicModel>> apply(@NonNull List<Topic> topics) throws Exception {
                return Single.fromCallable(() -> {
                    List<TopicModel> topicModels = new ArrayList<>();
                    for(Topic topic : topics){
                        topicModels.add(new TopicModel(topic.getTitle(), topic.getId()));
                    }
                    return topicModels;
                });

            }
        });
    }

}
