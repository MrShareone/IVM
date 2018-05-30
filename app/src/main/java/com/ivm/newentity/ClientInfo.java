package com.ivm.newentity;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 存放游客信息
 */

public class ClientInfo implements Serializable{
    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isFirst;   //是否是第一次登录
}
