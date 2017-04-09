package com.weizhi.redmartcatalog.network;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.network.jsonpojo.SingleItemJson;
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
    private final String mApiVersion;
    private final RetrofitWs webService;
    private final ObjectMapper mapper;

    public RedmartWsImpl(@NonNull String baseUrl, @NonNull String apiVersion){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .readTimeout(60, TimeUnit.MINUTES)
//                .connectTimeout(60, TimeUnit.MINUTES)
//                .build();

        mApiVersion = apiVersion;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
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
        Call<SearchJson> call = webService.search(mApiVersion, page, pageSize);

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
        Call<SingleItemJson> call = webService.getProduct(mApiVersion, productId);

        Product payload = null;
        Integer httpStatusCode = null;
        Throwable error = null;
        try {
            retrofit2.Response<SingleItemJson> response = call.execute();
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
        Call<SingleItemJson> getProduct(@Path("apiVersion") String apiVersion,
                                        @Path("id") long productId);
    }
}
