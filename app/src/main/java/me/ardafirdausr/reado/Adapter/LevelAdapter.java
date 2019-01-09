package me.ardafirdausr.reado.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import me.ardafirdausr.reado.Level1Activity;
import me.ardafirdausr.reado.Level2Activity;
import me.ardafirdausr.reado.R;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    Context context;
    ArrayList<String> levels;

    public LevelAdapter() { super(); }

    public LevelAdapter(ArrayList<String> levels) {
        super();
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_level_card, parent, false);
        itemView.getLayoutParams().width = (int) (parent.getMeasuredHeight() * 1);
        itemView.requestLayout();
        return new LevelAdapter.LevelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder viewHolder, int i) {
        final String word = levels.get(i);
        viewHolder.level.setText(word);
        viewHolder.level.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toLevel1Activity = new Intent(context, Level1Activity.class);
                context.startActivity(toLevel1Activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        public TextView level;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            this.level = (TextView) itemView.findViewById(R.id.txtLevel);
        }
    }
}
