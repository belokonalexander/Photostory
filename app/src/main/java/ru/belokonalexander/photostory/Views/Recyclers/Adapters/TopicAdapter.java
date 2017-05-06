package ru.belokonalexander.photostory.Views.Recyclers.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.Models.Topic;
import ru.belokonalexander.photostory.R;


import static android.support.v7.widget.RecyclerView.NO_POSITION;


public class TopicAdapter extends HeaderFooterAdapter<Topic> {

    @Override
    void onBindVH(RecyclerView.ViewHolder holder, int position) {
        TopicHolder h = (TopicHolder) holder;
        Topic item = getItem(position);
        h.titleTextView.setText(" -> " + item.getTitle());
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

    public class TopicHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_view)
        CardView cv;

        @BindView(R.id.title)
        TextView titleTextView;


        TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //

            if(onClickListener!=null ){

                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = getAdapterPosition();

                        if(position!=NO_POSITION)
                            onClickListener.onClick(getItem(position));
                    }
                });
            }
        }
    }

}
