package com.example.week1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week1.provider.item;

import java.util.ArrayList;
import java.util.List;

public class MyW6Adapter extends RecyclerView.Adapter<MyW6Adapter.ViewHolder> {

    List<item> data;

    public MyW6Adapter() {
    }

    @NonNull
    @Override
    //make object
    public MyW6Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    //getting view holder and putting data inside
    public void onBindViewHolder(@NonNull MyW6Adapter.ViewHolder holder, int position) {
        holder.nameView.setText(data.get(position).getItemName());
        holder.costView.setText(data.get(position).getCost().toString());
        holder.quantityView.setText(data.get(position).getQuantity());
        holder.descriptionView.setText(data.get(position).getDescription());
        holder.frozenView.setText(data.get(position).getFrozen().toString());
    }

    @Override
    //how many items
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<item> newData) {
        data = newData;
    }

    //this is the card
    //the view that is passed by the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView nameView;
        public TextView costView;
        public TextView quantityView;
        public TextView descriptionView;
        public TextView frozenView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.itemView = itemView; //reference to the card
            nameView = itemView.findViewById(R.id.nameView);
            costView = itemView.findViewById(R.id.costView);
            quantityView = itemView.findViewById(R.id.quantityView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            frozenView = itemView.findViewById(R.id.frozenView);
        }
    }
}
