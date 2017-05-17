/*
package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Helpers.Logger;
import ru.belokonalexander.photostory.Models.Topic;

import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.BaseViewHolder;
import ru.belokonalexander.photostory.Views.Recyclers.Adapters.DefaultHolders.SelectableItem;


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


    public class TopicHolder extends BaseViewHolder<Topic> {

        @BindView(R.id.item_view)
        CardView cv;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.addition)
        CheckBox checkBox;


        TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void bindView(Topic topic) {
            titleTextView.setText(" -> " + topic.getTitle());
            cv.setOnClickListener(v -> {
                if(onClickListener!=null) {
                    onClickListener.onClick(topic);
                }
            });

            checkBox.setChecked(false);

        }

    }

}
*/
