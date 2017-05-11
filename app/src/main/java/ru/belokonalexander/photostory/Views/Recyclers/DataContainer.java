package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Models.Topic;

/**
 * Created by Alexander on 09.05.2017.
 */

public class DataContainer<T> {

    private List<T> data;

    private boolean allDataWasObtaining = false;

    private int pageSize = 50;

    public DataContainer() {
        data = new ArrayList<T>();
        //this.pageSize = pageSize;
    }

    private DataContainer(DataContainer<T> realState) {
        data = realState.getData();
        pageSize = realState.getPageSize();
        allDataWasObtaining = realState.allDataWasObtaining;
    }

    public List<T> getData() {
        return data;
    }

    public int getOffset(){
        return data.size();
    }

    public int getPageSize() {
        return pageSize;
    }

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

    public ProviderInfo rewrite(List<T> part){
        allDataWasObtaining = false;
        data.clear();
        return addPart(part, ProviderInfo.UpdateMode.REWRITE);
    }


    public boolean isAllDataWasObtaining() {
        return allDataWasObtaining;
    }


    public static<T> DataContainer<T>  cloneState(DataContainer<T> realState) {
        return new DataContainer<>(realState);
    }
}
