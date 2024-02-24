package com.example.covidtracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String BASE_URL = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    abstract Call<List<ModelClass>> getCountryData();


}
