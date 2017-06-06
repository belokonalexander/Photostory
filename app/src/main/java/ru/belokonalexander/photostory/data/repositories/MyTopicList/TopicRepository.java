package ru.belokonalexander.photostory.data.repositories.MyTopicList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 16.05.2017.
 */

public class TopicRepository implements ITopicRepository {

    static int count = 0;

    @Override
    public Single<List<Topic>> getTopics(IPaginator paginator) {
        return Single.fromCallable(() -> {

            List<Topic> topicList = new ArrayList<>();

            for(int i = paginator.getOffset(); i < (paginator.getOffset() + paginator.getPageSize()) && i < 50; i++) {
                topicList.add(new Topic((long) i));
            }

            return topicList;
        }).delay(1000, TimeUnit.MILLISECONDS);
    }
}
