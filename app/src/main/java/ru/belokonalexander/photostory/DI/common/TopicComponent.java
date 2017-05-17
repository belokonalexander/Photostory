package ru.belokonalexander.photostory.DI.common;

import dagger.Subcomponent;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.MyTopicListFragment;

/**
 * Created by Alexander on 17.05.2017.
 */

@Subcomponent(modules = TopicModule.class)
@TopicScope
public interface TopicComponent {

    void inject(MyTopicListFragment myTopicListFragment);

}
