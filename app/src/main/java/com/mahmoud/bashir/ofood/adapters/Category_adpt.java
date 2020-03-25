package com.mahmoud.bashir.ofood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.models.Category_Model;
import com.mahmoud.bashir.ofood.ui.Product_details_Activity;
import com.mahmoud.bashir.ofood.ui.breakfast_menu_activit;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_adpt extends RecyclerView.Adapter<Category_adpt.ViewHolder> {

    Context context;
    List<Category_Model> list;

    public Category_adpt(Context context, List<Category_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Category_adpt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_adpt.ViewHolder holder, int position) {
        Category_Model category_model=list.get(position);

        holder.img_category.setImageResource(category_model.getImageURI());
        holder.name_category.setText(category_model.getCat_name());

        holder.itemView.setOnClickListener(v ->{
            switch (position){
                case 0 :
                    Intent in=new Intent(context, breakfast_menu_activit.class);
                    context.startActivity(in);
                    break;
                    default:
                        Toast.makeText(context, "Still empty , there is no data !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img_category;
        TextView  name_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_category=itemView.findViewById(R.id.img_category);
            name_category=itemView.findViewById(R.id.name_category);
        }
    }
}
