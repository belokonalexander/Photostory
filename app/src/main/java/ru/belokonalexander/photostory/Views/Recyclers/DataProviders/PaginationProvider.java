package ru.belokonalexander.photostory.Views.Recyclers.DataProviders;

import java.util.List;

/**
 * поставщик контента в список
 * реализует страничную погрузку данных
 * @param <T>
 */

public class PaginationProvider<T> implements SolidProvider<T> {

    protected PaginationSlider state;
    protected PaginationProviderController<T> paginationProviderController;

    //стандартный размер данных
    protected int pageSize = 20;

    public PaginationProvider(PaginationProviderController<T> paginationProviderController) {
        this.paginationProviderController = paginationProviderController;
        this.state = new PaginationSlider(pageSize);
    }

    public PaginationProvider(PaginationProviderController<T> paginationProviderController, PaginationSlider state){
        this.paginationProviderController = paginationProviderController;
        this.state = state;
    }

    public PaginationProvider() {
    }

    @Override
    public List<T> getData() {
        return paginationProviderController.getData(state);
    }

    public void setOffset(int offset) {
        state.setOffset(offset);
    }

    public int getPageSize(){
        return pageSize;
    }

    public interface PaginationProviderController<T>{
        List<T> getData(PaginationSlider state);
    }

    @Override
    public String toString() {
        return "PaginationProvider{" +
                "state=" + state +
                ", paginationProviderController=" + paginationProviderController +
                ", pageSize=" + pageSize +
                '}';
    }
}
