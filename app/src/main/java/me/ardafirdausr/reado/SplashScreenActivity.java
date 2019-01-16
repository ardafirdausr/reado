package me.ardafirdausr.reado;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import me.ardafirdausr.reado.database.DataBaseHelper;
import me.ardafirdausr.reado.util.AssetReader;

public class SplashScreenActivity extends AppCompatActivity {

    Animation smalltobig;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Context context = this;
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);


        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        textView = (TextView) findViewById(R.id.textView);
        textView.setTypeface(typeface);

        imageView = (ImageView) findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataBaseHelper database = new DataBaseHelper (context);
                try {
                    database.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    database.openDataBase();
                }catch(SQLException sqle){
                    throw sqle;
                }
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
