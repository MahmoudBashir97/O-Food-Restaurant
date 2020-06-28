package com.mahmoud.bashir.ofood.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class ProfileFragment extends Fragment {


    private static final int IMAGE_REQUEST=1;
    private static final int Gallerypick=1;


    // firebase
    private StorageReference UserprofileImage;
    StorageReference storageReference;

    private Uri imageuri;
    private StorageTask uploadtask;
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
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserprofileImage = FirebaseStorage.getInstance().getReference().child("Profile Images");

        profile_img.setOnClickListener(view -> {
                openImage();
        });


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

                        reference= FirebaseDatabase.getInstance().getReference("Users").child(CUID);
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
}
