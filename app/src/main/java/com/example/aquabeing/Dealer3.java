package com.example.aquabeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dealer3 extends AppCompatActivity {
    Button btnaddshop,btnorders,btnprocessed,btnupdateorders,orderfromowner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer3);

        btnaddshop= findViewById(R.id.addshop);
        btnorders= findViewById(R.id.orders);
        btnprocessed= findViewById(R.id.procorders);
        btnupdateorders= findViewById(R.id.update);
        orderfromowner = findViewById(R.id.btnorderfromowner);

       btnaddshop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Dealer3.this,DealerLocationActivity.class);
               startActivity(intent);
           }

       });

       btnorders.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Dealer3.this,dealers_orders.class);
               startActivity(intent);
           }
       });
       btnupdateorders.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Dealer3.this,dealer_update.class);
               startActivity(intent);
           }
       });


    }
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}