package com.mahmoud.bashir.ofood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mahmoud.bashir.ofood.R;

public class Payment_Methods_Activity extends AppCompatActivity {

    ImageView back_butn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__methods);

        back_butn=findViewById(R.id.back_butn);

        back_butn.setOnClickListener(view -> {
            Intent i=new Intent(Payment_Methods_Activity.this,Product_details_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
    }
}
