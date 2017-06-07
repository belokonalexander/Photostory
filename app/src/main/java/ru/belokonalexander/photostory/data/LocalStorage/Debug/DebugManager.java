package ru.belokonalexander.photostory.data.LocalStorage.Debug;

import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.data.LocalStorage.Models.DaoSession;
import ru.belokonalexander.photostory.data.LocalStorage.Models.Topic;

/**
 * Created by Alexander on 07.06.2017.
 */

public class DebugManager {


    private DaoSession daoSession;

    public final int topicLimit = 100;


    public DebugManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public void fillTopicData() {

        long count = daoSession.getTopicDao().count();

        for(int i = (int) count; i < topicLimit; i++){
            Logger.logThis(" Topic count: " + Topic.getDummy(i));
            daoSession.getTopicDao().insert(Topic.getDummy(i));
        }

        Logger.logThis(" Topics: " + daoSession.getTopicDao().loadAll().size());

    }
}
