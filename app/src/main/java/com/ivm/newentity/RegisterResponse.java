package com.ivm.newentity;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/1/30.
 */

public class RegisterResponse implements Serializable {

    /**
     * Code : 201
     * Desc : 用户已存在
     * result :
     */

    public String Code;
    public String Desc;
    public Object result;

}
