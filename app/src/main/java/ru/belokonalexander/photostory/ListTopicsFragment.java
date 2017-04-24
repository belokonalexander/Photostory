package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;
import ru.belokonalexander.photostory.Views.Recyclers.ActionRecyclerView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.TopicAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationProvider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.PaginationSlider;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SearchProvider;
import ru.belokonalexander.photostory.Views.Recyclers.SearchRecyclerView;

/**
 * Created by Alexander on 22.04.2017.
 */

public class ListTopicsFragment extends MvpAppCompatFragment implements ITopicListView {

    @InjectPresenter
    TopicListPresenter presenter;

    @Inject
    Logger logger;

    public final String IS_RECYCLER_DATA = "RECYCLER_DATA";

    @BindView(R.id.topics_recycler)
    SearchRecyclerView<Topic> topicsRecycler;
    TopicAdapter adapter;

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

        adapter = new TopicAdapter(getContext());

        adapter.setOnDelayedMainClick(this::showTopic);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());

        topicsRecycler.setLayoutManager(lm);


        topicsRecycler.init(adapter, new SearchProvider<Topic>(Topic.class, new PaginationProvider.PaginationProviderController<Topic>() {
            @Override
            public List<Topic> getData(PaginationSlider state) {
               return presenter.getData(state);
            }
        }));

        if(savedInstanceState==null)
            topicsRecycler.initData();
        else
            topicsRecycler.setInitialData((List<Topic>) savedInstanceState.getSerializable(IS_RECYCLER_DATA));

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IS_RECYCLER_DATA, topicsRecycler.getCurrentData());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showTopic(Topic topic) {
        //делегируем обработку активити
        ((ITopicListView)getActivity()).showTopic(topic);
    }




}