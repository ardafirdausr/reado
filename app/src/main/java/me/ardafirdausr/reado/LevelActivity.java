package me.ardafirdausr.reado;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import me.ardafirdausr.reado.adapter.LevelAdapter;
import me.ardafirdausr.reado.database.QuizzesHandler;
import me.ardafirdausr.reado.model.Quiz;

public class LevelActivity extends AppCompatActivity {

    ArrayList<Quiz> quizzes;
    RecyclerView rvLevel;
    TextView txtChooseLevel;
    int stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stage = extras.getInt("stage");
        }

        Typeface typeface =  Typeface.createFromAsset(getAssets(), "font/FredokaOneRegular.ttf");

        txtChooseLevel = (TextView) findViewById(R.id.txtChooseLevel);
        txtChooseLevel.setTypeface(typeface);

        QuizzesHandler quizzesHandler = new QuizzesHandler(this);
        quizzes = quizzesHandler.getQuizzesByStage(stage);
        rvLevel = (RecyclerView) findViewById(R.id.rvLevel);
        rvLevel.setAdapter(new LevelAdapter(this, stage, quizzes));
        rvLevel.setLayoutManager(new GridLayoutManager(this, 5));
    }


    @Override
    protected void onStart() {
        super.onStart();
        // ALWAYS REDRAW RECYCLE VIEW WHEN ACTIVITY STARTED, BCZ IDK HOW TO REDRAW RV
        rvLevel.setAdapter(new LevelAdapter(this, stage, quizzes));
        rvLevel.setLayoutManager(new GridLayoutManager(this, 5));
    }
}
