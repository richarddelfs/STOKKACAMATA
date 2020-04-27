package com.example.stokkacamata;

public class ProfileBarang {
    private String nama;
    private String merk;
    private String tipe;
    private String warna;
    private String jumlah;
    private String profilepicturebarang;
    private String qrcodeurl;

    public ProfileBarang() {

    }

    public ProfileBarang(String nama, String merk, String tipe, String warna, String jumlah, String profilepicturebarang, String qrcodeurl) {
        this.nama = nama;
        this.merk = merk;
        this.tipe = tipe;
        this.warna = warna;
        this.jumlah = jumlah;
        this.profilepicturebarang = profilepicturebarang;
        this.qrcodeurl = qrcodeurl;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public String getProfilepicturebarang() {
        return profilepicturebarang;
    }

    public void setProfilepicturebarang(String profilepicturebarang) {
        this.profilepicturebarang = profilepicturebarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }
}