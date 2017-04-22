package ru.belokonalexander.photostory.DI.Components;

import javax.inject.Singleton;

import dagger.Component;
import ru.belokonalexander.photostory.DI.Modules.AppModule;
import ru.belokonalexander.photostory.MainActivity;

/**
 * Created by Alexander on 22.04.2017.
 */

@Component (modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);

}
