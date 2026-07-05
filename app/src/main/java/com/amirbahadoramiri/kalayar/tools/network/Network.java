package com.amirbahadoramiri.kalayar.tools.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public static String URL = "https://www.tasnimnews.ir/fa/currency";
    private static Retrofit retrofit;
    private static NetworkInterface networkInterface;

    public static Retrofit create() {
        return new Retrofit.Builder()
                .baseUrl("https://google.com")
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
