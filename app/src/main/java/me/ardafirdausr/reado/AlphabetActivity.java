package me.ardafirdausr.reado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.ardafirdausr.reado.adapter.AlphabetAdapter;

public class AlphabetActivity extends AppCompatActivity {

    RecyclerView rvAlphabet;
    String word;
    ArrayList<Character> alphabets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        alphabets = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            word = extras.getString("word");
            for(char alphabet : word.toCharArray()) {
                alphabets.add(alphabet);
            }
        }
        rvAlphabet = (RecyclerView) findViewById(R.id.rv_alphabets);
        rvAlphabet.setAdapter(new AlphabetAdapter(alphabets));
        rvAlphabet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
