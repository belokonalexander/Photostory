package ru.belokonalexander.photostory.data.repositories.MyTopicList;

import java.util.List;

import io.reactivex.Single;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface ITopicRepository {

    Single<List<Topic>> getTopics(int offset);

}
