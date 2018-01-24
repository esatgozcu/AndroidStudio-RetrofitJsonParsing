package com.example.esatgozcu.retrofitjsonparsingkullanimi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("AndroidJsonParse.php")
    Call<JSONResponse> getJSON();
}
