package com.example.crazynet.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.example.crazynet.bakingapp.IdelingResource.SimpleIdlingResource;
import com.example.crazynet.bakingapp.adapters.MainGridAdapter;
import com.example.crazynet.bakingapp.adapters.MainRecyclerAdapter;
import com.example.crazynet.bakingapp.model.Response;
import com.example.crazynet.bakingapp.webService.RetrofitWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

    }

    public void getData(){

        RetrofitWebService.getService().getData().enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                List<Response> responses= response.body();
                ArrayList<Response> responseArrayList = (ArrayList<Response>) responses;
                initializeUI(responseArrayList);
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {

            }
        });

    }

    public void initializeUI(ArrayList<Response> responses){
        if(findViewById(R.id.main_gridView) != null){
            // tablet mode
            GridView gridView = findViewById(R.id.main_gridView);

            MainGridAdapter adapter = new MainGridAdapter(getApplicationContext(),responses);

            gridView.setAdapter(adapter);


        }else {
            // Portrait mode

            RecyclerView recyclerView = findViewById(R.id.main_recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            MainRecyclerAdapter adapter = new MainRecyclerAdapter(responses , getApplicationContext() );

            recyclerView.setAdapter(adapter);

        }
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
