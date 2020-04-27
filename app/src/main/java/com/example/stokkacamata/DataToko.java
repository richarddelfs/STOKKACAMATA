package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataToko extends AppCompatActivity {

    private ImageView btnLogout;
    private CardView ButtonBuatToko;
    private EditText namatoko1, deskripsitoko1;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProfileToko profileToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_toko);

        btnLogout = findViewById(R.id.btnLogout);
        ButtonBuatToko = findViewById(R.id.ButtonBuatToko);
        namatoko1 = findViewById(R.id.namatoko1);
        deskripsitoko1 = findViewById(R.id.deskripsitoko1);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("ProfileToko");
        profileToko = new ProfileToko();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(DataToko.this, Login.class);
                startActivity(intToMain);
            }
        });

        ButtonBuatToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                ref.child(profileToko.getNamatoko()).setValue(profileToko);
                Toast.makeText(DataToko.this, "Data Toko telah berhasil dibuat", Toast.LENGTH_LONG).show();
                Intent toko = new Intent(DataToko.this, PilihToko.class);
                startActivity(toko);
                finish();
                /*
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        ref.child(profileToko.getNamatoko()).setValue(profileToko);
                        Toast.makeText(DataToko.this, "Data Toko telah berhasil dibuat", Toast.LENGTH_LONG).show();
                        Intent toko = new Intent(DataToko.this, PilihToko.class);
                        startActivity(toko);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DataToko.this, "Data Toko belum berhasil dibuat", Toast.LENGTH_SHORT).show();
                    }
                });
                */

            }
        });
    }

    private void getValues()
    {
        profileToko.setNamatoko(namatoko1.getText().toString().trim());
        profileToko.setDeskripsitoko(deskripsitoko1.getText().toString().trim());
        //profilePegawai.setProfilpicturepegawai(imagePath);
    }
}