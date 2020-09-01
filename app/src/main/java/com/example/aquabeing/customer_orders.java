package com.example.aquabeing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquabeing.models.productlist;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.example.aquabeing.models.cartmodel;

import java.util.HashMap;
import java.util.Map;

public class customer_orders extends AppCompatActivity {

    private RecyclerView recyclerViewlist;
    private FirebaseAuth fAuthen;
    String dealerID, orderID,custID;
    private FirebaseFirestore fs;
    FirestoreRecyclerAdapter adapter;
    String p0,p1,p2,p3,p4,p5,p6,p7;

    Button proceed;

//    ClickListener mClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        fAuthen = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

//        dealerID = fAuthen.getCurrentUser().getUid();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));
        recyclerViewlist = findViewById(R.id.recv1);
        dealerID = "GEAqykAGaOg9ktuqdWu1exj0H6q2";
        custID = fAuthen.getCurrentUser().getUid();

        orderID = fs.collection("customers").document(custID).collection("orders").getId();

        Log.d("TAG", dealerID);



        //Query
        Query query = fs.collection("customers").document(custID).collection("orders");     //RecyclerOptions

        //RecyclerOptions

        FirestoreRecyclerOptions<cartmodel> options = new FirestoreRecyclerOptions.Builder<cartmodel>()
                .setQuery(query, cartmodel.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<cartmodel, cartViewHolder>(options) {
            @NonNull
            @Override
            public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_listitem,parent,false);

                return new cartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull cartViewHolder holder, int position, @NonNull final cartmodel model)
            {
         holder.brand.setText(model.getBrand());
         holder.customer_name.setText(model.getCustomer_name());
         holder.customer_id.setText(model.getCustomer_id());
         holder.dealer_name.setText(model.getDealer_name());
         holder.quantity.setText(model.getQuantity());
         holder.totalprice.setText(model.getTotal_price());
         holder.amount.setText(model.getAmount());

//                p0 = model.getBrand();
//                p1 = model.getPrice();
//                p2 = model.getCustomername();
//                p3 = model.getCustomer_id();
//                p4 = model.getDealername();
//                p5 = model.getQuantityordered();
//                p6 = model.getTotal_price();



                holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final DocumentReference documentReference = fs.collection("dealers").document(dealerID).collection("orders").document();
                 final Map<String, Object> dealer = new HashMap<>();
                 dealer.put("amount",model.getAmount());
                 dealer.put("brand", model.getBrand());
                 dealer.put("customer_id",model.getCustomer_id());
                 dealer.put("dealer_name",model.getDealer_name());
                 dealer.put("customer_name",model.getCustomer_name());
                 dealer.put("quantity", model.getQuantity());
                 dealer.put("total_price", model.getTotal_price());

                 new AlertDialog.Builder(customer_orders.this)
                         .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                         .setTitle("Are you sure you want to buy")
//set message
                         .setMessage("Pay on delivery")
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 Toast.makeText(getApplicationContext(), "Nothing registered", Toast.LENGTH_LONG).show();
                                 Intent intent = new Intent(customer_orders.this, product_details.class);
                                 startActivity(intent);
                             }
                         })
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                                 documentReference.set(dealer)
                                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                                             @Override
                                             public void onSuccess(Void aVoid) {
                                                 Log.d("TAG", "Dealer ID: " + dealerID);
                                                 Toast.makeText(customer_orders.this,"added to cart",Toast.LENGTH_SHORT).show();

                                             }
                                         }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Log.d("TAG", "Dealer ID not added: " + dealerID);

                                     }
                                 });
                                 Intent intent = new Intent(customer_orders.this,OrderplacedScreen.class);
                                 startActivity(intent);

                             }
                         }).show();
                 

                             }
         });


            }
        };
        //View Holder
        recyclerViewlist.setHasFixedSize(true);
        recyclerViewlist.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewlist.setAdapter(adapter);


    }

    private class cartViewHolder extends RecyclerView.ViewHolder{

        private TextView brand;
        private TextView quantity;
        private TextView amount;
        private TextView customer_name;
        private TextView customer_id;
        private TextView dealer_name;
        private TextView totalprice;


        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.brand);
            quantity = itemView.findViewById(R.id.quantity);
            amount = itemView.findViewById(R.id.amount);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_id = itemView.findViewById(R.id.customer_id);
            dealer_name = itemView.findViewById(R.id.dealer_name);
            totalprice = itemView.findViewById(R.id.total_price);

        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
