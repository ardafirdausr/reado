package me.ardafirdausr.reado;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    Button btnAbout, btnDeveloped, btnCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnDeveloped = (Button) findViewById(R.id.btnDeveloped);
        btnCredits = (Button) findViewById(R.id.btnCredits);

        btnAbout.setTypeface(typeface);
        btnDeveloped.setTypeface(typeface);
        btnCredits.setTypeface(typeface);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, AppActivity.class));
            }
        });

        btnDeveloped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, DeveloperActivity.class));
            }
        });

        btnCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, CreditActivity.class));
            }
        });
    }
}
