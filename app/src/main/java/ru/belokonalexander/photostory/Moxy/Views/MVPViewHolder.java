package ru.belokonalexander.photostory.Moxy.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.belokonalexander.photostory.Moxy.Presenters.TopicItemPresenter;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.TopicAdapter;

/**
 * Created by Alexander on 08.05.2017.
 */

public abstract class MVPViewHolder<T, P> extends RecyclerView.ViewHolder {
    public MVPViewHolder(View itemView) {
        super(itemView);
    }


    private MvpDelegate mvpDelegate;

    private boolean presenterIsExists(){
        for(String key :  getParentDelegate().getChildrenSaveState().keySet()) {
            if(getTag().equals(key.substring(key.lastIndexOf('$')+1,key.length()).trim()))
                return true;
        }
        return false;
    }

    protected P getPresenter(){
        if(getPresenterField()==null){
            getMvpDelegate().onCreate();
            getMvpDelegate().onAttach();
        }
        return getPresenterField();
    }


    private MvpDelegate getMvpDelegate(){
        if(mvpDelegate==null){
            mvpDelegate = new MvpDelegate<>(this);
            mvpDelegate.setParentDelegate(getParentDelegate(), getTag());
        }

        return mvpDelegate;
    }


    public void bind(T model){
        if(mvpDelegate !=null){
            getMvpDelegate().onSaveInstanceState();
            getMvpDelegate().onDetach();
            getMvpDelegate().onDestroyView();
            mvpDelegate = null;
        }

        bindModel(model);

        boolean presenterIsExists = presenterIsExists();


        if(presenterIsExists)
            getMvpDelegate().onCreate();

        bindView(model);

        if(presenterIsExists)
            getMvpDelegate().onAttach();

    }

    protected abstract void bindModel(T model);
    protected abstract void bindView(T model);
    protected abstract MvpDelegate getParentDelegate();
    protected abstract String getTag();
    protected abstract P getPresenterField();

}
