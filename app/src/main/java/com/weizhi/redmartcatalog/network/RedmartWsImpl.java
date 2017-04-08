package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.network.jsonpojo.DetailJson;
import com.weizhi.redmartcatalog.network.jsonpojo.ProductJson;
import com.weizhi.redmartcatalog.network.jsonpojo.SearchJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import timber.log.Timber;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class RedmartWsImpl implements RedmartWs {
    private static final String API_VERSION = "1.5.7";
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

    @NonNull
    @Override
    public RestResponse<List<Product>> search(int page, int pageSize) {
        Call<SearchJson> call = webService.search(API_VERSION, page, pageSize);

        List<Product> payload = new ArrayList<>();
        Integer httpStatusCode = null;
        Throwable error = null;
        try {
            retrofit2.Response<SearchJson> response = call.execute();
            if(response.isSuccessful()){
                Deserializer<ProductJson, Product> deserializer = new ProductJsonToProduct();

                List<ProductJson> productJsonList = response.body().products;
                for(ProductJson json : productJsonList){
                    Product product = deserializer.deserializeFrom(json);
                    payload.add(product);
                }
            }
            httpStatusCode = response.code();
        } catch (IOException e) {
            Timber.e("IOE while search");
            error = e;
        }

        return new RestResponse<>(payload, httpStatusCode, error);
    }

    @NonNull
    @Override
    public RestResponse<Product> getProduct(long productId) {
        Call<DetailJson> call = webService.getProduct(API_VERSION, productId);

        Product payload = null;
        Integer httpStatusCode = null;
        Throwable error = null;
        try {
            retrofit2.Response<DetailJson> response = call.execute();
            if(response.isSuccessful()){
                Deserializer<ProductJson, Product> deserializer = new ProductJsonToProduct();
                payload = deserializer.deserializeFrom(response.body().product);
            }
            httpStatusCode = response.code();
        } catch (IOException e) {
            error = e;
        }

        return new RestResponse<>(payload, httpStatusCode, error);
    }

    private interface RetrofitWs{
        @GET("v{apiVersion}/catalog/search")
        Call<SearchJson> search(@Path("apiVersion") String apiVersion,
                                @Query("page") int page,
                                @Query("pageSize") int pageSize);

        @GET("v{apiVersion}/catalog/products/{id}")
        Call<DetailJson> getProduct(@Path("apiVersion") String apiVersion,
                                    @Path("id") long productId);
    }
}
