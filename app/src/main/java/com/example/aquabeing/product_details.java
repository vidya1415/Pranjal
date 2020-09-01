package com.example.aquabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aquabeing.models.productlist;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class product_details extends AppCompatActivity {
    private FloatingActionButton addtocartbtn;
    private ImageView productimg;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productType, productname, dealername;
    private String productID = "";

    private FirebaseAuth fAuthen;
    String dealerID = "";
    private FirebaseFirestore fs;
    String p;
    String p1;
    String p2,p3,custname;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_items_layout);
        fs = FirebaseFirestore.getInstance();
        fAuthen = FirebaseAuth.getInstance();

              final String userId =(fAuthen.getCurrentUser()).getUid();

//
//        productID = getIntent().getStringExtra("pid");
//        dealerID =  getIntent().getStringExtra("did");

        dealerID = "GEAqykAGaOg9ktuqdWu1exj0H6q2";
        productID = fs.collection("dealers").document(dealerID).collection("products").document().getId();


        addtocartbtn = (FloatingActionButton) findViewById(R.id.addtocart);
        numberButton = (ElegantNumberButton) findViewById(R.id.counter);
        productimg = (ImageView) findViewById(R.id.cart_image_view);
        dealername = (TextView) findViewById(R.id.cart_dealer_name);
        productname = (TextView) findViewById(R.id.cart_prod_name);
        productType = (TextView) findViewById(R.id.cart_prod_type);
        productPrice = (TextView) findViewById(R.id.prod_price);

        DocumentReference productref = fs.collection("dealers").document("GEAqykAGaOg9ktuqdWu1exj0H6q2").collection("products").document("8x1Rryxnw59k9O0KDkGF");
        productref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//               dealername.setText(documentSnapshot.getString("Name"));
                productname.setText(documentSnapshot.getString("brand"));
                productType.setText(documentSnapshot.getString("price"));
                productPrice.setText(documentSnapshot.getString("type"));
                 p=documentSnapshot.getString("brand");
                 p1 = documentSnapshot.getString("price");
                 p2 = documentSnapshot.getString("type");
                 p3 = documentSnapshot.getString("Name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });

        DocumentReference dealername1 = fs.collection("dealers").document(dealerID);
        dealername1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dealername.setText(documentSnapshot.getString("Name"));
                p3 = documentSnapshot.getString("name");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");

            }
        });





        DocumentReference customerref = fs.collection("customers").document(userId);
        customerref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                custname= documentSnapshot.getString("name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");

            }
        });

        addtocartbtn.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = fs.collection("customers").document(userId).collection("orders").document();
                final Map<String, Object> dealer = new HashMap<>();
                dealer.put("amount", p1);
                dealer.put("brand", p);
                dealer.put("customer_id", userId);
                dealer.put("dealer_name",p3);
                dealer.put("customer_name",custname);
                dealer.put("quantity", "1");
                dealer.put("total_price", "30");
                documentReference.set(dealer)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "Dealer ID: " + userId);
                                Toast.makeText(product_details.this,"added to cart",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Dealer ID not added: " + userId);

                    }
                });
                Intent intent = new Intent(product_details.this,customer_orders.class);
                startActivity(intent);


            }




        });


    }

}

