package com.ivmai.kehu.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lk on 2017/8/28.
 */

public class OkhttpUtils {

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    private static JsonNode jsonnode=null;

    /**
    * post请求，返回code
    * */
     public  static String post(Object object, String url){

         url = url +"/"+ TokenBuilder.buildToken();
         OkHttpClient okHttpClient = new OkHttpClient()
                 .newBuilder()
                 .connectTimeout(5, TimeUnit.SECONDS)
                 .readTimeout(10, TimeUnit.SECONDS)
                 .build();

         PojoMapper pojoMapper = new PojoMapper();
         String json = null;

         try {
             json = pojoMapper.toJson(object,true);
             Log.d("json结果",json.toString());
         } catch (IOException e) {
             e.printStackTrace();
         }
         RequestBody requestBody= RequestBody.create(JSON,json );
         Request request = new Request.Builder()
                 .url(url)
                 .post(requestBody)
                 .build();
         try {
             Response response=okHttpClient.newCall(request).execute();
             Log.d("response",""+response.isSuccessful());
             //判断请求是否成功
             if(response.isSuccessful()){
                 String responsedata = response.body().string();
                 Log.d("返回结果",responsedata);
                 ObjectMapper m =new ObjectMapper();
                 JsonNode jsonNode  = m.readTree(responsedata);
                 JsonNode jsonNode1 = jsonNode.path("Code");
                 String code = jsonNode1.toString();
                 Log.d("code",code);
                 return code;
             }
         } catch (IOException e) {
             e.printStackTrace();
             return "EXCEPTION";
         }
         return "";
     }

    /**
     * post请求，返回code和result
     * */
    public static  String postResult(Object object,String url){

        url = url +"/"+ TokenBuilder.buildToken();
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        PojoMapper pojoMapper = new PojoMapper();
        String json = null;

        try {
            json = pojoMapper.toJson(object,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody= RequestBody.create(JSON,json );
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsedata = response.body().string();
                Log.d("返回结果",responsedata);
                ObjectMapper m =new ObjectMapper();
                JsonNode jsonNode  = m.readTree(responsedata);
                JsonNode json1=jsonNode.path("result");
                jsonnode=json1;
                JsonNode jsonNode1 = jsonNode.path("Code");
                String code = jsonNode1.toString();
                Log.d("code",code);
                return code;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "EXCEPTION";
        }
        return "";

    }


 /**
    * get请求，返回code
    * */

     public static  String get(String url){
         url = url +"/"+ TokenBuilder.buildToken();
         OkHttpClient okHttpClient = new OkHttpClient()
                 .newBuilder()
                 .connectTimeout(5, TimeUnit.SECONDS)
                 .readTimeout(10, TimeUnit.SECONDS)
                 .build();
         Request request = new Request.Builder()
                 .url(url)
                 .build();
         try {
             Response response=okHttpClient.newCall(request).execute();
             if(response.isSuccessful()){
                 String responsedata = response.body().string();
                 Log.d("返回结果",responsedata);
                 ObjectMapper m =new ObjectMapper();
                 JsonNode jsonNode  = m.readTree(responsedata);
                 JsonNode jn = jsonNode.path("Code");
         String code = jn.toString();
         Log.d("code",code);
         return code;
     }
} catch (IOException e) {
             e.printStackTrace();
             return "EXCEPTION";
         }
         return "";
     }

    /**
     * get请求，返回code和result
     * */

    public static String getResult(String url){

        url = url +"/"+ TokenBuilder.buildToken();
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsedata = response.body().string();
                Log.d("返回结果",responsedata);
                ObjectMapper m =new ObjectMapper();
                JsonNode jsonNode  = m.readTree(responsedata);
                JsonNode json=jsonNode.path("result");
                jsonnode=json;
                JsonNode jn = jsonNode.path("Code");
                String code = jn.toString();
                Log.d("code",code);
              return  code;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "EXCEPTION";
        }
        return  "";
    }
    /**
     * get图片请求，返回bitmap
     * */
    public static Bitmap getBitmap(String url)
          throws java.io.IOException {
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("onResponse: ", "xxxxxxxxx");
                byte[] Picture_bt = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
                return bitmap;
            }
            return null;
        }
    public static JsonNode getJsonnode() {
        return jsonnode;
    }

}

