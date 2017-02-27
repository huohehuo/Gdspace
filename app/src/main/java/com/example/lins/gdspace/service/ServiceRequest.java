package com.example.lins.gdspace.service;

import com.example.lins.gdspace.bean.MainBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LINS on 2017/2/27.
 */

interface ServiceRequest{
    //http://route.showapi.com/341-3?showapi_appid=32450&showapi_sign=2b8b8a8c02cd4543827b6c3e4ea36976&page=1&maxResult=10

    @GET("getgd/{data}")
    Observable<MainBean> getShow(
            @Path("data") String data
    );
}
