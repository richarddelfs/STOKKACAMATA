package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class EditHapusPegawai extends AppCompatActivity {
    EditText alamat, notelp, tempatlahir, tanggallahir, deskripsi, password;
    TextView key10;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hapus_pegawai);
        alamat = findViewById(R.id.alamat2);
        notelp = findViewById(R.id.notelp2);
        tempatlahir = findViewById(R.id.tempatlahir2);
        tanggallahir = findViewById(R.id.tanggallahir2);
        deskripsi = findViewById(R.id.deskripsi7);
        password = findViewById(R.id.password3);
        String key = getIntent().getExtras().get("nama").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("ProfilePegawai").child(key);

        key10 = findViewById(R.id.key3);
        key10.setText(key);
        alamat.setText(getIntent().getStringExtra("alamat"));
        notelp.setText(getIntent().getStringExtra("notelp"));
        tempatlahir.setText(getIntent().getStringExtra("tempatlahir"));
        tanggallahir.setText(getIntent().getStringExtra("tanggallahir"));
        deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        password.setText(getIntent().getStringExtra("password"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alamat.setText(dataSnapshot.child("alamat").getValue(String.class));
                notelp.setText(dataSnapshot.child("notelp").getValue(String.class));
                tempatlahir.setText(dataSnapshot.child("tempatlahir").getValue(String.class));
                tanggallahir.setText(dataSnapshot.child("tanggallahir").getValue(String.class));
                deskripsi.setText(dataSnapshot.child("deskripsi").getValue(String.class));
                password.setText(dataSnapshot.child("password").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Update(View view)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("alamat").setValue(alamat.getText().toString());
                dataSnapshot.getRef().child("notelp").setValue(notelp.getText().toString());
                dataSnapshot.getRef().child("tempatlahir").setValue(tempatlahir.getText().toString());
                dataSnapshot.getRef().child("tanggallahir").setValue(tanggallahir.getText().toString());
                dataSnapshot.getRef().child("deskripsi").setValue(deskripsi.getText().toString());
                dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                Toast.makeText(EditHapusPegawai.this, "Data Pegawai telah berhasil diupdate", Toast.LENGTH_LONG).show();
                EditHapusPegawai.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditHapusPegawai.this, "Data Pegawai belum berhasil diupdate", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EditHapusPegawai.this, "Data Pegawai telah berhasil dihapus...",Toast.LENGTH_LONG).show();
                    EditHapusPegawai.this.finish();
                }

                else
                {
                    Toast.makeText(EditHapusPegawai.this, "Data Pegawai belum berhasil dihapus...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}