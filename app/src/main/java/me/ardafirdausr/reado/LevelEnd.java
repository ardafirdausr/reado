package me.ardafirdausr.reado;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelEnd extends AppCompatActivity {

    private Button btnHome;
    private TextView textScreen;
    private Animation smalltobig;
    private ImageView imgLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_end);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        textScreen = (TextView) findViewById(R.id.textScreen);
        btnHome = (Button) findViewById(R.id.btnHome);
        imgLevel = (ImageView) findViewById(R.id.imgLevel);
        imgLevel.startAnimation(smalltobig);

        textScreen.setTypeface(typeface);
        btnHome.setTypeface(typeface);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelEnd.this, MainActivity.class));
                finish();
            }
        });
    }
}
