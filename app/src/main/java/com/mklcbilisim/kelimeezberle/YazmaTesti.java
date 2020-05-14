package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.service.autofill.OnClickAction;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class YazmaTesti extends AppCompatActivity implements TextToSpeech.OnInitListener,View.OnClickListener{

    Button btnKelime;
    int trIx2,engIx2;
    Cursor cursor;
    TextView sayac;
    String[] ezberTr;
    String[] ezberEn;
    String[] parcala;
    TextToSpeech tts;
    int say=0,say2=0;
    int randomKelime = 0;
    private int selectTitle;
    private Button myButton ;
    TextView txtKelime;
    String kelime = "";
    int tiklamaSira = 0;
    int kelimeUzun;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazma_testi);

        tts = new TextToSpeech(this,this);

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            btnKelime = findViewById(R.id.btnKelime);
            cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 2  LIMIT 5",null);
            engIx2 = cursor.getColumnIndex("kelimeing");
            trIx2 = cursor.getColumnIndex("kelimetr");
            ezberEn = new String[5];
            ezberTr = new String[5];

            while (cursor.moveToNext()){
                ezberEn[say] = cursor.getString(trIx2);
                ezberTr[say] = cursor.getString(engIx2);
                say = say+1;
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        kelimeSor(randomKelime);
        customButton(randomKelime);

    }


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



    protected void kelimeSor(int hangiKelime){
        sayac = findViewById(R.id.txtSayac);
        sayac.setText(randomKelime+1 + " / " + ezberTr.length);

           btnKelime.setText(ezberEn[hangiKelime]);

    }






    @Override
    public void onInit(int status) {
        if (status == tts.SUCCESS){
            int sonuc = tts.setLanguage(Locale.ENGLISH);
            if(sonuc == tts.LANG_MISSING_DATA || sonuc == tts.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Dil desteklenmiyor",Toast.LENGTH_SHORT).show();

            }else{
                yaziyiCevir(ezberTr[randomKelime]);

            }
        }else{
            Toast.makeText(this,"Başarısız",Toast.LENGTH_SHORT).show();
        }
    }




    private void yaziyiCevir(String kelime){
        String text = kelime;
        tts.speak(text,tts.QUEUE_FLUSH,null);


    }

    public void kelimeOku(View view){
        yaziyiCevir(ezberTr[randomKelime]);
    }


    private void customButton(int HangiKelime) {

        System.out.println("Hangi Kelime " + HangiKelime);
        yaziyiCevir(ezberTr[HangiKelime]);
        ll = (LinearLayout)findViewById(R.id.btn_layout);







    //int kelimeUzun = ezberTr[randomKelime].length();
       kelimeUzun = ezberTr[HangiKelime].length();
        parcala = new String[kelimeUzun];
        for(int i=0; i<kelimeUzun; i++){
            parcala[i] =  ezberTr[HangiKelime].substring(i,i+1);
        }
   //     System.out.println(kelimeUzun);

        for(int i=0;i<kelimeUzun;i++) {
            System.out.println(parcala[i]);
        }

        // RANDOM ŞIKLAR
        int random = new Random().nextInt((kelimeUzun) );
        int[] harfKaristir = new int[kelimeUzun];
        harfKaristir[0] = random;
        for (int i=0; i<kelimeUzun; i++){
            for (int z=0; z<i; z++){
                while(1==1){
                    if(harfKaristir[z] != random){
                        harfKaristir[i] = random;
                        break;
                    }else{
                        random = new Random().nextInt((kelimeUzun) );
                        z=0;
                    }
                }
            }
        }


        // RANDOM ŞIKLAR BİTİŞ


        LinearLayout btnLayout = findViewById(R.id.btn_layout);
        btnLayout.setOrientation(LinearLayout.VERTICAL);


             int kacinciSatir = 0;
         LinearLayout row = new LinearLayout(this);
         row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

             for (int i = 0; i < kelimeUzun; i++) {

                 myButton = new Button(this);
                 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                 params.setMargins(0,0,40,40);

                 myButton.setLayoutParams(params);
                // myButton.setBackgroundResource(R.drawable.seviye_sec);
                 myButton.setTextColor(Color.BLACK);
                 myButton.setTypeface(null, Typeface.BOLD);
                 myButton.setTextSize(20);
                 myButton.setBackgroundResource(R.drawable.seviye_sec);
                 myButton.setId(i);
                 myButton.setOnClickListener(this);
                 myButton.setText(parcala[harfKaristir[i]]);
                 row.addView(myButton,params);
                 myButton.setWidth(130);
                 myButton.setHeight(130);

                 int myButtonId = myButton.getId();
                 myButton = findViewById(myButtonId);



                 if(kacinciSatir < 4){
                     kacinciSatir++;
                 }else{
                     btnLayout.addView(row);
                     row = new LinearLayout(this);
                     row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                     kacinciSatir = 0;
                 }
             }
        btnLayout.addView(row);






/*
        for(int i=0;i<6;i++) {



            myButton = new Button(this);
            myButton.setId(i);
            myButton.setBackgroundResource(R.drawable.seviye_sec);
            myButton.setTextColor(Color.BLACK);
            myButton.setTypeface(null, Typeface.BOLD);
            myButton.setTextSize(20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            myButton.setLayoutParams(params);
            row.addView(myButton);



            final int myButtonId = myButton.getId();
            myButton.setText(parcala[harfKaristir[i]]);

            //       btnLayout.addView(myButton,120,120);
            if(i<6){
                btnLayout.addView(row);

            }else if(i<12){
                btnLayout.addView(row);
            }else if(i<18){
                btnLayout.addView(row);
            }else{
                btnLayout.addView(row);
            }

            myButton = ((Button) findViewById(myButtonId));
            myButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    // selectTitle=myButtonId;
                    // Toast.makeText(getApplicationContext(),"Seçilen: "+selectTitle,Toast.LENGTH_LONG).show();
                    myButton = findViewById(myButtonId);
                    txtKelime = findViewById(R.id.txtKelime);



                    System.out.println(myButton.getText() + " " + parcala[tiklamaSira]);
                    //    System.out.println(kelimeUzun);

                    if(parcala[tiklamaSira].equals(myButton.getText())){
                        kelime = kelime + " " + myButton.getText().toString();
                        txtKelime.setText(kelime);
                        myButton.setClickable(false);
                        myButton.setBackgroundResource(R.drawable.dogru_buton);
                        myButton.setTextColor(Color.WHITE);
                        myButton.setTypeface(null, Typeface.BOLD);
                        myButton.setTextSize(20);


                        if(tiklamaSira+1 == kelimeUzun){
                            tiklamaSira=0;
                            kelime="";
                            zamanla();

                        }else{
                            tiklamaSira++;
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Hatalı Seçim Yaptınız!",Toast.LENGTH_SHORT).show();
                        myButton.setBackgroundResource(R.drawable.yanlis_buton);
                        myButton.setTextColor(Color.WHITE);
                        myButton.setTextSize(20);
                        butonDuzelt();
                        hataYaptim(ezberTr[randomKelime]);

                    }



                }
            });
        }
*/






    }



    public void zamanla(){
        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {



                if(randomKelime != ezberEn.length-1){


                    int count = ll.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View child = ll.getChildAt(i);
                        if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                    }

                    randomKelime++;
                    kelimeSor(randomKelime);
                    txtKelime.setText("");
                    ll.removeAllViews();
                    customButton(randomKelime);


                }else{
                    Intent enSayfa = new Intent(YazmaTesti.this,DinlemeTesti.class);
                    startActivity(enSayfa);
                }

            }
        }.start();
    }


    public void butonDuzelt(){
        new CountDownTimer(400,400){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                myButton.setBackgroundResource(R.drawable.seviye_sec);
                myButton.setTextColor(Color.BLACK);
            }
        }.start();
    }



    public void hataYaptim(String kelime){
        System.out.println(kelime);
        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            String guncelleSrgu = "UPDATE kelimeler SET S3 = 1 WHERE kelimeing = '" + kelime + "'";
            System.out.println(guncelleSrgu);
            database.execSQL(guncelleSrgu);

        }catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
    }


    @Override
    public void onClick(View v) {
                myButton = findViewById(v.getId());
               txtKelime = findViewById(R.id.txtKelime);

        if(parcala[tiklamaSira].equals(myButton.getText())){
            kelime = kelime + " " + myButton.getText().toString();
            txtKelime.setText(kelime);
            myButton.setClickable(false);
            myButton.setBackgroundResource(R.drawable.dogru_buton);
            myButton.setTextColor(Color.WHITE);
            myButton.setTypeface(null, Typeface.BOLD);
            myButton.setTextSize(20);


            if(tiklamaSira+1 == kelimeUzun){
                tiklamaSira=0;
                LinearLayout ll = (LinearLayout) findViewById(R.id.btn_layout);
                kelime="";
                zamanla();

            }else{
                tiklamaSira++;
            }

        }else{
            Toast.makeText(getApplicationContext(),"Hatalı Seçim Yaptınız!",Toast.LENGTH_SHORT).show();
            myButton.setBackgroundResource(R.drawable.yanlis_buton);
            myButton.setTextColor(Color.WHITE);
            myButton.setTextSize(20);
            butonDuzelt();
            hataYaptim(ezberTr[randomKelime]);

        }


    }
    }

