package me.ardafirdausr.reado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import me.ardafirdausr.reado.adapter.WordAdapter;

public class WordActivity extends AppCompatActivity {

    String sentence;
    ArrayList<String> words;
    RecyclerView rvWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sentence = extras.getString("sentence").toUpperCase();
            words = new ArrayList<>(Arrays.asList(sentence.split("\\s+")));
        }
        rvWords = (RecyclerView) findViewById(R.id.rv_words);
        rvWords.setAdapter(new WordAdapter(words));
        rvWords.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

}
