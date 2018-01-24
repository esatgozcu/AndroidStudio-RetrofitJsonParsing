package com.example.esatgozcu.retrofitjsonparsingkullanimi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews(){
        //RecylerView'i tanımlıyoruz
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        //Json verilerimiz websitesinden çekiyoruz
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.esatgozcu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //RequestInterface ekliyoruz
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                //Eğer hata ile karşılaşılmazsa
                JSONResponse jsonResponse = response.body();
                //Gelen nesneyi Array olarak döndürüyoruz
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                //Adaptere datayı ekliyoruz
                adapter = new DataAdapter(data);
                //RecylerView'e adapteri ekliyoruz
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                //Hata ile karşılaşırsa hatayı bastırıyoruz
                Log.d("Error",t.getMessage());
            }
        });
    }
}
