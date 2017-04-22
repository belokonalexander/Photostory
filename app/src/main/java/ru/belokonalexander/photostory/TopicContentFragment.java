package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragment;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.MainActivityPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;

/**
 * Created by Alexander on 22.04.2017.
 */

public class TopicContentFragment extends BaseFragment implements IMainActivityView{

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "Main")
    MainActivityPresenter mainActivityPresenter;

    @BindView(R.id.topic_id)
    TextView topicId;

    Topic showedTopic;

    public Topic getShowedTopic() {
        return showedTopic;
    }

    public void setShowedTopic(Topic showedTopic) {
        this.showedTopic = showedTopic;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_content,container,false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void showTopic(Topic topic) {
        setTitle("Список проектов");
        showedTopic = topic;
        topicId.setText(String.valueOf(topic.getId()));

    }

    @Override
    public void returnToRoot() {

    }
}
