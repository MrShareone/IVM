package com.ivmai.kehu.chat.bean;

import com.ivmai.base.IvmApplication;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/5/14.
 */

public class Msg implements Serializable{
    public String time;
    public String sendID;
    public String reciveID;
    public String content;


    //别人发的
    public Msg(){
        time = "00:46:46";
        sendID = "2";
        reciveID = "1";
        content = "吃点啥？";
    }

    //我发的
    public Msg(int i){
        time = "00:48:37";
        sendID = "1";
        reciveID = "2";
        content = "吃你妈";
    }

    public Msg(String s){
        time = "00:53:45";
        sendID = "1";
        reciveID = "2";
        content = s;
    }

    public Msg(String time, String reciveID, String content) {
        this.time = time;
        this.sendID = IvmApplication.kehu.khTel;
        this.reciveID = reciveID;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendID() {
        return sendID;
    }

    public void setSendID(String sendID) {
        this.sendID = sendID;
    }

    public String getReciveID() {
        return reciveID;
    }

    public void setReciveID(String reciveID) {
        this.reciveID = reciveID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
