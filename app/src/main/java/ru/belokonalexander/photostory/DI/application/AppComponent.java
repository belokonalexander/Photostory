package ru.belokonalexander.photostory.DI.application;

import javax.inject.Singleton;


import dagger.Component;
import ru.belokonalexander.photostory.DI.common.TopicComponent;
import ru.belokonalexander.photostory.DI.common.TopicModule;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicModel;

/**
 * Created by Alexander on 22.04.2017.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    TopicComponent plus(TopicModule topicModel);


}
