package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private CardView ButtonSubmit2, ButtonSubmit3;
    private TextView registation1;
    private EditText TextEmail2, TextPassword2;
    private FirebaseAuth mfirebaseAuth;
    private String email, password;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public static Activity activitylogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuth = FirebaseAuth.getInstance();
        ButtonSubmit2 = findViewById(R.id.ButtonSubmit2);
        ButtonSubmit3 = findViewById(R.id.ButtonSubmit3);
        registation1 = findViewById(R.id.registation1);
        TextEmail2 = findViewById(R.id.TextEmail2);
        TextPassword2 = findViewById(R.id.TextPassword2);
        activitylogin = this;

        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseUser = mfirebaseAuth.getCurrentUser();
                if(mfirebaseUser != null){
                    Toast.makeText(Login.this, "Anda berhasil masuk", Toast.LENGTH_SHORT).show();
                    Intent i  = new Intent(Login.this, Home.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Silahkan Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ButtonSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = TextEmail2.getText().toString();
                password = TextPassword2.getText().toString();
                if(email.isEmpty()){
                    TextEmail2.setError("Masukkan email anda");
                    TextEmail2.requestFocus();
                }
                else if(password.isEmpty()){
                    TextPassword2.setError("Masukkan password anda");
                    TextPassword2.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(Login.this, "Masih kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mfirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login salah, silahkan ulangi sekali lagi", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent login = new Intent(Login.this, Home.class);
                                startActivity(login);
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                //Intent login = new Intent(Login.this, Home.class);
                //startActivity(login);
            }
        });

        registation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registraion = new Intent(Login.this, Registration.class);
                startActivity(registraion);
            }
        });

        ButtonSubmit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginpegawai = new Intent(Login.this, LoginPegawai.class);
                startActivity(loginpegawai);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mauthStateListener);
    }
}