package com.mahmoud.bashir.ofood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.models.Popular_Model;
import com.mahmoud.bashir.ofood.ui.Product_details_Activity;

import java.util.List;

public class Popular_adpt extends RecyclerView.Adapter<Popular_adpt.ViewHolder> {

    Context context;
    List<Popular_Model> list;

    public Popular_adpt(Context context, List<Popular_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Popular_Model popular_model=list.get(position);

        holder.img_pop.setImageResource(popular_model.getImageURI());
        holder.txt_name_pop.setText(popular_model.getNamePop());
        holder.txt_desc_pop.setText(popular_model.getDescPop());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n=new Intent(context, Product_details_Activity.class);
                n.putExtra("img_pop",popular_model.getImageURI());
                n.putExtra("name_pop",popular_model.getNamePop());
                n.putExtra("desc_pop",popular_model.getDescPop());
                context.startActivity(n);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_pop;
        TextView txt_name_pop,txt_desc_pop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pop=itemView.findViewById(R.id.img_pop);
            txt_name_pop=itemView.findViewById(R.id.txt_name_pop);
            txt_desc_pop=itemView.findViewById(R.id.txt_desc_pop);
        }
    }
}
