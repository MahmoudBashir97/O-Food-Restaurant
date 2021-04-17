package com.mahmoud.bashir.ofood.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Repository;
import com.mahmoud.bashir.ofood.Room.Favourite_DB.Favourite_Schema;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.mahmoud.bashir.ofood.ViewModel.Favourite_viewModel;
import com.mahmoud.bashir.ofood.adapters.Category_adpt;
import com.mahmoud.bashir.ofood.adapters.Cuisine_adpt;
import com.mahmoud.bashir.ofood.adapters.Popular_adpt;
import com.mahmoud.bashir.ofood.adpt_interfaces.fav_checked_btn;
import com.mahmoud.bashir.ofood.models.Category_Model;
import com.mahmoud.bashir.ofood.models.Cuisine_Model;
import com.mahmoud.bashir.ofood.models.Popular_Model;
import com.mahmoud.bashir.ofood.ui.SettingsActivity;
import com.mahmoud.bashir.ofood.ui.Shopping_cart_Activity;
import com.mahmoud.bashir.ofood.viewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements fav_checked_btn{


    private static final int IMAGE_REQUEST=1;
    private static final int Gallerypick=1;

    // firebase
    DatabaseReference reference;
    private StorageReference UserprofileImage;
    StorageReference storageReference;
    private FirebaseAuth auth;

    private Uri imageuri;
    private StorageTask uploadtask;
    String Current_user_ID;

    //imgs btns
    ImageView settings_btn , shopping_cart,user_image,added_check_marker;

    TextView txt_usename;

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
    //ViewModel
    Favourite_viewModel viewModel;
    String phone_no;

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
        settings_btn=v.findViewById(R.id.settings_btn);
        shopping_cart=v.findViewById(R.id.shopping_cart);
        txt_usename=v.findViewById(R.id.txt_usename);
        user_image=v.findViewById(R.id.user_image);
        added_check_marker=v.findViewById(R.id.added_check_marker);
        Favourite_Repository repository = new Favourite_Repository(getActivity().getApplication());
        viewModelFactory viewFactory = new viewModelFactory(getActivity().getApplication(),repository);
        viewModel = new ViewModelProvider(this, viewFactory).get(Favourite_viewModel.class);



        if (SharedPrefranceManager.getInastance(getContext()).getChecked().equals("true")){
            added_check_marker.setVisibility(View.VISIBLE);
        }

        auth = FirebaseAuth.getInstance();
        Current_user_ID = auth.getCurrentUser().getUid();
        phone_no = SharedPrefranceManager.getInastance(this.getContext()).getUserPhone();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(phone_no);
        UserprofileImage = FirebaseStorage.getInstance().getReference().child("Profile Images");

        RetrieveUserInfo();

        //get from storage
        txt_usename.setText(SharedPrefranceManager.getInastance(getContext()).getUsername());

        user_image.setOnClickListener(view -> {

            openImage();

        });


        settings_btn.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), SettingsActivity.class);
            intent.putExtra("CUID",Current_user_ID);
            startActivity(intent);
        });
        shopping_cart.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), Shopping_cart_Activity.class);
            startActivity(intent);
        });


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


       setCategoryList();
       setPopularList();
       setCuisineList();
        viewModel.getAllFavs().observe(getActivity(), new Observer<List<Favourite_Schema>>() {
            @Override
            public void onChanged(List<Favourite_Schema> favourite_schemas) {
                popular_adpt=new Popular_adpt(getContext(),popular_modelList,favourite_schemas,HomeFragment.this);
                popular_rec.setAdapter(popular_adpt);
            }
        });
        return v;
    }

    private void setPopularList() {
        int[] popularImgs = {
                R.drawable.lemon_ricotta_pancakes,
                R.drawable.lemon_ricotta_pancakes2,
                R.drawable.lemon_ricotta_pancakes,
                R.drawable.banana_oat_pancakes_title,
                R.drawable.french_food,
                R.drawable.egypt_cuisine
        };

        String[] popularNames = {
                "BlueBerry Ricatto Pancakes ",
                "BlueBerry Ricatto Pancakes ",
                "BlueBerry Ricatto Pancakes ",
                "BlueBerry Ricatto Pancakes ",
                "BlueBerry Ricatto Pancakes ",
                "BlueBerry Ricatto Pancakes "
        };
        String[] popularDesc = {
                "Fluffy and light BlueBerry Ricatto Pancakes",
                "Fluffy and light BlueBerry Ricatto Pancakes",
                "Fluffy and light BlueBerry Ricatto Pancakes",
                "Fluffy and light BlueBerry Ricatto Pancakes",
                "Fluffy and light BlueBerry Ricatto Pancakes",
                "Fluffy and light BlueBerry Ricatto Pancakes"
        };
        popular_modelList=new ArrayList<>();
        for (int i=0;i<popularImgs.length;i++) {
           Popular_Model popular_model = new Popular_Model(popularImgs[i],popularNames[i],popularDesc[i]);
           popular_modelList.add(popular_model);
        }
    }

    private void setCategoryList() {
        int[] catImgs ={
                R.drawable.breakfast,
                R.drawable.cake,
                R.drawable.fruits,
                R.drawable.indian,
                R.drawable.brazilian,
                R.drawable.indian,
        };
        String[] catNames={
                "Breakfast",
                "Sweets",
                "Fruits",
                "Indian",
                "Brazilian",
                "Egyptian"
        };
        category_modelList=new ArrayList<>();
        for (int i = 0; i <catImgs.length; i++) {
            Category_Model category_model = new Category_Model(catImgs[i],catNames[i]);
            category_modelList.add(category_model);
        }
        category_adpt=new Category_adpt(getContext(),category_modelList);
        category_rec.setAdapter(category_adpt);
    }

    private void setCuisineList() {
        int[] cuisineImgs = {
                R.drawable.italian_cuisine,
                R.drawable.moroccan_food,
                R.drawable.egypt_cuisine,
                R.drawable.asian_cuisine,
                R.drawable.egypt_cuisine,
                R.drawable.italian_cuisine,
        };
        String[] cuisineNames={
                "French",
                "Moroccan",
                "Egyptian",
                "Asian",
                "Egyptian",
                "Italian"
        };
        cuisine_modelList=new ArrayList<>();
        for (int i =0;i<cuisineImgs.length;i++){
           Cuisine_Model cuisine_model = new Cuisine_Model(cuisineImgs[i],cuisineNames[i]);
           cuisine_modelList.add(cuisine_model);
        }

        cuisine_adpt=new Cuisine_adpt(getContext(),cuisine_modelList);
        cuisine_rec.setAdapter(cuisine_adpt);
    }


    private void openImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadimage(){
        final ProgressDialog pd=new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();
        if (imageuri!=null){
            final StorageReference filereference=UserprofileImage.child(System.currentTimeMillis()+
                    ","+getFileExtension(imageuri));
            uploadtask=filereference.putFile(imageuri);
            uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUri= (Uri) task.getResult();
                        String muri=downloadUri.toString();

                        reference= FirebaseDatabase.getInstance().getReference("Users").child(phone_no);
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("image",muri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_REQUEST && resultCode== Activity.RESULT_OK
                && data !=null && data.getData() !=null) {
            imageuri = data.getData();

            if (uploadtask != null && uploadtask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadimage();
            }
        }
    }

    private void RetrieveUserInfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.child("image").getValue().toString().equals(null)) {
                    String prof_uri= String.valueOf(dataSnapshot.child("image").getValue());
                    String usName= String.valueOf(dataSnapshot.child("user_name").getValue());
                    String usEmail= String.valueOf(dataSnapshot.child("email").getValue());
                    String usPhone= String.valueOf(dataSnapshot.child("phone_no").getValue());
                    Picasso.get().load(prof_uri).resize(80, 70).centerInside().placeholder(R.drawable.ic_add_a_photo_black_24dp).into(user_image);
                    SharedPrefranceManager.getInastance(getContext()).saveUser(usName,usEmail,usPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void BtnStatus(String status,Favourite_Schema schema) {
        if (status.equals("insert") && schema != null){
            viewModel.insert(schema).subscribeOn(Schedulers.computation())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                }

                @Override
                public void onComplete() {
                    viewModel.Update(schema);
                    Log.d("checkbtn :","Favourite Data Inserted");
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                }
            });
        }else {
            viewModel.delete(schema).subscribeOn(Schedulers.computation())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            viewModel.Update(schema);
                            Log.d("checkbtn : ","Favourite Data Deleted");
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });
        }
    }
}