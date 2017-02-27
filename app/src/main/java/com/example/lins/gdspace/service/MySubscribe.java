package com.example.lins.gdspace.service;

import android.util.Log;
import android.widget.Toast;

import com.example.lins.gdspace.base.App;
import com.example.lins.gdspace.bean.MainBean;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by LINS on 2017/2/27.
 */

public abstract class MySubscribe<T extends MainBean> extends Subscriber<T> {
    @Override
    public void onNext(T t) {
        if (!"0".equals(t.getCode())){
            Toast.makeText(App.getContext(), "获取数据成功", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Log.e("eeeeee",t.getBody().getDatalist().get(1).getTitle());
        }

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException|e instanceof HttpException){
            Toast.makeText(App.getContext(), "error...", Toast.LENGTH_SHORT).show();
        }else{
            Log.e("MySubscibe Error-----",e.toString());
        }
    }
}
