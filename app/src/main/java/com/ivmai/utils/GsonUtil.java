package com.ivmai.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 *mr - shareone
 * gson解析工具封装
 */
public class GsonUtil {
    private static Gson gson = null;

    public Gson getInstance() {
        if (gson == null) gson = new Gson();
        return gson;
    }

    /**
     * 返回一个对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class clazz) {
        try {
            return (T) new GsonUtil().getInstance().fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回一个对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Type type){
        try {
            return (T) new GsonUtil().getInstance().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Map参数转成json格式传输
     *
     * @param map
     * @return
     */
    public static String toJson(Map map) {
        return new GsonUtil().getInstance().toJson(map);
    }

    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type  new TypeToken<List<yourbean>>(){}.getType() List<LeaveSchedule> tmpBacklogList = gson.fromJson(backlogJsonStr, new TypeToken<List<LeaveSchedule>>() {}.getType());
     * @return ArrayList<ContentMsg> json1 = gson.fromJson(s.toString(),new TypeToken<List<ContentMsg>>() {}.getType());
     */
    public static List<?> toList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        Gson gson = new Gson();
        if (obj == null) {
            return  null;
        }else {
            return  gson.toJson(obj);
        }
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * json字符串转成对象或集合（数组）
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJsonType(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }


}
