package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.R;


public class TopicAdapter extends CommonAdapter<Topic> {


    public TopicAdapter(Context context) {
        super(context);
    }

    @Override
    RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic,parent,false);

        return new LanguageHolder(v);
    }

    @Override
    void onBindVH(RecyclerView.ViewHolder holder, int position) {
        LanguageHolder h = (LanguageHolder) holder;
        Topic item = data.get(position);
        h.titleTextView.setText(item.getTitle());

    }

    public class LanguageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_view)
        CardView cv;

        @BindView(R.id.title)
        TextView titleTextView;


        LanguageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            if(onClickListener!=null){
                cv.setOnClickListener(v -> onClickListener.onClick(getItem(getLayoutPosition())));
            }


        }
    }

}
