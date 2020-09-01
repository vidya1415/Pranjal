package com.example.aquabeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class owner1 extends AppCompatActivity {
    private EditText emailid;
    private EditText password;
    private Button login;


    private String UId = "pranjaladmin@gmail.com";
    private String pwd = "admin@pranjal";

    private boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner1);
        emailid = findViewById(R.id.textInputEmail);
        password = findViewById(R.id.textInputPassword);
        login = findViewById(R.id.btnowner);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputinfo1 = emailid.getText().toString();
                String inputinfo2 = password.getText().toString();

                if (inputinfo1.isEmpty() || inputinfo2.isEmpty()) {
                    Toast.makeText(owner1.this, "Please fill all the details:-)", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validatefn(inputinfo1, inputinfo2);

                    if (isValid == false) {
                        Toast.makeText(owner1.this, "Please fill correct details.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(owner1.this, "Hurray! Login Successful.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(owner1.this, owner2.class);
                        startActivity(intent);

                    }

                }
            }


        });
}

    private boolean validatefn(String inputinfo1, String inputinfo2) {
        if (inputinfo1.equals(UId) && inputinfo2.equals(pwd))
        {
            return true;
        }

        return false;
    }
    }