package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragment;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

/**
 * Created by Alexander on 22.04.2017.
 */

public class TopicContentFragment extends BaseFragment  {

   // @InjectPresenter(type = PresenterType.GLOBAL, tag = "Main")
    //TopicListPresenter presenter;

    @BindView(R.id.topic_id)
    TextView topicId;

    Topic showedTopic;

    public Topic getShowedTopic() {
        return showedTopic;
    }

    public void setShowedTopic(Topic showedTopic) {
        this.showedTopic = showedTopic;
        fillViews();
    }

    private void fillViews() {
        topicId.setText(showedTopic.getId().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_content,container,false);
    }
    //


    @Override
    public void onStart() {
        super.onStart();

        //topicId.setText(String.valueOf(showedTopic.getId()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }
}
