package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.IDataContainer;

/**
 * Created by Alexander on 09.05.2017.
 */

public class DataContainer<T> implements IDataContainer<T>{

    private List<T> data;

    private boolean allDataWasObtaining = false;

    private int pageSize = 2;

    public DataContainer() {
        data = new ArrayList<T>();
    }


    public List<T> getData() {
        return data;
    }

    @Override
    public int getOffset(){
        return data.size();
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public ProviderInfo addPart(List<T> part){
        allDataWasObtaining = false;
        return addPart(part, ProviderInfo.UpdateMode.UPDATE);
    }

    private ProviderInfo addPart(List<T> part, ProviderInfo.UpdateMode updateMode){
        if(part.size()< pageSize)
            allDataWasObtaining = true;

        data.addAll(part);

        return new ProviderInfo(updateMode,allDataWasObtaining);
    }

    @Override
    public ProviderInfo rewrite(List<T> part){
        allDataWasObtaining = false;
        data.clear();
        return addPart(part, ProviderInfo.UpdateMode.REWRITE);
    }


    public boolean isAllDataWasObtaining() {
        return allDataWasObtaining;
    }

    @Override
    public List<T> getLast(int lastN) {
        return new ArrayList<T>(data.subList(data.size()-lastN,data.size()));
    }

}
