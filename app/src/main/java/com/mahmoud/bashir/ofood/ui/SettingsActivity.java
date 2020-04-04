package com.mahmoud.bashir.ofood.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mahmoud.bashir.ofood.Fragments.HomeFragment;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat switch_notification;
    RelativeLayout log_out;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switch_notification=findViewById(R.id.switch_notification);
        log_out=findViewById(R.id.log_out);
        img_back=findViewById(R.id.img_back);

        img_back.setOnClickListener(view -> {
            Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });


        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(SettingsActivity.this, "on", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingsActivity.this, "off", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //logout button
        log_out.setOnClickListener(view -> {

            SharedPrefranceManager.getInastance(this).clearUser();
            startActivity(new Intent(getApplicationContext(),Login_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });


    }
}
