package com.mahmoud.bashir.ofood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.mahmoud.bashir.ofood.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class Payment_Methods_Activity extends AppCompatActivity {

    ImageView back_butn;
    EasyFlipView flipview;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__methods);

        flipview = findViewById(R.id.flipview);
        back_butn=findViewById(R.id.back_butn);



        timer = new CountDownTimer(3000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try{
                    flipview.flipTheView();
                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();

        back_butn.setOnClickListener(view -> {
            Intent i=new Intent(Payment_Methods_Activity.this,Product_details_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
    }
}
