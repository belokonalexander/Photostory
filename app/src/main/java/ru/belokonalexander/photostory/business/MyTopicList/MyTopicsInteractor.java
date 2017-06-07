package ru.belokonalexander.photostory.business.MyTopicList;

import com.mikepenz.fastadapter.IItem;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Single;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.ITopicRepository;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;

/**
 * Created by Alexander on 07.06.2017.
 */

public class MyTopicsInteractor implements IMyTopicListInteractor{

    ITopicRepository topicRepository;
    ItemsToFlexibleMapper mapper;

    public MyTopicsInteractor(ITopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Override
    public Single<Set<IItem>> deleteItems(Set<IItem> iItem) {
        return topicRepository.deleteTopics(itemsToIds(iItem))
                .flatMap(aBoolean -> Single.just(iItem));
    }

    private Set<Long> itemsToIds(Set<IItem> iItems){
        Set<Long> set = new HashSet<>();
        for(IItem item : iItems){
            if(item instanceof TopicHolderModel){
                set.add(((TopicHolderModel)item).getId());
            }
        }
        return set;
    }

}
