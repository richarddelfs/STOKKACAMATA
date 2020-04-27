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
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editpegawai = new Intent(HalamanPegawai.this, DataPegawai.class);
                startActivity(editpegawai);
            }
        });

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupdata();
    }

    public void setupdata(){
        list.clear();
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