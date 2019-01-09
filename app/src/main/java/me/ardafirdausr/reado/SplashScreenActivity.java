package me.ardafirdausr.reado;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Animation smalltobig;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        textView = (TextView) findViewById(R.id.textView);
        textView.setTypeface(typeface);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.startAnimation(smalltobig);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
