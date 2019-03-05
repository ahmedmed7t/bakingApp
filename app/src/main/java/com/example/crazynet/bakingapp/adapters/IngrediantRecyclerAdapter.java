package com.example.crazynet.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazynet.bakingapp.R;
import com.example.crazynet.bakingapp.model.Response;
import com.example.crazynet.bakingapp.model.StepsItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CrazyNet on 02/03/2019.
 */

public class IngrediantRecyclerAdapter extends RecyclerView.Adapter<IngrediantRecyclerAdapter.ViewHolder> {

    private List<StepsItem> stepsItems;
    private Context context;
    sendVideoData sendVideoData;

    public IngrediantRecyclerAdapter(List<StepsItem> response, Context context , sendVideoData data) {
        this.stepsItems = response;
        this.context = context;
        sendVideoData = data ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView stepName;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            stepName = itemView.findViewById(R.id.txt_step_name);
            cardView = itemView.findViewById(R.id.step_cardView);
        }
    }

    @NonNull
    @Override
    public IngrediantRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.steps_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngrediantRecyclerAdapter.ViewHolder holder, final int position) {

        holder.stepName.setText(stepsItems.get(position).getShortDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StepsItem> arrayList = (ArrayList<StepsItem>) stepsItems;
                sendVideoData.send(arrayList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsItems.size();
    }

   public interface sendVideoData{
        void send(ArrayList<StepsItem> array , int postion);
   }
}
