package com.example.stokkacamata;

public class ProfileTransaksi {
    private String pembeli;
    private String alamat;
    private String notelp;
    private String profilepicturetransaksi;

    public ProfileTransaksi() {

    }

    public ProfileTransaksi(String nama, String alamat, String notelp, String profilepicturetransaksi) {
        this.pembeli = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.profilepicturetransaksi = profilepicturetransaksi;
    }

    public String getProfilepicturetransaksi() {
        return profilepicturetransaksi;
    }

    public void setProfilepicturetransaksi(String profilepicturetransaksi) {
        this.profilepicturetransaksi = profilepicturetransaksi;
    }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String nama) {
        this.pembeli = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
}