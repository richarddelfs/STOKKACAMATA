package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HalamanPegawai extends AppCompatActivity {

    RecyclerView employeeList;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<ProfilePegawai> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    PegawaiAdapter employeeAdapter;
    FloatingActionButton imageView1;
    Context context;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_pegawai);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        imageView1 = findViewById(R.id.imageView1);
        //profilePegawai = findViewById(R.id.profilepegawai);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpegawai = new Intent(HalamanPegawai.this, DataPegawai.class);
                startActivity(editpegawai);
            }
        });

        //Cara2
        employeeList = findViewById(R.id.rv_employee);
        Query query = FirebaseDatabase.getInstance().getReference().child("ProfilePegawai");
        FirebaseListOptions<ProfilePegawai> options = new FirebaseListOptions.Builder<ProfilePegawai>()
                .setLayout(R.layout.pegawai_info)
                .setQuery(query, ProfilePegawai.class)
                .build();

        employeeAdapter = new PegawaiAdapter();
        employeeAdapter.delegate = this;
        employeeList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        employeeList.setLayoutManager(layoutManager);
        employeeList.setAdapter(employeeAdapter);


//        adapter1 = new FirebaseListAdapter(options) {
//            @Override
//            protected void populateView(View v, Object model, int position) {
//                ImageView imageView = v.findViewById(R.id.iv_employ);
//                TextView nama = v.findViewById(R.id.tv_employ_name);
//                TextView alamat = v.findViewById(R.id.tv_employ_address);
//                TextView notelp = v.findViewById(R.id.tv_employ_phone);
//                TextView tempatlahir = v.findViewById(R.id.tv_employ_pob);
//                TextView tanggallahir = v.findViewById(R.id.tv_employ_dob);
//                TextView deskripsi = v.findViewById(R.id.tv_employ_desc);
//                TextView password = v.findViewById(R.id.et_employ_password);
//
//                ProfilePegawai profilePegawai = (ProfilePegawai) model;
//                Picasso.get().load(profilePegawai.getProfilepicturepegawai()).into(imageView);
//                //Picasso.with(HalamanPegawai.this).load(profilePegawai.getProfilepicturepegawai().toString()).into(imageView);
//                nama.setText(profilePegawai.getNama().toString());
//                alamat.setText(profilePegawai.getAlamat().toString());
//                notelp.setText(profilePegawai.getNotelp().toString());
//                tempatlahir.setText(profilePegawai.getTempatlahir().toString());
//                tanggallahir.setText(profilePegawai.getTanggallahir().toString());
//                deskripsi.setText(profilePegawai.getDeskripsi().toString());
//                password.setText(profilePegawai.getPassword().toString());
//            }
//        };
//        adapter1.startListening();
//        listView.setAdapter(adapter1);

//        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent UpdateDelete = new Intent(HalamanPegawai.this, EditHapusPegawai.class);
//                ProfilePegawai p = (ProfilePegawai) adapterView.getItemAtPosition(i);
//                UpdateDelete.putExtra("nama", p.getNama());
//                UpdateDelete.putExtra("alamat", p.getAlamat());
//                UpdateDelete.putExtra("notelp", p.getNotelp());
//                UpdateDelete.putExtra("tempatlahir", p.getTempatlahir());
//                UpdateDelete.putExtra("tanggallahir", p.getTanggallahir());
//                UpdateDelete.putExtra("deskripsi", p.getDeskripsi());
//                UpdateDelete.putExtra("password", p.getPassword());
//                startActivity(UpdateDelete);
//            }
//        });

/*
        //Cara 1
        //profilePegawai1 = new ProfilePegawai1();
        list = new ArrayList<String>();
        listView = findViewById(R.id.listview1);
        ref = FirebaseDatabase.getInstance().getReference("ProfilePegawai");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(ProfilePegawai1.class).toString();
                list.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//List RecycleView
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<ProfilePegawai1> list;
    MyAdapter adapter;
    FloatingActionButton imageView1;
    //private TextView nama, alamat, notelp;

        imageView1 = findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpegawai = new Intent(HalamanPegawai.this, DataPegawai.class);
                startActivity(editpegawai);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("ProfilePegawai");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ProfilePegawai1>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ProfilePegawai1 p = dataSnapshot1.getValue(ProfilePegawai1.class);
                    list.add(p);
                }
                adapter = new MyAdapter(HalamanPegawai.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(HalamanPegawai.this, "", Toast.LENGTH_SHORT).show();
            }
        });


 */
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupdata();
    }

    public void setupdata(){
        mDatabase.child("ProfilePegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    ProfilePegawai profileBarang = child.getValue(ProfilePegawai.class);
                    list.add(profileBarang);
                }
                employeeAdapter.employList = list;
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}