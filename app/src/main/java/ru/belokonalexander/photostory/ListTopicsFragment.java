package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.TopicAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.LazyLoadingRecycler;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;

import static ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo.UpdateMode.REWRITE;
import static ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo.UpdateMode.UPDATE;


/**
 * Created by Alexander on 22.04.2017.
 */

public class ListTopicsFragment extends MvpAppCompatFragment implements ITopicListView {

    @InjectPresenter()
    TopicListPresenter presenter;



    public final String IS_RECYCLER_DATA = "RECYCLER_DATA";

    @BindView(R.id.topics_recycler)
    LazyLoadingRecycler topicsRecycler;


    TopicAdapter topicAdapter;
    LinearLayoutManager lm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_list,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        App.getAppComponent().inject(this);

        lm = new LinearLayoutManager(getContext());

        topicsRecycler.setLayoutManager(lm);

        topicAdapter = new TopicAdapter(getMvpDelegate());

        topicAdapter.setFooterView(new ProgressBar(getContext()));
        topicAdapter.setHeaderView(new TextView(getContext()));


        topicsRecycler.setOnGetDataListener(() -> presenter.TopicListLoadMore(UPDATE));
        topicsRecycler.setOnRefreshListener(() -> presenter.TopicListLoadMore(REWRITE));

        topicsRecycler.setAdapter(topicAdapter);

        if(savedInstanceState==null){
            topicsRecycler.loadData();
        }

    }


    public void addNewTopic(Topic topic){
        //topicsRecycler.addItem(topic);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showTopic(Topic topic) {
        //делегируем обработку активити
        //т.к dual pane и может придется заполнять второй фрагмент
        ((ITopicListView)getActivity()).showTopic(topic);
    }



    @Override
    public void showNextPart(List<Topic> data, ProviderInfo updateMode) {
       topicAdapter.addData(data);
       resolveListUpdateState(updateMode);
    }

    @Override
    public void refreshList(List<Topic> data, ProviderInfo updateMode) {
       topicAdapter.rewriteData(data);
       topicsRecycler.unlockRefreshLoading();
       resolveListUpdateState(updateMode);
    }

    public void resolveListUpdateState(ProviderInfo updateMode){

        Logger.logThis(" UPDATE: " + updateMode.getInputUpdateMode());

        if(updateMode.isAllDataWasObtained()){
            topicsRecycler.lockLazyLoading();
            topicAdapter.hideFooter();
        } else {
            topicsRecycler.unlockLazyLoading();
            topicAdapter.showFooter();
        }
    }

}
