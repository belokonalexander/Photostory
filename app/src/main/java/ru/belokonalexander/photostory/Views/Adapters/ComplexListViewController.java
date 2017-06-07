package ru.belokonalexander.photostory.Views.Adapters;

import com.mikepenz.fastadapter.IItem;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;

/**
 * Created by Alexander on 06.06.2017.
 */

public interface ComplexListViewController<T>  {

    Single<List<T>> getMoreFunction(Paginator paginator);

    void showPartialList(List<T> data);

    void showFullList(List<T> content);

    void enableLoadMore();

    void disableLoadMore(ComplexListManager.LazyLoadingStopCause cause);

    void deleteItems(Set<Integer> positions);

    void selectItem(IItem item);

    void changeSelectionState();

}
