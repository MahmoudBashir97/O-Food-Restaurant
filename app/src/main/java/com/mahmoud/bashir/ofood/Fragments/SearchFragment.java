package com.mahmoud.bashir.ofood.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.adapters.Search_adpt;
import com.mahmoud.bashir.ofood.pojo.Item_search;
import com.mahmoud.bashir.ofood.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    TextView search_err;
    RecyclerView search_rec;

    Search_adpt search_adpt;
    Item_search search_item;
    List<Item_search> item_searchList;

    Toolbar toolbar;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);

        /*toolbar=v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setSubtitle("Search Here!");*/


        search_rec=v.findViewById(R.id.search_rec);
        search_rec.setHasFixedSize(true);
        search_rec.setLayoutManager(new LinearLayoutManager(getContext()));




        item_searchList=new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            switch (i) {
                case 0:
                    search_item = new Item_search("lemon Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                case 1:
                    search_item = new Item_search("karz Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                case 2:
                    search_item = new Item_search("orange Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                case 3:
                    search_item = new Item_search("apple Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                case 4:
                    search_item = new Item_search("banana Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                case 5:
                    search_item = new Item_search("red Ricotta Pancakes", "jkksbahvjgdajhbhkjfaskaksjhjs");
                    item_searchList.add(search_item);
                    break;
                default:
                    search_item = new Item_search("", "");
                    item_searchList.add(search_item);
            }
        }

        search_adpt=new Search_adpt(item_searchList);
        search_rec.setAdapter(search_adpt);


        searchView =v.findViewById(R.id.searchView);
        search_err=v.findViewById(R.id.search_err);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search_adpt.getFilter().filter(newText);
                return false;
            }
        });


       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    search_err.setVisibility(View.VISIBLE);
                    search_err.setText("No Match found");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                return false;
            }
        });*/

        return v;
    }
/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) searchitem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search_adpt.getFilter().filter(newText);
                return false;
            }
        });
    }*/
}
