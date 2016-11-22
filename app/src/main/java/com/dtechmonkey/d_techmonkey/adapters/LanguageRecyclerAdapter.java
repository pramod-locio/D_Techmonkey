package com.dtechmonkey.d_techmonkey.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dtechmonkey.d_techmonkey.R;

/**
 * Created by P KUMAR on 21-11-2016.
 */

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.RecyclerViewHolder> {
    String[] languages;
    public LanguageRecyclerAdapter(String[] languages) {
        this.languages=languages;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.language_list,parent,false);
        RecyclerViewHolder viewHolder=new RecyclerViewHolder(view);
        return viewHolder;
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
}
