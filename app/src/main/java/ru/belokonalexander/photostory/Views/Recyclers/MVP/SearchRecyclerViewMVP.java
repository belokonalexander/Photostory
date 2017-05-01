package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpDelegate;


import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SearchInputData;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SearchProvider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;
import ru.belokonalexander.photostory.Views.Search.EntitySearchView;
import ru.belokonalexander.photostory.Views.Search.SearchEntity;
import ru.belokonalexander.photostory.Views.Search.SearchItem;

/**
 * список с подгрузкой, реализующий функционал поиска
 * @param <T>
 * стоит обратить вниманеие на Override методы по добавлению элементов - они работают в зависимости от текущего фильтра
 */

public class SearchRecyclerViewMVP<T extends SearchEntity> extends LazyLoadingRecyclerViewMVP<T> {


    EntitySearchView searchView;

    ViewGroup searchFieldController;


    @Override
    public void enableEmptyController() {
        super.enableEmptyController();
        if(((SearchProvider)presenter.getProvider()).stateIsEmpty())
            hideSearchController();
    }


    @Override
    public void disableEmptyController() {
        super.disableEmptyController();
        showSearchController();
    }


    private void showSearchController() {
        if (searchFieldController != null) {
            searchFieldController.setVisibility(VISIBLE);
        }
    }

    private void hideSearchController() {
        if (searchFieldController != null) {
            searchFieldController.setVisibility(GONE);
        }
    }


    public SearchRecyclerViewMVP(Context context) {
        super(context);
    }

    public SearchRecyclerViewMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public void init(Class adapterHolderClass, SolidProvider<T> solidProvider, MvpDelegate delegate) {

        super.init(adapterHolderClass, solidProvider, delegate);

        if(presenter.getProvider() instanceof SearchProvider) {

            SearchProvider provider = (SearchProvider) presenter.getProvider();

            if (SearchItem.getSearchFieldsCount(provider.getItemType()) > 0) {

                if(searchView==null) {
                    LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    searchFieldController = (ViewGroup) layoutInflater.inflate(R.layout.item_search, null);
                    ViewGroup parent = (ViewGroup) getParent();


                    parent.addView(searchFieldController);
                    RelativeLayout.LayoutParams newParams = (RelativeLayout.LayoutParams) getLayoutParams();
                    newParams.addRule(RelativeLayout.BELOW, searchFieldController.getId());

                    searchView = (EntitySearchView) searchFieldController.findViewById(R.id.search_view);

                    searchView.initSearch(provider.getItemType());

                    searchView.setSearchTask(new EntitySearchView.SearchTask() {
                        @Override
                        public void startSearch(SearchInputData data) {
                            provider.update(data);
                            dataLoading(UpdateMode.REWRITE);
                        }

                        @Override
                        public void startEmpty(SearchInputData data) {
                            provider.update(data);
                            dataLoading(UpdateMode.REWRITE);
                        }
                    });
                } else
                    searchView.setKeyPosition(provider.getFilterKey());

            }

        }


    }

}


/**
     * проверяет, проходит ли значение по фильтру
     * @param item
     * @return
     *//*

    private T filter(T item){
        if(!getCastProvider().stateIsEmpty()){
            try {
                Field field = item.getClass().getDeclaredField(getCastProvider().getFilterKey());
                field.setAccessible(true);
                Object value = field.get(item);

                if(!getCastProvider().isFilterValue(value))
                    return null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return item;
    }

    private List<T> filter(List<T> data){

        if(!getCastProvider().stateIsEmpty()){
            List<T> filtered = new ArrayList<T>();
            for(T item : data){
                if(filter(item)!=null){
                    filtered.add(item);
                }
            }
            return filtered;
        }

        return data;
    }


    @SuppressWarnings("unchecked")
    private SearchProvider<T> getCastProvider(){
        return ((SearchProvider)provider);
    }

}
*/
