package com.example.crazynet.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazynet.bakingapp.Main2Activity;
import com.example.crazynet.bakingapp.R;
import com.example.crazynet.bakingapp.UI.ingrediantFragment;
import com.example.crazynet.bakingapp.model.Response;

import java.util.ArrayList;

/**
 * Created by CrazyNet on 02/03/2019.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private ArrayList<Response> arrayList;
    private Context context;

    public MainRecyclerAdapter(ArrayList<Response> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name , serving ;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_name);
            serving = itemView.findViewById(R.id.txt_serving);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.dish_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.serving.setText(String.valueOf(arrayList.get(position).getServings()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("item",arrayList.get(position));
                ingrediantFragment.setItem(arrayList.get(position));

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
