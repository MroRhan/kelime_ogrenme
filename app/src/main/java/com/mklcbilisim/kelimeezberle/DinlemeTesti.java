package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class DinlemeTesti extends AppCompatActivity implements TextToSpeech.OnInitListener{

    Button btnKelime,btnA,btnB,btnC,btnD;
    int trIx2,engIx2;
    TextView sayac;
    Cursor cursor;
    String[] ezberTr,ezberEn,tekrarTr,tekrarEn,tumEn,tumTr;
    int dogruSay ,randomKelime,yanlisSay,secilenHedef ,say =0;
    TextToSpeech tts;
    int say2=0;
    SharedPreferences gunlukHedef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinleme_testi);
        gunlukHedef = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);
        secilenHedef = gunlukHedef.getInt("hedef",0);
        tts = new TextToSpeech(this,this);
        btnKelime = findViewById(R.id.btnKelime);
        kelimeler();
        kelimeSor(randomKelime);


    }

    public void anasayfa(View view){
        Intent anasayfa = new Intent(this,AnaEkran.class);
        finish();
        startActivity(anasayfa);
    }

    public void ayarlar(View view){
        Intent ayarlar = new Intent(this,Ayarlar.class);
        finish();
        startActivity(ayarlar);
    }


    public void butonA(View view){

        if(btnA.getText().toString().equals(ezberTr[randomKelime])){
            btnA.setBackgroundResource(R.drawable.dogru_buton);
            dogruSay++;
        }else{
            tekrarTr[yanlisSay] = ezberTr[randomKelime];
            tekrarEn[yanlisSay] = ezberEn[randomKelime];
            yanlisSay++;

            btnA.setBackgroundResource(R.drawable.yanlis_buton);
            if(btnB.getText().toString().equals(ezberTr[randomKelime])){
                btnB.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnC.getText().toString().equals(ezberTr[randomKelime])){
                btnC.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnD.getText().toString().equals(ezberTr[randomKelime])){
                btnD.setBackgroundResource(R.drawable.dogru_buton);
            }
        }
        if(randomKelime < ezberTr.length){
            zamanla();
        }else{
            System.out.println("Kelime Bitti");
        }

    }

    public void butonB(View view){

        if(btnB.getText().toString().equals(ezberTr[randomKelime])){
            btnB.setBackgroundResource(R.drawable.dogru_buton);
            dogruSay++;
        }else{
            tekrarTr[yanlisSay] = ezberTr[randomKelime];
            tekrarEn[yanlisSay] = ezberEn[randomKelime];
            yanlisSay++;

            btnB.setBackgroundResource(R.drawable.yanlis_buton);
            if(btnA.getText().toString().equals(ezberTr[randomKelime])){
                btnA.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnC.getText().toString().equals(ezberTr[randomKelime])){
                btnC.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnD.getText().toString().equals(ezberTr[randomKelime])){
                btnD.setBackgroundResource(R.drawable.dogru_buton);
            }
        }

        if(randomKelime < ezberTr.length){
            zamanla();
        }else{
            System.out.println("Kelime Bitti");
        }
    }

    public void butonC(View view){
        if(btnC.getText().toString().equals(ezberTr[randomKelime])){
            btnC.setBackgroundResource(R.drawable.dogru_buton);
            dogruSay++;
        }else{
            tekrarTr[yanlisSay] = ezberTr[randomKelime];
            tekrarEn[yanlisSay] = ezberEn[randomKelime];
            yanlisSay++;

            btnC.setBackgroundResource(R.drawable.yanlis_buton);
            if(btnB.getText().toString().equals(ezberTr[randomKelime])){
                btnB.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnA.getText().toString().equals(ezberTr[randomKelime])){
                btnA.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnD.getText().toString().equals(ezberTr[randomKelime])){
                btnD.setBackgroundResource(R.drawable.dogru_buton);
            }
        }

        if(randomKelime < ezberTr.length){
            zamanla();
        }else{
            System.out.println("Kelime Bitti");
        }
    }

    public void butonD(View view){

        if(btnD.getText().toString().equals(ezberTr[randomKelime])){
            btnD.setBackgroundResource(R.drawable.dogru_buton);
            dogruSay++;
        }else{
            tekrarTr[yanlisSay] = ezberTr[randomKelime];
            tekrarEn[yanlisSay] = ezberEn[randomKelime];
            yanlisSay++;
            btnD.setBackgroundResource(R.drawable.yanlis_buton);
            if(btnB.getText().toString().equals(ezberTr[randomKelime])){
                btnB.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnC.getText().toString().equals(ezberTr[randomKelime])){
                btnC.setBackgroundResource(R.drawable.dogru_buton);
            }else if(btnA.getText().toString().equals(ezberTr[randomKelime])){
                btnA.setBackgroundResource(R.drawable.dogru_buton);
            }
        }

        if(randomKelime < ezberTr.length){
            zamanla();
        }else{
            System.out.println("Kelime Bitti");
        }

    }


    protected  void kelimeler(){
        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);

            cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 2 ORDER BY RANDOM() ",null);
            engIx2 = cursor.getColumnIndex("kelimeing");
            trIx2 = cursor.getColumnIndex("kelimetr");
            ezberEn = new String[secilenHedef];
            ezberTr = new String[secilenHedef];

            tekrarEn = new String[secilenHedef];
            tekrarTr = new String[secilenHedef];

            tumEn = new String[secilenHedef];
            tumTr = new String[secilenHedef];

            while (cursor.moveToNext()){
                ezberEn[say] = cursor.getString(engIx2);
                ezberTr[say] = cursor.getString(trIx2);

                tumEn[say] = cursor.getString(engIx2);
                tumTr[say] = cursor.getString(trIx2);

                say = say+1;
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    protected  void kelimeTekrar(){
        try {

            ezberEn = new String[yanlisSay];
            ezberTr = new String[yanlisSay];

            for (int i=0; i<yanlisSay; i++){
                ezberEn[i] = tekrarEn[i];
                ezberTr[i] = tekrarTr[i];

            }

            yanlisSay = 0;
            dogruSay = 0;



        }catch (Exception e){
            e.printStackTrace();
        }

    }



int tikSay =0;
    protected void kelimeSor(int hangiKelime){
        randomKelime = hangiKelime;
        sayac = findViewById(R.id.txtSayac);
        sayac.setText(randomKelime+1 + " / " + ezberTr.length);

        int random = new Random().nextInt(ezberTr.length);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        btnKelime.setText(ezberEn[randomKelime]);

        if(tikSay >= tumEn.length){

            hataYaptim(ezberEn[randomKelime]);

        }else{
            tikSay++;
        }

        yaziyiCevir(btnKelime.getText().toString());

        // RANDOM ŞIKLAR
        int[] sikCevap = new int[4];
        sikCevap[0] = randomKelime;

        for (int i=0; i<4; i++){
            for (int z=0; z<i; z++){
                while(1==1){
                    if(sikCevap[z] != random){
                        sikCevap[i] = random;
                        break;
                    }else{
                        random = new Random().nextInt(tumTr.length);
                        z=0;
                    }

                }

            }

        }
        // RANDOM ŞIKLAR BİTİŞ

        int random2 = new Random().nextInt(4);

        if(random2 == 0){
            if(tumTr[sikCevap[0]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[1]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[2]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[3]].equals(ezberTr[randomKelime])){
                btnA.setText(tumTr[sikCevap[0]]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }else{
                btnA.setText(tekrarTr[randomKelime]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }




        }else if(random2 == 1){

            if(tumTr[sikCevap[0]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[1]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[2]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[3]].equals(ezberTr[randomKelime])){
                btnB.setText(tumTr[sikCevap[0]]);
                btnA.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }else{
                btnB.setText(tekrarTr[randomKelime]);
                btnA.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }



        }else if(random2 == 2){

            if(tumTr[sikCevap[0]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[1]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[2]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[3]].equals(ezberTr[randomKelime])){
                btnC.setText(tumTr[sikCevap[0]]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnA.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }else{
                btnC.setText(tekrarTr[randomKelime]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnA.setText(tumTr[sikCevap[2]]);
                btnD.setText(tumTr[sikCevap[3]]);
            }



        }else if(random2 == 3){

            if(tumTr[sikCevap[0]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[1]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[2]].equals(ezberTr[randomKelime]) || tumTr[sikCevap[3]].equals(ezberTr[randomKelime])){
                btnD.setText(tumTr[sikCevap[0]]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnA.setText(tumTr[sikCevap[3]]);
            }else{
                btnD.setText(tekrarTr[randomKelime]);
                btnB.setText(tumTr[sikCevap[1]]);
                btnC.setText(tumTr[sikCevap[2]]);
                btnA.setText(tumTr[sikCevap[3]]);
            }



        }



    }

    public void zamanla(){
        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long l) {
                btnA.setClickable(false);
                btnB.setClickable(false);
                btnC.setClickable(false);
                btnD.setClickable(false);
            }

            @Override
            public void onFinish() {
                btnA.setBackgroundResource(R.drawable.seviye_sec);
                btnB.setBackgroundResource(R.drawable.seviye_sec);
                btnC.setBackgroundResource(R.drawable.seviye_sec);
                btnD.setBackgroundResource(R.drawable.seviye_sec);

                btnA.setClickable(true);
                btnB.setClickable(true);
                btnC.setClickable(true);
                btnD.setClickable(true);


                if(randomKelime != ezberEn.length-1){
                    randomKelime++;
                    kelimeSor(randomKelime);
                    //      System.out.println(randomKelime);
                }else{
                    Intent enSayfa = new Intent(DinlemeTesti.this,KonusmaTesti.class);

                    if(yanlisSay > 0){
                        randomKelime = 0;
                        kelimeTekrar();
                        kelimeSor(randomKelime);

                    }else{
                        finish();
                        startActivity(enSayfa);
                    }


                }

            }
        }.start();
    }



    @Override
    public void onInit(int status) {
        if (status == tts.SUCCESS){
            int sonuc = tts.setLanguage(Locale.ENGLISH);
            if(sonuc == tts.LANG_MISSING_DATA || sonuc == tts.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Dil desteklenmiyor",Toast.LENGTH_SHORT).show();

            }else{
                yaziyiCevir(btnKelime.getText().toString());

            }
        }else{
            Toast.makeText(this,"Başarısız",Toast.LENGTH_SHORT).show();
        }
    }




    private void yaziyiCevir(String kelime){
        String text = kelime;
        tts.speak(text,tts.QUEUE_FLUSH,null);


    }

    public void tekrarDnl(View view){
        yaziyiCevir(btnKelime.getText().toString());
    }


    public void hataYaptim(String kelime){
        System.out.println(kelime);
        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            String guncelleSrgu = "UPDATE kelimeler SET S4 = 1 WHERE kelimeing = '" + kelime + "'";
            System.out.println(guncelleSrgu);
            database.execSQL(guncelleSrgu);

        }catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
    }




    public void btnDurumum(View view){
        Intent intent = new Intent(this,GenelDurum.class);
        finish();
        startActivity(intent);
    }


}
