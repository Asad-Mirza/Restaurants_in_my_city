package com.example.restaurantsinmycity.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantsinmycity.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import javabeans.Section;

/**
 * Created by Asad Mirza on 25-01-2021.
 */

public class mainRecylerViewAdapter extends RecyclerView.Adapter<mainRecylerViewAdapter.viewHolder> {
    ArrayList<Section> sectionArrayList;

    public mainRecylerViewAdapter(ArrayList<Section> sectionArrayList) {
        this.sectionArrayList = sectionArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       View view =layoutInflater.inflate(R.layout.main_recyclerview_row,parent,false);
       viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     Section section = sectionArrayList.get(position);
     holder.sectionHeader.setText(section.getHeader());
     nestedRecyclerViewAdapter Nestedadapter = new nestedRecyclerViewAdapter(section.getRestaurantArrayList());
     holder.nestedRecyclerview.setAdapter(Nestedadapter);

    }

    @Override
    public int getItemCount() {
        return sectionArrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView sectionHeader;
        RecyclerView nestedRecyclerview;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sectionHeader = itemView.findViewById(R.id.sectionHeader);
            nestedRecyclerview = itemView.findViewById(R.id.nested_recylerview);
        }
    }
}
