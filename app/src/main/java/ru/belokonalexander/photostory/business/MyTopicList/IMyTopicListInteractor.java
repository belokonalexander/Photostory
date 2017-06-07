package ru.belokonalexander.photostory.business.MyTopicList;

import com.mikepenz.fastadapter.IItem;

import java.util.Set;

import io.reactivex.Single;

/**
 * Created by Alexander on 16.05.2017.
 */

public interface IMyTopicListInteractor {

    Single<Set<IItem>> deleteItems(Set<IItem> iItem);

}
