package ru.belokonalexander.photostory.DI.Modules;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.ContentTopicFragment;
import ru.belokonalexander.photostory.ListTopicsFragment;

/**
 * Created by Alexander on 22.04.2017.
 */

@Module
public class FragmentsModule {

    @Provides
    ListTopicsFragment provideTopicListFragment(){
        return new ListTopicsFragment();
    }

    @Provides
    ContentTopicFragment provideTopicContentFragment(){
        return new ContentTopicFragment();
    }

}
