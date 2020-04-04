package com.mahmoud.bashir.ofood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Verify_phone extends AppCompatActivity {


    TextInputEditText phone_no,verify_code;
    Button ok_btn,verify_code_btn;

    private FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseDatabase database;

    String getph_no="",getname,getemail,getsort,getSignupphone;

    private String verificationID;

    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        //init views
        phone_no=findViewById(R.id.phone_no);
        verify_code=findViewById(R.id.verify_code);
        verify_code_btn=findViewById(R.id.verify_code_btn);
        ok_btn=findViewById(R.id.ok_btn);



        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();



        getsort=getIntent().getStringExtra("sort");
        getemail=getIntent().getStringExtra("email");
        getname=getIntent().getStringExtra("name");
        getSignupphone=getIntent().getStringExtra("phone");

        //Toast.makeText(Verify_phone.this, ""+"email : "+getemail +" " + " name : "+getname, Toast.LENGTH_SHORT).show();


        if (getsort.equals("sign_up")){

            Toast.makeText(Verify_phone.this, ""+getSignupphone, Toast.LENGTH_SHORT).show();

            phone_no.setVisibility(View.GONE);
            ok_btn.setVisibility(View.GONE);

            verify_code.setVisibility(View.VISIBLE);
            verify_code_btn.setVisibility(View.VISIBLE);

            sendVerificationCode(getSignupphone);



            verify_code_btn.setOnClickListener(view -> {
                String verf_c=verify_code.getText().toString();
                if (verf_c.isEmpty() || verf_c.length()<6){
                    verify_code.setError("plz enter right code ...");
                    verify_code.requestFocus();
                    return;
                }
                verifycode(verf_c,getemail,getname,getSignupphone,"");

            });

        }



        if (getsort.equals("google")) {


            ok_btn.setOnClickListener(view -> {
                phone_no.setVisibility(View.INVISIBLE);
                ok_btn.setVisibility(View.INVISIBLE);


                verify_code.setVisibility(View.VISIBLE);
                verify_code_btn.setVisibility(View.VISIBLE);

                getph_no = phone_no.getText().toString();

                Toast.makeText(Verify_phone.this, ""+getph_no, Toast.LENGTH_SHORT).show();
                if (getph_no.isEmpty() || getph_no.length() < 6) {
                    phone_no.setError("plz enter right phone number with ur country code ...");
                    phone_no.requestFocus();
                    return;
                }
                sendVerificationCode(getph_no);

            });

            verify_code_btn.setOnClickListener(view -> {
                String verf_c = verify_code.getText().toString();
                if (verf_c.isEmpty() || verf_c.length() < 6) {
                    verify_code.setError("plz enter right code ...");
                    verify_code.requestFocus();
                    return;
                }
                verifycode(verf_c, getemail, getname, getph_no, getsort);

            });
        }
    }

    private void verifycode(String code,String email,String fname,String phone,String sort){
        try { PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,code);
            signWithCredential(credential,fname,email,phone,sort);

        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }


    private void signWithCredential(PhoneAuthCredential credential,final String fname,final String email, final String phone,String sort) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                           String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("email",email);
                            hashMap.put("user_name", fname);
                            hashMap.put("phone_no",getph_no);


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        if (sort.equals("sign_up")){
                                            intent = new Intent(Verify_phone.this, Login_Activity.class);
                                            startActivity(intent);
                                            SharedPrefranceManager.getInastance(context).saveUser(fname,email,phone);
                                            finish();

                                        }else {
                                            intent = new Intent(Verify_phone.this, MainActivity.class);
                                            intent.putExtra("id", userid);
                                            intent.putExtra("user_ph",SharedPrefranceManager.getInastance(context).getUserPhone());
                                            intent.putExtra("name",SharedPrefranceManager.getInastance(context).getUsername());
                                            intent.putExtra("email",SharedPrefranceManager.getInastance(context).getUserEmail());
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();

                                            SharedPrefranceManager.getInastance(context).saveUser(fname,email,phone);

                                        }
                                    }
                                }

                            });

                            /* intent = new Intent(Verify_phone.this, City.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);
                           finish();*/
                        } else {
                            Toast.makeText(Verify_phone.this, "You can not register with this email & password !!", Toast.LENGTH_SHORT).show();
                        }



                    }

                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String codesms=phoneAuthCredential.getSmsCode();
            if (codesms !=null){
                verifycode(codesms,null,null,null,null);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verify_phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
