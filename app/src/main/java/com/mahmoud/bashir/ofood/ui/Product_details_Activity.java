package com.mahmoud.bashir.ofood.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.mahmoud.bashir.ofood.R;
import com.mahmoud.bashir.ofood.Room.Add_To_Cart_DB.Add_To_cart_Schema;
import com.mahmoud.bashir.ofood.Storage.SharedPrefranceManager;
import com.mahmoud.bashir.ofood.ViewModel.Add_To_Cart_viewModel;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class Product_details_Activity extends AppCompatActivity {

    private static final String CHANNEL_ID ="BUBLES_CART" ;
    ImageView product_img;
    TextView txt_name_pop,txt_desc_pop,total_price;
    Button add_to_cart_btn,place_order_btn;
    int getImage;
    String getName,getDesc,Price;

    Add_To_Cart_viewModel add_to_cart_viewModel;
    Add_To_cart_Schema addToCartSchema;


    @RequiresApi(api = Build.VERSION_CODES.Q)
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


        //add to cart view model
        add_to_cart_viewModel = ViewModelProviders.of(this).get(Add_To_Cart_viewModel.class);




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
                Price=value+"";

                if (value == 0){
                    add_to_cart_btn.setEnabled(false);
                    place_order_btn.setEnabled(false);
                }else if (value > 0){
                    add_to_cart_btn.setEnabled(true);
                    place_order_btn.setEnabled(true);
                }
            }
        });


        add_to_cart_btn.setOnClickListener(view -> {

            int id = SharedPrefranceManager.getInastance(Product_details_Activity.this).getID();
            if (id >= -1){
                id=id+1;
                addToCartSchema = new Add_To_cart_Schema(id,getName,getDesc,getImage,Price);
                SharedPrefranceManager.getInastance(Product_details_Activity.this).save_Add_to_cart_ID(id);
                SharedPrefranceManager.getInastance(this).save_Add_to_cart_Marker(true);
            }
            add_to_cart_viewModel.insert(addToCartSchema);
            });
    }

    public float calculate_total_price(int numbers){

        float total= (int)(13.00 * numbers);

        return total;
    }


    public void place_order_onClick(View view) {
        Toast.makeText(this, "Place Order !", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Product_details_Activity.this,Payment_Methods_Activity.class);
        startActivity(i);
    }
}
