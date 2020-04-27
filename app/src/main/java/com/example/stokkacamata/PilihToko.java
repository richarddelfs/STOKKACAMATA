package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PilihToko extends AppCompatActivity {
    private EditText namatoko;
    private Button logintoko, buattoko;
    private ImageView btnLogout;
    private DatabaseReference ref;
    private String namatoko1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_toko);

        namatoko = findViewById(R.id.namatoko2);
        logintoko = findViewById(R.id.buttonlogintoko);
        buattoko = findViewById(R.id.buttonbuatintoko);
        btnLogout = findViewById(R.id.btnLogout4);

        ref = FirebaseDatabase.getInstance().getReference().child("ProfileToko");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(PilihToko.this, Login.class);
                startActivity(intToMain);
            }
        });

        buattoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buattoko = new Intent(PilihToko.this, DataToko.class);
                startActivity(buattoko);
            }
        });

        logintoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namatoko1 = namatoko.getText().toString();

                try {
                    ref.child(namatoko1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ProfileToko profileToko = dataSnapshot.getValue(ProfileToko.class);
                            if (namatoko1.equals(profileToko.getNamatoko())) {
                                Toast.makeText(PilihToko.this, "Login Toko Berhasil", Toast.LENGTH_LONG).show();
                                Intent login = new Intent(PilihToko.this, Home.class);
                                startActivity(login);
                            } else {
                                Toast.makeText(PilihToko.this, "Login Toko belum Berhasil", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(PilihToko.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                catch (Exception e)
                {
                    Toast.makeText(PilihToko.this, "Anda belum mendaftar Toko", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}