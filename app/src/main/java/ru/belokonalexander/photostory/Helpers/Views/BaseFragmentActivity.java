package ru.belokonalexander.photostory.Helpers.Views;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.Helpers.DoubleCort;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Helpers.Settings;
import ru.belokonalexander.photostory.R;

/**
 * Created by Alexander on 22.04.2017.
 */

public abstract class BaseFragmentActivity extends MvpAppCompatActivity {

    @Inject
    protected Settings settings;

    @Inject
    protected Logger logger;

    /**
     * кол-во контейнеров под фрагменты
     */
    ContainerManager[] containers;




    public void openInContainer(Fragment whichWillOpened, int containerId){

        /*try { // hide keyboard
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Bundle additional = whichWillOpened.getArguments();

        if(additional==null)
            additional = new Bundle();

        additional.putBoolean(settings.BACKPRESS_ENABLE, true);

        whichWillOpened.setArguments(additional);

        ContainerManager cm = ContainerManager.find(containers,containerId);
        if(cm == null)
            return;

        getSupportFragmentManager().beginTransaction()
                .add(containerId, whichWillOpened, cm.addNewFragment(false))
                .setCustomAnimations(R.anim.slide_in_left,0,0,R.anim.slide_out_right)
                .show(whichWillOpened)
                .addToBackStack(null)
                .commit();
    }


    public void initContainers(boolean fillFirst, DoubleCort... ids){
        containers = new ContainerManager[ids.length];

        for(int i =0; i < ids.length; i++){
            containers[i] = new ContainerManager((Integer) ids[i].getOne());
            if(fillFirst)
                getSupportFragmentManager().beginTransaction()
                .replace(containers[i].getContainerId(), (Fragment) ids[i].getTwo(), containers[i].addNewFragment(true))
                .commit();
        }
    }

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
        if(containers.length==1 && getSupportFragmentManager().getBackStackEntryCount()>0){
            ContainerManager cm = containers[0];
            cm.popNewFragment();
            getSupportFragmentManager().popBackStack();
            ((BaseFragment)getSupportFragmentManager().findFragmentByTag(cm.getTopFragmentTag())).fillToolbar();
            onRootSetted();
        }
        else {
            super.onBackPressed();
        }
    }
}
