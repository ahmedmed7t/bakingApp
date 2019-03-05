package com.example.crazynet.bakingapp.UI;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crazynet.bakingapp.AppWidget;
import com.example.crazynet.bakingapp.Main2Activity;
import com.example.crazynet.bakingapp.R;
import com.example.crazynet.bakingapp.adapters.IngrediantRecyclerAdapter;
import com.example.crazynet.bakingapp.model.IngredientsItem;
import com.example.crazynet.bakingapp.model.Response;
import com.example.crazynet.bakingapp.model.StepsItem;

import java.util.ArrayList;

/**
 * Created by CrazyNet on 02/03/2019.
 */

public class ingrediantFragment extends Fragment {

    private static Response item ;
    private Button add ;
    private TextView ingrediant;

    public static void setItem(Response iteme) {
        item = iteme;
    }

    public ingrediantFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.ingrediant_fragment,container,false);
         add = view.findViewById(R.id.add);

         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addToWidget();
                 Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
             }
         });

        if(savedInstanceState != null)
        {
            item = savedInstanceState.getParcelable("item");
        }

            ingrediant = view.findViewById(R.id.txt_ingrediant);
            int x = item.getIngredients().size();
            IngredientsItem ingredientsItem;

            for (int i = 0; i < item.getIngredients().size(); i++) {

                ingredientsItem = item.getIngredients().get(i);

                ingrediant.append("\t *  " + ingredientsItem.getIngredient().toString() + "  " +
                        String.valueOf(ingredientsItem.getQuantity()) + "  " +
                        ingredientsItem.getMeasure()+"\n");
            }

        RecyclerView recyclerView = view.findViewById(R.id.ingrediant_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        IngrediantRecyclerAdapter adapter = new IngrediantRecyclerAdapter(item.getSteps(), getContext(), new IngrediantRecyclerAdapter.sendVideoData() {
            @Override
            public void send(ArrayList<StepsItem> array, int position) {
                Main2Activity activity = (Main2Activity)getActivity();
                activity.send(array, position);
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("item",item);
        super.onSaveInstanceState(outState);
    }

    public void addToWidget(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(), AppWidget.class));
        AppWidget.updateWidget(getContext(),appWidgetManager,appWidgetIds,String.valueOf(ingrediant.getText()));

    }
}


