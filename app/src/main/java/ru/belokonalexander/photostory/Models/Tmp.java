package ru.belokonalexander.photostory.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Alexander on 09.05.2017.
 */

public class Tmp {

    public static Observable<List<Topic>> getAppTopics(int pageSize, int offset){
        return Observable.fromCallable(new Callable<List<Topic>>() {
            @Override
            public List<Topic> call() throws Exception {


                List<Topic> topics = new ArrayList<>();

                for(int i = offset; i < offset+pageSize; i++){
                    topics.add(new Topic((long)i));
                }

                return topics;
            }
        }).delay(3000, TimeUnit.MILLISECONDS);
    };

}
