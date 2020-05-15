package com.mklcbilisim.kelimeezberle.egzersiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mklcbilisim.kelimeezberle.AnaEkran;
import com.mklcbilisim.kelimeezberle.Ayarlar;
import com.mklcbilisim.kelimeezberle.GenelDurum;
import com.mklcbilisim.kelimeezberle.R;
import com.mklcbilisim.kelimeezberle.TestSonuclari;

import java.util.ArrayList;
import java.util.Locale;

public class KonusmaEgzersiz extends AppCompatActivity implements TextToSpeech.OnInitListener{

    Button btnKelime;
    int trIx2,engIx2;
    Cursor cursor,cursor2;
    String[] ezberTr,ezberEn;
    TextToSpeech tts;
    int say,secilenHedef,randomKelime=0;
    TextView txtGelenSes,txtGelenSes2,txtGelenSes3,sayac,btnGec;
    SpeechRecognizer mMikrofon;
    Intent mMikrofonIntent;
    SharedPreferences gunlukHedef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egzersiz_konusma);



        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            cursor2 = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 5  or durum = 4",null);
            secilenHedef = cursor2.getCount();

        }catch (Exception e){

        }

        tts = new TextToSpeech(this,this);
        btnKelime = findViewById(R.id.btnKelime);

        kelimeler();
        kelimeSor(randomKelime);


        izinKontrol();
        txtGelenSes = findViewById(R.id.txtGelenSes);
        txtGelenSes2 = findViewById(R.id.txtGelenSes2);
        txtGelenSes3 = findViewById(R.id.txtGelenSes3);

        mMikrofon = SpeechRecognizer.createSpeechRecognizer(this);
        mMikrofonIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mMikrofonIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mMikrofonIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        mMikrofon.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> eslestir = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(eslestir != null){
                    try{
                        if(btnKelime.getText().equals(eslestir.get(0)) || btnKelime.getText().equals(eslestir.get(1)) || btnKelime.getText().equals(eslestir.get(2))){
                            kelimeSor(randomKelime+1);
                        }else{
                          //  txtGelenSes.setText(eslestir.get(0));
                           // txtGelenSes2.setText(eslestir.get(1));
                           // txtGelenSes3.setText(eslestir.get(2));
                            txtGelenSes.setText("Eşleşme Bulunamadı");
                        }


                    }catch (Exception e){

                    }

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        findViewById(R.id.btnKonus).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){

                    case MotionEvent.ACTION_UP:
                        mMikrofon.stopListening();
                        txtGelenSes.setHint("");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        txtGelenSes.setText("");
                        txtGelenSes.setHint("Dinleniyor...");
                        mMikrofon.startListening(mMikrofonIntent);
                        break;
                }

                return false;
            }
        });


    }


    public void anasayfa(View view){
        Intent anasayfa = new Intent(this,AnaEkran.class);
        startActivity(anasayfa);
    }

    public void ayarlar(View view){
        Intent ayarlar = new Intent(this,Ayarlar.class);
        startActivity(ayarlar);
    }


    protected  void kelimeler(){
        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);

            cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 5  or durum = 4 ORDER BY RANDOM() ",null);
            engIx2 = cursor.getColumnIndex("kelimeing");
            trIx2 = cursor.getColumnIndex("kelimetr");
            ezberEn = new String[cursor.getCount()];
            ezberTr = new String[cursor.getCount()];


            while (cursor.moveToNext()){
                ezberEn[say] = cursor.getString(engIx2);
                ezberTr[say] = cursor.getString(trIx2);

                say = say+1;
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    protected void kelimeSor(int hangiKelime){

        randomKelime = hangiKelime;
        sayac = findViewById(R.id.txtSayac);
        sayac.setText(randomKelime+1 + " / " + ezberTr.length);

        btnKelime.setText(ezberEn[randomKelime]);



    }






    @Override
    public void onInit(int status) {
        if (status == tts.SUCCESS){
            int sonuc = tts.setLanguage(Locale.ENGLISH);
            if(sonuc == tts.LANG_MISSING_DATA || sonuc == tts.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Dil desteklenmiyor",Toast.LENGTH_SHORT).show();

            }else{
             //  yaziyiCevir(ezberEn[randomKelime]);

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
        yaziyiCevir(btnKelime.getText().toString());
    }





    public void zamanla(){
        new CountDownTimer(1000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {



                if(randomKelime != ezberEn.length-1){


                }else{
                    Intent enSayfa = new Intent(KonusmaEgzersiz.this,KonusmaEgzersiz.class);
                    startActivity(enSayfa);
                }

            }
        }.start();
    }



    private void izinKontrol(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},1903);

          /*      Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();*/
            }
        }
    }




    public void  sonrakiKelime(View view){

        if(secilenHedef-1 == randomKelime){

            System.out.println("Kelime bitti");
            Intent testSonuc = new Intent(KonusmaEgzersiz.this,KonusmaEgzersiz.class);
            startActivity(testSonuc);
        }else{

            randomKelime +=1;
            kelimeSor(randomKelime);


        }

    }









    public void btnDurumum(View view){
        Intent intent = new Intent(this,GenelDurum.class);
        startActivity(intent);
    }


/*
    public void basKonus(View view, MotionEvent motionEvent){
// BAS KONUŞ BUTTON CLİCK
        txtGelenSes = findViewById(R.id.txtGelenSes);

        switch (motionEvent.getAction()){

        }
    }
*/
}
