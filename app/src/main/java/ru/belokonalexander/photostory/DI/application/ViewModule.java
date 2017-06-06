package ru.belokonalexander.photostory.DI.application;

import android.content.Context;

import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.Views.Adapters.SelectableFastAdapterWrapper;

/**
 * Created by Alexander on 06.06.2017.
 */

@Module
public class ViewModule {

    @Provides
    @Named("ControlListPanel")
    SelectableFastAdapterWrapper<IItem> provideSelectableFastAdapterWrapper(Context context){
        return new SelectableFastAdapterWrapper<>(new FastItemAdapter<>(), context.getResources().getBoolean(R.bool.dualPaneMode));
    };


    @Provides
    Paginator providePaginator(Context context){

        //todo calculate pageSize for device

        return new Paginator(10);
    }

}
