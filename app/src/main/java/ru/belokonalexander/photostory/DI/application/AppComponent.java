package ru.belokonalexander.photostory.DI.application;

import javax.inject.Singleton;

import dagger.Component;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.common.Components.TopicComponent;
import ru.belokonalexander.photostory.DI.common.Modules.TopicModule;

/**
 * Created by Alexander on 22.04.2017.
 */

@Component(modules = {AppModule.class, ViewModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {

    TopicComponent plus(TopicModule topicModel);

    void inject(App app);

}
