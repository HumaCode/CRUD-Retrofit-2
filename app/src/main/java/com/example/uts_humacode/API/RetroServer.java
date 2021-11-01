package com.example.uts_humacode.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "https://humacode.000webhostapp.com/laundry/";
    private static Retrofit retro;


    public static Retrofit konekRetrofit(){
        if(retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retro;
    }
}
