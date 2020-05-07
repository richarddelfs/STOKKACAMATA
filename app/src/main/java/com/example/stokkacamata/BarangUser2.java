package com.example.stokkacamata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class BarangUser2 extends AppCompatActivity {

    private EditText nama, merk, tipe, warna, jumlah, editgenerateqrcode;
    private ImageView Placeholder, imageViewgenerateqrcode;
    private Button buttongenerateqrcode;
    private CardView ButtonTambahBarang;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProfileBarang profileBarang;
    private StorageReference storage;
    private static final int Gallery_intent = 1000;
    private static final int PERMISSION_CODE = 1000;
    private StorageReference imagePath;
    private String imageUrl  = "";
    private String qrcodeUrl;
    private StorageReference Qrcode;
    private Uri uri;
    private Bitmap bitmap;
    private byte[] data;
    private String url_path ="qrcode/";

    //Request Code Digunakan Untuk Menentukan Permintaan dari User
    /*
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;
    public static final int REQUEST_CODE_CAMERA1 = 003;
    public static final int REQUEST_CODE_GALLERY1 = 004;
    public static final int REQUEST_CODE_CAMERA2 = 005;
    public static final int REQUEST_CODE_GALLERY2 = 006;
    public static final int REQUEST_CODE_CAMERA3 = 007;
    public static final int REQUEST_CODE_GALLERY3 = 000;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_user2);

        nama = findViewById(R.id.nama5user2);
        merk = findViewById(R.id.merk1user2);
        tipe = findViewById(R.id.tipe1user2);
        warna = findViewById(R.id.warna1user2);
        jumlah = findViewById(R.id.jumlah1user2);
        Placeholder = findViewById(R.id.Placeholderuser2);

        imageViewgenerateqrcode = findViewById(R.id.imageViewgenerateqrcodeuser2);
        buttongenerateqrcode = findViewById(R.id.buttongenerateqrcodeuser2);

        ButtonTambahBarang = findViewById(R.id.ButtonTambahBaranguser2);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("ProfileBarang");
        storage = FirebaseStorage.getInstance().getReference();
        profileBarang = new ProfileBarang();

        buttongenerateqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode(nama.getText().toString(), BarcodeFormat.QR_CODE, 150, 150);
                    Bitmap bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565);
                    for(int x = 0; x<150; x++)
                    {
                        for(int y = 0; y<150; y++)
                        {
                            bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                        }
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    data = baos.toByteArray();
                    imageViewgenerateqrcode.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //        imageView.setDrawingCacheEnabled(true);
//        imageView.buildDrawingCache();
//        Bitmap bitmap = imageView.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            }

        ButtonTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String key = ref.push().getKey();
                getValues();
                ref.child(profileBarang.getNama()).setValue(profileBarang);
                final StorageReference storageReference = storage.child(url_path+ System.currentTimeMillis());
//                imagePath = storage.child("Qrcode");
                storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                qrcodeUrl = uri.toString();
                                Map<String, Object> url = new HashMap<>();
                                url.put("qrcodeUrl",url);
                                ref.child(profileBarang.getNama()).child("qrcodeurl").setValue(qrcodeUrl);
                                Toast.makeText(BarangUser2.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(BarangUser2.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(BarangUser2.this, "Data Barang telah berhasil masuk", Toast.LENGTH_LONG).show();
//                Intent barang = new Intent(BarangUser2.this, HomeUser2.class);
//                startActivity(barang);
                finish();
            }
        });

        Placeholder.setOnClickListener(new View.OnClickListener() {
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
/*
        Placeholder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage1();
            }
        });

        Placeholder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage2();
            }
        });

        Placeholder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage3();
            }
        });

 */
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
        profileBarang.setNama(nama.getText().toString().trim());
        profileBarang.setMerk(merk.getText().toString().trim());
        profileBarang.setTipe(tipe.getText().toString().trim());
        profileBarang.setWarna(warna.getText().toString().trim());
        profileBarang.setJumlah(jumlah.getText().toString().trim());
        profileBarang.setProfilepicturebarang(imageUrl);
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
                                EasyImage.openCamera(Barang.this, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(Barang.this, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    //Method Ini Digunakan Untuk Membuka Image dari Galeri atau Kamera
    private void setRequestImage1(){
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(Barang.this, REQUEST_CODE_CAMERA1);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(Barang.this, REQUEST_CODE_GALLERY1);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    //Method Ini Digunakan Untuk Membuka Image dari Galeri atau Kamera
    private void setRequestImage2(){
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(Barang.this, REQUEST_CODE_CAMERA2);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(Barang.this, REQUEST_CODE_GALLERY2);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    //Method Ini Digunakan Untuk Membuka Image dari Galeri atau Kamera
    private void setRequestImage3(){
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(Barang.this, REQUEST_CODE_CAMERA3);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(Barang.this, REQUEST_CODE_GALLERY3);
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
            uri = data.getData();
            Placeholder.setImageURI(uri);
            imagePath = storage.child("ProfileBarang").child(uri.getLastPathSegment());
            //ImageLocation = uri.getLastPathSegment();
            imagePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            Toast.makeText(BarangUser2.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(BarangUser2.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
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
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder);
                        break;

                    case REQUEST_CODE_GALLERY:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder);
                        break;

                    case REQUEST_CODE_CAMERA1:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder1);
                        break;

                    case REQUEST_CODE_GALLERY1:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder1);
                        break;

                    case REQUEST_CODE_CAMERA2:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder2);
                        break;

                    case REQUEST_CODE_GALLERY2:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder2);
                        break;

                    case REQUEST_CODE_CAMERA3:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder3);
                        break;

                    case REQUEST_CODE_GALLERY3:
                        Glide.with(Barang.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Placeholder3);
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