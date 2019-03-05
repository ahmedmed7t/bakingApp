package com.example.crazynet.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.example.crazynet.bakingapp.IdelingResource.SimpleIdlingResource;
import com.example.crazynet.bakingapp.UI.VideoFragment;
import com.example.crazynet.bakingapp.UI.ingrediantFragment;
import com.example.crazynet.bakingapp.model.Response;
import com.example.crazynet.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            response = intent.getParcelableExtra("item");
        } else {
            response = savedInstanceState.getParcelable("response");
        }


        ingrediantFragment ingrediantfragment = new ingrediantFragment();
        ingrediantfragment.setItem(response);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_ingrediant_P, ingrediantfragment)
                    .commit();


    }

    public void send(ArrayList<StepsItem> array , int position){
           if(findViewById(R.id.fragment_ingrediant_Tablet) == null){
                    // portrait mode
               Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
               intent.putParcelableArrayListExtra("array",array);
               intent.putExtra("position",position);
               startActivity(intent);

           }else{
               VideoFragment videofragment = new VideoFragment();
               videofragment.showData(array,position);

               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.fragment_ingrediant_Tablet, videofragment)
                       .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("response",response);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
