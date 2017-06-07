package ru.belokonalexander.photostory.DI.application;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.data.LocalStorage.Debug.DebugManager;
import ru.belokonalexander.photostory.data.LocalStorage.Models.DaoMaster;
import ru.belokonalexander.photostory.data.LocalStorage.Models.DaoSession;

/**
 * Created by Alexander on 06.06.2017.
 */

@Module
public class RepositoryModule {

    @Provides
    DaoSession provideDaoSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "db");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    };

    @Provides
    DebugManager provideDebugManager(DaoSession session){
        return new DebugManager(session);
    }
}
