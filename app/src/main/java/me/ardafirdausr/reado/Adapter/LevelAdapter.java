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
import android.widget.Toast;

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
        return new LevelAdapter.LevelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder viewHolder, int i) {
        final String level = levels.get(i);
        viewHolder.txtLevel.setText(level);
        viewHolder.txtLevel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + level, Toast.LENGTH_SHORT).show();
//                Intent toLevel1Activity = new Intent(context, Level1Activity.class);
//                context.startActivity(toLevel1Activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {

        public TextView txtLevel;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);
        }
    }
}
