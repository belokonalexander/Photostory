package ru.belokonalexander.photostory.Views.Adapters;

/**
 * Created by Alexander on 06.06.2017.
 */

public class Paginator implements IPaginator {

    private int pageSize;
    private int offset = 0;

    private boolean allDataWasObtaining = false;

    public Paginator(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean hasMore() {
        return !allDataWasObtaining;
    }

    public void calculateAllDataWasObtaining(int partSize) {
        this.allDataWasObtaining = partSize < pageSize;
    }
}
