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
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class OgrenmeyeBasla extends AppCompatActivity implements TextToSpeech.OnInitListener{

    Button butonEng,butonTr,btnOgrendim;
    int trIx2,engIx2;
    String turkce;
    TextToSpeech tts;
    String[] ezberTr;
    String[] ezberEn;
    int say=0,say2=0;
    SharedPreferences gunlukHedef;
    int secilenHedef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenmeye_basla);
        gunlukHedef = this.getSharedPreferences("com.mklcbilisim.kelimeezberle", Context.MODE_PRIVATE);
        secilenHedef = gunlukHedef.getInt("hedef",0);
        btnOgrendim = findViewById(R.id.btnOgrendim);
       //btnOgrendim.setVisibility(View.INVISIBLE);





        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM kelimeler WHERE durum = 2 ORDER BY RANDOM()",null);



                butonEng = findViewById(R.id.btnEng);
                butonTr = findViewById(R.id.btnTr);


                engIx2 = cursor.getColumnIndex("kelimeing");
                trIx2 = cursor.getColumnIndex("kelimetr");
                ezberEn = new String[secilenHedef];
                ezberTr = new String[secilenHedef];

                while (cursor.moveToNext()){


                    ezberEn[say] = cursor.getString(engIx2);
                    ezberTr[say] = cursor.getString(trIx2);
                    butonEng.setText(ezberEn[0]);
                    say = say+1;

                }


                cursor.close();






        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void anasayfa(View view){
        Intent anasayfa = new Intent(this,AnaEkran.class);
        startActivity(anasayfa);
    }

    public void ayarlar(View view){
        Intent ayarlar = new Intent(this,Ayarlar.class);
        startActivity(ayarlar);
    }



    public void  butoning(View view){


        tts = new TextToSpeech(this,this);
        yaziyiCevir(butonEng.getText().toString());
        butonTr.setText(ezberTr[say2]);
     //   System.out.println(say2);
    //    btnOgrendim.setVisibility(View.VISIBLE);
    }

    public void butontur(View view){

    }


    @Override
    public void onInit(int status) {
            if (status == tts.SUCCESS){
               int sonuc = tts.setLanguage(Locale.ENGLISH);
                if(sonuc == tts.LANG_MISSING_DATA || sonuc == tts.LANG_NOT_SUPPORTED){
                    Toast.makeText(this,"Dil desteklenmiyor",Toast.LENGTH_SHORT).show();

                }else{
                    yaziyiCevir(butonEng.getText().toString());

                }
            }else{
                Toast.makeText(this,"Başarısız",Toast.LENGTH_SHORT).show();
            }
    }


    private void yaziyiCevir(String kelime){
        String text = kelime;
        tts.speak(text,tts.QUEUE_FLUSH,null);


    }


    public void ogrendim(View view){

      butonTr.setText("*****");
        if(say2 == secilenHedef-1){
            say2 = 0;
            butonEng.setText(ezberEn[0]);
        }else{
            butonEng.setText(ezberEn[say2+1]);
            say2=say2+1;
        }


    }

    public void geri(View view){

    }

    public void ileri(View view){

    }

    public void testeBasla(View view){
        Intent testeBasla = new Intent(OgrenmeyeBasla.this,CoktanSecmeliTr.class);
        startActivity(testeBasla);
    }




    public void btnDurumum(View view){
        Intent intent = new Intent(this,GenelDurum.class);
        startActivity(intent);
    }

}
