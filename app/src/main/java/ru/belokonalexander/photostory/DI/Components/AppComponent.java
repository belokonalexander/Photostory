package ru.belokonalexander.photostory.DI.Components;

import javax.inject.Singleton;

import dagger.Component;
import ru.belokonalexander.photostory.DI.Modules.AppModule;
import ru.belokonalexander.photostory.DI.Modules.FragmentsModule;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragmentActivity;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragment;
import ru.belokonalexander.photostory.MainActivity;

/**
 * Created by Alexander on 22.04.2017.
 */

@Component (modules = {AppModule.class, FragmentsModule.class})
@Singleton
public interface AppComponent {

    void inject(BaseFragmentActivity mainActivity);
    void inject(BaseFragment mainActivity);
    void inject(MainActivity mainActivity);
}
