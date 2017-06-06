package ru.belokonalexander.photostory.Views.Adapters;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Helpers.BooleanWrapper;
import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 06.06.2017.
 */

public class ComplexListManager<T extends IItem>{

    private Paginator paginator;
    private List<T> content = new ArrayList<>();
    private Disposable disposableLoadMore;


    private ComplexListViewController<T> controller;
    private BooleanWrapper loadMoreInProgress = new BooleanWrapper();

    public ComplexListManager(Paginator paginator, ComplexListViewController<T> controller) {
        this.paginator = paginator;
        this.controller = controller;

    }

    public void loadMore(){
        //if(disposableLoadMore==null || disposableLoadMore.isDisposed()){
            controller.disableLoadMore(LazyLoadingStopCause.WAITING);
            loadMoreInProgress.setState(true);
            paginator.setOffset(content.size());
            controller.getMoreFunction(paginator).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleSuccessTopicList, this::handleFailureTopicList);
        //}
    }

    private void handleFailureTopicList(Throwable throwable) {
        loadMoreInProgress.setState(false);
    }

    private void handleSuccessTopicList(List<T> data) {

        loadMoreInProgress.setState(false);

        Logger.logThis(" GET MORE: " + content.size());

        paginator.calculateAllDataWasObtaining(data.size());
        int wasSize = content.size();

        content.addAll(data);

        if(wasSize==0)
            controller.showFullList(content);
        else controller.showPartialList(data);

        if(paginator.hasMore())
            controller.enableLoadMore();
        else controller.disableLoadMore(LazyLoadingStopCause.FINISH);

        //controller.enableLoadMore(paginator.hasMore(), isLoadMoreInProgress());
    }

    public void deleteItem(Set<Integer> positions) {

        int deletedItems = 0;
        for (int i = 0; i < content.size() - deletedItems; i++) {
            if (positions.contains(i)) {
                content.remove(i - deletedItems);
                deletedItems++;
            }
        }

        controller.deleteItems(positions);

        if(paginator.hasMore())
            controller.enableLoadMore();
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public enum LazyLoadingStopCause{
        WAITING, FINISH;
    }

}
