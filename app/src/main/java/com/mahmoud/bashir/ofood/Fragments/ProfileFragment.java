package com.mahmoud.bashir.ofood.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {


    DatabaseReference reference;
    FirebaseAuth mAuth;
    String CUID;

    TextView username;
    ImageView profile_img;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        username=v.findViewById(R.id.username);
        profile_img=v.findViewById(R.id.profile_img);


        username.setText(SharedPrefranceManager.getInastance(getContext()).getUsername());

        mAuth = FirebaseAuth.getInstance();
        CUID = mAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");

        Retrieve_UserInfo();

        return v;
    }


    public void Retrieve_UserInfo(){

        reference.child(CUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String getImage=String.valueOf(dataSnapshot.child("image").getValue());
                Picasso.get().load(getImage).placeholder(R.drawable.ic_add_a_photo_black_24dp).resize(200,150).centerInside().into(profile_img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
