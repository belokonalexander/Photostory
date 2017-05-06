package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.SimpleAsyncTask;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.TopicAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.LazyLoadingRecycler;


/**
 * Created by Alexander on 22.04.2017.
 */

public class ListTopicsFragment extends MvpAppCompatFragment implements ITopicListView {

    @InjectPresenter
    TopicListPresenter presenter;



    public final String IS_RECYCLER_DATA = "RECYCLER_DATA";

    @BindView(R.id.topics_recycler)
    LazyLoadingRecycler topicsRecycler;


    TopicAdapter topicAdapter;


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

        LinearLayoutManager lm = new LinearLayoutManager(getContext());

        topicsRecycler.setLayoutManager(lm);


        topicAdapter = new TopicAdapter();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                topicAdapter.setHeaderView(new TextView(getContext()));
            }
        }, 1000);


        topicAdapter.setFooterView(new ProgressBar(getContext()));

        topicAdapter.setOnClickListener(new CommonAdapter.OnClickListener<Topic>() {
            @Override
            public void onClick(Topic item) {
                Log.e("TAG", "CLICK: " + item.getTitle());
            }
        });



        topicsRecycler.setOnGetDataListener(() -> presenter.loadNextPart());




        topicsRecycler.setAdapter(topicAdapter);


        if(savedInstanceState==null){
            topicsRecycler.unlockLazyLoading();
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
        //т.к dual pane и может придется заполнят второй фрагмент
        ((ITopicListView)getActivity()).showTopic(topic);
    }

    @Override
    public void showNextPart(List<Topic> data, TopicListPresenter.UpdateMode updateMode) {
        topicAdapter.addData(data);

        switch (updateMode){
            case UPDATE:
                topicsRecycler.unlockLazyLoading();
                break;
            case FINISH:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        topicAdapter.hideFooter();
                    }
                }, 1000);
                break;
        }


    }




}
