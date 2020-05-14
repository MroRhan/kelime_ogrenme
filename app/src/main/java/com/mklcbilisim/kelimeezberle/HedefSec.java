package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class HedefSec extends AppCompatActivity {
    TextView txtNereden;
    EditText txtGelenHedef;
    SharedPreferences gunlukHedef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent ayarlarHedef = getIntent();

        gunlukHedef = this.getSharedPreferences("com.mklcbilisim.kelimeezberle",Context.MODE_PRIVATE);


        int secilenHedef;
        System.out.println("Ayarlar Gelen " +  ayarlarHedef.getStringExtra("ayarlarHedef"));
        if(ayarlarHedef.getStringExtra("ayarlarHedef") == null){
            secilenHedef = gunlukHedef.getInt("hedef",0);
        }else{

            secilenHedef = 0;

            try {

                SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
                String guncelleSrgu = "UPDATE kelimeler SET durum=1 WHERE durum = 2";
                database.execSQL(guncelleSrgu);

            }catch (Exception e){
                e.printStackTrace();
            }

        }



        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_hedef_sec);

        if(secilenHedef > 0){

            Intent intent = new Intent(getApplicationContext(),AnaEkran.class);
            intent.putExtra("hedef",secilenHedef);
            finish();
            startActivity(intent);
        }else{

            setContentView(R.layout.activity_hedef_sec);

            try {

                SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
                String guncelleSrgu = "UPDATE kelimeler SET durum=4 WHERE durum = 2";
                database.execSQL(guncelleSrgu);

            }catch (Exception e){
                e.printStackTrace();
            }

        }





    }

    public void kaydet(View view){
        Intent intent = new Intent(getApplicationContext(),AnaEkran.class);
        txtGelenHedef = findViewById(R.id.txtHedef);
        String hedef = txtGelenHedef.getText().toString();


        SQLiteDatabase database = this.openOrCreateDatabase("Kelimeler",MODE_PRIVATE,null);
        Cursor cursor2 = database.rawQuery("SELECT id FROM kelimeler WHERE durum = 1",null);
        if(Integer.parseInt(hedef) <= cursor2.getCount() ) {

            if (!hedef.trim().equals("")) {

                if (Integer.parseInt(hedef) > 4) {


                    intent.putExtra("hedef", Integer.parseInt(hedef));
                    gunlukHedef.edit().putInt("hedef", Integer.parseInt(hedef)).apply();


                    try {


                        String sqlSorgu = "SELECT * FROM kelimeler WHERE durum = 1 OR durum = 3 ORDER BY RANDOM() LIMIT " + hedef;

                        Cursor cursor = database.rawQuery(sqlSorgu, null);
                        int idIx = cursor.getColumnIndex("id");

                        while (cursor.moveToNext()) {
                            String guncelleSrgu = "UPDATE kelimeler SET durum = 2 WHERE id = " + cursor.getInt(idIx);
                            database.execSQL(guncelleSrgu);
                        }

                        cursor.close();
                        finish();
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "En Az 5 kelime!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "En Az 5 kelime!", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Kelime limitini aştınınız.", Toast.LENGTH_SHORT).show();
        }





    }


}


