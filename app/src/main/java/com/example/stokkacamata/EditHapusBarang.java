package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditHapusBarang extends AppCompatActivity {
    EditText merk, tipe, warna, jumlah, qrcode;
    TextView key10;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hapus_barang);

        merk = findViewById(R.id.merk3);
        tipe = findViewById(R.id.tipe);
        warna = findViewById(R.id.warna);
        jumlah = findViewById(R.id.jumlah2);
        key10 = findViewById(R.id.key1);

        String key = getIntent().getExtras().get("nama").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("ProfileBarang").child(key);
        key10.setText(key);
        merk.setText(getIntent().getStringExtra("merk"));
        tipe.setText(getIntent().getStringExtra("tipe"));
        warna.setText(getIntent().getStringExtra("warna"));
        jumlah.setText(getIntent().getStringExtra("jumlah"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                merk.setText(dataSnapshot.child("merk").getValue(String.class));
                tipe.setText(dataSnapshot.child("tipe").getValue(String.class));
                warna.setText(dataSnapshot.child("warna").getValue(String.class));
                jumlah.setText(dataSnapshot.child("jumlah").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("status").equals("pegawai")){
            if(getIntent().getStringExtra("from").equals("homeuser2")){
                Intent intent = new Intent(EditHapusBarang.this, HomeUser2.class);
                startActivity(intent);
            }
            else if(getIntent().getStringExtra("from").equals("halamanbaranguser2")) {
                Intent intent = new Intent(EditHapusBarang.this, HalamanBarangUser2.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(EditHapusBarang.this, ScanUser2.class);
                startActivity(intent);
            }

        }
        else if (getIntent().getStringExtra("status").equals("pemilik")) {
            if (getIntent().getStringExtra("from").equals("home")) {
                Intent intent = new Intent(EditHapusBarang.this, Home.class);
                startActivity(intent);
            }
            else if (getIntent().getStringExtra("from").equals("halamanbarang")) {
                Intent intent = new Intent(EditHapusBarang.this, HalamanBarang.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(EditHapusBarang.this, Scan.class);
                startActivity(intent);
            }
            finish();
        }

    }

    public void Update(View view)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("merk").setValue(merk.getText().toString());
                dataSnapshot.getRef().child("tipe").setValue(tipe.getText().toString());
                dataSnapshot.getRef().child("warna").setValue(warna.getText().toString());
                dataSnapshot.getRef().child("jumlah").setValue(jumlah.getText().toString());
                Toast.makeText(EditHapusBarang.this, "Data Barang telah berhasil diupdate", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(EditHapusBarang.this, Scan.class);
//                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditHapusBarang.this, "Data Barang belum berhasil diupdate", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void Delete(View view)
    {
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EditHapusBarang.this, "Data Barang telah berhasil dihapus...",Toast.LENGTH_LONG).show();
                    EditHapusBarang.this.finish();
                }

                else
                {
                    Toast.makeText(EditHapusBarang.this, "Data Barang belum berhasil dihapus...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}