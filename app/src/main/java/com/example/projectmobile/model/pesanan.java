package com.example.projectmobile.model;

public class pesanan {
    private String id, namamakanan,jumlah,harga,totalharga;



    public pesanan(String namamakanan, String jumlah, String harga, String totalharga){
        this.namamakanan = namamakanan;
        this.jumlah = jumlah;
        this.harga = harga;
        this.totalharga = totalharga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamamakanan() {
        return namamakanan;
    }

    public void setNamamakanan(String namamakanan) {
        this.namamakanan = namamakanan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTotalharga(){return totalharga;}

    public void setTotalharga(String totalharga) { this.totalharga = totalharga;    }
}