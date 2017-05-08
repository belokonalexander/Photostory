package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.Moxy.Presenters.TopicItemPresenter;
import ru.belokonalexander.photostory.Moxy.ViewInterface.ITopicView;
import ru.belokonalexander.photostory.R;


import static android.support.v7.widget.RecyclerView.NO_POSITION;


public class TopicAdapter extends HeaderFooterAdapter<Topic> {


    public TopicAdapter(MvpDelegate delegate) {
        super(delegate);
    }

    @Override
    void onBindVH(RecyclerView.ViewHolder holder, int position) {
        TopicHolder h = (TopicHolder) holder;
        Topic item = getItem(position);
        //h.titleTextView.setText(" -> " + item.getTitle());
        h.bind(item);
    }

    @Override
    RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic,parent,false);
        return new TopicHolder(v);
    }

    @Override
    int getItemType(int position) {
        return 0;
    }

    public class TopicHolder extends RecyclerView.ViewHolder implements ITopicView {


        Topic topic;

        MvpDelegate mvpDelegate;

        @InjectPresenter
        TopicItemPresenter presenter;

        @ProvidePresenter
        TopicItemPresenter provideTopicItemPresenter(){
            return new TopicItemPresenter(topic);
        }

        TopicItemPresenter getPresenter(){
          if(presenter==null){
              getMvpDelegate().onCreate();
              getMvpDelegate().onAttach();
          }
          return presenter;
        };


        @BindView(R.id.item_view)
        CardView cv;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.addition)
        ProgressBar progressBar;


        void bind(Topic topic){
            if(mvpDelegate !=null){
                getMvpDelegate().onSaveInstanceState();
                getMvpDelegate().onDetach();
                getMvpDelegate().onDestroyView();
                mvpDelegate = null;
            }

            this.topic = topic;

            boolean presenterIsExists = presenterIsExists();

            if(presenterIsExists)
                getMvpDelegate().onCreate();

            titleTextView.setText(" -> " + topic.getTitle());
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().startTask();
                }
            });
            progressBar.setProgress(0);

            if(presenterIsExists)
                getMvpDelegate().onAttach();
        }


        boolean presenterIsExists(){

            //Logger.logThis(" ---> КЛЮЧИ: " + TopicAdapter.this.getParentDelegate());
            for(String key :  TopicAdapter.this.getParentDelegate().getChildrenSaveState().keySet()) {
                if(topic.getId().toString().equals(key.substring(key.lastIndexOf('$')+1,key.length()).trim()))
                    return true;
            }

            return false;
        }

        MvpDelegate getMvpDelegate(){
            if(mvpDelegate==null){
                mvpDelegate = new MvpDelegate<>(this);

           /*     Logger.logThis(" delegate:  " + TopicAdapter.this.getParentDelegate());
                Logger.logThis(" topic:  " + topic);
                Logger.logThis(" hdelegate:  " + mvpDelegate);
*/
                mvpDelegate.setParentDelegate(TopicAdapter.this.getParentDelegate(),topic.getId().toString());
            }

            return mvpDelegate;
        }


        TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            /*if(onClickListener!=null ){

                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = getAdapterPosition();

                        if(position!=NO_POSITION)
                            Logger.logThis(" Position:  " + getAdapterPosition() + " / " + getLayoutPosition());
                            onClickListener.onClick(getItem(position));

                        Observable.intervalRange(0,40,0,1, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        progressBar.setProgress(aLong.intValue());
                                    }
                                });

                    }
                });
            }*/
        }

        @Override
        public void showTopic(Topic topic) {

        }

        @Override
        public void updateProgress(int percent) {
            progressBar.setProgress(percent);
        }
    }

}
