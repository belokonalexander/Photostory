package ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders;

/**
 * Created by Alexander on 12.05.2017.
 */

public class SelectableItem<T> {

    private T model;

    public SelectableItem(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
