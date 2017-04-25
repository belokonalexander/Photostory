package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicContentPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicContentView;
import ru.belokonalexander.photostory.Views.CounterWidget;

/**
 * Created by Alexander on 22.04.2017.
 */

public class ContentTopicFragment extends MvpAppCompatFragment implements ITopicContentView {


    @BindView(R.id.topic_id)
    CounterWidget topicId;



    @InjectPresenter
    TopicContentPresenter presenter;

    @BindView(R.id.root)
    ViewGroup root;

    @Inject
    Settings settings;

    @ProvidePresenter
    TopicContentPresenter provideTopicContentPresenter(){

        try {
            Topic topic = (Topic) getArguments().getSerializable("Topic");
            return new TopicContentPresenter(topic);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return new TopicContentPresenter(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_content,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        App.getAppComponent().inject(this);


        topicId.init(getMvpDelegate());

    }

    CounterWidget testView;

    @Override
    public void fillContent(Topic topic) {
/*

        TextView textv = new TextView(getContext());

        if(topic!=null)
            textv.setText(" -> " + topic.getId());
        else textv.setText(" -> Empty ");

        root.addView(textv);


                boolean add = false;
                if(testView==null) {
                testView = new CounterWidget(getContext());
                    add = true;
                }

                testView.init(getMvpDelegate());

                if(add) {
                    testView.setText(" la la la");
                    root.addView(testView);
                }
*/

    }

    public void updateContent(Topic topic) {
        presenter.setData(topic);
    }
}
