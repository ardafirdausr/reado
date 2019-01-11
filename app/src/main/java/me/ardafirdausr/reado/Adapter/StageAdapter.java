package me.ardafirdausr.reado.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {


    Context context;
    ArrayList<String> stages;

    public StageAdapter() {
        super();
    }


    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
//        View itemView = LayoutInflater.from(context).inflate(R.)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder stageViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return stages.size();
    }

    class StageViewHolder extends RecyclerView.ViewHolder{

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
