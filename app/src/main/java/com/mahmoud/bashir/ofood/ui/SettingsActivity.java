package com.mahmoud.bashir.ofood.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mahmoud.bashir.ofood.Fragments.HomeFragment;
import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Services.Notification_Receivers;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat switch_notification;
    RelativeLayout log_out;
    ImageView img_back;

    String CUID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);




        switch_notification=findViewById(R.id.switch_notification);
        log_out=findViewById(R.id.log_out);
        img_back=findViewById(R.id.img_back);

        CUID=getIntent().getStringExtra("CUID");

        img_back.setOnClickListener(view -> {
            onSupportNavigateUp();
        });


        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Alarm_check_to_Notification(b);
                }else{
                    Toast.makeText(SettingsActivity.this, "off", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //logout button
        log_out.setOnClickListener(view -> {
            SharedPrefranceManager.getInastance(this).clearUser();
            Intent i = new Intent(SettingsActivity.this,Login_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });


    }
    public void Alarm_check_to_Notification(boolean state){
        AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent myintent;
        PendingIntent pendingIntent;

        myintent = new Intent(SettingsActivity.this, Notification_Receivers.class);
        myintent.putExtra("news","hey we added new Items , come to see it!");
        myintent.putExtra("CUID",CUID);




        if (state){

            pendingIntent = PendingIntent.getBroadcast(this,0,myintent,0);
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+6000,pendingIntent);

        }



           // manager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+6000,6000,pendingIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }



}
