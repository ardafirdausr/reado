package me.ardafirdausr.reado.Adapter;

import android.content.Context;
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
import java.util.List;
import java.util.Locale;

import me.ardafirdausr.reado.R;

public class AlphabetAdapter extends  RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder> {

    Context context;
    ArrayList<Character> alphabets;
    String[] alphabetPronounce;
    String[] numericPronounce;
    TextToSpeech tts;

    public AlphabetAdapter() { super(); }

    public AlphabetAdapter(ArrayList<Character> alphabets) {
        super();
        this.alphabets = alphabets;
    }

    @NonNull
    @Override
    public AlphabetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        alphabetPronounce = context.getResources().getStringArray(R.array.alphabets);
        numericPronounce = context.getResources().getStringArray(R.array.numerics);
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_alphabet_card, parent, false);
        itemView.getLayoutParams().width = (int) (parent.getMeasuredHeight() * 1);
        itemView.requestLayout();
        return new AlphabetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlphabetViewHolder alphabetViewHolder, int i) {
        final char alphabet = alphabets.get(i);
        String tempPronounce = "" + alphabets.get(i);
        if((int)alphabet >= 65 && (int)alphabet <= 90){
            tempPronounce = this.alphabetPronounce[(int)alphabet - 65];
        }
        else if((int)alphabet >= 48 && (int)alphabet <= 57){
            tempPronounce = this.numericPronounce[(int)alphabet - 48];
        }
        final String pronounce = tempPronounce;
        alphabetViewHolder.txtAlphabet.setText("" + alphabet);
        alphabetViewHolder.txtRead.setText(pronounce);
        alphabetViewHolder.btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(pronounce, TextToSpeech.QUEUE_FLUSH, null, "reado!-" + alphabet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alphabets.size();
    }

    class AlphabetViewHolder extends RecyclerView.ViewHolder{

        public TextView txtAlphabet, txtRead;
        public Button btnSound;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAlphabet = itemView.findViewById(R.id.txtAlphabet);
            txtRead = itemView.findViewById(R.id.txtRead);
            btnSound = itemView.findViewById(R.id.btnSound);
        }
    }
}
