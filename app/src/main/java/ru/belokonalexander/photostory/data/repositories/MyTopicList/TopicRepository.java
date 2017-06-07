package ru.belokonalexander.photostory.data.repositories.MyTopicList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;
import ru.belokonalexander.photostory.data.LocalStorage.Models.DaoSession;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 16.05.2017.
 */

public class TopicRepository implements ITopicRepository {


    private DaoSession daoSession;

    public TopicRepository(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Single<List<Topic>> getTopics(IPaginator paginator) {

        return Single.fromCallable(() -> daoSession.getTopicDao().queryBuilder()
                .offset(paginator.getOffset()).limit(paginator.getPageSize()).list()).delay(500, TimeUnit.MILLISECONDS);
    }

    @Override
    public Single<Boolean> deleteTopics(Set<Long> setIds) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getTopicDao().deleteByKeyInTx(setIds);
                return true;
            }
        });
    }
}
