package com.example.restaurantsinmycity.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantsinmycity.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import javabeans.REAL_restaurant;

/**
 * Created by Asad Mirza on 25-01-2021.
 */

public class nestedRecyclerViewAdapter extends RecyclerView.Adapter<nestedRecyclerViewAdapter.viewHolder>{
    ArrayList<REAL_restaurant> restaurantArrayList;

    public nestedRecyclerViewAdapter(ArrayList<REAL_restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.restaurant_row_layout,parent,false);
   viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     REAL_restaurant real_restaurant = restaurantArrayList.get(position);
     holder.rName.setText(real_restaurant.getName());
     holder.rRating.setText(real_restaurant.getRating()+"/5");
     holder.RTiming.setText(real_restaurant.getTiming());

    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
    TextView rName,RTiming,rRating;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            rName = itemView.findViewById(R.id.resto_name);
            rRating = itemView.findViewById(R.id.resto_rating);
            RTiming  = itemView.findViewById(R.id.resto_timing);
        }
    }
}
