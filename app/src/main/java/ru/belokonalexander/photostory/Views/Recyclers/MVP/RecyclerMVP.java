package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import ru.belokonalexander.photostory.Moxy.Presenters.ActionListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.Views.CounterWidget;
import ru.belokonalexander.photostory.Views.Recyclers.UpdateMode;

/**
 * Created by Alexander on 25.04.2017.
 */

public class RecyclerMVP extends RecyclerView  {


    MvpDelegate parentDelegate;
    MvpDelegate mvpDelegate;

    public RecyclerMVP(Context context) {
        super(context);
    }

    public RecyclerMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(MvpDelegate parentDelegate){
        this.parentDelegate = parentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getMvpDelegate().onSaveInstanceState();
        getMvpDelegate().onDetach();
    }


    public MvpDelegate getMvpDelegate() {
        if (mvpDelegate != null) {
            return mvpDelegate;
        }

        mvpDelegate = new MvpDelegate<>(this);
        mvpDelegate.setParentDelegate(parentDelegate, String.valueOf(getId()));
        return mvpDelegate;
    }
}
