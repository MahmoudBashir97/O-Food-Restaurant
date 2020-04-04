package com.mahmoud.bashir.ofood.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class Product_details_Activity extends AppCompatActivity {

    ImageView product_img;
    TextView txt_name_pop,txt_desc_pop,total_price;
    Button add_to_cart_btn,place_order_btn;
    int getImage;
    String getName,getDesc;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);



        // init view
        product_img=findViewById(R.id.product_img);
        txt_name_pop=findViewById(R.id.txt_name_pop);
        txt_desc_pop=findViewById(R.id.txt_desc_pop);
        total_price=findViewById(R.id.total_price);
        add_to_cart_btn=findViewById(R.id.add_to_cart_btn);
        place_order_btn=findViewById(R.id.place_order_btn);

        // get Intent data
        /*int getImage=getIntent().getIntExtra("img_pop",1);
        String getName=getIntent().getStringExtra("name_pop");
        String getDesc=getIntent().getStringExtra("desc_pop");*/

        getImage = Integer.parseInt(SharedPrefranceManager.getInastance(this).getPop_Image());
        getName  = SharedPrefranceManager.getInastance(this).getPop_Name();
        getDesc  = SharedPrefranceManager.getInastance(this).getPop_Desc();



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
               // Toast.makeText(getApplicationContext(), value + "", Toast.LENGTH_SHORT).show();
                String c="$"+calculate_total_price(value);
                total_price.setText(c);

                if (value == 0){
                    add_to_cart_btn.setEnabled(false);
                    place_order_btn.setEnabled(false);
                }else if (value > 0){
                    add_to_cart_btn.setEnabled(true);
                    place_order_btn.setEnabled(true);
                }
            }
        });

    }

    public float calculate_total_price(int numbers){

        float total= (int)(13.00 * numbers);

        return total;
    }


    public void add_to_cart_OnClick(View view) {
        Toast.makeText(this, "Add To Cart !", Toast.LENGTH_SHORT).show();
    }

    public void place_order_onClick(View view) {
        Toast.makeText(this, "Place Order !", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Product_details_Activity.this,Payment_Methods_Activity.class);
        startActivity(i);
    }
}
