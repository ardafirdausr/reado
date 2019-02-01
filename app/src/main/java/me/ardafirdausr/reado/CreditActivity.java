package me.ardafirdausr.reado;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CreditActivity extends AppCompatActivity {

    TextView txtCredits, txtYoutube, txtFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        txtCredits = (TextView) findViewById(R.id.txtCredits);
        txtYoutube = (TextView) findViewById(R.id.txtYoutube);
        txtFlat = (TextView) findViewById(R.id.txtFlat);

        txtCredits.setTypeface(typeface);
        txtYoutube.setTypeface(typeface);
        txtFlat.setTypeface(typeface);
    }
}
