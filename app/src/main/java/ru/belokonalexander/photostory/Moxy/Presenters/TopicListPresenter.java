package ru.belokonalexander.photostory.Moxy.Presenters;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {

    //model
    List<Topic> topics;

    boolean allDataWasObtaining = false;

    final int tmp_DataSize = 40;
    final int tmp_PartSize = 3;

    SimpleAsyncTask partLoadingTask;

    public TopicListPresenter() {
        topics = new ArrayList<>();
    }

    public void selectTopic(Topic topic) {
        getViewState().showTopic(topic);
    }

    public void loadNextPart(){



        partLoadingTask = SimpleAsyncTask.create(new SimpleAsyncTask.InBackground<List<Topic>>() {
            @Override
            public List<Topic> doInBackground() {

                List<Topic> part = new ArrayList<Topic>();

                Logger.logThis(" Подгружаем ");

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = topics.size(); i < topics.size() + tmp_PartSize ; i++) {

                    if(i > tmp_DataSize)
                        break;

                    part.add(new Topic());
                }

                return part;
            }
        }, new SimpleAsyncTask.PostExecute<List<Topic>>() {
            @Override
            public void doPostExecute(List<Topic> result) {

                if(result.size()<tmp_PartSize)
                    allDataWasObtaining = true;
                topics.addAll(result);

                UpdateMode updateMode = allDataWasObtaining ? UpdateMode.FINISH : UpdateMode.UPDATE;

                getViewState().showNextPart(result, updateMode);
            }
        });

        if(!allDataWasObtaining)
            partLoadingTask.execute();

    }

    public enum UpdateMode{
        UPDATE, FINISH;
    }

}
