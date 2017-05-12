package ru.belokonalexander.photostory.Views.Recyclers;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.SelectableItem;

/**
 * Created by Alexander on 09.05.2017.
 */

public class SelectableDataContainer<T>  {

    private List<SelectableItem<T>> data;



    private boolean allDataWasObtaining = false;

    private int pageSize = 50;

    public SelectableDataContainer() {
        data = new ArrayList<SelectableItem<T>>();
        //this.pageSize = pageSize;
    }

    private SelectableDataContainer(SelectableDataContainer<T> realState) {
        data = realState.getData();
        pageSize = realState.getPageSize();
        allDataWasObtaining = realState.allDataWasObtaining;
    }

    public List<SelectableItem<T>> getData() {
        return data;
    }

    public int getOffset(){
        return data.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public ProviderInfo addPart(List<SelectableItem<T>> part){
        allDataWasObtaining = false;
        return addPart(part, ProviderInfo.UpdateMode.UPDATE);
    }

    private ProviderInfo addPart(List<SelectableItem<T>> part, ProviderInfo.UpdateMode updateMode){
        if(part.size()< pageSize)
            allDataWasObtaining = true;

        data.addAll(part);

        return new ProviderInfo(updateMode,allDataWasObtaining);
    }

    public ProviderInfo rewrite(List<SelectableItem<T>> part){
        allDataWasObtaining = false;
        data.clear();
        return addPart(part, ProviderInfo.UpdateMode.REWRITE);
    }


    public boolean isAllDataWasObtaining() {
        return allDataWasObtaining;
    }


    public static<T> SelectableDataContainer<T> cloneState(SelectableDataContainer<T> realState) {
        return new SelectableDataContainer<>(realState);
    }
}
