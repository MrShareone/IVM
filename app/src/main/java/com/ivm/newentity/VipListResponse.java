package com.ivm.newentity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/5/14.
 */

public class VipListResponse implements Serializable{

    /**
     * Code : 200
     * Desc : 成功
     * result : [{"hyJibie":1,"hyLsbyue":0,"hyNote":"","hyRuhuitime":"2017-10-16 11:07:14","hyShbyue":0,"khid":10009,"shAddress":"中南民族大学三食堂二楼","shEmail":"","shFapiaoname":"小米米民大四食堂店","shFendianhao":"","shFullname":"小米米中南民族大学学生三食堂二楼店","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":200,"shMengzhuname":"","shName":"小米米民大三食堂二楼店","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"13914166715","shWeituoshoufeibiaozhi":1,"shYinkamincunru":100,"shYinkapeitaobilu":0.10000000149011612,"shYinkaxinyongxiane":0,"shZuanshimincunru":300,"shZuanshipeitaobilu":0.11111000180244446,"shZuanshixinyongxiane":100,"shid":116},{"hyJibie":1,"hyLsbyue":0,"hyNote":"","hyRuhuitime":"2017-10-27 14:28:48","hyShbyue":0,"khid":10009,"shAddress":"1234","shEmail":"","shFapiaoname":"贺氏小店","shFendianhao":"","shFullname":"贺氏小店","shFuwuzhuangtai":"2","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"","shName":"贺氏小店","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"15872393523","shWeituoshoufeibiaozhi":1,"shYinkamincunru":1000,"shYinkapeitaobilu":0.05000000074505806,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.20000000298023224,"shZuanshixinyongxiane":100,"shid":121},{"hyJibie":1,"hyLsbyue":0,"hyNote":"","hyRuhuitime":"2017-10-27 14:28:56","hyShbyue":0,"khid":10009,"shAddress":"","shEmail":"","shFapiaoname":"","shFendianhao":"","shFullname":"","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"","shName":"","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":1,"shTel":"18871483163","shWeituoshoufeibiaozhi":1,"shYinkamincunru":1000,"shYinkapeitaobilu":0.05000000074505806,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.10000000149011612,"shZuanshixinyongxiane":100,"shid":120},{"hyJibie":1,"hyLsbyue":0,"hyNote":"","hyRuhuitime":"2017-10-27 14:29:07","hyShbyue":0,"khid":10009,"shAddress":"中南民族大学三食堂二楼","shEmail":"","shFapiaoname":"犀米家民大四食堂店","shFendianhao":"","shFullname":"湖北爱微迈智能科技有限责任公司","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.492192 114.384887)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"犀米家","shName":"爱微迈公司","shPutongmincunru":0,"shPutongpeitaobilu":0,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"18907130210","shWeituoshoufeibiaozhi":1,"shYinkamincunru":1000,"shYinkapeitaobilu":0,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.11111100018024445,"shZuanshixinyongxiane":100,"shid":115},{"hyJibie":1,"hyLsbyue":0,"hyNote":"","hyRuhuitime":"2017-10-27 14:29:12","hyShbyue":0,"khid":10009,"shAddress":"中南民族大学","shEmail":"","shFapiaoname":"翁家家","shFendianhao":"","shFullname":"翁家家","shFuwuzhuangtai":"1","shGpsdizhi":"POINT(30.481666667 114.385555556)","shJinkapeitaobilu":0.10000000149011612,"shJinkaxinyongxiane":0,"shJnkamincunrui":1000000,"shMengzhuname":"","shName":"翁家家","shPutongmincunru":10,"shPutongpeitaobilu":0.008999999612569809,"shPutongxinyongxiane":0,"shStatus":100,"shTel":"15927327212","shWeituoshoufeibiaozhi":1,"shYinkamincunru":1000,"shYinkapeitaobilu":0.05000000074505806,"shYinkaxinyongxiane":0,"shZuanshimincunru":10000000,"shZuanshipeitaobilu":0.10000000149011612,"shZuanshixinyongxiane":100,"shid":109}]
     */

    public String Code;
    public String Desc;
    public List<VipMsg> result;


    public static class VipMsg implements Serializable{
        /**
         * hyJibie : 1
         * hyLsbyue : 0
         * hyNote :
         * hyRuhuitime : 2017-10-16 11:07:14
         * hyShbyue : 0
         * khid : 10009
         * shAddress : 中南民族大学三食堂二楼
         * shEmail :
         * shFapiaoname : 小米米民大四食堂店
         * shFendianhao :
         * shFullname : 小米米中南民族大学学生三食堂二楼店
         * shFuwuzhuangtai : 1
         * shGpsdizhi : POINT(30.492192 114.384887)
         * shJinkapeitaobilu : 0.10000000149011612
         * shJinkaxinyongxiane : 0
         * shJnkamincunrui : 200
         * shMengzhuname :
         * shName : 小米米民大三食堂二楼店
         * shPutongmincunru : 0
         * shPutongpeitaobilu : 0
         * shPutongxinyongxiane : 0
         * shStatus : 100
         * shTel : 13914166715
         * shWeituoshoufeibiaozhi : 1
         * shYinkamincunru : 100
         * shYinkapeitaobilu : 0.10000000149011612
         * shYinkaxinyongxiane : 0
         * shZuanshimincunru : 300
         * shZuanshipeitaobilu : 0.11111000180244446
         * shZuanshixinyongxiane : 100
         * shid : 116
         */

        public int hyJibie;
        public int hyLsbyue;
        public String hyNote;
        public String hyRuhuitime;
        public int hyShbyue;
        public int khid;
        public String shAddress;
        public String shEmail;
        public String shFapiaoname;
        public String shFendianhao;
        public String shFullname;
        public String shFuwuzhuangtai;
        public String shGpsdizhi;
        public String shJinkapeitaobilu;
        public int shJinkaxinyongxiane;
        public int shJnkamincunrui;
        public String shMengzhuname;
        public String shName;
        public int shPutongmincunru;
        public String shPutongpeitaobilu;
        public int shPutongxinyongxiane;
        public int shStatus;
        public String shTel;
        public int shWeituoshoufeibiaozhi;
        public int shYinkamincunru;
        public String shYinkapeitaobilu;
        public int shYinkaxinyongxiane;
        public int shZuanshimincunru;
        public String shZuanshipeitaobilu;
        public int shZuanshixinyongxiane;
        public int shid;
    }
}
