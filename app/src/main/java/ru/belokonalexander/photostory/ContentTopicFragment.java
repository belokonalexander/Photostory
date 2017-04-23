package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragment;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicContentPresenter;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicContentView;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

/**
 * Created by Alexander on 22.04.2017.
 */

public class ContentTopicFragment extends MvpAppCompatFragment implements ITopicContentView {


    @BindView(R.id.topic_id)
    TextView topicId;

    @InjectPresenter()
    TopicContentPresenter presenter;

    @ProvidePresenter
    TopicContentPresenter provideTopicContentPresenter(){
        return new TopicContentPresenter((Topic) getArguments().getSerializable("Topic"));
    }


    //public Topic getShowedTopic() {
    //    return showedTopic;
    //}

    public void setShowedTopic(Topic showedTopic) {
       // this.showedTopic = showedTopic;

    }

    private void fillViews() {
        //topicId.setText(showedTopic.getId().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_content,container,false);
        ButterKnife.bind(this, view);
        return view;
    }
    //


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void fillContent(Topic topic) {
        topicId.setText("---> " + String.valueOf(topic.getId()));
    }
}
