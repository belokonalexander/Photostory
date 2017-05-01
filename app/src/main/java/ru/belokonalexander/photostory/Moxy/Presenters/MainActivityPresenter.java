package ru.belokonalexander.photostory.Moxy.Presenters;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.ViewInterface.IMainActivityView;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicContentView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Alexander on 22.04.2017.
 */

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<IMainActivityView> {

    final int CREATE_TOPIC_CODE =  101;

    public MainActivityPresenter() {

    }

    public void createTopic(){
        Log.e("TAG","----000: " + getViewState());
        getViewState().createTopic(CREATE_TOPIC_CODE);
    }

    public void onResult(int resultCode, int requestCode, Intent data) {
        //if(resultCode==RESULT_OK){
            if(requestCode==CREATE_TOPIC_CODE){
                getViewState().addNewTopic(new Topic());
            }
        //}
    }
}
