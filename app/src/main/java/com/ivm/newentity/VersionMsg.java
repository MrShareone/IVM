package com.ivm.newentity;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/2/4.
 * 版本信息
 */

public class VersionMsg implements Serializable {
    public int cutVersionCode = 0;  //当前版本
    public int newVersionCode = 0;   //最新版本
    public String curVersionName = "";    //当前版本名称
    public String newVersionName = "";    //最新版本名称
    public boolean needUpdate;      //是否需要更新
}
