package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weizhi.redmartcatalog.network.response.DetailJson;
import com.weizhi.redmartcatalog.network.response.SearchJson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class RedmartWsImpl implements RedmartWs {
    private final RetrofitWs webService;
    private final ObjectMapper mapper;

    public RedmartWsImpl(@NonNull String baseUrl){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .readTimeout(60, TimeUnit.MINUTES)
//                .connectTimeout(60, TimeUnit.MINUTES)
//                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        mapper = new ObjectMapper();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        webService = retrofit.create(RetrofitWs.class);
    }

    private interface RetrofitWs{
        @GET("v{apiVersion}/catalog/search")
        Call<SearchJson> search(@Path("apiVersion") int apiVersion,
                                @Query("page") int page,
                                @Query("pageSize") int pageSize);

        @GET("v{apiVersion}/catalog/products/{id}")
        Call<DetailJson> getProduct(@Path("apiVersion") int apiVersion,
                                    @Path("id") int productId);
    }
}
