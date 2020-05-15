package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mklcbilisim.kelimeezberle.egzersiz.DinlemeEgzersiz;
import com.mklcbilisim.kelimeezberle.egzersiz.IngilizceEgzersiz;
import com.mklcbilisim.kelimeezberle.egzersiz.KonusmaEgzersiz;
import com.mklcbilisim.kelimeezberle.egzersiz.SiralamaEgzersiz;
import com.mklcbilisim.kelimeezberle.egzersiz.TurkceEgzersiz;

public class AnaEkran extends AppCompatActivity {

    TextView txtGelenHedef;
    SharedPreferences gunlukShareHedef,seviye;
    int secilenHedef,egzersizKelime;
    Cursor cursor,cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);


        txtGelenHedef = findViewById(R.id.txtHedef);

        gunlukShareHedef = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);
        secilenHedef = gunlukShareHedef.getInt("hedef",0);


       txtGelenHedef.setText(" GÜNLÜK HEDEFİNİZ " + secilenHedef + " KELİMEDİR");



        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM kelimeler", null);


            int idIx = cursor.getColumnIndex("id");
            int durumIx = cursor.getColumnIndex("durum");
            int seviyeIx = cursor.getColumnIndex("seviye");
            int turkceIx = cursor.getColumnIndex("kelimetr");
            int ingilizceIx = cursor.getColumnIndex("kelimeing");

            int S1 = cursor.getColumnIndex("S1");
            int S2 = cursor.getColumnIndex("S2");
            int S3 = cursor.getColumnIndex("S3");
            int S4 = cursor.getColumnIndex("S4");
            int S5 = cursor.getColumnIndex("S5");

            while (cursor.moveToNext()){


                System.out.print("İD : " + cursor.getInt(idIx));
                System.out.print("-- DURUM : " + cursor.getInt(durumIx));
                System.out.print("-- SEVİYE : " + cursor.getInt(seviyeIx));

                System.out.print("-- S1 : " + cursor.getInt(S1));
                System.out.print("-- S2 : " + cursor.getInt(S2));
                System.out.print("-- S3 : " + cursor.getInt(S3));
                System.out.print("-- S4 : " + cursor.getInt(S4));
                System.out.println("-- S5 : " + cursor.getInt(S5));


            }


            cursor.close();


        }catch (Exception e){

        }





        //EGZERSİZ KELİME SAYISI

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            cursor2 = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 5  or durum = 4",null);
            egzersizKelime = cursor2.getCount();
            System.out.println(egzersizKelime);
        }catch (Exception e){

        }



    }



    public void ogrenmeyeBasla(View view){



        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);

            String tamamlandı = "UPDATE kelimeler SET durum = 5 WHERE durum = 4 ";
            database.execSQL(tamamlandı);

            Cursor cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 2",null);
            int kacKelimeVar = cursor.getCount();


            if(kacKelimeVar == 0){
                Cursor cursor2 = database.rawQuery("SELECT COUNT(*) as kacKelime FROM kelimeler WHERE durum = 1 OR durum = 3",null);
                int kalanKacKelime = cursor2.getColumnIndex("kacKelime");
                cursor2.moveToNext();
                int havuzKelime = cursor2.getInt(kalanKacKelime);

                if(havuzKelime < 5){
                    //SEVİYE ATLAT KELİME KALMADI
                    System.out.println("Seviye Atla");

                    // SEVİYE ÖĞREN
                   seviye = this.getSharedPreferences("com.mklcbilisim.kelimeezberle",Context.MODE_PRIVATE);
                    int secilenSeviye = seviye.getInt("nereden",0);
                    System.out.println("Seviye " + secilenSeviye);


                    AlertDialog.Builder alert = new AlertDialog.Builder(this);

                    if(secilenSeviye == 1){

                        alert.setTitle("Başlangıç Seviyeyi Tamamladınız!");
                        alert.setMessage("Orta seviyeye geçmek istiyor musunuz?");

                        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // YENİ KELİEMELER YÜKLEE
                                Intent seviyeAtla = new Intent(getApplicationContext(),MainActivity.class);
                                seviye.edit().putInt("nereden", 0).apply();
                                startActivity(seviyeAtla);

                            }
                        });

                    }else if(secilenSeviye == 2){

                        alert.setTitle("Orta Seviyeyi Tamamladınız!");
                        alert.setMessage("İleri seviyeye geçmek istiyor musunuz?");

                        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // YENİ KELİEMELER YÜKLEE
                                Intent seviyeAtla = new Intent(getApplicationContext(),MainActivity.class);
                                seviye.edit().putInt("nereden", 0).apply();
                                startActivity(seviyeAtla);

                            }
                        });


                    }else if(secilenSeviye == 3){



                        alert.setTitle("İleri Seviyeyi Tamamladınız!");
                        alert.setMessage("İleri seviyeyi tamamladınız. Yeni seviye seçmek ister misiniz?");


                        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                SQLiteDatabase database = AnaEkran.this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
                                String tamamlandı = "DELETE FROM kelimeler";
                                database.execSQL(tamamlandı);

                                // YENİ KELİEMELER YÜKLEE
                                Intent seviyeAtla = new Intent(getApplicationContext(),MainActivity.class);
                                seviye.edit().putInt("nereden", 0).apply();
                                startActivity(seviyeAtla);

                            }
                        });


                    }



                    alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("Hayır");
                        }
                    });


                    alert.show();





                }else{

                    // YENİ KELİME GELSİN


                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Yeni Kelimeler?");
                    alert.setMessage("Yeni kelime öğrenmeye hazır mısın?");
                    alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                          // YENİ KELİEMELER YÜKLEE
                            Intent intent = new Intent(getApplicationContext(),OgrenmeyeBasla.class);
                            try {



                                SQLiteDatabase database = AnaEkran.this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
                                String sqlSorgu = "SELECT * FROM kelimeler WHERE durum = 1 OR durum = 3 ORDER BY RANDOM() LIMIT "+ secilenHedef;



                                Cursor cursor = database.rawQuery(sqlSorgu,null);
                                int idIx = cursor.getColumnIndex("id");

                                while (cursor.moveToNext()){
                                    String guncelleSrgu = "UPDATE kelimeler SET durum = 2 WHERE id = " + cursor.getInt(idIx);
                                    database.execSQL(guncelleSrgu);
                                }

                                cursor.close();
                             //   finish();
                                startActivity(intent);

                            }catch (Exception e){
                                e.printStackTrace();
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


            }else{
                // ÖĞRENMEYE BAŞLA START
                cursor.close();
                Intent intent = new Intent(getApplicationContext(),OgrenmeyeBasla.class);
                startActivity(intent);
            }





        }catch (Exception e){
            e.printStackTrace();
        }




    }





     public void btnTrclick(View view){


         if(egzersizKelime > 5 ){

             Intent intent = new Intent(this, TurkceEgzersiz.class);
       //      finish();
             startActivity(intent);


         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setTitle("Yetersiz Kelime!");
             alert.setMessage("Egzersiz yapmak için en az  5 kelime öğremelisiniz.");
             alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });


             alert.show();
         }

     }




     public void btnEnclick(View view){


         if(egzersizKelime > 5 ){

             Intent intent = new Intent(getApplicationContext(), IngilizceEgzersiz.class);
      //       finish();
             startActivity(intent);


         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setTitle("Yetersiz Kelime!");
             alert.setMessage("Egzersiz yapmak için en az 5 kelime öğremelisiniz.");
             alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });


             alert.show();
         }



     }

     public void btnSiraclick(View view){

         if(egzersizKelime > 5 ){

             Intent intent = new Intent(getApplicationContext(), SiralamaEgzersiz.class);
        //      finish();
             startActivity(intent);


         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setTitle("Yetersiz Kelime!");
             alert.setMessage("Egzersiz yapmak için en az  5 kelime öğremelisiniz.");
             alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });


             alert.show();
         }



     }

     public void btnDinleclick(View view){


         if(egzersizKelime > 5 ){

             Intent intent = new Intent(getApplicationContext(), DinlemeEgzersiz.class);
      //       finish();
             startActivity(intent);


         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setTitle("Yetersiz Kelime!");
             alert.setMessage("Egzersiz yapmak için en az 5 kelime öğremelisiniz.");
             alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });


             alert.show();
         }


     }

     public void btnKnsclick(View view){


         if(egzersizKelime > 5 ){

             Intent intent = new Intent(getApplicationContext(), KonusmaEgzersiz.class);
         //    finish();
             startActivity(intent);


         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setTitle("Yetersiz Kelime!");
             alert.setMessage("Egzersiz yapmak için 5 kelime öğremelisiniz.");
             alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                 }
             });


             alert.show();
         }


     }

     public void btnAyarclick(View view){
         Intent intent = new Intent(getApplicationContext(),Ayarlar.class);
    //     finish();
         startActivity(intent);
     }

    public void btnDurumum(View view){
        Intent intent = new Intent(getApplicationContext(),GenelDurum.class);
   //     finish();
        startActivity(intent);
    }

}
