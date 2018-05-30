package com.ivmai.kehu.chat.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MR-SHAREONE on 2018/5/15.
 */

public class MsgListList implements Serializable {
    public int listsnum;
    public ArrayList<MsgList> msgLists = new ArrayList<>();

    public MsgListList(ArrayList<MsgList> msgLists) {
        if(msgLists == null)
            return;
        this.msgLists = msgLists;
        listsnum = msgLists.size();
    }

    public MsgListList() {
    }

    public ArrayList<MsgList> getMsgLists() {
        return msgLists;
    }

    public void setMsgLists(ArrayList<MsgList> msgLists) {
        this.msgLists = msgLists;
    }
}
