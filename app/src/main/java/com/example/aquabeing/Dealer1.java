package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dealer1 extends AppCompatActivity {
    private EditText dealerem;
    private EditText dpwd;
    private Button login;
    private TextView dregister;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer1);

        dealerem = findViewById(R.id.editTextEmail);
        dpwd = findViewById(R.id.editTextPassword);
        fAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.cirLoginButton);
        dregister = findViewById(R.id.registerbtn);

        fAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        dregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dealer1.this,Dealer2.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = dealerem.getText().toString().trim();
                String pwd = dpwd.getText().toString().trim();
                if (TextUtils.isEmpty(em)) {
                    dealerem.setError("Phone Number Required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    dpwd.setError("Enter Password");
                    return;
                }

                fAuth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        final String dealerid = fAuth.getCurrentUser().getUid();
                        if (task.isSuccessful()){

                            fs.collection("dealers").document(dealerid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()){
                                        Toast.makeText(Dealer1.this,"Welcome",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Dealer1.this,Dealer3.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(Dealer1.this,"Dealer Account Not Found",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }else {
                            Toast.makeText(Dealer1.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });





    }
}