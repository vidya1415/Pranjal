package com.example.aquabeing;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class navlin extends Fragment {
    private FirebaseAuth fAuthen;
    String userId;
    private FirebaseFirestore fs;
    TextView emailview, username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_header, container, false);
        TextView emailview = view.findViewById(R.id.user_email);
        TextView username = view.findViewById(R.id.username);

        Displaydata();
        return view;
    }

    public void Displaydata() {
        DocumentReference documentReference = fs.collection("customers").document(userId);
        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                if (documentSnapshot.exists()) {
                    emailview.setText(documentSnapshot.getString("email"));
                    username.setText(documentSnapshot.getString("name"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }
}