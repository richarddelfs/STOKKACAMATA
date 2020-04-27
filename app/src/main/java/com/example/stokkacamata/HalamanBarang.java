//package com.example.stokkacamata;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.SearchView;
//import android.widget.TextView;
//import com.firebase.ui.database.FirebaseListAdapter;
//import com.firebase.ui.database.FirebaseListOptions;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.squareup.picasso.Picasso;
//import org.w3c.dom.ls.LSOutput;
//import java.util.ArrayList;
//
//public class HalamanBarang extends AppCompatActivity {
//    private ImageView btnLogout5;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;
//    private FloatingActionButton imageView1;
//    private TextView TextNamaToko;
//    private ListView listView;
//    private FirebaseListAdapter adapter1;
//    private SearchView searchView;
//    //DatabaseReference ref;
//    //ArrayList<ProfileBarang> list;
//    //RecyclerView recyclerView;
//
///*
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_halaman_barang);
// */
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_halaman_barang);
//        btnLogout5 = findViewById(R.id.btnLogout5);
//        imageView1 = findViewById(R.id.imageView1);
//        TextNamaToko = findViewById(R.id.TextNamaToko);
//        searchView = findViewById(R.id.searchView1);
//        //recyclerView = findViewById(R.id.recyclerview2);
//        //ref = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");
//
//        listView = findViewById(R.id.listview3);
//        Query query = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");
//        FirebaseListOptions<ProfileBarang> options = new FirebaseListOptions.Builder<ProfileBarang>()
//                .setLayout(R.layout.barang_info)
//                .setQuery(query, ProfileBarang.class)
//                .build();
//
//        adapter1 = new FirebaseListAdapter(options) {
//            @Override
//            protected void populateView(View v, Object model, int position) {
//                ImageView imageView = v.findViewById(R.id.profilebarang1);
//                TextView nama = v.findViewById(R.id.namabarang2);
//                TextView merk = v.findViewById(R.id.merk2);
//                TextView tipe = v.findViewById(R.id.tipe2);
//                TextView warna = v.findViewById(R.id.warna2);
//                TextView jumlah = v.findViewById(R.id.jumlah3);
//                ImageView qrcode = v.findViewById(R.id.qrcode);
//
//                ProfileBarang profileBarang = (ProfileBarang) model;
//                Picasso.get().load(profileBarang.getProfilepicturebarang()).into(imageView);
//                //Picasso.with(Home.this).load(profileBarang.getProfilepicturebarang().toString()).into(imageView);
//                nama.setText(profileBarang.getNama());
//                merk.setText(profileBarang.getMerk());
//                tipe.setText(profileBarang.getTipe());
//                warna.setText(profileBarang.getWarna());
//                jumlah.setText(profileBarang.getJumlah());
//                Picasso.get().load(profileBarang.getQrcodeurl()).into(qrcode);
//                System.out.println("getqrcode"+profileBarang.getQrcodeurl());
//            }
//        };
//        adapter1.startListening();
//        listView.setAdapter(adapter1);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent UpdateDelete = new Intent(HalamanBarang.this, EditHapusBarang.class);
//                ProfileBarang p = (ProfileBarang) adapterView.getItemAtPosition(i);
//                UpdateDelete.putExtra("nama", p.getNama());
//                UpdateDelete.putExtra("merk", p.getMerk());
//                UpdateDelete.putExtra("tipe", p.getTipe());
//                UpdateDelete.putExtra("warna", p.getWarna());
//                UpdateDelete.putExtra("jumlah", p.getJumlah());
//                startActivity(UpdateDelete);
//            }
//        });
//
//        TextNamaToko.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent halbar = new Intent(HalamanBarang.this, HalamanToko.class);
//                startActivity(halbar);
//            }
//        });
//
//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent halbarang = new Intent(HalamanBarang.this, Barang.class);
//                startActivity(halbarang);
//            }
//        });
//
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent searchbarang = new Intent(HalamanBarang.this, Search.class);
//                startActivity(searchbarang);
//            }
//        });
//
//        //Initialize And Assign Variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        //Set Home Selected
//        bottomNavigationView.setSelectedItemId(R.id.nav_databarang);
//
//        //Perform ItemSelectedListener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.nav_home:
//                        startActivity(new Intent(getApplicationContext(), Home.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.nav_databarang:
//                        return true;
//                    case R.id.nav_scan:
//                        startActivity(new Intent(getApplicationContext(), Scan.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.nav_transaksi:
//                        startActivity(new Intent(getApplicationContext(), Transaksi.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.nav_history:
//                        startActivity(new Intent(getApplicationContext(), History.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });
//
//        btnLogout5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intToMain = new Intent(HalamanBarang.this, Login.class);
//                startActivity(intToMain);
//            }
//        });
//
//        /*
//            if (ref != null) {
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        list = new ArrayList<>();
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            list.add(dataSnapshot1.getValue(ProfileBarang.class));
//                        }
//                        AdapterClassBarang adapterClassBarang = new AdapterClassBarang(list);
////                        AdapterClassBarang.MyViewHolder();
//                        recyclerView.setAdapter(adapterClassBarang);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(HalamanBarang.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//            if (searchView != null) {
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    //search(s);
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    search(s);
//                    return true;
//                }
//            });
//        }
//         */
//    }
//
//    /*
//    private void search(String string) {
//        ArrayList<ProfileBarang> myList = new ArrayList<>();
//        for (ProfileBarang object : list) {
//            if (object.getNama().toLowerCase().contains(string.toLowerCase())) ;
//            {
//                System.out.println("ayam");
////                if(object.getTipe().toLowerCase().contains(string.toLowerCase()));
////                {
////                    if(object.getWarna().toLowerCase().contains(string.toLowerCase()));
////                    {
////                        if(object.getJumlah().toLowerCase().contains(string.toLowerCase()));
////                        {
////                            myList.add(object);
////                        }
////                    }
////                }
//                myList.add(object);
//            }
//
//            if(object.getMerk().toLowerCase().contains(string.toLowerCase()));
//            {
//                myList.add(object);
//            }
//            if(object.getTipe().toLowerCase().contains(string.toLowerCase()));
//            {
//                myList.add(object);
//            }
//            if(object.getWarna().toLowerCase().contains(string.toLowerCase()));
//            {
//                myList.add(object);
//            }
//            if(object.getJumlah().toLowerCase().contains(string.toLowerCase()));
//            {
//                myList.add(object);
//            }
//
//        }
//    }
//    */
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //adapter1.startListening();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter1.startListening();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        adapter1.stopListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter1.stopListening();
//    }
//}
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

public class HalamanBarang extends AppCompatActivity {
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
        setContentView(R.layout.activity_halaman_barang);
        btnLogout5 = findViewById(R.id.btnLogout5);
        imageView1 = findViewById(R.id.imageView1);
        TextNamaToko = findViewById(R.id.TextNamaToko);
        textView = findViewById(R.id.searchtext1);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //recyclerView = findViewById(R.id.recyclerview2);
        //ref = FirebaseDatabase.getInstance().getReference().child("ProfileBarang");

        listView = findViewById(R.id.listview3);

        /* Setup RecyclerView */
        adapter1 = new AdapterClassBarang();
        adapter1.delegate = this;
        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter1);

        setupdata();


        TextNamaToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent halbar = new Intent(HalamanBarang.this, HalamanToko.class);
                startActivity(halbar);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent halbarang = new Intent(HalamanBarang.this, Barang.class);
                startActivity(halbarang);
            }
        });


        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_databarang);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_databarang:
                        return true;
                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), Scan.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_transaksi:
                        startActivity(new Intent(getApplicationContext(), Transaksi.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_history:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        btnLogout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HalamanBarang.this, Login.class);
                startActivity(intToMain);
            }
        });

    }
    public void setupsearch(){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("ABC");
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