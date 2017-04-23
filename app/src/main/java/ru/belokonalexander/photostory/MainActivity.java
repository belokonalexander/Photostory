package ru.belokonalexander.photostory;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

public class MainActivity extends MvpAppCompatActivity implements ITopicListView {


    @Inject
    Logger logger;

    @Inject
    Settings settings;


    @Inject
    ListTopicsFragment topicListFragment;


    @Nullable
    @BindView(R.id.fragment_container)
    ViewGroup commonContainer;

    @Nullable
    @BindView(R.id.fragment_container_list)
    ViewGroup containerListTopics;

    @Nullable
    @BindView(R.id.fragment_topic)
    ViewGroup containerTopic;

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


            if (containerTopic == null) {
                // одиночное меню
                isDualMode = false;

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, topicListFragment, null)
                        .show(topicListFragment)
                        .commit();


            } else {
                //большой экран
                isDualMode = true;

            }

        } else {

        }
    }



    @Override
    public void showTopic(Topic topic) {

       logger.logThis(" --------> " + topic.getId());

       if(!isDualMode) {

           ContentTopicFragment contentTopicFragment = new ContentTopicFragment();
           Bundle bundle = new Bundle();
           bundle.putSerializable("Topic",topic);
           contentTopicFragment.setArguments(bundle);
           //openInContainer(topicContentFragment,R.id.fragment_container);

           getSupportFragmentManager().beginTransaction()
                   .add(R.id.fragment_container, contentTopicFragment, "topic")
                   .setCustomAnimations(R.anim.slide_in_left,0,0,R.anim.slide_out_right)
                   .show(contentTopicFragment)
                   .addToBackStack(null)
                   .commit();

        }

        logger.logThis(" ---> " + topic.getId());
    }
}
