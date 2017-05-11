package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import com.arellomobile.mvp.MvpDelegate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 11.05.2017.
 */

public abstract class SelectableHeaderFooterAdapter<T> extends HeaderFooterAdapter<T> {

    public SelectableHeaderFooterAdapter(MvpDelegate delegate) {
        super(delegate);
    }


    /*enum SelectMode{
        MULTI, SINGLE;
    }*/

    public enum SelectedIntent{
        SELECT_ONE, SELECT_MORE;
    }

    //SelectMode selectMode = SelectMode.SINGLE;

    private Set<Integer> selectedItems = new HashSet<>();

    public void selectItem(int adapterPosition, SelectedIntent selectedIntent){
        if(headerIsEnabled)
            adapterPosition--;

        select(adapterPosition, selectedIntent);
    }

    private void select(int adapterPosition, SelectedIntent selectedIntent) {

        int shift = headerIsEnabled ? 1 : 0;

        if(selectedIntent==SelectedIntent.SELECT_ONE){

            if(selectedItems.contains(adapterPosition)){
                //очищаем элемент
            }

            for(int i = 0; i < selectedItems.size(); i++){

            }

        }

        if(selectedItems.contains(adapterPosition))
            selectedItems.remove(adapterPosition);
        else selectedItems.add(adapterPosition);
    }

}
