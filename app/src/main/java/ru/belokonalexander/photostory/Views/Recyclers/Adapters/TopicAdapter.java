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
import ru.belokonalexander.photostory.Moxy.Views.MVPViewHolder;
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

    public class TopicHolder extends MVPViewHolder<Topic, TopicItemPresenter> implements ITopicView {

        Topic topic;

        @InjectPresenter
        TopicItemPresenter presenter;

        @ProvidePresenter
        TopicItemPresenter provideTopicItemPresenter(){
            return new TopicItemPresenter(topic);
        }

        @BindView(R.id.item_view)
        CardView cv;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.addition)
        ProgressBar progressBar;


        TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void bindModel(Topic model) {
            this.topic = model;
        }

        @Override
        protected void bindView(Topic model) {
            titleTextView.setText(" -> " + topic.getTitle());
            cv.setOnClickListener(v -> {
                getPresenter().startTask();
            });
            progressBar.setProgress(0);
        }

        @Override
        protected MvpDelegate getParentDelegate() {
            return TopicAdapter.this.getParentDelegate();
        }

        @Override
        protected String getTag() {
            return String.valueOf(topic.getId());
        }

        @Override
        protected TopicItemPresenter getPresenterField() {
            return presenter;
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
