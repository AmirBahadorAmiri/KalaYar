package com.amirbahadoramiri.kalayar.tools.network;

import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NetworkInterface {

    @GET
    String getData(@Url String url);

}
