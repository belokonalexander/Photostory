package ru.belokonalexander.photostory.Views.Recyclers.MVP;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import ru.belokonalexander.photostory.Moxy.Presenters.ActionListPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.VIActionList;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.ActionRecyclerView;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.CommonAdapter;
import ru.belokonalexander.photostory.Views.Recyclers.DataProviders.SolidProvider;
import ru.belokonalexander.photostory.Views.Recyclers.UpdateMode;

/**
 * Created by Alexander on 25.04.2017.
 */

public class ActionRecyclerViewMVP<T> extends RecyclerMVP implements VIActionList<T> {


    @InjectPresenter
    ActionListPresenter presenter;

    @ProvidePresenter
    ActionListPresenter provideActionListPresenter(){
        Log.e("TAG", "CONST: " + provider);
        return new ActionListPresenter(provider);
    }

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


    private SolidProvider<T> provider;


    public ActionRecyclerViewMVP(Context context) {
        super(context);
    }

    public ActionRecyclerViewMVP(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void update(List<T> data, UpdateMode updateMode) {

        //если подгрузка, то добавляем данные
        if(updateMode==UpdateMode.ADD) {
            if(!data.isEmpty()) {
                addInner(data);
            }
        } else {
            //если другие режимы, то данные переписываются
            rewriteAllInner(data);
        }
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

        provider = solidProvider;

        try {
            adapter = (CommonAdapter<T>) Class.forName(adapterHolderClass.getName()).getConstructor(Context.class).newInstance(getContext());
            setAdapter(adapter);

        } catch (Exception e){
            throw new UnsupportedOperationException("Invalid reflection: " + e);
        }


        init(delegate);
    }

    public void initData(){
        presenter.getData(UpdateMode.INITIAL);
    }

    /**
     * методы для взаимодействия с содержимом списка - могут быть вызваны извне
     * @param index
     */
    public void moveToTop(int index) {
        T object = adapter.getData().get(index);
        adapter.getData().remove(index);
        adapter.getData().add(0,object);
        adapter.notifyItemMoved(index,0);
        onDataSizeChanged();
        scrollToTop();
    }

    public void addToTop(T object){
        adapter.getData().add(0,object);
        adapter.notifyItemInserted(0);
        onDataSizeChanged();
        scrollToTop();
    }

    /**
     * скроллит на вверх списка, если текущее значение скроллера не больше размера границы
     */
    public void scrollToTop(){
        if(computeVerticalScrollOffset()<defaultItemHeight){
            scrollToPosition(0);
        }
    }

    public void update(T item, int index) {
        T object = adapter.getData().get(index);
        object = item;
        adapter.notifyItemChanged(index);
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }

    public void remove(T object) {

        int index = adapter.getData().indexOf(object);
        if(index>=0){
            adapter.getData().remove(index);
            adapter.notifyItemRemoved(index);
        }

        onDataSizeChanged();
    }

    public void removeAll(){

        if(adapter.getDecoration()== CommonAdapter.Decoration.FOOTER)
            adapter.setDecoration(CommonAdapter.Decoration.SIMPLE);

        int index = adapter.getRealItems();
        adapter.getData().clear();


        adapter.notifyDataSetChanged();
        onDataSizeChanged();
    }

    public void add(List<T> list) {
        addInner(list);
    }

    public void rewriteAll(List<T> data){
        rewriteAllInner(data);
    }

    /**
     * protected методы для внутреннего использования
     * @param list
     */
    final protected void addInner(List<T> list) {
        int was = adapter.getData().size();
        adapter.getData().addAll(list);
        adapter.notifyItemRangeChanged(was,adapter.getData().size());
        onDataSizeChanged();
    }

    final protected void rewriteAllInner(List<T> data){


        adapter.setData(data);
        adapter.notifyDataSetChanged();
        onDataSizeChanged();

    }

    public void onDataSizeChanged(){
        if(adapter.getRealItems()==0)
            presenter.onEnableEmptyController();
        else presenter.onDisableEmptyController();
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
