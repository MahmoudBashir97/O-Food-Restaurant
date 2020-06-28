package com.mahmoud.bashir.ofood.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.mahmoud.bashir.ofood.models.UsersInfo;


import de.hdodenhof.circleimageview.CircleImageView;

public class Login_Activity extends AppCompatActivity   {

    private static final String TAG = "Login_Activity";

    //FacebookHelper mfacebook;
    //GoogleHelper mgoogle;

    SignInButton google_sign;
    TextView to_signup;
    CircleImageView circle_facebook;
    EditText login_email,login_pass;
    Button login_btn;
    GoogleSignInClient mGoogleSignInClient;


    DatabaseReference send_user_info_to_databaserealtime;

    private static final int RC_SIGN_IN= 101;

    private FirebaseAuth mAuth;

    UsersInfo usersInfo;
    String CUID="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);



        if(SharedPrefranceManager.getInastance(this).isLoggedIn()){
            startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }

        //initViews
        to_signup=findViewById(R.id.to_signup);
        circle_facebook=findViewById(R.id.circle_facebook);
        login_email=findViewById(R.id.login_email);
        login_pass=findViewById(R.id.login_pass);
        login_btn=findViewById(R.id.login_btn);

        send_user_info_to_databaserealtime= FirebaseDatabase.getInstance().getReference().child("Users");





        // go to sign up page
        to_signup.setOnClickListener(view -> {
            Intent i=new Intent(Login_Activity.this,SignUp_Activity.class);
            startActivity(i);
        });

        login_btn.setOnClickListener(view -> {
            String getemail=login_email.getText().toString();
            String getpass=login_pass.getText().toString();

            if (getemail.isEmpty() | getpass.isEmpty()){
                login_email.setError("plz enter correct email");
                login_email.requestFocus();


                login_pass.setError("plz enter correct password");
                login_pass.requestFocus();
                return;
            }

            login_existinUser(getemail,getpass);

        });


        //init Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        google_sign = findViewById(R.id.google_sign);
        google_sign.setOnClickListener(view -> {
            signIn();
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            CUID=user.getUid();


                                Intent n=new Intent(Login_Activity.this,Verify_phone.class);
                                n.putExtra("email",user.getEmail());
                                n.putExtra("name",user.getDisplayName());
                                n.putExtra("sort","google");
                                startActivity(n);
                                finish();


                                usersInfo=new UsersInfo(user.getDisplayName(),user.getEmail(),user.getPhoneNumber()+"");

                                send_user_info_to_databaserealtime.child(CUID).setValue(usersInfo);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressBar();
                        // [END_EXCLUDE]
                    }
   });
    }


    public void login_existinUser(String email,String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            CUID=user.getUid();

                            Intent i=new Intent(Login_Activity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
