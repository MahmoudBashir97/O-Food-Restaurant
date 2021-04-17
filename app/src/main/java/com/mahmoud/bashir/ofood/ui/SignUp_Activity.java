package com.mahmoud.bashir.ofood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mahmoud.bashir.ofood.R;

public class SignUp_Activity extends AppCompatActivity {

    TextView to_login;
    EditText signup_name,signup_email,signup_pass,signup_phone;
    Button signup_btn;
    private FirebaseAuth mAuth;
    String CUID="";
    String getname,getemail,getpassword,getphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        to_login=findViewById(R.id.to_login);
        signup_name=findViewById(R.id.signup_name);
        signup_email=findViewById(R.id.signup_email);
        signup_pass=findViewById(R.id.signup_pass);
        signup_phone=findViewById(R.id.signup_phone);
        signup_btn=findViewById(R.id.signup_btn);


        to_login.setOnClickListener(view -> {
            Intent i =new Intent(SignUp_Activity.this,Login_Activity.class);
            startActivity(i);
            finish();
        });

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        signup_btn.setOnClickListener(view -> {
            getname=signup_name.getText().toString();
            getemail=signup_email.getText().toString();
            getpassword=signup_pass.getText().toString();
            getphone=signup_phone.getText().toString();

            if (getname.isEmpty()| getemail.isEmpty() | getpassword.isEmpty() | getphone.isEmpty()){

                signup_name.setError("plz enter ur name!");
                signup_name.requestFocus();

                signup_email.setError("plz enter ur email!");
                signup_name.requestFocus();

                signup_pass.setError("plz enter ur password!");
                signup_name.requestFocus();

                signup_phone.setError("plz enter ur phone!");
                signup_name.requestFocus();

                return;

            }else{
                signin_auth(getemail,getpassword);
            }
        });
    }

    public void signin_auth (String email ,String pass){
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent i =new Intent(SignUp_Activity.this,Verify_phone.class);
                            i.putExtra("sort","sign_up");
                            i.putExtra("name",getname);
                            i.putExtra("phone",getphone);
                            i.putExtra("email",getemail);
                            startActivity(i);

                        } else {
                            Toast.makeText(SignUp_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
