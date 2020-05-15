package com.mklcbilisim.kelimeezberle;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mklcbilisim.kelimeezberle.egzersiz.KonusmaEgzersiz;

import java.util.List;



public class KelimeAdaptor extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<Kelime> kelimes;
    Activity activity;
    public KelimeAdaptor(Activity activity, List<Kelime> mList){
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        kelimes = mList;
        this.activity = activity;
    }





    @Override
    public int getCount() {
        return kelimes.size();
    }

    @Override
    public Object getItem(int position) {
        return kelimes.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View satirView;
        satirView = layoutInflater.inflate(R.layout.kelime,null);
        Button button = satirView.findViewById(R.id.btnTekrar);
        TextView txtTurkce = satirView.findViewById(R.id.txtTurkce);
        TextView txtIngilizce = satirView.findViewById(R.id.txtInglizce);

        TextView txt9 = satirView.findViewById(R.id.txt1);
        TextView txt6 = satirView.findViewById(R.id.txt2);
        TextView txt3 = satirView.findViewById(R.id.txt3);
        TextView txt4 = satirView.findViewById(R.id.txt4);
        TextView txt7 = satirView.findViewById(R.id.txt5);
        TextView txt8 = satirView.findViewById(R.id.txt6);
        TextView txt5 = satirView.findViewById(R.id.txt7);
        TextView txt2 = satirView.findViewById(R.id.txt8);
        TextView txt1 = satirView.findViewById(R.id.txt9);
        TextView txt10 = satirView.findViewById(R.id.txt10);

        final Kelime kelime = kelimes.get(position);


        txtTurkce.setText(kelime.getTurkce().toUpperCase());
        txtIngilizce.setText(kelime.getIngilizce().toUpperCase());

        //BAŞARI #00E300
        // BAŞARISIZ  #FF002D

        if(kelime.getTren() == 0){
            txt1.setTextColor(Color.parseColor("#00E300"));
        }else{
            txt2.setTextColor(Color.parseColor("#FF002D"));
        }

        if(kelime.getEntr() == 0){
            txt3.setTextColor(Color.parseColor("#00E300"));
        }else{
            txt4.setTextColor(Color.parseColor("#FF002D"));
        }

        if(kelime.getSiralama() == 0){
            txt5.setTextColor(Color.parseColor("#00E300"));
        }else{
            txt6.setTextColor(Color.parseColor("#FF002D"));
        }

        if(kelime.getDinleme() == 0){
            txt7.setTextColor(Color.parseColor("#00E300"));
        }else{
            txt8.setTextColor(Color.parseColor("#FF002D"));
        }

        if(kelime.getKonusma() == 0){
            txt9.setTextColor(Color.parseColor("#00E300"));
        }else{
            txt10.setTextColor(Color.parseColor("#FF002D"));
        }



       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {

                        SQLiteDatabase database = activity.openOrCreateDatabase("Kelimeler",Context.MODE_PRIVATE,null);
                        String guncelleSrgu = "UPDATE kelimeler SET durum=3,S1=0,S2=0,S3=0,S4=0,S5=0 WHERE id = '" + kelime.getId() + "'";
                        System.out.println(guncelleSrgu);
                        database.execSQL(guncelleSrgu);

                        Toast.makeText(activity,"Kelime tekrar öğrenilecek",Toast.LENGTH_SHORT).show();



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

        });


        return satirView;
    }



}
