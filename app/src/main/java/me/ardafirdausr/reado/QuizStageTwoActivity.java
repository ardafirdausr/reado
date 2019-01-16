package me.ardafirdausr.reado;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import me.ardafirdausr.reado.database.QuizzesHandler;
import me.ardafirdausr.reado.database.StagesHandler;
import me.ardafirdausr.reado.model.Quiz;
import me.ardafirdausr.reado.util.AssetReader;

public class QuizStageTwoActivity extends AppCompatActivity {

    private int counter, maxPresCounter;
    private Animation smallbigforth, smalltobig;
    private TextView textScreen, textQuestion, textTitle;
    private Button btnSound;
    private LinearLayout layoutParent;
    private EditText editText;
    private StagesHandler stagesHandler;
    private QuizzesHandler quizzesHandler;
    private Quiz quiz;
    private Typeface typeface;
    private TextToSpeech tts;
    private SharedPreferences mSharedPreferences;
    private String sharedPrefFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stage_two);

        tts = new TextToSpeech(QuizStageTwoActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

        sharedPrefFile = getPackageName();
        mSharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        quizzesHandler = new QuizzesHandler(this);
        stagesHandler = new StagesHandler(this);

        // GET DATA FROM INTENT
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int stage = bundle.getInt("stage");
            int level = bundle.getInt("level");
            quiz = quizzesHandler.getQuiz(stage, level);
        }

        // CREATE ANIMATION
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        // GET ASSETS
        typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        // BINDING VIEW TO OBJECT
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView) findViewById(R.id.textTitle);
        btnSound = (Button) findViewById(R.id.btnSound);
        layoutParent = (LinearLayout) findViewById(R.id.layoutParent);
        editText = (EditText) (EditText) findViewById(R.id.editText);

        textScreen.setText("Level " + quiz.getStage() + " - " + quiz.getLevel());

        // SET TYPEFACE
        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);

        // DEFINES ACTIVITY's FLOW
        maxPresCounter = quiz.getQuestion().length();
        quiz.setQuestion(shuffleString(quiz.getQuestion().toCharArray()));
        for (char key : quiz.getQuestion().toCharArray()){
            addView(layoutParent, key, editText);
        }
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(quiz.getAnswere(), TextToSpeech.QUEUE_FLUSH, null, "reado!-" + quiz.getAnswere());
            }
        });
    }

    private void addView(final LinearLayout viewParent, final char text, final EditText editText){
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.rightMargin = 30;
        final TextView textView = new TextView(this);
        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(getDrawable(R.drawable.bg_rounded_corner1));
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);
        textView.setText("" + text);
        textView.setWidth(96);
        textView.setHeight(96);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(24);
        textView.setTypeface(typeface);
        textView.startAnimation(smalltobig);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(viewParent);
                editText.setText(editText.getText().toString() + text);
                textView.setVisibility(View.GONE);
                counter++;
                doValidate();
            }
        });
        viewParent.addView(textView);

    }

    // VALIDATE THE ANSWERE, WHETHER IT IS TRUE OR FALSE
    private void doValidate(){
        if(editText.getText().toString().equals(quiz.getAnswere())) {

            final int maxStage = stagesHandler.getStagesCount();
            final int maxLevel = quizzesHandler.getQuizzesCountByStage(quiz.getStage());

            // UPDATE SHARED PREFERENCES
            int currentStage, currentLevel;
            currentStage = mSharedPreferences.getInt("currentStage", 1);
            currentLevel = mSharedPreferences.getInt("currentLevel", 1);
            SharedPreferences.Editor sharedPreferencesEditor = mSharedPreferences.edit();
            if(quiz.getStage() == currentStage && quiz.getLevel() == currentLevel){
                if (currentLevel < maxLevel){
                    sharedPreferencesEditor.putInt("currentLevel", quiz.getLevel() + 1);
                }
                else if(currentLevel == maxLevel){
                    if(currentStage < maxStage){
                        sharedPreferencesEditor.putInt("currentStage", currentStage + 1);
                    }
                    sharedPreferencesEditor.putInt("currentLevel", 1);
                }
            }

            sharedPreferencesEditor.apply();

            // Remove linear
            layoutParent.animate()
                    .alpha(0)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            layoutParent.setVisibility(View.GONE);
                        }
                    });
            // GO TO NEXT QUESTION
            String ho = mSharedPreferences.getInt("currentStage", 99) + "-" + mSharedPreferences.getInt("currentLevel", 99);
            Log.d("WWWWW", this.getLocalClassName() + " == "+ ho);
            Snackbar snackbar = Snackbar
                    .make(layoutParent, "Correct!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("NEXT", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int maxLevel = quizzesHandler.getQuizzesCountByStage(quiz.getStage());
                            if(quiz.getLevel() == maxLevel){
                                Intent toNextStage = new Intent(QuizStageTwoActivity.this, StageActivity.class);
                                toNextStage.putExtra("stage", quiz.getStage() + 1);
                                toNextStage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(toNextStage);
                                finish();
                            }
                            else{
                                Intent toNextQuiz = new Intent(QuizStageTwoActivity.this, QuizStageTwoActivity.class);
                                toNextQuiz.putExtra("stage", quiz.getStage());
                                toNextQuiz.putExtra("level", quiz.getLevel() + 1);
                                startActivity(toNextQuiz);
                                finish();
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            snackbar.show();
        }
        else if(maxPresCounter <= counter && !editText.getText().toString().equals(quiz.getAnswere())){
            // RETRY SNACKBAR
            Snackbar snackbar = Snackbar
                    .make(layoutParent, "Whoops, wrong!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editText.setText("");
                            counter = 0;
                            layoutParent.removeAllViews();
                            quiz.setQuestion(shuffleString(quiz.getQuestion().toCharArray()));
                            for (char key : quiz.getQuestion().toCharArray()){
                                addView(layoutParent, key, editText);
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    // SHUFFLE STRING
    private String shuffleString(char[] charArr){
        Random rnd = new Random();
        for (int i = charArr.length - 1; i > 0; i-- ){
            int index = rnd.nextInt(i+1);
            char a = charArr[index];
            charArr[index] = charArr[i];
            charArr[i] = a;
        }
        return new String(charArr);
    }
}
