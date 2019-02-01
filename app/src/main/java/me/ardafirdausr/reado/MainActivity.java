package me.ardafirdausr.reado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnGetWords, btnRunQuiz, btnVideoPlay;
    ImageView imgView2;
    Animation smalltobig;
    private SharedPreferences mSharedPreferences;
    private String sharedPrefFile;
    private int creatCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        sharedPrefFile = getPackageName();
        mSharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        btnGetWords = (Button) findViewById(R.id.btnGetWords);
        btnRunQuiz = (Button) findViewById(R.id.btnRunQuiz);
        imgView2 = (ImageView) findViewById(R.id.imageView2);
        imgView2.startAnimation(smalltobig);

        btnGetWords.setTypeface(typeface);
        btnRunQuiz.setTypeface(typeface);

        btnGetWords.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TextScannerActivity.class));
            }
        });

        btnRunQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), StageActivity.class));
            }
        });

        imgView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                creatCounter++;
                if(creatCounter == 10){
                    creatCounter = 0;
                    SharedPreferences.Editor sharedPreferencesEditor = mSharedPreferences.edit();
                    sharedPreferencesEditor.putInt("currentStage", 3);
                    sharedPreferencesEditor.putInt("currentLevel", 8);
                    sharedPreferencesEditor.apply();
                }
            }
        });
    }
}
