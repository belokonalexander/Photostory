package ru.belokonalexander.photostory.DI.common.Modules;

import com.mikepenz.fastadapter.IItem;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.DI.common.Scopes.TopicScope;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.Views.Recyclers.LazyRecyclerPresenter;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.business.MyTopicList.MyTopicListInteractor;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.ITopicRepository;
import ru.belokonalexander.photostory.data.repositories.MyTopicList.TopicRepository;

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
    IMyTopicListInteractor<IItem> provideIMyTopicListInteractor(ITopicRepository repository, ItemsToFlexibleMapper mapper){
        return new MyTopicListInteractor(repository, mapper);
    }

    @Provides
    @TopicScope
    LazyRecyclerPresenter provideLazyRecyclerPresenter(IMyTopicListInteractor<IItem> interactor, Paginator paginator){
        return new LazyRecyclerPresenter(interactor, paginator);
    }




}
