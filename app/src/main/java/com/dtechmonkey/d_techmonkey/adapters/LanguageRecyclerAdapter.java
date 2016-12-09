package com.dtechmonkey.d_techmonkey.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dtechmonkey.d_techmonkey.Language;
import com.dtechmonkey.d_techmonkey.R;

/**
 * Created by P KUMAR on 21-11-2016.
 */

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.RecyclerViewHolder> {

    private String[] languages;

    private ItemSelectedListener listener;

    public LanguageRecyclerAdapter(String[] languages) {
        this.languages=languages;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.language_list,parent,false);
        RecyclerViewHolder viewHolder=new RecyclerViewHolder(view);
        onItemClicked(viewHolder);
        return viewHolder;
    }

    private void onItemClicked(final RecyclerViewHolder holder) {
        if (listener != null) {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = holder.getAdapterPosition();
                    listener.onItemClicked(position);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    holder.checkBox.setChecked(!holder.checkBox.isChecked());
                    listener.onItemClicked(position);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.language.setText(languages[position]);
    }

    @Override
    public int getItemCount() {
        return languages.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView language;
        CheckBox checkBox;

        public RecyclerViewHolder(View view) {
            super(view);
            language=(TextView)view.findViewById(R.id.language_name);
            checkBox=(CheckBox)view.findViewById(R.id.language_checkbox);
        }
    }

    public interface ItemSelectedListener {
        void onItemClicked(int position);
    }

    public void setListener(ItemSelectedListener listener) {
        this.listener = listener;
    }
}
