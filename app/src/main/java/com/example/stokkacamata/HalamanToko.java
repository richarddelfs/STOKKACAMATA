package com.example.stokkacamata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HalamanToko extends AppCompatActivity {

    private FloatingActionButton imageView1;
    private ListView listView;
    private FirebaseListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_toko);

        imageView1 = findViewById(R.id.imageView1);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukatoko = new Intent(HalamanToko.this, DataToko.class);
                startActivity(bukatoko);
            }
        });

        listView = findViewById(R.id.listview4);
        Query query = FirebaseDatabase.getInstance().getReference().child("ProfileToko");
        FirebaseListOptions<ProfileToko> options = new FirebaseListOptions.Builder<ProfileToko>()
                .setLayout(R.layout.toko_info)
                .setQuery(query, ProfileToko.class)
                .build();

        adapter1 = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView namatoko = v.findViewById(R.id.namatoko3);
                TextView deskripsi = v.findViewById(R.id.deskripsi4);

                ProfileToko profileToko = (ProfileToko) model;
                namatoko.setText(profileToko.getNamatoko().toString());
                deskripsi.setText(profileToko.getDeskripsitoko().toString());
            }
        };
        adapter1.startListening();
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent UpdateDelete = new Intent(HalamanToko.this, EditHapusToko.class);
                ProfileToko p = (ProfileToko) adapterView.getItemAtPosition(i);
                UpdateDelete.putExtra("namatoko", p.getNamatoko());
                UpdateDelete.putExtra("deskripsitoko", p.getDeskripsitoko());
                startActivity(UpdateDelete);
            }
        });
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