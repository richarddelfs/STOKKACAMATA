package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HalamanBarangUser2 extends AppCompatActivity {
    private ImageView btnLogout5;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FloatingActionButton imageView1;
    private TextView TextNamaToko;
    private ListView listView;
    private FirebaseListAdapter adapter1;
    private SearchView searchView;
    //DatabaseReference ref;
    //ArrayList<ProfileBarang> list;
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
        searchView = findViewById(R.id.searchView1user2);
        //recyclerView = findViewById(R.id.recyclerview2);
        //ref = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");

        listView = findViewById(R.id.listview3user2);
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
                Intent UpdateDelete = new Intent(HalamanBarangUser2.this, EditHapusBarang.class);
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

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchbarang = new Intent(HalamanBarangUser2.this, SearchUser2.class);
                startActivity(searchbarang);
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
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_databarang:
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

        btnLogout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HalamanBarangUser2.this, Login.class);
                startActivity(intToMain);
            }
        });

        /*
            if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            list.add(dataSnapshot1.getValue(ProfileBarang.class));
                        }
                        AdapterClassBarang adapterClassBarang = new AdapterClassBarang(list);
//                        AdapterClassBarang.MyViewHolder();
                        recyclerView.setAdapter(adapterClassBarang);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(HalamanBarang.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

            if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    //search(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
         */
    }

    /*
    private void search(String string) {
        ArrayList<ProfileBarang> myList = new ArrayList<>();
        for (ProfileBarang object : list) {
            if (object.getNama().toLowerCase().contains(string.toLowerCase())) ;
            {
                System.out.println("ayam");
//                if(object.getTipe().toLowerCase().contains(string.toLowerCase()));
//                {
//                    if(object.getWarna().toLowerCase().contains(string.toLowerCase()));
//                    {
//                        if(object.getJumlah().toLowerCase().contains(string.toLowerCase()));
//                        {
//                            myList.add(object);
//                        }
//                    }
//                }
                myList.add(object);
            }

            if(object.getMerk().toLowerCase().contains(string.toLowerCase()));
            {
                myList.add(object);
            }
            if(object.getTipe().toLowerCase().contains(string.toLowerCase()));
            {
                myList.add(object);
            }
            if(object.getWarna().toLowerCase().contains(string.toLowerCase()));
            {
                myList.add(object);
            }
            if(object.getJumlah().toLowerCase().contains(string.toLowerCase()));
            {
                myList.add(object);
            }

        }
    }
    */

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