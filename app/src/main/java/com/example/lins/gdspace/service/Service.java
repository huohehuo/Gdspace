package com.example.lins.gdspace.service;

import com.example.lins.gdspace.base.App;
import com.example.lins.gdspace.bean.MainBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by LINS on 2017/2/23.
 */

public class Service {
    private ServiceRequest request;
    public Service(){
        request = App.getRetrofit().create(ServiceRequest.class);
    }

    public void getShow(MySubscribe<MainBean> subscribe){
        Observable<MainBean> observable = request.getShow("getjson");
        toSubcribe(observable,subscribe);

    }
    //retrofit线程管理
    private static <T> void toSubcribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
