package com.ivmai.kehu.chat.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MR-SHAREONE on 2018/5/14.
 */

public class MsgList implements Serializable{
    public String mainID;
    public String clientID;
    public Boolean newMessage;   //表示是否有新消息
    int msgNum;
    public ArrayList<Msg> msgs;

    public MsgList(String mainID, String clientID, ArrayList<Msg> msgs,Boolean b){
        this.mainID = mainID;
        this.clientID = clientID;
        if(msgs != null){
            this.msgNum = msgs.size();
        }else{
            this.msgNum = 0;
        }
        newMessage = b;
        this.msgs = msgs;
    }
}
