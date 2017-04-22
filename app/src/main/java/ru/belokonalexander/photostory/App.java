package ru.belokonalexander.photostory;

import android.app.Application;

import ru.belokonalexander.photostory.DI.Components.AppComponent;
import ru.belokonalexander.photostory.DI.Components.DaggerAppComponent;
import ru.belokonalexander.photostory.DI.Modules.AppModule;
import ru.belokonalexander.photostory.Helpers.Settings;

/**
 * Created by Alexander on 22.04.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this, Settings.WorkMode.Release)).build();
    }
}
