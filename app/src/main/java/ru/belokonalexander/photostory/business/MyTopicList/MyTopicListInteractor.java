package ru.belokonalexander.photostory.business.MyTopicList;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.ITopicRepository;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;

/**
 * Created by Alexander on 16.05.2017.
 */

public class MyTopicListInteractor implements IMyTopicListInteractor<IItem> {

    ITopicRepository topicRepository;
    ItemsToFlexibleMapper mapper;

    public MyTopicListInteractor(ITopicRepository topicRepository, ItemsToFlexibleMapper mapper) {
        this.topicRepository = topicRepository;
        this.mapper = mapper;
    }

    @Override
    public Single<List<IItem>> getData(IPaginator paginator) {
        return topicRepository.getTopics(paginator).flatMap(new Function<List<Topic>, SingleSource<List<TopicHolderModel>>>() {
            @Override
            public SingleSource<List<TopicHolderModel>> apply(@NonNull List<Topic> topics) throws Exception {
                return Single.fromCallable(() -> {
                    List<TopicHolderModel> topicModels = new ArrayList<>();
                    for(Topic topic : topics){
                        topicModels.add(new TopicHolderModel(topic.getTitle(), topic.getId()));
                    }
                    return topicModels;
                });
            }
        }).flatMap(mapper.toFlexible());
    }



}
