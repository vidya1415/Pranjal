package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class DealerLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG ="Dealer Registration";
    Button button;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private FirebaseAuth fAuthen;
    String dealerID;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_location);

        fAuthen = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLasstLocation();


    }


    private void fetchLasstLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE );
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!= null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+"  "+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(DealerLocationActivity.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(" My Location.");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                new AlertDialog.Builder(DealerLocationActivity.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Are you sure this is your location for register")
//set message
                        .setMessage(currentLocation.getLatitude() + "," + currentLocation.getLongitude())
//set positive button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Toast.makeText(getApplicationContext(), "Nothing registered", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DealerLocationActivity.this, Dealer3.class);
                                startActivity(intent);

                            }
                        })
//set negative button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(), "shop added ", Toast.LENGTH_LONG).show();

                                dealerID = fAuthen.getCurrentUser().getUid();
                                LatLng registerlatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                LatLng reg = registerlatlng;
                                final GeoPoint geoPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
                                final DocumentReference document = fs.collection("dealers").document(dealerID);
                                final Map<String, Object> dealer = new HashMap<>();
                                dealer.put("locationlatlng", geoPoint);

                                document.update("locationlatlng", FieldValue.arrayUnion(geoPoint))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });
                            }
                        })

                        .show();

                return false;
            }
        });
    }


//
//    public void add_field (final String key, final GeoPoint value, final String collection_ref) {
//        FirebaseFirestore.getInstance().collection(collection_ref).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            if (task.isSuccessful()) {
//                Toast.makeText(DealerLocationActivity.this,"Dealer Account Created.",Toast.LENGTH_SHORT).show();
//                WriteBatch batch = fs.batch();
//
//                for (DocumentSnapshot document : task.getResult()) {
//                    DocumentReference docRef = document.getReference();
//                    Map<String, GeoPoint> new_map = new HashMap<>();
//                    new_map.put(key, value);
//                    batch.update(docRef, new_map);
//                }
//                batch.commit();
//            } else {
//                // ... "Error adding field -> " + task.getException()
//            }
//        }
//    })
//            .addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//            // ... "Failure getting documents -> " + e
//        }
//    });
//}

//    dealerID = fAuthen.getCurrentUser().getUid();
//    LatLng registerlatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//    LatLng reg = registerlatlng;
//    final GeoPoint geoPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
//    final DocumentReference document = fs.collection("dealers").document(dealerID);
//    final Map<String, GeoPoint> dealer = new HashMap<>();
//                                document.set(dealer)
//            .addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            if (task.isSuccessful()) {
//                dealerID = fAuthen.getCurrentUser().getUid();
//                DocumentReference docref = fs.collection("dealers").document(dealerID);
//                dealer.put("locationlatlng", geoPoint);
//                docref.set(dealer).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "Dealer ID: " + dealerID);
//                    }
//                });
//                Intent intent = new Intent(DealerLocationActivity.this, Dealer3.class);
//                startActivity(intent);
//
//            } else {
//                Toast.makeText(DealerLocationActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        }
//
//    });


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLasstLocation();
                }
                break;
        }
    }
}