package ru.belokonalexander.photostory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.belokonalexander.photostory.Helpers.Views.BaseFragment;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.MainActivityPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;

/**
 * Created by Alexander on 22.04.2017.
 */

public class TopicListFragment extends BaseFragment implements IMainActivityView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "Main")
    MainActivityPresenter mainActivityPresenter;

    @OnClick(R.id.button)
    void onClick(){
        mainActivityPresenter.selectTopic(15);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_list,container,false);
        ButterKnife.bind(this, view);
        setTitle("Название проекта");
        return view;
    }

    @Override
    public void showTopic(Topic topic) {
        //подсветить клик
        //((IMainActivityView)getActivity()).showTopic(topic);
    }

    @Override
    public void returnToRoot() {

    }
}
