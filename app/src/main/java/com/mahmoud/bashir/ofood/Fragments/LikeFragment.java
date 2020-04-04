package com.mahmoud.bashir.ofood.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Room.Favourite_Schema;
import com.mahmoud.bashir.ofood.ViewModel.Favourite_viewModel;
import com.mahmoud.bashir.ofood.adapters.fav_adpt;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {

    private Favourite_viewModel favourite_viewModel;
    RecyclerView fav_rec;
    fav_adpt favAdpt;


    public LikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_like, container, false);

        //initViews
        fav_rec=v.findViewById(R.id.fav_rec);
        fav_rec.setHasFixedSize(true);
        fav_rec.setLayoutManager(new LinearLayoutManager(getContext()));

        favAdpt=new fav_adpt();

        fav_rec.setAdapter(favAdpt);



        favourite_viewModel= ViewModelProviders.of(getActivity()).get(Favourite_viewModel.class);
        favourite_viewModel.getAllFavs().observe(this, new Observer<List<Favourite_Schema>>() {
            @Override
            public void onChanged(List<Favourite_Schema> favourite_schemas) {

                favAdpt.submitList(favourite_schemas);
            }
        });

        return v;
    }

}
