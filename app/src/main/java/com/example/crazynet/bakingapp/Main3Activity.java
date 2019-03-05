package com.example.crazynet.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.crazynet.bakingapp.UI.VideoFragment;
import com.example.crazynet.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        ArrayList<StepsItem> arrayList = intent.getParcelableArrayListExtra("array");
        int position = intent.getIntExtra("position",-1);

        VideoFragment ingrediantfragment = new VideoFragment();
        ingrediantfragment.showData(arrayList,position);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.video_fragment_portrait, ingrediantfragment)
                .commit();
    }
}
