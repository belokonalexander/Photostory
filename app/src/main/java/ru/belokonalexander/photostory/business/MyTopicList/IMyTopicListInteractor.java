package ru.belokonalexander.photostory.business.MyTopicList;

import com.mikepenz.fastadapter.IItem;

import java.util.List;

import io.reactivex.Single;
import ru.belokonalexander.photostory.Views.Adapters.IPaginator;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface IMyTopicListInteractor {

    Single<List<IItem>> getTopicsForList(IPaginator paginator);

}
