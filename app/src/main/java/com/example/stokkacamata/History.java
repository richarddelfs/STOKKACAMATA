package com.example.stokkacamata;

import android.app.SearchManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class History extends AppCompatActivity {
    private ImageView btnLogout3;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView TextNamaToko;
    private ListView listView;
    private FirebaseListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnLogout3 = findViewById(R.id.btnLogout3);
        TextNamaToko = findViewById(R.id.TextNamaToko);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_databarang:
                        startActivity(new Intent(getApplicationContext(), HalamanBarang.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), Scan.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_transaksi:
                        startActivity(new Intent(getApplicationContext(), Transaksi.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_history:
                        return true;
                }
                return false;
            }
        });

        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(History.this, HalamanToko.class);
                startActivity(history);
            }
        });

        btnLogout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(History.this, Login.class);
                startActivity(intToMain);
            }
        });

        listView = findViewById(R.id.listview5);
        Query query = FirebaseDatabase.getInstance().getReference().child("ProfileTransaksi");
        FirebaseListOptions<ProfileTransaksi> options = new FirebaseListOptions.Builder<ProfileTransaksi>()
                .setLayout(R.layout.transaksi_info)
                .setQuery(query, ProfileTransaksi.class)
                .build();

        adapter1 = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                ImageView imageView = v.findViewById(R.id.profiletransaksi1);
                TextView pembeli = v.findViewById(R.id.pembeli1);
                TextView alamat = v.findViewById(R.id.alamat3);
                TextView notelp = v.findViewById(R.id.notelp3);

                ProfileTransaksi profileTransaksi = (ProfileTransaksi) model;
                Picasso.get().load(profileTransaksi.getProfilepicturetransaksi()).into(imageView);
                //Picasso.with(History.this).load(profileTransaksi.getProfilepicturetransaksi().toString()).into(imageView);
                pembeli.setText(profileTransaksi.getPembeli().toString());
                alamat.setText(profileTransaksi.getAlamat().toString());
                notelp.setText(profileTransaksi.getNotelp().toString());
            }
        };
        adapter1.startListening();
        listView.setAdapter(adapter1);
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
}