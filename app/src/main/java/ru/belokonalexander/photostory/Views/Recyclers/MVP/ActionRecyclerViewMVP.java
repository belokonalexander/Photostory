package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.belokonalexander.photostory.Moxy.Presenters.ActionListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;

/**
 * Created by Alexander on 25.04.2017.
 */

public class ActionRecyclerViewMVP<T> extends RecyclerMVP implements VIActionList<T> {


    @InjectPresenter
    ActionListPresenter presenter;


    /**
     *  адаптер с данными, содержащимися в списке
     */
    protected CommonAdapter<T> adapter;

    /**
     *  надпись, появляющаяся, если источник не имеет данных
     */
    RelativeLayout emptyDataController;

    /**
     * высота элемента списка по-умолчанию
     */
    int defaultItemHeight;


    //protected SolidProvider<T> provider;


    public ActionRecyclerViewMVP(Context context) {
        super(context);
    }

    public ActionRecyclerViewMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void updateData(List<T> data, UpdateMode updateMode) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }


    public void init(Class adapterHolderClass, SolidProvider<T> solidProvider, MvpDelegate delegate){

        if(emptyDataController==null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            emptyDataController = (RelativeLayout) layoutInflater.inflate(R.layout.item_empty_data, null);

            ((ViewGroup)getParent()).setLayoutTransition(new LayoutTransition());
            ((ViewGroup)getParent()).addView(emptyDataController);
            disableEmptyController();
            defaultItemHeight = getContext().getResources().getDimensionPixelSize(R.dimen.default_list_height);
        }


        try {
            adapter = (CommonAdapter<T>) Class.forName(adapterHolderClass.getName()).getConstructor(Context.class).newInstance(getContext());
            setAdapter(adapter);

        } catch (Exception e){
            throw new UnsupportedOperationException("Invalid reflection: " + e);
        }


        init(delegate);

        if(presenter.getProvider()==null)
            presenter.setProvider(solidProvider);
    }




    protected void dataLoading(UpdateMode updateMode){
        presenter.getData(updateMode);
    }

    public void initData(){
        dataLoading(UpdateMode.INITIAL);
    }


    @Override
    public void enableEmptyController(){
        emptyDataController.setVisibility(VISIBLE);
    }

    @Override
    public void disableEmptyController(){
        emptyDataController.setVisibility(INVISIBLE);
    }

    @Override
    public void setClickListener(CommonAdapter.OnClickListener<T> onItemClickListener) {
        adapter.setOnClickListener(onItemClickListener);
    }


    public void setOnDataContentChangeListener(ActionListPresenter.OnDataContentChangeListener listener){
        presenter.setOnDataContentChangeListener(listener);
    }


    public void setOnItemClickListener(CommonAdapter.OnClickListener<T> onItemClickListener) {
        presenter.setOnClickListener(onItemClickListener);
    }
}
