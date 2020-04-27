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

public class EditHapusToko extends AppCompatActivity {
    EditText deskripsitoko;
    TextView key10;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hapus_toko);

        deskripsitoko = findViewById(R.id.deskripsi6);
        String key = getIntent().getExtras().get("namatoko").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("ProfileToko").child(key);

        key10 = findViewById(R.id.key2);
        key10.setText(key);
        deskripsitoko.setText(getIntent().getStringExtra("deskripsitoko"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deskripsitoko.setText(dataSnapshot.child("deskripsitoko").getValue(String.class));
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
                dataSnapshot.getRef().child("deskripsitoko").setValue(deskripsitoko.getText().toString());
                Toast.makeText(EditHapusToko.this, "Data Toko telah berhasil diupdate", Toast.LENGTH_LONG).show();
                EditHapusToko.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditHapusToko.this, "Data Toko belum berhasil diupdate", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EditHapusToko.this, "Data Toko telah berhasil dihapus...",Toast.LENGTH_LONG).show();
                    EditHapusToko.this.finish();
                }

                else
                {
                    Toast.makeText(EditHapusToko.this, "Data Toko belum berhasil dihapus...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}