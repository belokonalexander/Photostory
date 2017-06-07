package ru.belokonalexander.photostory.Views.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import ru.belokonalexander.photostory.App;
import ru.belokonalexander.photostory.DI.common.Modules.TopicModule;
import ru.belokonalexander.photostory.Views.Adapters.ComplexListManager;
import ru.belokonalexander.photostory.Views.Adapters.SelectableFastAdapterWrapper;
import ru.belokonalexander.photostory.presentation.MyTopicList.model.TopicHolderModel;
import ru.belokonalexander.photostory.presentation.MyTopicList.view.IListView;

/**
 * Created by Alexander on 07.06.2017.
 */

/**
 * Список со своим презентером и Fast Adapter'ом
 * необходима только IListInteractor реализация
 * адаптер автоматически назначается, перед назначением (init) его можно изменить
 */
public class MvpLazyRecycler extends LazyLoadingRecycler implements IListView {

    private MvpDelegate mParentDelegate;
    private MvpDelegate<IListView> mMvpDelegate;

    @Inject @InjectPresenter
    LazyRecyclerPresenter presenter;

    @ProvidePresenter
    public LazyRecyclerPresenter provideTopicListPresenter(){
        return presenter;
    }

    public MvpLazyRecycler(Context context) {
        super(context);
        App.get(getContext()).getAppComponent().plus(new TopicModule()).inject(this);
    }

    public MvpLazyRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        App.get(getContext()).getAppComponent().plus(new TopicModule()).inject(this);
    }

    public MvpLazyRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Inject @Named("ControlListPanel")
    SelectableFastAdapterWrapper<IItem> adapter;



    private FooterAdapter<ProgressItem> footerAdapter = new FooterAdapter<>();

    /**
     * создание recycler'a
     * @param parentDelegate
     */
    public void init(MvpDelegate parentDelegate){


        adapter.setClickListener(new FastAdapter.OnClickListener<IItem>() {
            @Override
            public boolean onClick(View v, IAdapter<IItem> adapter, IItem item, int position) {
                presenter.onItemClick(item);
                return false;
            }
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

        adapter.getFastItemAdapter().withSelectionListener((item, selected) -> presenter.changeSelectionState());

        setAdapter(footerAdapter.wrap(adapter.getFastItemAdapter()));

        setOnGetDataListener(new LazyLoadingRecycler.OnGetDataListener() {
            @Override
            public void getData() {
                //Logger.logThis(" ПОДГРУЖАЮ ДАННЫЕ ----- ");
                presenter.getListManager().loadMore();
            }
        });

        lockLazyLoading();

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


    public MvpDelegate<IListView> getMvpDelegate() {
        if (mMvpDelegate != null) {
            return mMvpDelegate;
        }

        mMvpDelegate = new MvpDelegate<>(this);
        mMvpDelegate.setParentDelegate(mParentDelegate, String.valueOf(getId()));
        return mMvpDelegate;
    }

    @Override
    public void afterLoadMore(List<IItem> part) {
        adapter.getFastItemAdapter().add(part);
    }

    @Override
    public void showList(List<IItem> data) {
        adapter.getFastItemAdapter().add(data);
    }

    @Override
    public void itemClick(IItem item) {
        adapter.select(item);
        if(onClickListener!=null){
            onClickListener.onClick(item);
        }
    }

    @Override
    public void deleteItem(Set<Integer> itemsPositions) {
        adapter.getFastItemAdapter().deleteAllSelectedItems();
    }

    public void deleteSelected(){
        presenter.getListManager().deleteItem(adapter.getFastItemAdapter().getSelections());
    }

    @Override
    public void changeOnSelectTopicState() {
        if(onMultiSelect!=null) {
            if (adapter.getFastItemAdapter().getSelectedItems().size() > 0) {
                onMultiSelect.moveInMultiselectMode();
            } else {
                onMultiSelect.leaveMultiselectMode();
            }
        }
    }

    @Override
    public void disableLoadMore(ComplexListManager.LazyLoadingStopCause cause) {
        lockLazyLoading();

        if(cause== ComplexListManager.LazyLoadingStopCause.WAITING){
            if(footerAdapter.getAdapterItemCount()==0){
                footerAdapter.add(new ProgressItem().withEnabled(false));
            }
        } else {
            if(footerAdapter.getAdapterItemCount()>0) {
                footerAdapter.clear();
            }
        }
    }

    @Override
    public void enableLoadMore() {
        unlockLazyLoading();

        if(footerAdapter.getAdapterItemCount()==0)
            footerAdapter.add(new ProgressItem().withEnabled(false));
    }



    OnItemClickListener onClickListener;
    OnMultiSelect onMultiSelect;

    public void setOnMultiSelect(OnMultiSelect onMultiSelect) {
        this.onMultiSelect = onMultiSelect;
        adapter.setMultiSelect();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnItemClickListener {
        void onClick(IItem iItem);
    }

    public interface OnMultiSelect {
        void moveInMultiselectMode();
        void leaveMultiselectMode();
    }
}
