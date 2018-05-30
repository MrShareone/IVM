package com.ivmai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ivm.newentity.VersionMsg;
import com.ivmai.kehu.chat.bean.MsgListList;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 管理sharepreference的类，提供存储清除查询方法
 */

public class Config {

    Context context;

    SharedPreferences userInfo;   //用户个人信息

    SharedPreferences clientInfo;   //存储游客信息

    SharedPreferences versionInfo;   //存储版本信息

    SharedPreferences chatmsg;   //存储聊天信息

    public Config( Context context) {
       this.context = context;

       userInfo = getSp(context,"userinfo");
       clientInfo = getSp(context,"isnewclient");
       versionInfo = getSp(context,"versionInfo");
       chatmsg = getSp(context,"chatmsg");
    }

    /**
     * @param ctx  上下文
     * @param name 字段
     * @return 返回ctx下name对应的sharepreference
     */
    private SharedPreferences getSp(Context ctx, String name) {
        return ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 保存需要做缓存接口的json数据
     *
     * @param jsonString
     */
    public void setuserinfoJsonString( String jsonString) {
        String key = "ivm_userinfo";
        userInfo.edit().putString(key, jsonString).commit();
    }

    /**
     * 获取需要做缓存接口的json数据
     *
     * @param defaultString  如果获取不到数据返回的默认字符
     */
    public String getuserinfoJsonString(String defaultString) {
        String key = "ivm_userinfo";
        return userInfo.getString(key, defaultString);
    }


    public String getClientMsgJsonString(String defaultString){
        String key = "ivm_clientinfo";
        return clientInfo.getString(key,defaultString);
    }

    public void setClientMsgJsonString( String jsonString) {
        String key = "ivm_clientinfo";
        clientInfo.edit().putString(key, jsonString).commit();
    }


    public VersionMsg getVersionInfo(String defaultString) {
        String key = "ivm_versioninfo";
        String s = versionInfo.getString(key,defaultString);
        VersionMsg versionMsg = (VersionMsg) GsonUtil.toObject(s,VersionMsg.class);
        return  versionMsg;
    }

    public void setVersionInfo(VersionMsg msg) {
        String key = "ivm_versioninfo";
        String s = GsonUtil.toJson(msg);
        if(!TextUtils.isEmpty(s)){
            versionInfo.edit().putString(key,s).commit();
        }
    }


    /**
     * @param defaultString
     * @return 获取聊天信息
     */
    public MsgListList getChatMsg(String defaultString){
        String key = "ivm_chatmsg";
        String s = chatmsg.getString(key,defaultString);
        MsgListList msgListList = GsonUtil.toObject(s,MsgListList.class);
        return msgListList;
    }

    /**
     * @param msgListList
     * 存储聊天信息
     */
    public void setChatmsg(MsgListList msgListList){
        String key = "ivm_chatmsg";
        String s = GsonUtil.toJson(msgListList);
        if(!TextUtils.isEmpty(s)){
            Boolean b  = chatmsg.edit().putString(key,s).commit();
            LogUtil.log("axxxxxxx",b.toString());
        }
    }

    /**
     * 清空对应的sharepreference
     * @param sp 需要清空的对象
     * @return 清空成功与否
     */
    public boolean clear(SharedPreferences sp) {
        return sp.edit().clear().commit();
    }

}
