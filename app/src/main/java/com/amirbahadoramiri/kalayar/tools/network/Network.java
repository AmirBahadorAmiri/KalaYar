package com.amirbahadoramiri.kalayar.tools.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public static String API_URL = "https://www.tasnimnews.ir/";
    private static Retrofit retrofit;
    private static NetworkInterface networkInterface;

    public static Retrofit create() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        if ( retrofit == null ) {
            retrofit = create();
        }
        return retrofit;
    }

    public static NetworkInterface getNetworkInterface() {
        if ( networkInterface == null ) {
            networkInterface = getRetrofit().create(NetworkInterface.class);
        }
        return networkInterface;
    }
}
