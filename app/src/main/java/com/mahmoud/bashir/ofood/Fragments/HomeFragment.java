package com.mahmoud.bashir.ofood.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.adapters.Category_adpt;
import com.mahmoud.bashir.ofood.adapters.Cuisine_adpt;
import com.mahmoud.bashir.ofood.adapters.Popular_adpt;
import com.mahmoud.bashir.ofood.models.Category_Model;
import com.mahmoud.bashir.ofood.models.Cuisine_Model;
import com.mahmoud.bashir.ofood.models.Popular_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //recyclerviews
    RecyclerView category_rec,popular_rec,cuisine_rec;
    //adapters
    Category_adpt category_adpt;
    Popular_adpt popular_adpt;
    Cuisine_adpt cuisine_adpt;

    //pojo
    Category_Model category_model;
    Popular_Model popular_model;
    Cuisine_Model cuisine_model;

    //list of Model
    List<Category_Model> category_modelList;
    List<Popular_Model> popular_modelList;
    List<Cuisine_Model> cuisine_modelList;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        //init view
        category_rec=v.findViewById(R.id.category_rec);
        popular_rec=v.findViewById(R.id.popular_rec);
        cuisine_rec=v.findViewById(R.id.cuisine_rec);


        //recyclerview
        category_rec.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        category_rec.setLayoutManager(horizontalLayoutManager);

        popular_rec.setHasFixedSize(true);
        LinearLayoutManager PophorizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popular_rec.setLayoutManager(PophorizontalLayoutManager);


        cuisine_rec.setHasFixedSize(true);
        LinearLayoutManager CuisinehorizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cuisine_rec.setLayoutManager(CuisinehorizontalLayoutManager);


        category_modelList=new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            switch (i) {
                case 0:
                    category_model = new Category_Model(R.drawable.breakfast, "Breakfast");
                    category_modelList.add(category_model);
                    break;
                case 1:
                    category_model = new Category_Model(R.drawable.cake, "Sweets");
                    category_modelList.add(category_model);
                    break;
                case 2:
                    category_model = new Category_Model(R.drawable.fruits, "Fruits");
                    category_modelList.add(category_model);
                    break;
                case 3:
                    category_model = new Category_Model(R.drawable.indian, "Indian");
                    category_modelList.add(category_model);
                    break;
                case 4:
                    category_model = new Category_Model(R.drawable.brazilian, "Brazilian");
                    category_modelList.add(category_model);
                    break;
                case 5:
                    category_model = new Category_Model(R.drawable.indian, "Egyptian");
                    category_modelList.add(category_model);
                    break;
                default:
                    category_model = new Category_Model(R.drawable.breakfast, "");
                    category_modelList.add(category_model);
            }
        }

        category_adpt=new Category_adpt(getContext(),category_modelList);
        category_rec.setAdapter(category_adpt);


        popular_modelList=new ArrayList<>();

        for (int i=0;i<=5;i++) {
            switch (i) {
                case 0:
                    popular_model = new Popular_Model(R.drawable.lemon_ricotta_pancakes, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                case 1:
                    popular_model = new Popular_Model(R.drawable.lemon_ricotta_pancakes, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                case 2:
                    popular_model = new Popular_Model(R.drawable.lemon_ricotta_pancakes2, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                case 3:
                    popular_model = new Popular_Model(R.drawable.banana_oat_pancakes_title, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                case 4:
                    popular_model = new Popular_Model(R.drawable.french_food, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                case 5:
                    popular_model = new Popular_Model(R.drawable.egypt_cuisine, "BlueBerry Ricatto Pancakes ","Fluffy and light BlueBerry Ricatto Pancakes");
                    popular_modelList.add(popular_model);
                    break;
                default:
                    popular_model = new Popular_Model(R.drawable.lemon_ricotta_pancakes, "","");
                    popular_modelList.add(popular_model);

            }
        }
        popular_adpt=new Popular_adpt(getContext(),popular_modelList);
        popular_rec.setAdapter(popular_adpt);




        cuisine_modelList=new ArrayList<>();

        for (int i=0;i<=5;i++) {
            switch (i) {
                case 0:
                    cuisine_model = new Cuisine_Model(R.drawable.italian_cuisine, "French");
                    cuisine_modelList.add(cuisine_model);
                    break;
                case 1:
                    cuisine_model = new Cuisine_Model(R.drawable.italian_cuisine, "Italian");
                    cuisine_modelList.add(cuisine_model);
                    break;
                case 2:
                    cuisine_model = new Cuisine_Model(R.drawable.moroccan_food, "Moroccan");
                    cuisine_modelList.add(cuisine_model);
                    break;
                case 3:
                    cuisine_model = new Cuisine_Model(R.drawable.egypt_cuisine, "Egyptian");
                    cuisine_modelList.add(cuisine_model);
                    break;
                case 4:
                    cuisine_model = new Cuisine_Model(R.drawable.asian_cuisine, "Asian");
                    cuisine_modelList.add(cuisine_model);
                    break;
                case 5:
                    cuisine_model = new Cuisine_Model(R.drawable.egypt_cuisine, "Egyptian");
                    cuisine_modelList.add(cuisine_model);
                    break;
                default:
                    cuisine_model = new Cuisine_Model(R.drawable.lemon_ricotta_pancakes, "");
                    cuisine_modelList.add(cuisine_model);
            }
        }

        cuisine_adpt=new Cuisine_adpt(getContext(),cuisine_modelList);
        cuisine_rec.setAdapter(cuisine_adpt);

        return v;
    }

}
