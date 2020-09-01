package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText pwd;
    private Button login;
    private TextView register;
    private FirebaseAuth FAuth;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editTextEmail);
        pwd = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.cirLoginButton);
        register = findViewById(R.id.registerbtn);

        FAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String passwd = pwd.getText().toString();
                if (TextUtils.isEmpty(em)) {
                    email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(passwd)){
                    pwd.setError("Enter Password");
                    return;
                }


                FAuth.signInWithEmailAndPassword(em,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        final String custID = FAuth.getCurrentUser().getUid();


                        if (task.isSuccessful()){

                            fs.collection("customers").document(custID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.getResult().exists()){

                                        Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Note:- User Not Found",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(MainActivity.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }
}