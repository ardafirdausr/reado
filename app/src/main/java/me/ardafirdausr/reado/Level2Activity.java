package me.ardafirdausr.reado;

import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Level2Activity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 4;
    private String[] keys = {"I","O","D","L","N"};
    private String textAnswer = "LION";
    TextView textScreen, textQuestion;
    private Animation smallbigforth, smalltobig;
    private Button btnSound;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);
        for (String key : keys){
            addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));
        }

        maxPresCounter = 4;

    }

    private String[] shuffleArray(String[] ar){
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i-- ){
            int index = rnd.nextInt(i+1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText){
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bg_rounded_corner1));
        textView.setTextColor(this.getResources().getColor(R.color.colorText));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setWidth(80);
        textView.setHeight(80);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textScreen = (TextView) findViewById(R.id.textScreen);
        btnSound = (Button) findViewById(R.id.btnSound);
        btnSound.startAnimation(smalltobig);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        final String word = textAnswer;
        tts = new TextToSpeech(Level2Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, "reado!-" + word);
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presCounter < maxPresCounter){
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });

        viewParent.addView(textView);

    }

    private void doValidate(){
        presCounter = 0;
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);

        if (editText.getText().toString().equals(textAnswer)){
            Toast.makeText(Level2Activity.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Level2Activity.this, LevelThree.class);
            startActivity(intent);
            editText.setText("");
        }else{
            Toast.makeText(Level2Activity.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        for (String key : keys){
            addView(linearLayout, key, editText);
        }
    }
}
