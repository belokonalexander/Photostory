package ru.belokonalexander.photostory.presentation.MyTopicList.model;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Adapters.AbstractSelectedItem;

/**
 * Created by Alexander on 16.05.2017.
 */

public class TopicHolderModelImmutable extends AbstractSelectedItem<TopicHolderModelImmutable,TopicHolderModelImmutable.ViewHolder> {

    private String title;
    private Long id;



    public TopicHolderModelImmutable() {

            this.title =  " |||| " + new Random().nextInt();
            this.id = new Random().nextLong();
            mSelectable = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof TopicHolderModelImmutable) {
            TopicHolderModelImmutable inItem = (TopicHolderModelImmutable) inObject;
            return this.id.equals(inItem.id);
        }
        return false;
    }


    @Override
    public int getType() {
        return R.id.activity_create_topic;
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.titleTextView.setText(null);

        if(isSelected())
            holder.selectableCheckBox.setChecked(false);

    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public FastAdapter.OnClickListener<TopicHolderModelImmutable> getOnPreItemClickListener() {

        return super.getOnPreItemClickListener();
    }

    /**
     * The Adapter and the Payload are provided to get more specific information from it.
     */
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        if(isSelected()){
            viewHolder.selectableCheckBox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selectableCheckBox.setVisibility(View.GONE);
        }

        viewHolder.selectableCheckBox.setChecked(isSelected());
        viewHolder.selectableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        if(isSingleSelected()) {
            viewHolder.titleTextView.setTextColor(Color.RED);
        } else {
            viewHolder.titleTextView.setTextColor(Color.GRAY);
        }

        viewHolder.titleTextView.setText(title);
    }



    @Override
    public int getLayoutRes() {
        return R.layout.item_topic;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{


        @BindView((R.id.content_container))
        View contentContainer;

        @BindView(R.id.selectable_checkbox)
        public CheckBox selectableCheckBox;

        @BindView(R.id.title)
        public TextView titleTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }


    }
}
