package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DataPegawai extends AppCompatActivity {

    private EditText nama, alamat, notelp, tempatlahir, tanggallahir, deskripsi, password;
    private Button insert, button2;
    private ImageView profilepicturepegawai;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProfilePegawai profilePegawai;
    private static final int Gallery_intent = 1000;
    private static final int PERMISSION_CODE = 1000;
    private StorageReference imagePath;
    private StorageReference storage;
    private String imageUrl = "";
    //private String ImageLocation;

    //Request Code Digunakan Untuk Menentukan Permintaan dari User
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pegawai);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        tempatlahir = findViewById(R.id.tempatlahir);
        tanggallahir = findViewById(R.id.tanggallahir);
        deskripsi = findViewById(R.id.deskripsi);
        password = findViewById(R.id.password);
        insert = findViewById(R.id.btnInsert);
        button2 = findViewById(R.id.button2);
        profilepicturepegawai = findViewById(R.id.profilepicture2);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("ProfilePegawai");
        storage = FirebaseStorage.getInstance().getReference();
        profilePegawai = new ProfilePegawai();


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String key = ref.push().getKey();
                getValues();
                ref.child(profilePegawai.getNama()).setValue(profilePegawai);
                Toast.makeText(DataPegawai.this, "Data Pegawai telah berhasil masuk", Toast.LENGTH_LONG).show();
                Intent pegawai = new Intent(DataPegawai.this, HalamanPegawai.class);
                startActivity(pegawai);
                finish();
                /*
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            getValues();
                            ref.child(profilePegawai.getNama()).setValue(profilePegawai);
                            Toast.makeText(DataPegawai.this, "Data Pegawai telah berhasil masuk", Toast.LENGTH_LONG).show();
                            Intent pegawai = new Intent(DataPegawai.this, HalamanPegawai.class);
                            startActivity(pegawai);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(DataPegawai.this, "Data Pegawai belum berhasil masuk", Toast.LENGTH_SHORT).show();
                        }
                    });
                    */
                }
            });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DataPegawai.this, HalamanPegawai.class);
                startActivity(back);
            }
        });

        profilepicturepegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setRequestImage();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Gallery_intent);
        //startActivityForResult(Intent.createChooser(intent, "Pick an image"), Gallery_intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void getValues()
    {
        profilePegawai.setNama(nama.getText().toString().trim());
        profilePegawai.setAlamat(alamat.getText().toString().trim());
        profilePegawai.setNotelp(notelp.getText().toString().trim());
        profilePegawai.setTempatlahir(tempatlahir.getText().toString().trim());
        profilePegawai.setTanggallahir(tanggallahir.getText().toString().trim());
        profilePegawai.setDeskripsi(deskripsi.getText().toString().trim());
        profilePegawai.setPassword(password.getText().toString().trim());
        profilePegawai.setProfilepicturepegawai(imageUrl);
    }
/*
    //Method Ini Digunakan Untuk Membuka Image dari Galeri atau Kamera
    private void setRequestImage(){
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(DataPegawai.this, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(DataPegawai.this, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }
*/
    //Method Ini Digunakan Untuk Menapatkan Hasil pada Activity, dari Proses Yang kita buat sebelumnya
    //Dan Mendapatkan Hasil File Photo dari Galeri atau Kamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_intent && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            profilepicturepegawai.setImageURI(uri);
            imagePath = storage.child("ProfilePegawai").child(uri.getLastPathSegment());
            //ImageLocation = uri.getLastPathSegment();
            imagePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            Toast.makeText(DataPegawai.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(DataPegawai.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            });
        }
/*
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Method Ini Digunakan Untuk Menghandle Error pada Image
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Method Ini Digunakan Untuk Menghandle Image
                switch (type) {
                    case REQUEST_CODE_CAMERA:
                        Glide.with(DataPegawai.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(profilepicture);
                        break;

                    case REQUEST_CODE_GALLERY:
                        Glide.with(DataPegawai.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(profilepicture);
                        break;
                }
            }
            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Batalkan penanganan, Anda mungkin ingin menghapus foto yang diambil jika dibatalkan
            }
        });
        */
    }
}