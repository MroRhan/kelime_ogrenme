package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    SharedPreferences seviyeSec;
    VerileriIndir verileriIndir = new VerileriIndir();
        ArrayList<String> trArray = new ArrayList<>();
    ArrayList<String> enArray = new ArrayList<>();
    ArrayList<Integer> svArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayShowHomeEnabled(true);
     //   actionBar.setIcon(R.drawable.title);
        //Spannable text = new SpannableString("Kelime Seviyesi Seçiniz");
        //text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //actionBar.setTitle(text);
     //   actionBar.setTitle(" Kelime Seviyesi Seçiniz");
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1c2833")));





        Intent ayarlarOgrenme = getIntent();


        seviyeSec = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);



        int nereden;
        System.out.println("Ayarlar Seviye " +  ayarlarOgrenme.getStringExtra("ayarlarOgrenme"));
        if(ayarlarOgrenme.getStringExtra("ayarlarOgrenme") == null){

            nereden = seviyeSec.getInt("nereden",0);
        }else{

            nereden = 0;

        }




        System.out.println(nereden);
        super.onCreate(savedInstanceState);
        if(nereden == 1){

            Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
            hedefSec.putExtra("nereden","1");

            startActivity(hedefSec);
        }else if(nereden == 2){
            Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
            hedefSec.putExtra("nereden","2");

            startActivity(hedefSec);
        }else if(nereden == 3){
            Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
            hedefSec.putExtra("nereden","3");

            startActivity(hedefSec);
        }else{

            setContentView(R.layout.activity_main);


        }

        verileriIndir.execute();


    }

//Kelimeleri Json ile çekme arka planda
    private class VerileriIndir extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sonuc = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL("http://mrorhan.com/kelimeler/index.json");

                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int veri = inputStreamReader.read();

                while (veri > 0){
                    char character = (char) veri;
                    sonuc += character;
                    veri = inputStreamReader.read();
                }

                return sonuc;

            }catch (Exception e){
                return e.toString();
            }


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);





            try {


                JSONObject  jsonRootObject = new JSONObject(s);



                JSONArray jsonArray = jsonRootObject.optJSONArray("kelimeler");


                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String tr = jsonObject.optString("tr");
                    String en = jsonObject.optString("en");
                    Integer sv = jsonObject.optInt("se");


                        trArray.add(tr);
                        enArray.add(en);
                        svArray.add(sv);



                }

            } catch (JSONException e) {e.printStackTrace();}

        }
    }



   public void baslangic(View view){

      Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
      hedefSec.putExtra("nereden","1");
      seviyeSec.edit().putInt("nereden",1).apply();

       try {

           SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
           database.execSQL("CREATE TABLE IF NOT EXISTS kelimeler ( id INTEGER PRIMARY KEY AUTOINCREMENT , 'kelimetr' VARCHAR(75) NOT NULL , 'kelimeing' VARCHAR(75) NOT NULL , 'seviye' INT(5) NOT NULL , 'durum' INT(5) NOT NULL, 'S1' INT(5) NOT NULL  , 'S2' INT(5) NOT NULL , 'S3' INT(5) NOT NULL , 'S4' INT(5) NOT NULL , 'S5' INT(5) NOT NULL )");


           for(int i =0; i<trArray.size(); i++){
               if(svArray.get(i) == 1 ){
                   //INSERT SEVİYE 1
                   System.out.println(trArray.get(i));
                   database.execSQL("INSERT INTO kelimeler (kelimetr,kelimeing,seviye,durum,S1,S2,S3,S4,S5) VALUES ('"+trArray.get(i)+"','"+enArray.get(i)+"',1,1,0,0,0,0,0)");
               }
           }


       }catch (Exception e){
           e.printStackTrace();
       }
       finish();
      startActivity(hedefSec);

    }

    public void orta(View view){
        Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
        hedefSec.putExtra("nereden","2");
        seviyeSec.edit().putInt("nereden",2).apply();

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS kelimeler ( id INTEGER PRIMARY KEY AUTOINCREMENT , 'kelimetr' VARCHAR(75) NOT NULL , 'kelimeing' VARCHAR(75) NOT NULL , 'seviye' INT(5) NOT NULL , 'durum' INT(5) NOT NULL, 'S1' INT(5) NOT NULL  , 'S2' INT(5) NOT NULL , 'S3' INT(5) NOT NULL , 'S4' INT(5) NOT NULL , 'S5' INT(5) NOT NULL )");


            for(int i =0; i<trArray.size(); i++){
                if(svArray.get(i) == 2 ){
                    //INSERT SEVİYE 2
                    System.out.println(trArray.get(i));
                    database.execSQL("INSERT INTO kelimeler (kelimetr,kelimeing,seviye,durum,S1,S2,S3,S4,S5) VALUES ('"+trArray.get(i)+"','"+enArray.get(i)+"',2,1,0,0,0,0,0)");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
        startActivity(hedefSec);
    }

    public void ileri(View view){
        Intent hedefSec = new Intent(getApplicationContext(),HedefSec.class);
        hedefSec.putExtra("nereden","3");
        seviyeSec.edit().putInt("nereden",3).apply();

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS kelimeler ( id INTEGER PRIMARY KEY AUTOINCREMENT , 'kelimetr' VARCHAR(75) NOT NULL , 'kelimeing' VARCHAR(75) NOT NULL , 'seviye' INT(5) NOT NULL , 'durum' INT(5) NOT NULL, 'S1' INT(5) NOT NULL  , 'S2' INT(5) NOT NULL , 'S3' INT(5) NOT NULL , 'S4' INT(5) NOT NULL , 'S5' INT(5) NOT NULL )");


            for(int i =0; i<trArray.size(); i++){
                if(svArray.get(i) == 3 ){
                    //INSERT SEVİYE 1
                    System.out.println(trArray.get(i));
                    database.execSQL("INSERT INTO kelimeler (kelimetr,kelimeing,seviye,durum,S1,S2,S3,S4,S5) VALUES ('"+trArray.get(i)+"','"+enArray.get(i)+"',3,1,0,0,0,0,0)");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
        startActivity(hedefSec);
    }
}
