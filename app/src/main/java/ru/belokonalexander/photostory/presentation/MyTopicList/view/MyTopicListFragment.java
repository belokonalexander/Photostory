package ru.belokonalexander.photostory.presentation.MyTopicList.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.common.Modules.TopicModule;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Adapters.SelectableFastAdapterWrapper;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;
import ru.belokonalexander.photostory.presentation.MyTopicList.presenter.TopicListPresenter;

/**
 * Created by Alexander on 16.05.2017.
 */

public class MyTopicListFragment extends MvpAppCompatFragment implements ITopicListView {

    @Inject @InjectPresenter
    TopicListPresenter topicListPresenter;

    @BindView(R.id.topics_recycler)
    RecyclerView recyclerView;

    SelectableFastAdapterWrapper<IItem> adapter = new SelectableFastAdapterWrapper<>(new FastItemAdapter<>());

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter.getFastItemAdapter());

        //adapter.withSelectable(true);
        /*adapter.withSelectable(true);
        adapter.withSelectWithItemUpdate(true);
        adapter.withSelectOnLongClick(true);
        adapter.withMultiSelect(true);

        adapter.withOnClickListener(new FastAdapter.OnClickListener<IItem>() {
            @Override
            public boolean onClick(View v, IAdapter<IItem> adapter, IItem item, int position) {
                if(adapter.getFastAdapter().getSelectedItems().size()>0){
                   adapter.getFastAdapter().toggleSelection(position);
                } else

                return true;
            }
        });*/

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

    }


    @Override
    public void afterLoadMoreTopics(List<IItem> part) {

        adapter.getFastItemAdapter().add(part);

    }

    @Override
    public void showTopic(IItem item) {
        Logger.logThis(" Выбран: " + ((TopicHolderModel)item).getTitle() + " ---> "  + item.isSelected() + " adapter: " + adapter.getFastItemAdapter().getSelectedItems());
    }
}
