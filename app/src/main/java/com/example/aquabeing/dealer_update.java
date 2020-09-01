package com.example.aquabeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class dealer_update extends AppCompatActivity {

    private ImageView bisleri_add_dealer, aquafina_add_dealer;
    private ImageView kinley_add_dealer, divyajal_add_dealer;
    private ImageView tataplus_add_dealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_update);

        bisleri_add_dealer = (ImageView) findViewById(R.id.bisleri_add_dealer);
        aquafina_add_dealer = (ImageView) findViewById(R.id.aquafina_add_dealer);
        kinley_add_dealer = (ImageView) findViewById(R.id.kinley_add_dealer);
        divyajal_add_dealer = (ImageView) findViewById(R.id.divyajal_add_dealer);
        tataplus_add_dealer = (ImageView) findViewById(R.id.tataplus_add_dealer);




        bisleri_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dealer_update.this, Addnewproduct_dealer.class);
                intent.putExtra("category", "bisleri_add_dealer");
                startActivity(intent);
            }
        });

        aquafina_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dealer_update.this, Addnewproduct_dealer.class);
                intent.putExtra("category", "aquafina_add_dealer");
                startActivity(intent);
            }
        });

        kinley_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dealer_update.this, Addnewproduct_dealer.class);
                intent.putExtra("category", "kinley_add_dealer");
                startActivity(intent);
            }
        });

        divyajal_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dealer_update.this, Addnewproduct_dealer.class);
                intent.putExtra("category", "divyajal_add_dealer");
                startActivity(intent);
            }
        });

        tataplus_add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dealer_update.this, Addnewproduct_dealer.class);
                intent.putExtra("category", "tataplus_add_dealer");
                startActivity(intent);
            }
        });
    }
}