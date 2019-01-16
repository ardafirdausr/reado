package me.ardafirdausr.reado.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.ardafirdausr.reado.MainActivity;
import me.ardafirdausr.reado.QuizStageOneActivity;
import me.ardafirdausr.reado.QuizStageThreeActivity;
import me.ardafirdausr.reado.QuizStageTwoActivity;
import me.ardafirdausr.reado.R;
import me.ardafirdausr.reado.StageActivity;
import me.ardafirdausr.reado.model.Quiz;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    Context context;
    int stage, currentStage, currentLevel;
    ArrayList<Quiz> quizzes;
    SharedPreferences mSharedPrefernces;
    SharedPreferences mPreferences;
    String sharedPrefFile;

    public LevelAdapter() { super(); }

    public LevelAdapter(Context context, int stage, ArrayList<Quiz> levels) {
        super();
        this.context = context;
        this.stage = stage;
        this.quizzes = levels;
        sharedPrefFile = context.getPackageName();
        mSharedPrefernces = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        currentStage = mSharedPrefernces.getInt("currentStage", 1);
        currentLevel = mSharedPrefernces.getInt("currentLevel", 1);
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
        Typeface typeface =  Typeface.createFromAsset(context.getAssets(), "font/FredokaOneRegular.ttf");

        final Quiz quiz = quizzes.get(i);
        viewHolder.txtLevel.setText("" + quiz.getLevel());
        viewHolder.txtLevel.setTypeface(typeface);

        final Intent toQuizActivity;
        switch (quiz.getStage()){
            case 1:
                toQuizActivity = new Intent(context, QuizStageOneActivity.class);
                break;
            case 2:
                toQuizActivity = new Intent(context, QuizStageTwoActivity.class);
                break;
            case 3:
                toQuizActivity = new Intent(context, QuizStageThreeActivity.class);
                break;
            default:
                toQuizActivity = new Intent(context, StageActivity.class);
                break;
        }
        // serializeable object
        // toQuizActivity.putExtra("quiz", quiz);
        toQuizActivity.putExtra("stage", quiz.getStage());
        toQuizActivity.putExtra("level", quiz.getLevel());
        if(quiz.getStage() <= currentStage){
            if(quiz.getStage() < currentStage){
                viewHolder.txtLevel.setTextColor(Color.GREEN);
                viewHolder.vLock.setVisibility(View.GONE);
                viewHolder.imgLock.setVisibility(View.GONE);
                viewHolder.txtLevel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        context.startActivity(toQuizActivity);
                    }
                });
            }
            else if(quiz.getStage() == currentStage && quiz.getLevel() <= currentLevel){
                if(quiz.getLevel() < currentLevel){
                    viewHolder.txtLevel.setTextColor(Color.GREEN);
                }
                viewHolder.vLock.setVisibility(View.GONE);
                viewHolder.imgLock.setVisibility(View.GONE);
                viewHolder.txtLevel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        context.startActivity(toQuizActivity);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {

        public TextView txtLevel;
        public View vLock;
        public ImageView imgLock;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);
            this.vLock = (View) itemView.findViewById(R.id.vLock);
            this.imgLock = (ImageView) itemView.findViewById(R.id.imgLock);
        }
    }
}
