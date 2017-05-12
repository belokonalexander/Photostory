package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 12.05.2017.
 */

public interface IDataContainer<T> {

    ProviderInfo addPart(List<T> part);
    ProviderInfo rewrite(List<T> part);
    int getPageSize();
    int getOffset();
    boolean isAllDataWasObtaining();
    List<T> getLast(int lastN);
}
