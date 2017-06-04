package ru.belokonalexander.photostory.DI.application;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;

/**
 * Created by Alexander on 22.04.2017.
 */

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return appContext;
    }

    @Provides
    @Singleton
    Settings provideSettings(){
        return new Settings(Settings.WorkMode.DEBUG);
    }

    @Provides
    @Singleton
    ItemsToFlexibleMapper provideItemsToFlexibleMapper(){
        return new ItemsToFlexibleMapper();
    }

}
