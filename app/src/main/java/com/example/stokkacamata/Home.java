package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import org.w3c.dom.ls.LSOutput;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private Button buttoneditpegawai;
    private ImageView btnLogout;
    private TextView TextNamaToko;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FloatingActionButton imageView1;
    private RecyclerView listView;
    private AdapterClassBarang productAdapter;
    private DatabaseReference mDatabase;

    ArrayList<ProfileBarang> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        buttoneditpegawai = findViewById(R.id.buttoneditpegawai);
        btnLogout = findViewById(R.id.btnLogout);
        imageView1 = findViewById(R.id.imageView1);
        TextNamaToko = findViewById(R.id.TextNamaToko);
        listView = findViewById(R.id.listview2);

        /* Setup Adapter */
        productAdapter = new AdapterClassBarang();
        productAdapter.delegate = this;
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        productAdapter.status = "pemilik";
        productAdapter.from = "home";
        listView.setAdapter(productAdapter);

        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Home.this, HalamanToko.class);
                startActivity(home);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Home.this, Barang.class);
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
                        startActivity(new Intent(getApplicationContext(), HalamanBarang.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), Scan.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_transaksi:
                        startActivity(new Intent(getApplicationContext(), Transaksi.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_history:
                        startActivity(new Intent(getApplicationContext(), History.class));
                            finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(Home.this, Login.class);
                startActivity(intToMain);
                finish();
            }
        });

        buttoneditpegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Home.this, HalamanPegawai.class);
                startActivity(home);
                finish();
            }
        });
    }

    public void onBackPressed(){
        System.exit(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupdata();
    }

    public void setupdata(){
        list.clear();
        mDatabase.child("ProfileBarang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    ProfileBarang profileBarang = child.getValue(ProfileBarang.class);
                    list.add(profileBarang);
                }
                productAdapter.list = list;
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}