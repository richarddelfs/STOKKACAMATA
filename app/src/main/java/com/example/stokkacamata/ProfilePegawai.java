package com.example.stokkacamata;

public class ProfilePegawai {
    private String nama;
    private String alamat;
    private String notelp;
    private String tempatlahir;
    private String tanggallahir;
    private String deskripsi;
    private String password;
    private String profilepicturepegawai;
    //private boolean permission;

    public ProfilePegawai() {
    }

    public ProfilePegawai(String nama, String alamat, String notelp, String tempatlahir, String tanggallahir, String deskripsi, String password, String profilepicturepegawai, boolean permission) {
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.tempatlahir = tempatlahir;
        this.tanggallahir = tanggallahir;
        this.deskripsi = deskripsi;
        this.password = password;
        this.profilepicturepegawai = profilepicturepegawai;
        //this.permission = permission;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getTempatlahir() {
        return tempatlahir;
    }

    public void setTempatlahir(String tempatlahir) {
        this.tempatlahir = tempatlahir;
    }

    public String getTanggallahir() {
        return tanggallahir;
    }

    public void setTanggallahir(String tanggallahir) {
        this.tanggallahir = tanggallahir;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilepicturepegawai() {
        return profilepicturepegawai;
    }

    public void setProfilepicturepegawai(String profilepicturepegawai) {
        this.profilepicturepegawai = profilepicturepegawai;
    }
/*
    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
 */
}