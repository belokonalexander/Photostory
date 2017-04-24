package ru.belokonalexander.photostory;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements ITopicListView {


    @Inject
    Logger logger;

    @Inject
    Settings settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);
    }


    @Override
    public void showTopic(Topic topic) {
        ContentTopicFragment contentTopicFragment = (ContentTopicFragment)getSupportFragmentManager().findFragmentById(R.id.topic_content);

        if(contentTopicFragment==null){
            Intent intent = new Intent(this,ContentTopicActivity.class);
            intent.putExtra(settings.TOPIC_TAG,topic);
            startActivity(intent);
        } else {
            contentTopicFragment.updateContent(topic);
        }
    }
}
