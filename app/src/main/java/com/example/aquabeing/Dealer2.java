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

public class Dealer2 extends AppCompatActivity {
    public static final String TAG ="Dealer Registration";
    private EditText name, email, password, cnfpassword, address, phone;
    private Button submit;
    private TextView back;
    private FirebaseAuth fAuthen;
    String dealerID;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer2);
        name = findViewById(R.id.etdealername);
        email = findViewById(R.id.etdealeremail);
        password = findViewById(R.id.etdealerpwd);
        cnfpassword = findViewById(R.id.etdealercpwd);
        address = findViewById(R.id.etdealeraddress);
        phone = findViewById(R.id.etdealerphone);
        submit = findViewById(R.id.btnregsubmit);
        back = findViewById(R.id.tvdback);

        fAuthen = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n = name.getText().toString().trim();
                final String em = email.getText().toString().trim();
                final String pwd = password.getText().toString().trim();
                String confirm = cnfpassword.getText().toString().trim();
                final String add = address.getText().toString().trim();
                final String ph = phone.getText().toString().trim();

                if (TextUtils.isEmpty(em)) {
                    email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(n)){
                    name.setError("Dealer Name Required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    password.setError("Enter Password");
                    return;
                }
                if (TextUtils.isEmpty(confirm)){
                    cnfpassword.setError("Confirm Password");
                    return;
                }
                if (TextUtils.isEmpty(add)){
                    address.setError("Fill the address");
                    return;
                }
                if (TextUtils.isEmpty(ph)){
                    phone.setError("Enter Phone Number");
                    return;
                }
                if (pwd.length()<8){
                    password.setError("Password Must be more than 8 charaters");
                    return;
                }

                fAuthen.createUserWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Dealer2.this,"Dealer Account Created.",Toast.LENGTH_SHORT).show();
                            dealerID = fAuthen.getCurrentUser().getUid();
                            DocumentReference docref = fs.collection("dealers").document(dealerID);
                            Map<String, Object> dealer = new HashMap<>();
                            dealer.put("name", n);
                            dealer.put("email", em);
                            dealer.put("password", pwd);
                            dealer.put("address", add);
                            dealer.put("phone", ph);
                            docref.set(dealer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"Dealer ID: "+dealerID);
                                }
                            });
                            Intent intent = new Intent(Dealer2.this,Dealer1.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(Dealer2.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dealer2.this,Dealer1.class);
                startActivity(i);
            }
        });

    }

}