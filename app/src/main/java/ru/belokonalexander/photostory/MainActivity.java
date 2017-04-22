package ru.belokonalexander.photostory;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.Helpers.Views.BaseActivity;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.MainActivityPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;

public class MainActivity extends BaseActivity implements IMainActivityView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "Main")
    MainActivityPresenter mainActivityPresenter;

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

        logger.logThis("Привет: " + settings.getWorkMode());


        if(fragmentTopic==null) {
            // одиночное меню
            isDualMode = false;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(getContainerId(), topicListFragment, addNewFragment(true));
            ft.commit();


        } else {
            //большой экран
            isDualMode = true;
        }

    }

    @Override
    public void showTopic(Topic topic) {

        logger.logThis(" Topic: " + topic.getId());


        if(!isDualMode){
            openInThisContainer(topicContentFragment);
        } else {

        }



    }

    @Override
    public void returnToRoot() {

    }


    @Override
    protected int getContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onRootSetted() {
        mainActivityPresenter.clearNavigationState();
    }
}
