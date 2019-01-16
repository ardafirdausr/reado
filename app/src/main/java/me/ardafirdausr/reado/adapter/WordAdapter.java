package me.ardafirdausr.reado.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    Typeface typeface;

    public WordAdapter() { super(); }

    public WordAdapter(ArrayList<String> words) {
        super();
        this.words = words;
    }

    // OVERIDE : CREATE A ITEMVIEW LAYOUT FOR SINGLE ITEM, RETURN SINGLE ITEM LAYOUT VIEW
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_word_card, parent, false);
        itemView.getLayoutParams().width = (int) (parent.getMeasuredHeight() * 1);
        itemView.requestLayout();
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        return new WordViewHolder(itemView);
    }

    // OVERRIDE : BIND VALUES OR OTHER CALLBACK TO ITEMVIEW LAYOUT
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder viewHolder, int i) {
        final String word = words.get(i);
        viewHolder.word.setText(word);

        viewHolder.word.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toAlphabetActivity = new Intent(context, AlphabetActivity.class);
                toAlphabetActivity.putExtra("word", word.trim());
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

    // OVERRIDE : GET LENGTH OF DATA THAT WILL IMPLEMENTED IN RECYCLEVIEW
    @Override
    public int getItemCount() {
        return words.size();
    }

    // INNER CLASS THAT REPRESENT FOR ITEMVIEW LAYOUT
    class WordViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout itemWord;
        public TextView word;
        public Button btnSound;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            typeface =  Typeface.createFromAsset(context.getAssets(), "font/FredokaOneRegular.ttf");

            this.itemWord =  (ConstraintLayout) itemView.findViewById(R.id.itemWord);
            this.word = (TextView) itemView.findViewById(R.id.txtWord);
            this.btnSound = (Button) itemView.findViewById(R.id.btnSound);

            this.word.setTypeface(typeface);
        }
    }
}
