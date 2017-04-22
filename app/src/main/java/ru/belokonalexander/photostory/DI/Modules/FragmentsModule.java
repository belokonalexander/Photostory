package ru.belokonalexander.photostory.DI.Modules;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.TopicContentFragment;
import ru.belokonalexander.photostory.TopicListFragment;

/**
 * Created by Alexander on 22.04.2017.
 */

@Module
public class FragmentsModule {

    @Provides
    TopicListFragment provideTopicListFragment(){
        return new TopicListFragment();
    }

    @Provides
    TopicContentFragment provideTopicContentFragment(){
        return new TopicContentFragment();
    }

}
