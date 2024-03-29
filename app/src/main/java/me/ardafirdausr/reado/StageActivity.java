package me.ardafirdausr.reado;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.ardafirdausr.reado.adapter.StageAdapter;
import me.ardafirdausr.reado.database.DataBaseHelper;
import me.ardafirdausr.reado.database.QuizzesHandler;
import me.ardafirdausr.reado.database.StagesHandler;
import me.ardafirdausr.reado.model.ArrayListWrapper;
import me.ardafirdausr.reado.model.Quiz;
import me.ardafirdausr.reado.model.Stage;

public class StageActivity extends AppCompatActivity implements
        DiscreteScrollView.ScrollStateChangeListener<StageAdapter.StageViewHolder>,
        DiscreteScrollView.OnItemChangedListener<StageAdapter.StageViewHolder>{

    DiscreteScrollView rvStage;
    int stageCount;
    TextView txtBackHome;
    Button btnContinue;
    ArrayList<Stage> stages;
    SharedPreferences mPreferences;
    String sharedPrefFile;
    StagesHandler stagesHandler;
    QuizzesHandler quizzesHandler;
    int stageZeroQuizzesCount, currentStage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        sharedPrefFile = getPackageName();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        currentStage = mPreferences.getInt("currentStage",1);
        stagesHandler = new StagesHandler(this);
        quizzesHandler = new QuizzesHandler(this);
        stageZeroQuizzesCount = quizzesHandler.getQuizzesCountByStage(0);
        stages = stagesHandler.getAllStages();
        // -1 because only 3 stages counted as stage. stage 0 is not counted
        stageCount = stages.size() - 1;

        txtBackHome = (TextView) findViewById(R.id.btnBackHome);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        rvStage = (DiscreteScrollView) findViewById(R.id.rvStage);
        txtBackHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StageActivity.this, LevelActivity.class);
                switch(rvStage.getCurrentItem()){
                    case 0:
                        if(stageZeroQuizzesCount == 0){
                            Toast.makeText(getBaseContext(), "No scanned words saved", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(stageZeroQuizzesCount > 10){
                            stageZeroQuizzesCount = 10;
                        }
                        ArrayListWrapper<Quiz> quizzes = new ArrayListWrapper<Quiz>(
                                quizzesHandler.getRandomQuizzes(0, stageZeroQuizzesCount)
                        );
                        Intent toQuizStageZeroActivity = new Intent(StageActivity.this, QuizStageZeroActivity.class);
                        toQuizStageZeroActivity.putExtra("quizzes", quizzes);
                        startActivity(toQuizStageZeroActivity);
                        break;
                    case 1:
                        intent.putExtra("stage", 1);
                        break;
                    case 2:
                        intent.putExtra("stage", 2);
                        break;
                    case 3:
                        intent.putExtra("stage", 3);
                        break;
                    default:
                        intent.putExtra("stage", 1);
                }
                if(rvStage.getCurrentItem() <= currentStage){
                    startActivity(intent);
                }
            }
        });
        rvStage.setAdapter(new StageAdapter(this, stages));
        rvStage.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());
        rvStage.addOnItemChangedListener(this);
        rvStage.addScrollStateChangeListener(this);
        Log.d("WWWWW", "" + currentStage );
        if(currentStage <= stageCount) {
            rvStage.scrollToPosition(currentStage);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // MOVE TO NEW STAGE AND ALERT IT FINISH LAST STAGE
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.containsKey("finishTrain")){
                getIntent().removeExtra("finishTrain");
                rvStage.scrollToPosition(0);
                new SweetAlertDialog(StageActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(getResources().getString(R.string.good_job))
                        .setContentText(getResources().getString(R.string.finish_all))
                        .show();
            }
            else if(bundle.containsKey("stage")){
                final int moveToStage = bundle.getInt("stage");
                rvStage.scrollToPosition(bundle.getInt("stage") - 1);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if(moveToStage <= stageCount){
                                    rvStage.smoothScrollToPosition(moveToStage);
                                }
                                else{
                                    new SweetAlertDialog(StageActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText(getResources().getString(R.string.good_job))
                                            .setContentText(getResources().getString(R.string.finish_all))
                                            .show();
                                    SharedPreferences.Editor sharedPreferencesEditor = mPreferences.edit();
                                    sharedPreferencesEditor.putInt("currentStage", stageCount);
                                    sharedPreferencesEditor.apply();
                                }
                                getIntent().removeExtra("stage");
                            }
                        },
                        300);
            }
        }
    }

    @Override
    public void onScrollStart(@NonNull StageAdapter.StageViewHolder currentItemHolder, int adapterPosition) {
        currentItemHolder.hideText();
    }

    @Override
    public void onScrollEnd(@NonNull StageAdapter.StageViewHolder currentItemHolder, int adapterPosition) {
        currentItemHolder.showText();
    }

    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable StageAdapter.StageViewHolder currentHolder, @Nullable StageAdapter.StageViewHolder newCurrent) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable StageAdapter.StageViewHolder viewHolder, int adapterPosition) {
        //viewHolder will never be null, because we never remove items from adapter's list
        if (viewHolder != null) {
//            viewHolder.showText();
        }
    }
}
