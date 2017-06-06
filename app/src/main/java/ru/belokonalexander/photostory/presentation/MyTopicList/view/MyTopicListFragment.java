package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.common.Modules.TopicModule;
import ru.belokonalexander.photostory.MainActivity;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Adapters.SelectableFastAdapterWrapper;
import ru.belokonalexander.photostory.Views.Recyclers.LazyLoadingRecycler;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;
import ru.belokonalexander.photostory.presentation.MyTopicList.presenter.TopicListPresenter;

/**
 * Created by Alexander on 16.05.2017.
 */

public class MyTopicListFragment extends MvpAppCompatFragment implements ITopicListView {

    @Inject @InjectPresenter
    TopicListPresenter topicListPresenter;

    @BindView(R.id.topics_recycler)
    LazyLoadingRecycler recyclerView;

    @Inject @Named("ControlListPanel")
    SelectableFastAdapterWrapper<IItem> adapter;

    private FooterAdapter<ProgressItem> footerAdapter = new FooterAdapter<>();


    @ProvidePresenter
    public TopicListPresenter provideTopicListPresenter(){
        return topicListPresenter;
    }



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


        //footerAdapter.add(new ProgressItem().withEnabled(false));


        adapter.setClickListener((v, adapter1, item, position) -> {
            topicListPresenter.selectTopic(item);
            return false;
        });


        adapter.getFastItemAdapter().withItemEvent(new ClickEventHook<IItem>() {
            @Override
            public void onClick(View v, int position, FastAdapter fastAdapter, IItem item) {
                adapter.getFastItemAdapter().toggleSelection(position);
            }

            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if(viewHolder instanceof TopicHolderModel.ViewHolder)
                    return ((TopicHolderModel.ViewHolder)viewHolder).selectableCheckBox;

                return null;
            }
        });

        adapter.getFastItemAdapter().withSelectionListener((item, selected) -> topicListPresenter.changeTopicSelectionState());



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(footerAdapter.wrap(adapter.getFastItemAdapter()));

        recyclerView.setOnGetDataListener(new LazyLoadingRecycler.OnGetDataListener() {
            @Override
            public void getData() {
                topicListPresenter.loadMore();
            }
        });

    }






    @Override
    public void showTopicList(List<IItem> topicList) {
        adapter.getFastItemAdapter().add(topicList);
    }

    @Override
    public void afterLoadMoreTopics(List<IItem> part) {
        adapter.getFastItemAdapter().add(part);
    }



    @Override
    public void showTopic(IItem item) {
        adapter.select(item);
    }

    @Override
    public void deleteTopics(Set<Integer> items) {

        adapter.getFastItemAdapter().deleteAllSelectedItems();

    }

    @Override
    public void changeOnSelectTopicState() {
        if(adapter.getFastItemAdapter().getSelectedItems().size()>0) {
            moveInMultiselectMode();
        } else {
            leaveMultiselectMode();
        }
    }

    @Override
    public void enableLoadMore(boolean booleanWrapper) {
        footerAdapter.clear();
        if (booleanWrapper) {
            footerAdapter.add(new ProgressItem().withEnabled(false));
            recyclerView.unlockLazyLoading();
        }
    }


    public void moveInMultiselectMode(){


        if(getToolbar().getMenu().findItem(1)==null) {

            MenuItem clearSelected = getToolbar().getMenu().add(101, 1, 1, getString(R.string.delete));
            clearSelected.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            clearSelected.setIcon(R.drawable.ic_delete_white_24dp);
            clearSelected.setOnMenuItemClickListener(item -> {
                topicListPresenter.deleteTopics(adapter.getFastItemAdapter().getSelections());
                leaveMultiselectMode();
                return false;
            });
        }

    }

    public void leaveMultiselectMode(){

        getToolbar().getMenu().removeGroup(101);

    }

    public Toolbar getToolbar(){
        return ((MainActivity)getActivity()).getToolbar();
    }

}
