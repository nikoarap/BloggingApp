package com.nikoarap.bloggingapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestClass {

    //url for json data
    public static final String BASE_URL = "https://sym-json-server.herokuapp.com";


    //retrofit instantiation
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static FetchJSONDataAPI fetchJSONDataAPI = retrofit.create(FetchJSONDataAPI.class);

    public static FetchJSONDataAPI fetchApi() {
        return fetchJSONDataAPI;
    }


}


