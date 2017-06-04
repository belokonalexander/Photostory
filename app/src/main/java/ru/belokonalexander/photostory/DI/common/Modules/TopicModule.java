package ru.belokonalexander.photostory.DI.common.Modules;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.DI.common.Scopes.TopicScope;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.business.MyTopicList.MyTopicListInteractor;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.ITopicRepository;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.TopicRepository;
import ru.belokonalexander.photostory.presentation.MyTopicList.presenter.TopicListPresenter;

/**
 * Created by Alexander on 17.05.2017.
 */

@Module
public class TopicModule {

    @Provides
    @TopicScope
    ITopicRepository provideITopicRepository(){
        return new TopicRepository();
    }

    @Provides
    @TopicScope
    IMyTopicListInteractor provideIMyTopicListInteractor(ITopicRepository repository, ItemsToFlexibleMapper mapper){
        return new MyTopicListInteractor(repository, mapper);
    }

    @Provides
    @TopicScope
    TopicListPresenter provideTopicListPresenter(IMyTopicListInteractor interactor){
        return new TopicListPresenter(interactor);
    }

}
