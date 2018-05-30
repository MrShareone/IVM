package com.ivm.newentity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/1/31.
 */

public class OrderResponse implements Serializable {
    public String Code;
    public String Desc;
    public ArrayList<DingDan> result;

    public static class DingDan implements Serializable {

//      "dDID": 735,
//              "ddBeichengTime": "1970-01-01 00:00:00",
//              "ddDingdanTime": "2017-10-18 15:41:29",
//              "ddFanxianPrice": 0,
//              "ddFapiao": "",
//              "ddImei": "**imei?**",
//              "ddJiageBiaoqianZhi": "优品=9",
//              "ddKaipiaoTime": "1970-01-01 00:00:00",
//              "ddKehuLiuyan": "",
//              "ddKexuanBiaoqianZhi": "",
//              "ddNote": "自取带走时需要支付的打包费:0.6000",
//              "ddQitaWenjian": "",
//              "ddSPNum": 1,
//              "ddShuomingshu": "",
//              "ddState": "1",
//              "ddTihuoDidian": "自取带走",
//              "ddTihuoTime": "1970-01-01 00:00:00",
//              "ddTihuoma": "",
//              "ddWeizhiFuwuBiaoqian": "10000",
//              "ddZhifuTime": "1970-01-01 00:00:00",
//              "ddZhifuhao": "",
//              "ddvalue": 9.6,
//              "kHID": 10009,
//              "khHuiyuanJibie": 1,
//              "khImei": "",
//              "khTel": "13437105740",
//              "khWeixinName": "",
//              "sHID": 116,
//              "sPID": 38,
//              "shAddress": "中南民族大学三食堂二楼",
//              "shFendianhao": "",
//              "shMengzhuName": "",
//              "shName": "小米米民大四食堂店",
//              "shTel": "13914166715",
//              "spEweima": "",
//              "spJianjie": "茄子去皮了，拌饭很好吃",
//              "spKexuanBiaoqian": "",
//              "spName": "肉末茄子",
//              "spPriceBiaoqian": "优品=9",
//              "spShangpingPrice": 9,
//              "spTupian": "upload/SP/c5dfb9ff-6469-4041-ac71-d1a493574685.jpg",
//              "spURL": "",
//              "spWeizhiBiaoqian": "11000"

        public int dDID;
        public String ddBeichengTime;
        public String ddDingdanTime;
        public int ddFanxianPrice;
        public String ddFapiao;
        public String ddImei;
        public String ddJiageBiaoqianZhi;
        public String ddKaipiaoTime;
        public String ddKehuLiuyan;
        public String ddKexuanBiaoqianZhi;
        public String ddNote;
        public String ddQitaWenjian;
        public int ddSPNum;
        public String ddShuomingshu;
        public String ddState;  //1|XX1未支付状态  2|X10已支付状态  3|X11信用支付状态   4|1XX备成状态  5|5XX已提货状态  6|1XXX订单撤销状态
        public String ddTihuoDidian;  // 0| 打包自取            1|自取            2|传菜堂食            3|外卖            4|储物柜取
        public String ddTihuoTime;
        public String ddTihuoma;
        public String ddWeizhiFuwuBiaoqian;
        public String ddZhifuTime;
        public String ddZhifuhao;
        public float ddvalue;
        public int kHID;
        public int khHuiyuanJibie;
        public String khImei;
        public String khTel;
        public String khWeixinName;
        public int sHID;
        public int sPID;
        public String shAddress;
        public String shFendianhao;
        public String shMengzhuName;
        public String shName;
        public String shTel;
        public String spEweima;
        public String spJianjie;
        public String spKexuanBiaoqian;
        public String spName;
        public String spPriceBiaoqian;
        public int spShangpingPrice;
        public String spTupian;
        public String spURL;
        public String spWeizhiBiaoqian;

        /**
         * @return 根据约定的ddstate解析返回文本描述的订单状态
         */
        public String getStringState() {
            if (ddState != null) {

                if (ddState.endsWith("1")) {
                    return "未支付";
                }

                if (ddState.endsWith("10")) {
                    return "已支付";
                }

                if (ddState.endsWith("11")) {
                    return "信用支付";
                }

                if (ddState.startsWith("1") && ddState.length() == 3) {
                    return "备成状态";
                }

                if (ddState.startsWith("5")) {
                    return "已提货状态";
                }

                if (ddState.startsWith("1") && ddState.length() == 4) {
                    return "已撤销";
                }
            }
            return "";
        }

        public String getDdValue(){
            if (ddState != null) {

                if (ddState.endsWith("1")) {
                    return "待支付：￥"+ddvalue;
                }

                if (ddState.endsWith("10")) {
                    return "实付：￥"+ddvalue;
                }

                if (ddState.endsWith("11")) {
                    return "信用支付:￥"+ddvalue;
                }

                if (ddState.startsWith("1") && ddState.length() == 3) {
                    return "实付：￥"+ddvalue;
                }

                if (ddState.startsWith("5")) {
                    return "实付：￥"+ddvalue;
                }

                if (ddState.startsWith("1") && ddState.length() == 4) {
                    return "";
                }
            }
            return "";
        }

        public List<String> getTags(){
            List<String> tags = new ArrayList<>();

            if(!TextUtils.isEmpty(ddKexuanBiaoqianZhi)){
                tags.add(ddKexuanBiaoqianZhi);
            }
            if(!TextUtils.isEmpty(ddJiageBiaoqianZhi)){
                try {
                    tags.add(ddJiageBiaoqianZhi.substring(0,ddJiageBiaoqianZhi.indexOf("=")));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return tags;
        }
    }
}
