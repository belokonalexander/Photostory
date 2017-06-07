package ru.belokonalexander.photostory.DI.common.Modules;

import com.mikepenz.fastadapter.IItem;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.DI.common.Scopes.TopicScope;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.business.ListUtils.IListInteractor;
import ru.belokonalexander.photostory.Views.Recyclers.LazyRecyclerPresenter;
import ru.belokonalexander.photostory.business.MappersUtils.ItemsToFlexibleMapper;
import ru.belokonalexander.photostory.business.MyTopicList.IMyTopicListInteractor;
import ru.belokonalexander.photostory.business.MyTopicList.MyTopicListInteractor;
import ru.belokonalexander.photostory.business.MyTopicList.MyTopicsInteractor;
import ru.belokonalexander.photostory.data.LocalStorage.Models.DaoSession;
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
    ITopicRepository provideITopicRepository(DaoSession daoSession){
        return new TopicRepository(daoSession);
    }

    @Provides
    @TopicScope
    IMyTopicListInteractor provideIMyTopicListInteractor(ITopicRepository repository){
        return new MyTopicsInteractor(repository);
    }

    @Provides
    @TopicScope
    TopicListPresenter provideTopicListPresenter(IMyTopicListInteractor interactor){
        return new TopicListPresenter(interactor);
    }


    @Provides
    @TopicScope
    IListInteractor<IItem> provideListInteractor(ITopicRepository repository, ItemsToFlexibleMapper mapper){
        return new MyTopicListInteractor(repository,mapper);
    }

    @Provides
    @TopicScope
    LazyRecyclerPresenter provideLazyRecyclerPresenter(IListInteractor<IItem> interactor, Paginator paginator){
        return new LazyRecyclerPresenter(interactor, paginator);
    }






}
