package com.example.egoalv2.adapters;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egoalv2.R;
import com.library.foysaltech.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class TopPerformerSliderAdapter extends SliderViewAdapter<TopPerformerSliderAdapter.SliderViewHolder> {

    private final List<SpannableString> topPerformers;

    public TopPerformerSliderAdapter(List<SpannableString> topPerformers) {
        this.topPerformers = topPerformers;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_performer, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.textView.setText(topPerformers.get(position));
    }

    @Override
    public int getCount() {
        return topPerformers.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        TextView textView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.top_performer_text);
        }
    }
}