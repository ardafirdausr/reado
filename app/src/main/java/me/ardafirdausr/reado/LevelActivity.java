package me.ardafirdausr.reado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import me.ardafirdausr.reado.Adapter.LevelAdapter;

public class LevelActivity extends AppCompatActivity {

    ArrayList<String> levels;
    RecyclerView rvLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        levels = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        rvLevel = (RecyclerView) findViewById(R.id.rvLevel);
        rvLevel.setAdapter(new LevelAdapter(levels));
        rvLevel.setLayoutManager(new GridLayoutManager(this, 5));
    }
}
