package ru.belokonalexander.photostory.Views.Recyclers;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mikepenz.fastadapter.IItem;

import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListManager;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListViewController;
import ru.belokonalexander.photostory.Views.Adapters.Paginator;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.IListInteractor;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.IListView;

/**
 * Created by Alexander on 07.06.2017.
 */

@InjectViewState
public class LazyRecyclerPresenter extends MvpPresenter<IListView> implements ComplexListViewController<IItem>{


    private ComplexListManager listManager;
    private IListInteractor<IItem> interactor;

    public LazyRecyclerPresenter(IListInteractor<IItem> interactor, Paginator paginator) {
        this.listManager = new ComplexListManager<>(paginator,this);
        this.interactor = interactor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        listManager.loadMore();
    }

    @Override
    public Single<List<IItem>> getMoreFunction(Paginator paginator) {
        return interactor.getData(paginator);
    }

    @Override
    public void showPartialList(List<IItem> data) {
        getViewState().afterLoadMore(data);
    }

    @Override
    public void showFullList(List<IItem> content) {
        getViewState().showList(content);
    }

    @Override
    public void enableLoadMore() {
        getViewState().enableLoadMore();
    }

    @Override
    public void disableLoadMore(ComplexListManager.LazyLoadingStopCause cause) {
        getViewState().disableLoadMore(cause);
    }

    @Override
    public void deleteItems(Set<Integer> positions) {
        getViewState().deleteItem(positions);
    }



    public void onItemClick(IItem topicItem) {
        getViewState().itemClick(topicItem);
    }

    public void changeSelectionState() {
        getViewState().changeOnSelectTopicState();
    }


    public ComplexListManager getListManager() {
        return listManager;
    }
}
