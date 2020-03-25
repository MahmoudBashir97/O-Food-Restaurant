package com.mahmoud.bashir.ofood.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mahmoud.bashir.ofood.R;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class Product_details_Activity extends AppCompatActivity {

    ImageView product_img;
    TextView txt_name_pop,txt_desc_pop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);



        // init view
        product_img=findViewById(R.id.product_img);
        txt_name_pop=findViewById(R.id.txt_name_pop);
        txt_desc_pop=findViewById(R.id.txt_desc_pop);

        // get Intent data
        int getImage=getIntent().getIntExtra("img_pop",1);
        String getName=getIntent().getStringExtra("name_pop");
        String getDesc=getIntent().getStringExtra("desc_pop");

        // set data to views
        product_img.setImageResource(getImage);
        txt_name_pop.setText(getName);
        txt_desc_pop.setText(getDesc);


        StepperTouch stepperTouch = findViewById(R.id.stepperTouch);
        stepperTouch.setMinValue(0);
        stepperTouch.setMaxValue(20);
        stepperTouch.setSideTapEnabled(true);
        stepperTouch.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean positive) {
                Toast.makeText(getApplicationContext(), value + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
