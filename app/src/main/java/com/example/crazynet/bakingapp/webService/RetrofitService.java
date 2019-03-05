package com.example.crazynet.bakingapp.webService;



import com.example.crazynet.bakingapp.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Medhat on 16/08/2018.
 */

public interface RetrofitService {

     @GET("May/59121517_baking/baking.json")
     Call<List<Response>> getData ();

}
