package me.ardafirdausr.reado;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Locale;
import java.util.Random;

public class Level3Activity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 6;
    private String[] keys = {"A","R","C","R","O","T"};
    private String textAnswer = "CARROT";
    TextView textScreen, textQuestion;
    private Animation smallbigforth, smalltobig;
    private TextToSpeech tts;
    private VideoView videoView;
    MediaController mediaController;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);
        for (String key : keys){
            addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));
        }

        maxPresCounter = 6;
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

        btnPlay = (Button) findViewById(R.id.btnPlay);

        videoView = (VideoView) findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);
        btnPlay.setTypeface(typeface);

        final String word = textAnswer;
        tts = new TextToSpeech(Level3Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlay(videoView);
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
            Toast.makeText(Level3Activity.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Level3Activity.this, LevelEnd.class);
            startActivity(intent);
            editText.setText("");
        }else{
            Toast.makeText(Level3Activity.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        for (String key : keys){
            addView(linearLayout, key, editText);
        }
    }

    private void videoPlay(View v){
        String videoPath = "android.resource://me.ardafirdausr.reado/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();
    }
}
