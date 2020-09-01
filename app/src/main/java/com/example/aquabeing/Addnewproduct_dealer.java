package com.example.aquabeing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Addnewproduct_dealer extends AppCompatActivity {

    private String CategoryName , Size , Number, Price , Pname, saveCurrentDate, saveCurrentTime, productRandomkey;
    private Button AddNewProductButton;
    private ImageView ProductImage;
    private EditText ProductName,ProductSize, NumberofProducts, PriceperBottle;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private FirebaseFirestore fs;
    private FirebaseAuth fauth;
    String dealerid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewproduct_dealer);

        CategoryName = getIntent().getExtras().get("category").toString();

        AddNewProductButton = (Button) findViewById(R.id.add_product_button);
        ProductImage = (ImageView) findViewById(R.id.select_product_image);
        ProductName = (EditText) findViewById(R.id.product_name);
        ProductSize = (EditText) findViewById(R.id.product_size);
        NumberofProducts = (EditText) findViewById(R.id.number_of_products);
        PriceperBottle = (EditText) findViewById(R.id.product_price);
        fs = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();


            }
        });

    }

    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode == RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            ProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData(){
        Size = ProductSize.getText().toString();
        Number = NumberofProducts.getText().toString();
        Price = PriceperBottle.getText().toString();
        Pname = ProductName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Product Image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Size))
        {
            Toast.makeText(this, "Please mention the size of the bottle", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Number))
        {
            Toast.makeText(this, "Please mention the number of bottles", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please mention the price of each bottle", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please mention the brand of the bottle", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
        dealerid = fauth.getCurrentUser().getUid();


        DocumentReference docref = fs.collection("dealers").document(dealerid).collection("products").document();
        Map<String, Object> product = new HashMap<>();
        product.put("brand",Pname);
        product.put("price", Price);
        product.put("quantity", Number);
        product.put("type", Size);
        docref.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Addnewproduct_dealer.this, "Updation Done", Toast.LENGTH_SHORT).show();

            }
        });
        Intent intent = new Intent(Addnewproduct_dealer.this,dealer_update.class);
        startActivity(intent);



    }
     //this method is for displaying when the products are updated on customer side
    private void StoreProductInformation(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currenttime.format(calendar.getTime());

        productRandomkey = saveCurrentDate + saveCurrentTime;

    }
}