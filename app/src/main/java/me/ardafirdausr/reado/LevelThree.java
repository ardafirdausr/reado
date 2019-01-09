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

public class LevelThree extends AppCompatActivity {

    private Button btnReady;
    private TextView textScreen, textQuestion, btnBackHome;
    private Animation smalltobig;
    private ImageView imgLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_three);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        textScreen = (TextView) findViewById(R.id.textScreen);
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        btnBackHome = (TextView) findViewById(R.id.btnBackHome);
        btnReady = (Button) findViewById(R.id.btnReady);
        imgLevel = (ImageView) findViewById(R.id.imgLevel);
        imgLevel.startAnimation(smalltobig);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        btnBackHome.setTypeface(typeface);
        btnReady.setTypeface(typeface);

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishFromChild(LevelThree.this);
            }
        });

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelThree.this, Level3Activity.class));
            }
        });
    }
}
