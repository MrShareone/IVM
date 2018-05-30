package com.ivm.newentity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/2/1.
 */

public class ProductsResponseSuccess implements Serializable {


    /**
     * Code : 200
     * Desc : 成功
     * result : [{"shAddress":"中南民族大学三食堂二楼","shEmail":"","shFapiaoname":"犀米家民大四食堂店","shFendianhao":"","shFullname":"湖北爱微迈智能科技有限责任公司","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"犀米家","shName":"爱微迈公司","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"18907130210","shYinkamincunru":1000,"shYinkapeitaobilu":0,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.11111100018024445,"shZuanshixinyongxiane":100,"shid":115,"spDizhifuwubiaoqian":"1000","spEweima":"","spJianjie":"水","spKexuanbiaoqian":"","spName":"水/饮料","spNote":"","spPricebiaoqian":"农夫=2.0|绿茶=4.0|小茗同学=8.0","spShangpingprice":0,"spShuliang":9949,"spShuomingshu":"","spStatus":1,"spTupian":"upload/SP/90f197f8-3f15-4d2f-a809-eec5e19af033.jpg","spUrl":"","spYiweima":"","spid":50},{"shAddress":"中南民族大学三食堂二楼","shEmail":"","shFapiaoname":"犀米家民大四食堂店","shFendianhao":"","shFullname":"湖北爱微迈智能科技有限责任公司","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"犀米家","shName":"爱微迈公司","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"18907130210","shYinkamincunru":1000,"shYinkapeitaobilu":0,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.11111100018024445,"shZuanshixinyongxiane":100,"shid":115,"spDizhifuwubiaoqian":"1000","spEweima":"","spJianjie":"0.5mm规格，极度滑爽","spKexuanbiaoqian":"","spName":"圆珠笔芯","spNote":"","spPricebiaoqian":"0.5mm=0.1","spShangpingprice":0,"spShuliang":967,"spShuomingshu":"","spStatus":1,"spTupian":"upload/SP/a5fe3cc1-b9ab-496f-bb75-b9b124c851b2.jpg","spUrl":"","spYiweima":"","spid":52},{"shAddress":"中南民族大学","shEmail":"","shFapiaoname":"翁家家","shFendianhao":"","shFullname":"翁家家","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.481666667 114.385555556)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"","shName":"翁家家","shPutongmincunru":10,"shPutongpeitaobilu":0.008999999612569809,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"15927327212","shYinkamincunru":1000,"shYinkapeitaobilu":0.05000000074505806,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.10000000149011612,"shZuanshixinyongxiane":100,"shid":109,"spDizhifuwubiaoqian":"11110","spEweima":"","spJianjie":"一只恼人的猫","spKexuanbiaoqian":"","spName":"猫","spNote":"","spPricebiaoqian":"大=10|中=7","spShangpingprice":0,"spShuliang":54,"spShuomingshu":"","spStatus":1,"spTupian":"upload/SP/4f93e373-b3ab-4cf8-94b1-83d9162baad3.jpg","spUrl":"","spYiweima":"","spid":12}]
     */

    public String Code;
    public String Desc;
    public List<ResultBean> result;
    
    public static class ResultBean {
        /**
         * shAddress : 中南民族大学三食堂二楼
         * shEmail : 
         * shFapiaoname : 犀米家民大四食堂店
         * shFendianhao : 
         * shFullname : 湖北爱微迈智能科技有限责任公司
         * shFuwuzhuangtai : 1
         * shGpsdizhi : POINT(30.492192 114.384887)
         * shJinkapeitaobilu : 0.10000000149011612
         * shJinkaxinyongxiane : 0
         * shJnkamincunrui : 1000000
         * shMengzhuname : 犀米家
         * shName : 爱微迈公司
         * shPutongmincunru : 0
         * shPutongpeitaobilu : 0
         * shPutongxinyongxiane : 0
         * shStatus : 100
         * shTel : 18907130210
         * shYinkamincunru : 1000
         * shYinkapeitaobilu : 0
         * shYinkaxinyongxiane : 0
         * shZuanshimincunru : 10000000
         * shZuanshipeitaobilu : 0.11111100018024445
         * shZuanshixinyongxiane : 100
         * shid : 115
         * spDizhifuwubiaoqian : 1000
         * spEweima : 
         * spJianjie : 水
         * spKexuanbiaoqian : 
         * spName : 水/饮料
         * spNote : 
         * spPricebiaoqian : 农夫=2.0|绿茶=4.0|小茗同学=8.0
         * spShangpingprice : 0
         * spShuliang : 9949
         * spShuomingshu : 
         * spStatus : 1
         * spTupian : upload/SP/90f197f8-3f15-4d2f-a809-eec5e19af033.jpg
         * spUrl : 
         * spYiweima : 
         * spid : 50
         */

        public String shAddress;
        public String shEmail;
        public String shFapiaoname;
        public String shFendianhao;
        public String shFullname;
        public String shFuwuzhuangtai;
        public String shGpsdizhi;
        private String shJinkapeitaobilu;
        public int shJinkaxinyongxiane;
        public int shJnkamincunrui;
        public String shMengzhuname;
        public String shName;
        public int shPutongmincunru;
        private String shPutongpeitaobilu;
        public int shPutongxinyongxiane;

        public Double getShJinkapeitaobilu() {
            return Double.parseDouble(shJinkapeitaobilu);
        }


        public Double getShPutongpeitaobilu() {
            return Double.parseDouble(shPutongpeitaobilu);
        }



        public Double getShYinkapeitaobilu() {
            return Double.parseDouble(shYinkapeitaobilu);
        }


        public Double getShZuanshipeitaobilu() {
            return Double.parseDouble(shZuanshipeitaobilu);
        }



        public int shStatus;
        public String shTel;
        public int shYinkamincunru;
        private String shYinkapeitaobilu;
        public int shYinkaxinyongxiane;
        public int shZuanshimincunru;
        private String shZuanshipeitaobilu;
        public int shZuanshixinyongxiane;
        public int shid;
        public String spDizhifuwubiaoqian;
        public String spEweima;
        public String spJianjie;
        public String spKexuanbiaoqian;
        public String spName;
        public String spNote;
        public String spPricebiaoqian;
        public int spShangpingprice;
        public int spShuliang;
        public String spShuomingshu;
        public int spStatus;
        public String spTupian;
        public String spUrl;
        public String spYiweima;
        public int spid;
    }

}
