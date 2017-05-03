package ru.belokonalexander.photostory.DI.Components;

import javax.inject.Singleton;

import dagger.Component;
import ru.belokonalexander.photostory.ContentTopicActivity;
import ru.belokonalexander.photostory.ContentTopicFragment;
import ru.belokonalexander.photostory.DI.Modules.AppModule;
import ru.belokonalexander.photostory.DI.Modules.FragmentsModule;

import ru.belokonalexander.photostory.ListTopicsFragment;
import ru.belokonalexander.photostory.MainActivity;


/**
 * Created by Alexander on 22.04.2017.
 */

@Component (modules = {AppModule.class, FragmentsModule.class})
@Singleton
public interface AppComponent {


    void inject(MainActivity mainActivity);
    void inject(ContentTopicActivity contentTopicActivity);

    void inject(ContentTopicFragment contentTopicFragment);
    void inject(ListTopicsFragment listTopicsFragment);


}
