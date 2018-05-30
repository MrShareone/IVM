package com.ivmai.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 网络请求工具
 */

public class NetWorkUtils {
    public static final String TAG = "NetWorkUtils";

    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public NetWorkUtils(Context context, Handler handler) {
    }

    /**
     * @param url  地址
     * @param hashMap  参数
     * @param context  上下文
     * @param handler  回调
     * @param code     这次请求的识别码
     */
    public static void startPost(final String url, HashMap<String,Object> hashMap, Context context, final Handler handler , final int code){
        String json = "";
        RequestBody requestBody;
        final Request request;
        Request.Builder builder = new Request.Builder( );
        builder.url(url);
        OkHttpClient okHttpClient = new OkHttpClient();
        if(!hashMap.isEmpty()){

            Iterator<Map.Entry<String, Object>> iterator = hashMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                json = GsonUtil.toJson(entry.getValue());
            }
        }
        if(!TextUtils.isEmpty(json)){

             requestBody = RequestBody.create(JSON, json);
             builder.post(requestBody);
        }

        request = builder.build();

        Call call = okHttpClient.newCall(request);

        if(!IvmApplication.isNetValuable){
            ToastUtil.showToast(context,"网络不可用");
            Message message = new Message();
            message.what = code;
            handler.sendMessage(message);
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                LogUtil.log(TAG,"请求失败！"+"url:"+url+"code:"+code);
                Message message = new Message();
                message.what = code;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                Message message = new Message();
                message.what = code;
                if(response.isSuccessful()){
                    message.obj = response.body().string();
                }
                handler.sendMessage(message);
            }
        });
    }


    /**
     * @param url  地址
     * @param context  上下文
     * @param handler  回调
     * @param code     这次请求的识别码
     */
    public static void startPost(final String url, Context context, final Handler handler , final int code){
        String json = "";
        RequestBody requestBody;
        final Request request;
        Request.Builder builder = new Request.Builder( );
        builder.url(url);
        OkHttpClient okHttpClient = new OkHttpClient();

        request = builder.build();

        Call call = okHttpClient.newCall(request);

        if(!IvmApplication.isNetValuable){
            ToastUtil.showToast(context,"网络不可用");
            Message message = new Message();
            message.what = code;
            handler.sendMessage(message);
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                LogUtil.log(TAG,"请求失败！"+"url:"+url+"code:"+code);
                Message message = new Message();
                message.what = code;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                Message message = new Message();
                message.what = code;
                if(response.isSuccessful()){
                    message.obj = response.body().string();
                }
                handler.sendMessage(message);
            }
        });
    }



}
