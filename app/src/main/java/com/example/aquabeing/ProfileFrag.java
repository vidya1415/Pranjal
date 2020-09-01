package com.example.aquabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;
import java.util.concurrent.Executor;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ProfileFrag extends Fragment {
    private FirebaseAuth fAuthen;
    String userId;
    private FirebaseFirestore fs;
    TextView emailview,username;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragprofile,container,false);
        Button orders = view.findViewById(R.id.orders);
        Button pre_orders = view.findViewById(R.id.procorders);
        Button edit_profile = view.findViewById(R.id.Edit_profile);
        Button logout = view.findViewById(R.id.Sign_out);

        fAuthen = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        if(fAuthen.getCurrentUser() != null){
            ProfileFrag fragment = new ProfileFrag();

        }

        emailview = view.findViewById(R.id.user_email);
        username = view.findViewById(R.id.username);


         Displaydata();
      logout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              FirebaseAuth.getInstance().signOut();//logout
              startActivity(new Intent(getActivity(),MainActivity2.class));

          }
      });



        return view;

    }
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void Displaydata() {
      userId = Objects.requireNonNull(fAuthen.getCurrentUser()).getUid();
      DocumentReference documentReference = fs.collection("customers").document(userId);
      documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
          @Override
          public void onSuccess(DocumentSnapshot documentSnapshot) {
              emailview.setText(documentSnapshot.getString("email"));
              username.setText(documentSnapshot.getString("name"));
              Log.d("tag", "onEvent: Document do  exists");

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Log.d("tag", "onEvent: Document do not exists");
          }
      });

  }
}
