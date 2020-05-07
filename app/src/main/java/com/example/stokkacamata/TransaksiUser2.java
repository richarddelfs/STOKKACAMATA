package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;

public class TransaksiUser2 extends AppCompatActivity {
    private ImageView scan;
    private CardView ButtonTransaksiTengah;
    private ImageView btnLogout2;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView TextNamaToko;
    private EditText pembeli, alamat, notelp;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProfileTransaksi profileTransaksi;
    private SearchView searchView;
    private ImageView profilepicturetransaksi;
    private static final int Gallery_intent = 1000;
    private static final int PERMISSION_CODE = 1000;
    private StorageReference imagePath;
    private StorageReference storage;
    private String imageUrl = "";
    //private String ImageLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_user2);
        //scan = findViewById(R.id.scan);
        ButtonTransaksiTengah = findViewById(R.id.ButtonTransaksiTengahuser2);
        btnLogout2 = findViewById(R.id.btnLogout2user2);
        TextNamaToko = findViewById(R.id.TextNamaTokouser2);
        pembeli = findViewById(R.id.TextNama1user2);
        alamat = findViewById(R.id.TextAlamat1user2);
        notelp = findViewById(R.id.TextNoTelp1user2);
        //searchView = findViewById(R.id.searchView3);
        profilepicturetransaksi = findViewById(R.id.profilepictureuser2);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("ProfileTransaksi");
        storage = FirebaseStorage.getInstance().getReference();
        profileTransaksi = new ProfileTransaksi();

        ButtonTransaksiTengah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String key = ref.push().getKey();
                getValues();
                ref.child(profileTransaksi.getPembeli()).setValue(profileTransaksi);
                Toast.makeText(TransaksiUser2.this, "Data Transaksi telah berhasil masuk....", Toast.LENGTH_LONG).show();
                Intent transaksi = new Intent(TransaksiUser2.this, HomeUser2.class);
                startActivity(transaksi);
                TransaksiUser2.this.finish();
                /*
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        ref.child(profileTransaksi.getPembeli()).setValue(profileTransaksi);
                        Toast.makeText(Transaksi.this, "Data Transaksi telah berhasil masuk....", Toast.LENGTH_LONG).show();
                        Intent transaksi = new Intent(Transaksi.this, History.class);
                        startActivity(transaksi);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Transaksi.this, "Data Transaksi belum berhasil masuk...", Toast.LENGTH_SHORT).show();
                    }
                });
                */
            }
        });

        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transaksi = new Intent(TransaksiUser2.this, HalamanToko.class);
                startActivity(transaksi);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user2);

        bottomNavigationView.setSelectedItemId(R.id.nav_transaksi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeUser2.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_databarang:
                        startActivity(new Intent(getApplicationContext(), HalamanBarangUser2.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanUser2.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_transaksi:
                        return true;
                        /*
                    case R.id.nav_history:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        overridePendingTransition(0,0);
                        return true;
                        */
                }
                return false;
            }
        });

        btnLogout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(TransaksiUser2.this, Login.class);
                startActivity(intToMain);
            }
        });
/*
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchbarang = new Intent(TransaksiUser2.this, SearchUser2.class);
                startActivity(searchbarang);
            }
        });
*/
        profilepicturetransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setRequestImage();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Gallery_intent);
        //startActivityForResult(Intent.createChooser(intent, "Pick an image"), Gallery_intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(TransaksiUser2.this, HomeUser2.class);
        startActivity(intent);
        finish();
    }

    private void getValues()
    {
        profileTransaksi.setPembeli(pembeli.getText().toString().trim());
        profileTransaksi.setAlamat(alamat.getText().toString().trim());
        profileTransaksi.setNotelp(notelp.getText().toString().trim());
        profileTransaksi.setProfilepicturetransaksi(imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_intent && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            profilepicturetransaksi.setImageURI(uri);
            imagePath = storage.child("ProfileTransaksi").child(uri.getLastPathSegment());
            //ImageLocation = uri.getLastPathSegment();
            imagePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            Toast.makeText(TransaksiUser2.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TransaksiUser2.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}