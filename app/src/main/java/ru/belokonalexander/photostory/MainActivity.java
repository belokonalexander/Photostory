package ru.belokonalexander.photostory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.MainActivityPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicListView;

public class MainActivity extends MvpAppCompatActivity implements ITopicListView, IMainActivityView {


    @InjectPresenter
    MainActivityPresenter presenter;

    @Inject
    Logger logger;

    @Inject
    Settings settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.create_new_topic)
    FloatingActionButton fabCreate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);

        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createTopic();
            }
        });

    }


    @Override
    public void showTopic(Topic topic) {
        ContentTopicFragment contentTopicFragment = (ContentTopicFragment)getSupportFragmentManager().findFragmentById(R.id.topic_content);

        if(contentTopicFragment==null){
           Intent intent = new Intent(this,ContentTopicActivity.class);
            intent.putExtra("Topic",topic);
            startActivity(intent);


        } else {
            contentTopicFragment.updateContent(topic);
        }
    }

    @Override
    public void createTopic(int code) {
        Intent intent = new Intent(this,CreateTopicActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void addNewTopic(Topic topic) {
        Log.e("TAG"," create topic: " + topic);
         ListTopicsFragment listTopicsFragment = (ListTopicsFragment)getSupportFragmentManager().findFragmentById(R.id.topic_list);;
         listTopicsFragment.addNewTopic(topic);

    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.logThis(" ON START ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.logThis(" ON RESUME ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getMvpDelegate().onAttach();
        presenter.onResult(resultCode,requestCode, data);

    }
}
