package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;

import ru.belokonalexander.photostory.Views.Recyclers.Adapters.TopicAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataContainer;
import ru.belokonalexander.photostory.Views.Recyclers.LazyLoadingRecycler;
import ru.belokonalexander.photostory.Views.Recyclers.ProviderInfo;


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
        //topicsRecycler.setItemAnimator(new SlideInUpAnimator());
        //topicsRecycler.setNestedScrollingEnabled(false);
        //topicsRecycler.getItemAnimator().setAddDuration(22000);


        //topicsRecycler.getItemAnimator().setAddDuration(2000);


        topicAdapter.setOnClickListener(new CommonAdapter.OnClickListener<Topic>() {
            @Override
            public void onClick(Topic item) {
                Log.e("TAG", "CLICK: " + item.getTitle());
            }
        });


        topicsRecycler.setOnGetDataListener(() -> presenter.loadNextPart(ProviderInfo.UpdateMode.UPDATE));
        topicsRecycler.setOnRefreshListener(() -> presenter.loadNextPart(ProviderInfo.UpdateMode.REWRITE));

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
        //т.к dual pane и может придется заполнят второй фрагмент
        ((ITopicListView)getActivity()).showTopic(topic);
    }



    @Override
    public void showNextPart(List<Topic> data, ProviderInfo updateMode) {

        Logger.logThis(" Обновили: " + updateMode.getInputUpdateMode());


        switch (updateMode.getInputUpdateMode()){
            case REWRITE:
                topicAdapter.rewriteData(data);
                topicsRecycler.unlockRefreshLoading();
                break;
            case UPDATE:
                topicAdapter.addData(data);
                break;
        }

        if(updateMode.isAllDataWasObtained()){
            topicsRecycler.lockLazyLoading();
            topicAdapter.hideFooter();
        } else {
            topicsRecycler.unlockLazyLoading();
            topicAdapter.showFooter();
        }

        /*if(updateMode.getInputUpdateMode()== ProviderInfo.UpdateMode.UPDATE) {
            topicAdapter.addData(data);
            if(updateMode.isAllDataWasObtained()){
                topicAdapter.hideFooter();
            } else
                topicsRecycler.unlockRefreshLoading();
        } else if (updateMode.getInputUpdateMode()== ProviderInfo.UpdateMode.REWRITE) {
            topicAdapter.rewriteData(data);
            if(updateMode.isAllDataWasObtained()){
                topicAdapter.hideFooter();
            } else {
                topicsRecycler.unlockRefreshLoading();

            }
        }*/

      /*
        switch (updateMode){
            case UPDATE:
                topicAdapter.addData(data);
                topicsRecycler.unlockLazyLoading();
                break;
            case FINISH:
                topicAdapter.addData(data);
                topicAdapter.hideFooter();
                break;
            case REWRITE:
                topicAdapter.rewriteData(data);
                topicsRecycler.unlockRefreshLoading();
                topicAdapter.showFooter();
                break;
        }*/


    }




}
