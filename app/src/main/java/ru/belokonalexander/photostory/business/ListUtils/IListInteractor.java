package ru.belokonalexander.photostory.business.ListUtils;

import java.util.List;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;

/**
 * Created by Alexander on 07.06.2017.
 */

public interface IListInteractor<T> {

    Single<List<T>> getData(IPaginator paginator);


}
