package ru.belokonalexander.photostory;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.belokonalexander.photostory.DI.application.AppComponent;
import ru.belokonalexander.photostory.DI.application.AppModule;
import ru.belokonalexander.photostory.DI.application.DaggerAppComponent;
import ru.belokonalexander.photostory.data.LocalStorage.Debug.DebugManager;


/**
 * Created by Alexander on 22.04.2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Inject
    DebugManager debugManager;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
        initData();
    }

    private void initData() {
        debugManager.fillTopicData();
    }

    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

}
