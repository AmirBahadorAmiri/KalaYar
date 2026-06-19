package com.amirbahadoramiri.kalayar.tools.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NetworkInterface {

    @GET
    Single<String> getData(@Url String url);

}
