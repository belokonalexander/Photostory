package ru.belokonalexander.photostory.Helpers.Views;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.R;

/**
 * Created by Alexander on 22.04.2017.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Inject
    protected Settings settings;

    @Inject
    protected Logger logger;


    int fragmentsCount = 0;

    public String addNewFragment(boolean starter){
        if(starter)
            fragmentsCount = 0;
        else fragmentsCount++;
        return String.valueOf(fragmentsCount);
    }

    public void popNewFragment(){
        fragmentsCount--;
    }

    public int getTopFragment(){
        return fragmentsCount;
    }

    public void openInThisContainer(Fragment whichWillOpened){

        try { // hide keyboard
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle additional = whichWillOpened.getArguments();

        if(additional==null)
            additional = new Bundle();

        additional.putBoolean(settings.BACKPRESS_ENABLE, true);

        whichWillOpened.setArguments(additional);


        getSupportFragmentManager().beginTransaction()
                .add(getContainerId(), whichWillOpened, addNewFragment(false))
                .setCustomAnimations(R.anim.slide_in_left,0,0,R.anim.slide_out_right)
                .show(whichWillOpened)
                .addToBackStack(null)
                .commit();
    }

    protected abstract int getContainerId();

    public abstract  Toolbar getToolbar();

    public abstract  void onRootSetted();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            popNewFragment();
            getSupportFragmentManager().popBackStack();
            ((BaseFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(getTopFragment()))).fillToolbar();
            onRootSetted();
        }
        else {
            super.onBackPressed();
        }
    }
}
