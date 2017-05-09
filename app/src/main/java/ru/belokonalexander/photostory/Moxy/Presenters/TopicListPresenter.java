package ru.belokonalexander.photostory.Moxy.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.DataContainer;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class TopicListPresenter extends MvpPresenter<ITopicListView> {



    DataContainer<Topic> data = new DataContainer<>();

    SimpleAsyncTask partLoadingTask;

    public TopicListPresenter() {

    }

    int tmp_DataSize = 44;

    public void selectTopic(Topic topic) {
        getViewState().showTopic(topic);
    }

    public void loadNextPart(ProviderInfo.UpdateMode inputUpdateMode){
        DataContainer<Topic> inputData;

        if(inputUpdateMode== ProviderInfo.UpdateMode.UPDATE)
            inputData = DataContainer.cloneState(data);
        else inputData = new DataContainer<>();

        partLoadingTask = SimpleAsyncTask.create(new SimpleAsyncTask.InBackground<List<Topic>>() {
            @Override
            public List<Topic> doInBackground() {

                List<Topic> part = new ArrayList<Topic>();

                Logger.logThis(" Подгружаем ");

                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = inputData.getOffset(); i < inputData.getPageSize() + inputData.getOffset() ; i++) {

                    if(i > tmp_DataSize)
                        break;

                    part.add(new Topic((long)i));
                }

                return part;
            }
        }, new SimpleAsyncTask.PostExecute<List<Topic>>() {
            @Override
            public void doPostExecute(List<Topic> result) {
                data = inputData;
                ProviderInfo providerInfo = (inputUpdateMode == ProviderInfo.UpdateMode.REWRITE) ? data.rewrite(result) : data.addPart(result);
                getViewState().showNextPart(result, providerInfo);
            }
        });

        if(!inputData.isAllDataWasObtaining())
            partLoadingTask.execute();

    }




}
