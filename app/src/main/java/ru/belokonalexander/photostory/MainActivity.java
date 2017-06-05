package ru.belokonalexander.photostory;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.MyTopicListFragment;
import ru.belokonalexander.photostory.presentation.TopicContent.TopicContentFragment;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.topics_container)
    FrameLayout topicsContainer;

    @Nullable
    @BindView(R.id.topic_content_container)
    FrameLayout topicContentContainer;

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState==null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.topics_container, new MyTopicListFragment());
            ft.commit();
        }

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTopicContent(new Bundle());
            }
        }, 2000);*/

    }

    public void showTopicContent(Bundle args){

        TopicContentFragment topicContentFragment = new TopicContentFragment();
        topicContentFragment.setArguments(args);

        if(getResources().getBoolean(R.bool.dualPaneMode)){

            getSupportFragmentManager().beginTransaction().replace(R.id.topic_content_container, topicContentFragment)
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.topics_container, topicContentFragment)
                    .commit();
        }

    }

    public Toolbar getToolbar(){
        return toolbar;
    }

}
