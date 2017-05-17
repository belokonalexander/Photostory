package ru.belokonalexander.photostory.data.repositories.MyTopicList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.Single;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 16.05.2017.
 */

public class TopicRepository implements ITopicRepository {

    @Override
    public Single<List<Topic>> getTopics(int offset) {
        return Single.fromCallable(new Callable<List<Topic>>() {
            @Override
            public List<Topic> call() throws Exception {

                List<Topic> topicList = new ArrayList<>();

                for(int i =0; i < 10; i++) {
                    topicList.add(new Topic());
                }

                return topicList;
            }
        });
    }
}
