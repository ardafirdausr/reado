package me.ardafirdausr.reado.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import com.transitionseverywhere.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.transitionseverywhere.extra.Scale;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import me.ardafirdausr.reado.LevelActivity;
import me.ardafirdausr.reado.R;
import me.ardafirdausr.reado.model.Stage;
import me.ardafirdausr.reado.util.AssetReader;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {

    Animation smalltobig, bigtosmall;
    Context context;
    ArrayList<Stage> stages;
    Typeface typeface;
    SharedPreferences mPreferences;
    String sharedPrefFile;
    int currentStage;

    public StageAdapter() {
        super();
    }

    public StageAdapter(Context context, ArrayList<Stage> stages) {
        super();
        this.stages = stages;
        smalltobig = AnimationUtils.loadAnimation(context, R.anim.smalltobig);
        bigtosmall = AnimationUtils.loadAnimation(context, R.anim.bigtosmall);
        sharedPrefFile = context.getPackageName();
        mPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        currentStage = mPreferences.getInt("currentStage", 1);
    }

    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_stage_card, parent, false);
        itemView.getLayoutParams().width = (int) (parent.getMeasuredHeight() * 1);
        itemView.requestLayout();
        return new StageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder stageViewHolder, int i) {
        final Stage stage = stages.get(i);

        stageViewHolder.imgStage.getLayoutParams().height = stageViewHolder.imgStage.getLayoutParams().width;

        Drawable image = AssetReader.getDrawableFromAssets(context, stage.getMedia());
        typeface = Typeface.createFromAsset(context.getAssets(), "font/FredokaOneRegular.ttf");

        stageViewHolder.imgStage.setImageDrawable(image);
        stageViewHolder.txtTitle.setText(stage.getName());
        stageViewHolder.txtTitle.setTypeface(typeface);
        stageViewHolder.txtDescription.setText(stage.getDescription());
        stageViewHolder.txtDescription.setTypeface(typeface);
        if(stage.getId() == currentStage){
            stageViewHolder.txtDescription.setVisibility(View.VISIBLE);
        }
        if(stage.getId() <= currentStage){
            stageViewHolder.vLock.setVisibility(View.GONE);
            stageViewHolder.imgLock.setVisibility(View.GONE);
            stageViewHolder.itemStage.setOnClickListener(new View.OnClickListener() {
                // TODO : IF STAGE UNLOCKED CANT CLICK THIS & TOAST "UNLOCKED"
                @Override
                public void onClick(View v) {
                    Intent toLevelActivity = new Intent(context, LevelActivity.class);
                    toLevelActivity.putExtra("stage", stage.getId());
                    context.startActivity(toLevelActivity);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stages.size();
    }

    public class StageViewHolder extends RecyclerView.ViewHolder{

        View vLock;
        ConstraintLayout itemStage;
        TextView txtTitle, txtDescription;
        ImageView imgStage, imgLock;
        TransitionSet set;

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemStage = (ConstraintLayout) itemView.findViewById(R.id.itemStage);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgStage = (ImageView) itemView.findViewById(R.id.imgStage);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            vLock = (View) itemView.findViewById(R.id.vLock);
            imgLock = (ImageView) itemView.findViewById(R.id.imgLock);
            set = new TransitionSet()
                    .setDuration(250)
                    .addTransition(new Scale(0.5f))
                    .addTransition(new Fade())
                    .setInterpolator(new FastOutLinearInInterpolator());
        }

        public void showText(){
            TransitionManager.beginDelayedTransition(itemStage, set);
            txtDescription.setVisibility(View.VISIBLE);
        }

        public void hideText(){
            TransitionManager.beginDelayedTransition(itemStage, set);
            txtDescription.setVisibility(View.INVISIBLE);
        }

    }
}

