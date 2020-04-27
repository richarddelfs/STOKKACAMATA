package com.example.stokkacamata;

public class ProfileToko {
    private String namatoko;
    private String deskripsitoko;

    public ProfileToko() {

    }

    public ProfileToko(String namatoko, String deskripsitoko) {
        this.namatoko = namatoko;
        this.deskripsitoko = deskripsitoko;
    }

    public String getNamatoko() {
        return namatoko;
    }

    public void setNamatoko(String namatoko) {
        this.namatoko = namatoko;
    }

    public String getDeskripsitoko() {
        return deskripsitoko;
    }

    public void setDeskripsitoko(String deskripsitoko) {
        this.deskripsitoko = deskripsitoko;
    }
}