package com.scdm15.cecep.peminatan;

public class Mahasiswa {

    public String id;
    public String nim;
    public String nama;
    public String angkatan;
    public String peminatan;
    public String password;

    public Mahasiswa(String nim, String nama, String angkatan, String peminatan, String password) {
        this.nim = nim;
        this.nama= nama;
        this.angkatan= angkatan;
        this.peminatan= peminatan;
        this.password = password;
    }
}
