package me.ardafirdausr.reado.Adapter;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import me.ardafirdausr.reado.AlphabetActivity;
import me.ardafirdausr.reado.R;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    Context context;
    ArrayList<String> words;
    TextToSpeech tts;

    public WordAdapter() { super(); }

    public WordAdapter(ArrayList<String> words) {
        super();
        this.words = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_word_card, parent, false);
        itemView.getLayoutParams().width = (int) (parent.getMeasuredHeight() * 1);
        itemView.requestLayout();
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder viewHolder, int i) {
        final String word = words.get(i);
        viewHolder.word.setText(word);

        viewHolder.word.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toAlphabetActivity = new Intent(context, AlphabetActivity.class);
                toAlphabetActivity.putExtra("word", word.trim());
                Log.d("WWWWWW", "FROM WORDADAPTER-" + word + "-");
                context.startActivity(toAlphabetActivity);
            }
        });

        viewHolder.btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, "reado!-" + word);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder{

        public TextView word;
        public Button btnSound;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            this.word = (TextView) itemView.findViewById(R.id.txtWord);
            this.btnSound = (Button) itemView.findViewById(R.id.btnSound);
        }
    }
}