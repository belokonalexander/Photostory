package ru.belokonalexander.photostory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Models.Topic;

public class ContentTopicActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    ViewGroup fragmentContainer;

    @Inject
    ContentTopicFragment contentTopicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_topic);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);

        if(savedInstanceState==null){
            contentTopicFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,contentTopicFragment)
                    .commit();
        }
    }
}
