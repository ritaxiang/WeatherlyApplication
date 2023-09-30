/*
Description: The RecyclerViewAdapter class is used to help two unrelated classes to work together.
In this case, it grabs the data from locations class and use that data to create a recycler view containing the saved cities.
 */
package com.example.weatherapptutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CN_RecyclerViewAdapter extends RecyclerView.Adapter<CN_RecyclerViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<SavedLocationsModel> SavedLocationsModels;

    private RecyclerViewInterface mRecyclerViewInterface;

    public CN_RecyclerViewAdapter(Context context, ArrayList<SavedLocationsModel> SavedLocationsModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.SavedLocationsModels = SavedLocationsModels;
        this.mRecyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CN_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);

        return new CN_RecyclerViewAdapter.MyViewHolder(view, mRecyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull CN_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(SavedLocationsModels.get(position).getSavedCity());
        holder.imageView.setImageResource(SavedLocationsModels.get(position).getImage());


    }

    @Override
    public int getItemCount() {

        return SavedLocationsModels.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView tvName;
        RecyclerViewInterface recyclerViewInterface;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textView2);
            this.recyclerViewInterface = recyclerViewInterface;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewInterface.OnItemClick(getAdapterPosition());

        }
    }

    public interface RecyclerViewInterface{
        void OnItemClick (int position);
    }
}
