package ru.belokonalexander.photostory.data.repositories.MyTopicList;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicRepository {

    Single<List<Topic>> getTopics(IPaginator paginator);

    Single<Boolean> deleteTopics(Set<Long> setIds);

}
