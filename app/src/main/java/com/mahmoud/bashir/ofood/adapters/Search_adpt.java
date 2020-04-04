package com.mahmoud.bashir.ofood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.pojo.Item_search;

import java.util.ArrayList;
import java.util.List;

public class Search_adpt extends RecyclerView.Adapter<Search_adpt.ViewHolder> implements Filterable {

    List<Item_search> itemSearchList;
    List<Item_search> item_searchListfull;

    public Search_adpt(List<Item_search> itemSearchList) {
        this.itemSearchList = itemSearchList;

        item_searchListfull=new ArrayList<>(itemSearchList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item_search search=itemSearchList.get(position);

        holder.srch_name.setText(search.getText1());
        holder.srch_desc.setText(search.getText2());
    }

    @Override
    public int getItemCount() {
        return itemSearchList.size();
    }

    @Override
    public Filter getFilter() {
        return itemfilter;
    }

    private Filter itemfilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           List<Item_search> filteredList =new ArrayList<>();

           if (charSequence == null || charSequence.length() == 0) {
           filteredList.addAll(item_searchListfull);
           }else {
               String filterPattern = charSequence.toString().toLowerCase().trim();

               for (Item_search item: item_searchListfull){
                   if (item.getText1().toLowerCase().contains(filterPattern)){
                       filteredList.add(item);
                   }
               }
           }
           FilterResults results=new FilterResults();
           results.values=filteredList;
           return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            itemSearchList.clear();
            itemSearchList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView srch_name,srch_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srch_name=itemView.findViewById(R.id.srch_name);
            srch_desc=itemView.findViewById(R.id.srch_desc);
        }
    }
}
