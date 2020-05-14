package com.mklcbilisim.kelimeezberle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class Hakkimizda extends AppCompatActivity {


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkimizda);
        textView = findViewById(R.id.hakkimizda);

        textView.setText(Html.fromHtml( "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur." +
                ""+"<br><br>"+"Malesuada fames ac turpis egestas integer eget. Imperdiet sed euismod nisi porta. " +
                "Purus in mollis nunc sed id semper. At volutpat diam ut venenatis. " +
                "Interdum posuere lorem ipsum dolor sit amet consectetur adipiscing. " +
                "Urna et pharetra pharetra massa massa ultricies. " +
                "Et tortor at risus viverra adipiscing." ));



    }


    public void btnAnasayfa(View view){
        Intent intent = new Intent(this,AnaEkran.class);
        finish();
        startActivity(intent);
    }


    public void btnAyarclick(View view){
        Intent intent = new Intent(this,Ayarlar.class);
        finish();
        startActivity(intent);
    }

    public void btnDurumum(View view){
        Intent intent = new Intent(this,GenelDurum.class);
        finish();
        startActivity(intent);
    }

}
