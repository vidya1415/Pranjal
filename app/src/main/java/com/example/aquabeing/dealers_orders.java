package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class dealers_orders extends AppCompatActivity {
    private FirebaseFirestore fs;
    private CollectionReference collref;
    private NoteAdapterOrdersForDealer noteAdapterOrdersForDealer;
    String custID,dealerID,orderID;
    private FirebaseAuth fAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealers_orders);
        fs = FirebaseFirestore.getInstance();
        fAuthen = FirebaseAuth.getInstance();
        dealerID = fAuthen.getCurrentUser().getUid();

        orderID = fs.collection("dealers").document(dealerID).collection("orders").document().getId();
        final DocumentReference documentReference = fs.collection("dealers").document(dealerID).collection("orders").document(orderID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                custID = documentSnapshot.getString("customer_id");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });
        collref = fs.collection("customers").document("100000001").collection("orders");
        setUpRecyclerViewforDealer();
    }
    public void setUpRecyclerViewforDealer(){
        Query query = fs.collection("dealers").document(dealerID).collection("orders");
        FirestoreRecyclerOptions<NoteOrdersForDealer> options = new FirestoreRecyclerOptions.Builder<NoteOrdersForDealer>()
                .setQuery(query, NoteOrdersForDealer.class).build();
        noteAdapterOrdersForDealer = new NoteAdapterOrdersForDealer(options);
        RecyclerView recyclerView = findViewById(R.id.dealer_orders_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapterOrdersForDealer);
    }
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapterOrdersForDealer.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapterOrdersForDealer.stopListening();
    }
}