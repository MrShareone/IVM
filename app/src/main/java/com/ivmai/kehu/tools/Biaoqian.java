package com.ivmai.kehu.tools;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by MR-SHAREONE on 2017/8/15.
 */

public class Biaoqian {
    public String[] strings;//加肉(￥12.00)/加肉(￥12.00)...
    public String[] shuxings;//加肉/不加肉...
    public String[] jiages;//(￥12.00)/(￥12.13)...
    public Double[] prices;//12.00/12.13...
    String checkedbiqoain = "";//设置被选中的项的数据
    ArrayList<String> shuxingArrayList = new ArrayList<>();
    ArrayList<String> jiagegArrayList = new ArrayList<>();
    ArrayList<String> biaoqianList = new ArrayList<>();
    ArrayList<Double> pricesArrayList = new ArrayList<>();

    //传入string
    public Biaoqian(String string) {
        if (string.contains("=")) {
            strings = string.split("\\|");
            for (int i = 0; i < strings.length; i++) {
                String a, b, c;
                c = strings[i];
                if (c.contains("=")) {
                    Log.d("c==================", c);
                    a = c.substring(0, c.indexOf("="));
                    b = c.substring(c.indexOf("=") + 1);

                    Log.d("a==================", a);
                    Log.d("b==================", b);

                    Double dd = Double.parseDouble(b);
//                    String ddd = String.format("(￥%.2f)", b);
                    shuxingArrayList.add(a);
                    pricesArrayList.add(dd);
                    jiagegArrayList.add("(￥"+b+")");
                }
            }
            shuxings = (String[]) shuxingArrayList.toArray(new String[shuxingArrayList.size()]);

            checkedbiqoain = strings[0];
            Log.d("checkedbiaoqian========",checkedbiqoain);

            jiages = (String[]) jiagegArrayList.toArray(new String[jiagegArrayList.size()]);
            for (int i = 0; i < shuxings.length; i++) {
                biaoqianList.add(shuxings[i] + "\n" + jiages[i]);
            }
            strings = (String[]) biaoqianList.toArray(new String[biaoqianList.size()]);//存为价格标签
            prices = new Double[pricesArrayList.size()];
            pricesArrayList.toArray(prices);  //存好标签数据
        } else {//存为可选标签
            strings = string.split("\\|");
        }
    }


    public String[] getStrings() {
        return strings;
    }

    public String[] getShuxings() {
        return shuxings;
    }

    public String[] getJiages() {
        return jiages;
    }

    public Double[] getPrices() {
        return prices;
    }

    public String getCheckedbiqoain() {
        return checkedbiqoain;
    }

    public void setCheckedbiqoain(String checkedbiqoain) {
        this.checkedbiqoain = checkedbiqoain;
    }
}
