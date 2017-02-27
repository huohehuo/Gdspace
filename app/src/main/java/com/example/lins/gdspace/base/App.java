package com.example.lins.gdspace.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.lins.gdspace.service.Service;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LINS on 2017/2/23.
 */

public class App extends Application{

    private static OkHttpClient okHttpClient;
    private static HttpLoggingInterceptor interceptor;
    private static Gson gson;

    private static Retrofit retrofit;
    private static Service mService;

    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        gson = new Gson();
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://192.168.1.239:8080/")
                .build();
        mService = new Service();
    }
    public static Service getService() {
        return mService;
    }

    public static void setService(Service mService) {
        App.mService = mService;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        App.retrofit = retrofit;
    }

    public static Context getContext() {
        return mContext;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static Gson getGson() {
        return gson;
    }
}
