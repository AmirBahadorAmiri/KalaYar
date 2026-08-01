package com.amirbahadoramiri.kalayar.tools.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface NetworkInterface {

    @GET
    Call<ResponseBody> getData(@Url String url);

    @Headers("Content-Length: 0")
    @POST("common/CurrencyTable")
    Call<ResponseBody> getLatestPrices();

}
