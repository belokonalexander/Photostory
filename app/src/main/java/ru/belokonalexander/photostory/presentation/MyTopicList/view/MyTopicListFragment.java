package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.mikepenz.fastadapter.IItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.common.Modules.TopicModule;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.MainActivity;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.MvpLazyRecycler;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;

/**
 * Created by Alexander on 16.05.2017.
 */

public class MyTopicListFragment extends MvpAppCompatFragment {


    @BindView(R.id.topics_recycler)
    MvpLazyRecycler lazyRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.get(getContext()).getAppComponent().plus(new TopicModule()).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_list,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        lazyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        lazyRecycler.setOnClickListener(new MvpLazyRecycler.OnItemClickListener() {
            @Override
            public void onClick(IItem iItem) {
                if(iItem instanceof TopicHolderModel) {
                    Logger.logThis(" Click " + ((TopicHolderModel)iItem).getTitle());
                }
            }
        });

        lazyRecycler.setOnMultiSelect(new MvpLazyRecycler.OnMultiSelect() {
            @Override
            public void moveInMultiselectMode() {
                if(getToolbar().getMenu().findItem(1)==null) {

                    MenuItem clearSelected = getToolbar().getMenu().add(101, 1, 1, getString(R.string.delete));
                    clearSelected.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    clearSelected.setIcon(R.drawable.ic_delete_white_24dp);
                    clearSelected.setOnMenuItemClickListener(item -> {
                        lazyRecycler.deleteSelected();
                        leaveMultiselectMode();
                        return false;
                    });
                }
            }

            @Override
            public void leaveMultiselectMode() {
                getToolbar().getMenu().removeGroup(101);
            }
        });

        lazyRecycler.init(getMvpDelegate());
    }








    /*
    public void moveInMultiselectMode(){


        if(getToolbar().getMenu().findItem(1)==null) {

            MenuItem clearSelected = getToolbar().getMenu().add(101, 1, 1, getString(R.string.delete));
            clearSelected.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            clearSelected.setIcon(R.drawable.ic_delete_white_24dp);
            clearSelected.setOnMenuItemClickListener(item -> {
                topicListPresenter.getTopicListManager().deleteItem(adapter.getFastItemAdapter().getSelections());
                leaveMultiselectMode();
                return false;
            });
        }

    }

    public void leaveMultiselectMode(){

        getToolbar().getMenu().removeGroup(101);

    }
    */

    public Toolbar getToolbar(){
        return ((MainActivity)getActivity()).getToolbar();
    }

}
