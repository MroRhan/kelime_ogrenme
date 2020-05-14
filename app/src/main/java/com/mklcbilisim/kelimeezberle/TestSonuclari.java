package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TestSonuclari extends AppCompatActivity   {


    TextView txtGelenHedef,basariYuzde;
    ListView listView;
    List<Kelime> kelimeler = new ArrayList<>();
    SharedPreferences gunlukHedef;
    float genelBasari;
    RatingBar ratingBar;
    KelimeAdaptor kelimeAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sonuclari);

        gunlukHedef = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);
        int secilenHedef = gunlukHedef.getInt("hedef",0);



        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            String guncelleSrgu = "UPDATE kelimeler SET durum=4 WHERE durum = 2";
            System.out.println("Öğrendim");
            database.execSQL(guncelleSrgu);

        }catch (Exception e){
            e.printStackTrace();
        }


        txtGelenHedef = findViewById(R.id.txtHedef);
        txtGelenHedef.setText(" Öğrenmeye çalıştığınız " + secilenHedef + " kelimenin sonuçları");

    //    gunlukHedef.edit().putInt("hedef", 0).apply();

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 4 ", null);


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


              //  String guncelleSrgu = "UPDATE kelimeler SET durum=3 WHERE id = '" + cursor.getInt(idIx) + "'";
                //database.execSQL(guncelleSrgu);


                System.out.print("İD : " + cursor.getInt(idIx));
                System.out.print("-- DURUM : " + cursor.getInt(durumIx));
                System.out.print("-- SEVİYE : " + cursor.getInt(seviyeIx));

                System.out.print("-- S1 : " + cursor.getInt(S1));
                System.out.print("-- S2 : " + cursor.getInt(S2));
                System.out.print("-- S3 : " + cursor.getInt(S3));
                System.out.print("-- S4 : " + cursor.getInt(S4));
                System.out.println("-- S5 : " + cursor.getInt(S5));

                genelBasari += cursor.getInt(S1) + cursor.getInt(S2) + cursor.getInt(S3) + cursor.getInt(S4) + cursor.getInt(S5);
                kelimeler.add(new Kelime(cursor.getString(turkceIx),cursor.getString(ingilizceIx),cursor.getInt(S1),cursor.getInt(S2),cursor.getInt(S3),cursor.getInt(S4),cursor.getInt(S5),cursor.getInt(idIx)));

            }


            cursor.close();


        }catch (Exception e){

        }

        ratingBar = findViewById(R.id.ratingBar2);
        basariYuzde = findViewById(R.id.txtBasari);
        float yuzde = ((genelBasari*100)/(secilenHedef*5));
        ratingBar.setRating((100-yuzde)/20);
        basariYuzde.setText("%" + new DecimalFormat("##.##").format(100-yuzde));


        //LİST VİEW DOLDUR

        listView = findViewById(R.id.lstView);
        kelimeAdaptor = new KelimeAdaptor(this,kelimeler);
        listView.setAdapter(kelimeAdaptor);





    }

   /* public void tekrarEt(int Id){
       int tekrarEtGelen = Id;

        try {

            SQLiteDatabase database = kelimeAdaptor.activity.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);

            String geriAt = "UPDATE kelimeler SET durum=2,S1=1,S2=1,S3=1,S4=1,S5=1 WHERE id = '"+tekrarEtGelen+"' ";
            System.out.println(geriAt);
            database.execSQL(geriAt);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
*/

    public void anasayfa(View view){
        Intent anasayfa = new Intent(this,AnaEkran.class);
        startActivity(anasayfa);
    }

    public void ayarlar(View view){
        Intent ayarlar = new Intent(this,Ayarlar.class);
        startActivity(ayarlar);
    }



    public void btnDurumum(View view){
        Intent intent = new Intent(this,GenelDurum.class);
        startActivity(intent);
    }



}
