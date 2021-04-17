package com.mahmoud.bashir.ofood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Schema;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.mahmoud.bashir.ofood.ViewModel.Favourite_viewModel;
import com.mahmoud.bashir.ofood.adpt_interfaces.fav_checked_btn;
import com.mahmoud.bashir.ofood.models.Popular_Model;
import com.mahmoud.bashir.ofood.ui.MainActivity;
import com.mahmoud.bashir.ofood.ui.Product_details_Activity;

import java.util.List;

public class Popular_adpt extends RecyclerView.Adapter<Popular_adpt.ViewHolder> {

     Context context;
     List<Popular_Model> list;
     Favourite_viewModel viewModel;
     List<Favourite_Schema> schemas;
     fav_checked_btn favCheckedBtn;

    public Popular_adpt(Context context, List<Popular_Model> list,List<Favourite_Schema> schemas,fav_checked_btn CheckedBtn) {
        this.context = context;
        this.list = list;
        this.schemas = schemas;
        this.favCheckedBtn = CheckedBtn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Popular_Model popular_model=list.get(position);



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

                SharedPrefranceManager.getInastance(context).save_Popular_Data(popular_model.getImageURI(),popular_model.getNamePop(),popular_model.getDescPop());
                context.startActivity(n);
            }
        });

        if (schemas.size() >0){
         for (Favourite_Schema favourite_schema: schemas){
            if (favourite_schema.getId() == position){
                holder.checkbox_heart.setChecked(true);
            }
         }
        }


        holder.checkbox_heart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Favourite_Schema favourite_schema = new Favourite_Schema(popular_model.getNamePop(), popular_model.getDescPop(), popular_model.getImageURI());

                if (b) {
                    favCheckedBtn.BtnStatus("insert",
                            favourite_schema);
                }else{
                    favCheckedBtn.BtnStatus("delete",
                           favourite_schema);
                }
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
        CheckBox checkbox_heart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pop=itemView.findViewById(R.id.img_pop);
            txt_name_pop=itemView.findViewById(R.id.txt_name_pop);
            txt_desc_pop=itemView.findViewById(R.id.txt_desc_pop);
            checkbox_heart=itemView.findViewById(R.id.checkbox_heart);
        }
    }


}