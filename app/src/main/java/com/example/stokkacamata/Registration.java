package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    private CardView ButtonSubmit1;
    private EditText TextEmail1, TextUsername1, TextPassword1;
    private TextView sudahdaftar;
    private FirebaseAuth mfirebaseAuth;
    private String email, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mfirebaseAuth = FirebaseAuth.getInstance();
        ButtonSubmit1 = findViewById(R.id.ButtonSubmit1);
        sudahdaftar = findViewById(R.id.sudahdaftar);
        TextEmail1 = findViewById(R.id.TextEmail1);
        TextUsername1 = findViewById(R.id.TextUsername1);
        TextPassword1 = findViewById(R.id.TextPassword1);

        ButtonSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = TextEmail1.getText().toString();
                username = TextUsername1.getText().toString();
                password = TextPassword1.getText().toString();
                if(email.isEmpty()){
                    TextEmail1.setError("Masukkan email anda");
                    TextEmail1.requestFocus();
                }
                else if(username.isEmpty()){
                    TextUsername1.setError("Masukkan username anda");
                    TextUsername1.requestFocus();
                }
                else if(password.isEmpty()){
                    TextPassword1.setError("Masukkan password anda");
                    TextPassword1.requestFocus();
                }
                else if(email.isEmpty() && username.isEmpty() && password.isEmpty()){
                    Toast.makeText(Registration.this, "Masih kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && username.isEmpty() && password.isEmpty())){
                    mfirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Registration.this, "Gagal registrasi, Silahkan ulangi sekali lagi", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(Registration.this, Home.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Registration.this, "Error!", Toast.LENGTH_SHORT).show();
                }

                //Intent login = new Intent(Registration.this, Login.class);
                //startActivity(login);
            }
        });

        sudahdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Registration.this, Login.class);
                startActivity(login);
            }
        });
    }
}