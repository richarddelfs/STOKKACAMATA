package com.example.stokkacamata;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView LogoAplikasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(MainActivity.this, Login.class);
                startActivity(main);

                finish();
            }
        }, 1000);

        //LogoAplikasi = findViewById(R.id.LogoAplikasi);

        //LogoAplikasi.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent main = new Intent(MainActivity.this, Login.class);
        //        startActivity(main);
        //    }
        //});
    }
}