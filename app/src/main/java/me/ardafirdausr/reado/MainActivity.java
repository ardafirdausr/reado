package me.ardafirdausr.reado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnGetWords, btnRunQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetWords = (Button) findViewById(R.id.btnGetWords);
        btnRunQuiz = (Button) findViewById(R.id.btnRunQuiz);

        btnGetWords.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TextScannerActivity.class));
            }
        });
    }
}
