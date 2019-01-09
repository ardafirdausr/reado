package me.ardafirdausr.reado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import me.ardafirdausr.reado.Adapter.LevelAdapter;

public class FunQuizActivity extends AppCompatActivity {

    ArrayList<String> levels;
    RecyclerView rvLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_quiz);

        levels = new ArrayList<>(Arrays.asList("Level 1", "Level 2", "Level 3", "Level 4"));
        rvLevels = (RecyclerView) findViewById(R.id.rv_level);
        rvLevels.setAdapter(new LevelAdapter(levels));
        rvLevels.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
