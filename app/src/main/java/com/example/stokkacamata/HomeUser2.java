package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeUser2 extends AppCompatActivity {
    private Button buttoneditpegawai;
    private ImageView btnLogout;
    private TextView TextNamaToko;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FloatingActionButton imageView1;
    private ListView listView;
    private FirebaseListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user2);
//        buttoneditpegawai = findViewById(R.id.buttoneditpegawai);
        btnLogout = findViewById(R.id.btnLogoutuser2);
        imageView1 = findViewById(R.id.imageView1user2);
        TextNamaToko = findViewById(R.id.TextNamaTokouser2);

        listView = findViewById(R.id.listview2user2);
        Query query = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");
        FirebaseListOptions<ProfileBarang> options = new FirebaseListOptions.Builder<ProfileBarang>()
                .setLayout(R.layout.barang_info)
                .setQuery(query, ProfileBarang.class)
                .build();

        adapter1 = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                ImageView imageView = v.findViewById(R.id.profilebarang1);
                TextView nama = v.findViewById(R.id.tv_product_name);
                TextView merk = v.findViewById(R.id.tv_product_brand);
                TextView tipe = v.findViewById(R.id.tv_product_type);
                TextView warna = v.findViewById(R.id.tv_product_color);
                TextView jumlah = v.findViewById(R.id.tv_product_quantity);
                ImageView qrcode = v.findViewById(R.id.qrcode);

                ProfileBarang profileBarang = (ProfileBarang) model;
                Picasso.get().load(profileBarang.getProfilepicturebarang()).into(imageView);
                //Picasso.with(Home.this).load(profileBarang.getProfilepicturebarang().toString()).into(imageView);
                nama.setText(profileBarang.getNama());
                merk.setText(profileBarang.getMerk());
                tipe.setText(profileBarang.getTipe());
                warna.setText(profileBarang.getWarna());
                jumlah.setText(profileBarang.getJumlah());
                Picasso.get().load(profileBarang.getQrcodeurl()).into(qrcode);
                System.out.println("getqrcode"+profileBarang.getQrcodeurl());
            }
        };
        adapter1.startListening();
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent UpdateDelete = new Intent(HomeUser2.this, EditHapusBarang.class);
                ProfileBarang p = (ProfileBarang) adapterView.getItemAtPosition(i);
                UpdateDelete.putExtra("nama", p.getNama());
                UpdateDelete.putExtra("merk", p.getMerk());
                UpdateDelete.putExtra("tipe", p.getTipe());
                UpdateDelete.putExtra("warna", p.getWarna());
                UpdateDelete.putExtra("jumlah", p.getJumlah());
                startActivity(UpdateDelete);
            }
        });

        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(HomeUser2.this, HalamanToko.class);
                startActivity(home);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(HomeUser2.this, Barang.class);
                startActivity(home);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_databarang:
                        startActivity(new Intent(getApplicationContext(), HalamanBarangUser2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanUser2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_transaksi:
                        startActivity(new Intent(getApplicationContext(), TransaksiUser2.class));
                        overridePendingTransition(0,0);
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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeUser2.this, Login.class);
                startActivity(intToMain);
            }
        });
/*
        buttoneditpegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(HomeUser2.this, HalamanPegawai.class);
                startActivity(home);
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter1.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter1.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter1.stopListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
/*
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Home.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null && result.getContents() != null) {

                new AlertDialog.Builder(Home.this)
                        .setTitle("TambahTransaksi Result")
                        .setMessage(result.getContents())
                        .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData data = ClipData.newPlainText("result", result.getContents());
                                manager.setPrimaryClip(data);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
*/
}