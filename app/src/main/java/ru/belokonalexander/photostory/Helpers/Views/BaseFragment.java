package ru.belokonalexander.photostory.Helpers.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.Components.DaggerAppComponent;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;


/**
 * Created by Alexander on 22.04.2017.
 */

public abstract class BaseFragment extends MvpAppCompatFragment {

    @Inject
    Settings settings;

    @Inject
    Logger logger;

    String title;

    public void fillToolbar(){
        if(getArguments()!=null && getArguments().getBoolean(settings.BACKPRESS_ENABLE)){
            getToolbar().setNavigationIcon(android.R.drawable.ic_menu_day);

            getToolbar().setNavigationOnClickListener(view1 -> {


                getActivity().onBackPressed();
                Fragment fragment = null;
                BaseActivity activity = (BaseActivity) getActivity();
                logger.logThis(" ---> " + activity.getSupportFragmentManager().getFragments());
                for(Fragment fr : activity.getSupportFragmentManager().getFragments()){
                    if(fr==null){
                        continue;
                    }
                    if(fr.getTag()!=null && fr.getTag().equals(String.valueOf(activity.getTopFragment()))){
                        fragment = fr;
                    }
                }

                if(fragment!=null)
                    fragment.onResume();

            });

        }  else {
            getToolbar().setNavigationIcon(null);
            //первый

        }

        getToolbar().setContentInsetStartWithNavigation(0);
        getToolbar().setTitle(title);
    }


    public void setTitle(String title){
        this.title = title;
    };

    public String getTitle() {
        return title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillToolbar();
    }

    public Toolbar getToolbar(){
        return ((BaseActivity)getActivity()).getToolbar();
    }

}
