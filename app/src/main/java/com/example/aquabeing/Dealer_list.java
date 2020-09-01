package com.example.aquabeing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

public class Dealer_list extends AppCompatActivity {
    private FirebaseFirestore fs;

    private CollectionReference notebookref;
    private NoteAdapter noteAdapter;


    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_list);
        //dealer_list = findViewById(R.id.dealer_list_rec_view);
        fs = FirebaseFirestore.getInstance();
        notebookref = fs.collection("dealers");

        setUpRecyclerView();



       /* notebookref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                Note note = documentSnapshot.toObject(Note.class);
                String dealer_name = note.getName();
                String dealer_address = note.getAddress();

                data += "Dealer Name: "+dealer_name + "\nDealer Address: "+dealer_address +"\n\n";
                }
                dealer_list.
            }
        });*/
    }
    private void setUpRecyclerView(){
        final Note note = new Note();
        final String dealerid = fs.collection("dealers").getId();
        final Query query = fs.collection("dealers");
        final FirestoreRecyclerOptions<Note> option = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();
        noteAdapter = new NoteAdapter(option);
        RecyclerView recyclerView = findViewById(R.id.dealer_list_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter.setCustomOnClickListener(new NoteAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("TAG", "Name Click at " +position);
//                             String Dealerid = fs.collection("dealers").document().getId();
//                Bundle bundle = new Bundle();
//                bundle.putString("dealers",note.getName());
                Intent activityBstartIntent = new Intent(getApplicationContext(), dealer_products_list.class);
                activityBstartIntent.putExtra("key", dealerid.toString());
                startActivity(activityBstartIntent);




            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("TAG", "Name Click at " +position);

            }
        });

        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }


}