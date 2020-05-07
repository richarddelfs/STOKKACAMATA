package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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

public class HalamanBarangUser2 extends AppCompatActivity {
    private ImageView btnLogout5;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FloatingActionButton imageView1;
    private TextView TextNamaToko;
    private RecyclerView listView;
    private AdapterClassBarang adapter1;
    private TextView textView;
    private DatabaseReference mDatabase;
    //DatabaseReference ref;
    ArrayList<ProfileBarang> list = new ArrayList<ProfileBarang>();
    //RecyclerView recyclerView;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_barang);
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_barang_user2);
        btnLogout5 = findViewById(R.id.btnLogout5user2);
        imageView1 = findViewById(R.id.imageView1user2);
        TextNamaToko = findViewById(R.id.TextNamaTokouser2);
        textView = findViewById(R.id.searchtext1user2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //recyclerView = findViewById(R.id.recyclerview2);
        //ref = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");

        listView = findViewById(R.id.listview3user2);

        /* Setup RecyclerView */
        adapter1 = new AdapterClassBarang();
        adapter1.delegate = this;
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        adapter1.status = "pegawai";
        adapter1.from = "halamanbaranguser2";
        listView.setAdapter(adapter1);

        setupdata();


        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent halbar = new Intent(HalamanBarangUser2.this, HalamanToko.class);
                startActivity(halbar);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent halbarang = new Intent(HalamanBarangUser2.this, BarangUser2.class);
                startActivity(halbarang);
            }
        });


        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user2);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_databarang);

        //Perform ItemSelectedListener
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
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanUser2.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_transaksi:
                        startActivity(new Intent(getApplicationContext(), TransaksiUser2.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.nav_history:
//                        startActivity(new Intent(getApplicationContext(), History.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });


        btnLogout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HalamanBarangUser2.this, Login.class);
                startActivity(intToMain);
            }
        });


    }
    public void onBackPressed(){
        Intent intent = new Intent(HalamanBarangUser2.this, HomeUser2.class);
        startActivity(intent);
        finish();
    }
    public void setupsearch(){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    ArrayList<ProfileBarang> filteredProductList = new ArrayList<>();
                    for (ProfileBarang product : list){
                        if(product.getNama().contains(s)){
                            filteredProductList.add(product);
                        }else if(product.getMerk().contains(s)){
                            filteredProductList.add(product);
                        }else if(product.getTipe().contains(s)){
                            filteredProductList.add(product);
                        }else if(product.getWarna().contains(s)){
                            filteredProductList.add(product);
                        }
                    }

                    adapter1.list = filteredProductList;
                    adapter1.notifyDataSetChanged();
                }else{
                    adapter1.list = list;
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setupdata(){
        list.clear();
        mDatabase.child("ProfileBarang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    ProfileBarang profileBarang = child.getValue(ProfileBarang.class);
                    list.add(profileBarang);
                }
                adapter1.list = list;
                adapter1.notifyDataSetChanged();
                setupsearch();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //adapter1.startListening();
    }


}