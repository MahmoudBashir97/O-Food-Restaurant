package com.mahmoud.bashir.ofood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Room.Favourite_Schema;

import java.util.List;

public class fav_adpt extends ListAdapter<Favourite_Schema,fav_adpt.ViewHolder> {

   // List<Favourite_Schema> list;

    public fav_adpt() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Favourite_Schema> DIFF_CALLBACK=new DiffUtil.ItemCallback<Favourite_Schema>() {
        @Override
        public boolean areItemsTheSame(@NonNull Favourite_Schema oldItem, @NonNull Favourite_Schema newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Favourite_Schema oldItem, @NonNull Favourite_Schema newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getImageURI() == newItem.getImageURI();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view_item,parent,false);
         return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favourite_Schema schema=getItem(position);

        holder.img_srch.setImageResource(schema.getImageURI());
        holder.srch_name.setText(schema.getName());
        holder.srch_desc.setText(schema.getDescription());

    }

    public Favourite_Schema getFavAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_srch;
        TextView srch_name,srch_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_srch=itemView.findViewById(R.id.img_srch);
            srch_name=itemView.findViewById(R.id.srch_name);
            srch_desc=itemView.findViewById(R.id.srch_desc);
        }
    }
}
