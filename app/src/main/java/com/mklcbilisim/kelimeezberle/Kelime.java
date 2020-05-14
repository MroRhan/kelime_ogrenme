package com.mklcbilisim.kelimeezberle;

public class Kelime {
    String turkce;
    String ingilizce;
    int tren,entr,siralama,dinleme,konusma,id;



    public Kelime(String mTurkce, String mIngilizce, int mTren, int mEntr, int mSiralama, int mDinleme, int mKonusma, int mId ){
        turkce = mTurkce;
        ingilizce = mIngilizce;
        tren = mTren;
        entr = mEntr;
        siralama = mSiralama;
        dinleme = mDinleme;
        konusma = mKonusma;
        id = mId;
    }



    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }

    public int getTren() {
        return tren;
    }

    public void setTren(int tren) {
        this.tren = tren;
    }

    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    public int getSiralama() {
        return siralama;
    }

    public void setSiralama(int siralama) {
        this.siralama = siralama;
    }

    public int getDinleme() {
        return dinleme;
    }

    public void setDinleme(int dinleme) {
        this.dinleme = dinleme;
    }

    public int getKonusma() {
        return konusma;
    }

    public void setKonusma(int konusma) {
        this.konusma = konusma;
    }

    public int getId() { return id;    }

    public void setId(int id) { this.id = id; }
}
