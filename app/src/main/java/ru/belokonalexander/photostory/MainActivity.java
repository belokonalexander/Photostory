package ru.belokonalexander.photostory;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.DoubleCort;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragmentActivity;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

public class MainActivity extends BaseFragmentActivity implements ITopicListView {




    @Inject
    TopicContentFragment topicContentFragment;

    @Inject
    TopicListFragment topicListFragment;


    @Nullable
    @BindView(R.id.fragment_container)
    ViewGroup fragmentContainer;

    @Nullable
    @BindView(R.id.fragment_container_list)
    ViewGroup fragmentContainerList;

    @Nullable
    @BindView(R.id.fragment_topic)
    ViewGroup fragmentTopic;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    boolean isDualMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);

        if(savedInstanceState==null) {

            logger.logThis("Привет: " + settings.getWorkMode());


            if (fragmentTopic == null) {
                // одиночное меню

                isDualMode = false;

                initContainers(true, new DoubleCort<>(R.id.fragment_container, topicListFragment));


            } else {
                //большой экран
                isDualMode = true;
                initContainers(true, new DoubleCort<>(R.id.fragment_container_list, topicListFragment),
                        new DoubleCort<>(R.id.fragment_topic, topicContentFragment));
            }

        } else {
            if(!isDualMode)
                initContainers(false, new DoubleCort<>(R.id.fragment_container, topicListFragment));
            else  initContainers(false, new DoubleCort<>(R.id.fragment_container_list, topicListFragment),
                    new DoubleCort<>(R.id.fragment_topic, topicContentFragment));
        }
    }



    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onRootSetted() {

    }


    @Override
    public void showTopic(Topic topic) {
       if(!isDualMode) {
           topicContentFragment.setShowedTopic(topic);
           openInContainer(topicContentFragment,R.id.fragment_container);

        }

        logger.logThis(" ---> " + topic.getId());
    }
}
