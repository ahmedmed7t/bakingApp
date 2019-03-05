package com.example.crazynet.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crazynet.bakingapp.Main2Activity;
import com.example.crazynet.bakingapp.R;
import com.example.crazynet.bakingapp.model.Response;

import java.util.ArrayList;

/**
 * Created by CrazyNet on 02/03/2019.
 */

public class MainGridAdapter extends BaseAdapter {

    ArrayList<Response> arrayList ;
    Context mcontext;
    private LayoutInflater inflater;

    public MainGridAdapter(Context context ,ArrayList<Response> responses) {
        arrayList = responses ;
        mcontext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.dish_item,parent,false);

            CardView cardView = convertView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, Main2Activity.class);
                    intent.putExtra("item",arrayList.get(position));

                    mcontext.startActivity(intent);
                }
            });

            TextView name = convertView.findViewById(R.id.txt_name);
            name.setText(arrayList.get(position).getName());

            TextView serving = convertView.findViewById(R.id.txt_serving);
            serving.setText(String.valueOf(arrayList.get(position).getServings()));
        }


        return convertView;
    }
}
