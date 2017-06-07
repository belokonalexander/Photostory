package ru.belokonalexander.photostory.business.MyTopicList;

import com.mikepenz.fastadapter.IItem;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Mapper.Mapper;
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


    /**
     * возвращаются успешно удаленные элементы
     * @param iItem
     * @return
     */
    @Override
    public Single<Set<IItem>> deleteItems(final Set<IItem> iItem) {
        HashSet<Integer>  mask = new HashSet<>();
        return topicRepository.deleteTopics(itemsToIds(iItem, mask))
                .flatMap(aBoolean -> Single.just(Mapper.getEmptyMask(iItem,mask)));
    }

    private Set<Long> itemsToIds(final Set<IItem> iItems, Set<Integer> mask){
        Set<Long> set = new HashSet<>();
        int pos = 0;
        for(IItem item : iItems){
            if(item instanceof TopicHolderModel){
                set.add(((TopicHolderModel)item).getId());
                mask.add(pos);
            }

            pos++;
        }

        Logger.logThis("MASK: " + mask);

        return set;
    }

}
