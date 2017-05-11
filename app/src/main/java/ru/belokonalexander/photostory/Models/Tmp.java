package ru.belokonalexander.photostory.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;

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

                SimpleAsyncTask.create(new SimpleAsyncTask.InBackground<Object>() {
                    @Override
                    public Object doInBackground() {

                        int tag = new Random().nextInt(1000);

                        for(int i =0; i < 3; i++) {
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //Logger.logThis(" ---> " + "tick - " + tag);
                        }
                        return null;
                    }
                }).execute();

                return topics;
            }
        }).delay(1200, TimeUnit.MILLISECONDS);
    };

}
