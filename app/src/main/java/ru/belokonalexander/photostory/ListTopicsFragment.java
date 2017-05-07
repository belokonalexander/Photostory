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
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
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


        topicAdapter = new TopicAdapter();


        //topicAdapter.setFooterView(new ProgressBar(getContext()));
        topicAdapter.setHeaderView(new TextView(getContext()));
        topicsRecycler.setItemAnimator(new SlideInUpAnimator());

        //topicsRecycler.getItemAnimator().setAddDuration(2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Topic> arra= new ArrayList<Topic>();
                for(int i =0; i < 5; i++){
                    arra.add(new Topic((long) i * 12));
                }
                topicAdapter.addToTop(arra);
            }
        }, 3000);

        topicAdapter.setOnClickListener(new CommonAdapter.OnClickListener<Topic>() {
            @Override
            public void onClick(Topic item) {
                Log.e("TAG", "CLICK: " + item.getTitle());
            }
        });



        topicsRecycler.setOnGetDataListener(() -> presenter.loadNextPart());
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
    public void showNextPart(List<Topic> data, TopicListPresenter.UpdateMode updateMode) {
        topicAdapter.addData(data);

        switch (updateMode){
            case UPDATE:
                topicsRecycler.unlockLazyLoading();
                break;
            case FINISH:
                //lm.scrollToPosition(0);
                topicAdapter.hideFooter();
                break;
        }


    }




}
