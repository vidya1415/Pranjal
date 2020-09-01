package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    public static final String TAG ="Customer Registration";
    private EditText name,email,phone,password,cnfpwd;
    private Button submit;
    private TextView back;
    private FirebaseAuth fauth;
    String custID;
    private FirebaseFirestore fs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.etname);
        email = findViewById(R.id.etcemail);
        password = findViewById(R.id.etpwd);
        cnfpwd = findViewById(R.id.etpwd2);
        phone = findViewById(R.id.etphone);
        submit = findViewById(R.id.btnsubmit);
        back = findViewById(R.id.tvback);

        fauth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cn = name.getText().toString().trim();
                final String cem = email.getText().toString().trim();
                final String cpwd = password.getText().toString().trim();
                String cpwd2 = cnfpwd.getText().toString().trim();
                final String cph = phone.getText().toString().trim();

                if (TextUtils.isEmpty(cn)) {
                    name.setError("Name Required");
                    return;
                }
                if (TextUtils.isEmpty(cem)){
                    email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(cpwd)){
                    password.setError("Enter Password");
                    return;
                }
                if (TextUtils.isEmpty(cpwd2)){
                    cnfpwd.setError("Confirm Password");
                    return;
                }

                if (TextUtils.isEmpty(cph)){
                    phone.setError("Enter Phone Number");
                    return;
                }
                if (cpwd.length()<8){
                    password.setError("Password Must be more than 8 charaters");
                    return;
                }
                fauth.createUserWithEmailAndPassword(cem,cpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity2.this,"Customer Account Created.",Toast.LENGTH_SHORT).show();
                            custID = fauth.getCurrentUser().getUid();
                            DocumentReference docref = fs.collection("customers").document(custID);
                            Map<String, Object> customer = new HashMap<>();
                            customer.put("name",cn);
                            customer.put("email",cem);
                            customer.put("password",cpwd);
                            customer.put("phone",cph);
                            docref.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"User Created with User ID: "+custID);
                                }
                            });
                            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(MainActivity2.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}