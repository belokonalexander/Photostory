package ru.belokonalexander.photostory.DI.Modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;

/**
 * Created by Alexander on 22.04.2017.
 */

@Module
public class AppModule {

    private Settings.WorkMode workMode;
    private Context appContext;

    public AppModule(@NonNull Context appContext, @NonNull Settings.WorkMode workMode) {
        this.appContext = appContext;
        this.workMode = workMode;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return appContext;
    }


/*
    @Provides
    @Singleton
    Logger provideLogger(){
        return new Logger();
    }
*/

    @Provides
    @Singleton
    Settings provideSettings(){
        return new Settings(workMode);
    }

}
