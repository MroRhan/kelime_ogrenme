package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import com.onesignal.OneSignal;

public class Ayarlar extends AppCompatActivity {


    SharedPreferences seviye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        seviye = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }

    public void anasayfa(View view) {
        Intent anasayfa = new Intent(this, AnaEkran.class);
        finish();
        startActivity(anasayfa);
    }

    public void ayarlar(View view) {
        Intent ayarlar = new Intent(this, Ayarlar.class);
        finish();
        startActivity(ayarlar);
    }

    public void gunlukKelime(View view) {
        Intent gunlukKelime = new Intent(this, HedefSec.class);
        gunlukKelime.putExtra("ayarlarHedef", "1");
        finish();
        startActivity(gunlukKelime);
    }


    public void ogrenmeSeviye(View view) {



        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Seviye Değiştirme");
        alert.setMessage("Öğrendiğiniz tüm kelimeler sıfırlanacaktır!");

        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {

                    SQLiteDatabase database = Ayarlar.this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
                    String tamamlandı = "DELETE FROM kelimeler";
                    database.execSQL(tamamlandı);

                    Intent ogrenmeSeviye = new Intent(Ayarlar.this, MainActivity.class);
                    ogrenmeSeviye.putExtra("ayarlarOgrenme", "1");
                    seviye.edit().putInt("nereden", 0).apply();
                    finish();
                    startActivity(ogrenmeSeviye);


                }catch (Exception e){

                }


            }
        });

        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Hayır");
            }
        });

        alert.show();


    }


    public void kelimeSifirla(View view) {



        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Kelimeleri Sıfırla");
        alert.setMessage("Öğrendiğiniz tüm kelimeleri sıfırlamak istiyor musunuz?");

        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

           try {

               SQLiteDatabase database = Ayarlar.this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
               String tamamlandı = "DELETE FROM kelimeler";
               database.execSQL(tamamlandı);

               Intent ogrenmeSeviye = new Intent(Ayarlar.this, MainActivity.class);
               ogrenmeSeviye.putExtra("ayarlarOgrenme", "1");
               seviye.edit().putInt("nereden", 0).apply();
               startActivity(ogrenmeSeviye);


           }catch (Exception e){

           }


            }
        });

        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Hayır");
            }
        });

        alert.show();

    }



    public void ayarlarPaylas(View view) {
       Intent paylas = new Intent(Intent.ACTION_SEND);
       paylas.setType("text/plain");
       String baslik = "Arkadaşlarınla paylaş";
       String icerik = "Google Play Linki Eklenecek";
       paylas.putExtra(Intent.EXTRA_SUBJECT, baslik);
       paylas.putExtra(Intent.EXTRA_TEXT, icerik);

       startActivity(Intent.createChooser(paylas,"Arkadaşlarınla paylaş"));
    }


    public void hakkimizda(View view) {
        Intent gunlukKelime = new Intent(this, Hakkimizda.class);
        gunlukKelime.putExtra("ayarlarHedef", "1");
        finish();
        startActivity(gunlukKelime);
    }

    public void durumum(View view){
        Intent durumum = new Intent(this,GenelDurum.class);
        finish();
        startActivity(durumum);
    }



}

