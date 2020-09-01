package com.example.aquabeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class First extends AppCompatActivity {
    private  Button dealer;
    private  Button customer, owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        dealer = findViewById(R.id.btndealer);
        customer = findViewById(R.id.btncustomer);
        owner = findViewById(R.id.btnowner);

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this,owner1.class);
                startActivity(intent);
            }
        });

        dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this,Dealer1.class);
                startActivity(intent);

            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}