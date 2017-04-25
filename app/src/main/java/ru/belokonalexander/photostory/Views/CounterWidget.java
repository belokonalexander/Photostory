package ru.belokonalexander.photostory.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.belokonalexander.photostory.Moxy.Presenters.CounterPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.CounterView;
import ru.belokonalexander.photostory.R;

/**
 * Created by Alexander on 24.04.2017.
 */

public class CounterWidget extends TextView implements CounterView {

    private MvpDelegate mParentDelegate;
    private MvpDelegate<CounterWidget> mMvpDelegate;

    @InjectPresenter
    CounterPresenter mCounterPresenter;


    public CounterWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        //LayoutInflater.from(context).inflate(R.layout.item_counter, this, true);
        //mCounterTextView = (TextView) findViewById(R.id.count_text);
        //View button = findViewById(R.id.plus_button);

        setOnClickListener(view -> mCounterPresenter.onPlusClick());
    }

    public void init(MvpDelegate parentDelegate) {
        mParentDelegate = parentDelegate;

        getMvpDelegate().onCreate();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getMvpDelegate().onSaveInstanceState();
        getMvpDelegate().onDetach();
    }

    public MvpDelegate<CounterWidget> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));
        return mMvpDelegate;
    }

    @Override
    public void showCount(int count) {
        Log.e("TAG", " count ----> " + count);
       setText(String.valueOf(count));
    }
}
