package ru.belokonalexander.photostory.Views.Adapters;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
            controller.disableLoadMore(LazyLoadingStopCause.WAITING);
            loadMoreInProgress.setState(true);
            paginator.setOffset(content.size());
            controller.getMoreFunction(paginator).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleSuccessTopicList, this::handleFailureTopicList);
    }

    private void handleFailureTopicList(Throwable throwable) {
        throwable.printStackTrace();
        Logger.logThis(" Error: " + throwable);
        loadMoreInProgress.setState(false);
    }

    private void handleSuccessTopicList(List<T> data) {

        Logger.logThis(" Success  recieve: " + data);

        loadMoreInProgress.setState(false);

        paginator.calculateAllDataWasObtaining(data.size());
        int wasSize = content.size();

        content.addAll(data);

        if(wasSize==0)
            controller.showFullList(content);
        else controller.showPartialList(data);

        if(paginator.hasMore())
            controller.enableLoadMore();
        else controller.disableLoadMore(LazyLoadingStopCause.FINISH);


    }

    public void deleteItem(Set<IItem> items) {

        Iterator it = content.iterator();
        int pos = 0;
        Set<Integer> deleted = new HashSet<>();
        while(it.hasNext()){
            IItem item = (IItem) it.next();
            if(items.contains(item)){
                it.remove();
                deleted.add(pos);
            }
            pos++;
        }

        controller.deleteItems(deleted);

        if(paginator.hasMore())
            controller.enableLoadMore();
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void selectItem(IItem item) {
        controller.selectItem(item);
    }

    public void changeSelectionState() {
        controller.changeSelectionState();
    }

    public enum LazyLoadingStopCause{
        WAITING, FINISH;
    }

}
