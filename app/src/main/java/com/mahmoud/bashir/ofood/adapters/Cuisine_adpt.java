package com.mahmoud.bashir.ofood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.models.Cuisine_Model;

import java.util.List;

public class Cuisine_adpt extends RecyclerView.Adapter<Cuisine_adpt.ViewHolder> {

    Context context;
    List<Cuisine_Model> list;

    public Cuisine_adpt(Context context, List<Cuisine_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuisine,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cuisine_Model cuisine_model=list.get(position);

        holder.img_cuisine.setImageResource(cuisine_model.getImageURI());
        holder.name_cuisine.setText(cuisine_model.getNameCuisine());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView img_cuisine;
        TextView name_cuisine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_cuisine=itemView.findViewById(R.id.img_cuisine);
            name_cuisine=itemView.findViewById(R.id.name_cuisine);
        }
    }
}
