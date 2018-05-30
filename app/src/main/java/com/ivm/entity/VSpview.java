package com.ivm.entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.math.BigDecimal;

public class VSpview implements Serializable {

    private Bitmap bitmap;

    private Long spid;

    private Long shid;

    private String spName;

    private BigDecimal spShangpingprice;

    private String spDizhifuwubiaoqian;

    private String spPricebiaoqian;

    private String spKexuanbiaoqian;

    private Integer spShuliang;

    private String spJianjie;

    private String spUrl;

    private String spShuomingshu;

    private String spTupian;

    private String spEweima;

    private String spYiweima;

    private Short spStatus;

    private String spNote;

    private String shName;

    private String shFullname;

    private String shFapiaoname;

    private String shTel;

    private String shMengzhuname;

    private String shFendianhao;

    private String shGpsdizhi;

    private Integer shZuanshixinyongxiane;

    private Float shZuanshipeitaobilu;

    private Integer shZuanshimincunru;

    private Integer shJinkaxinyongxiane;

    private Float shJinkapeitaobilu;

    private Integer shJnkamincunrui;

    private Integer shYinkaxinyongxiane;

    private Float shYinkapeitaobilu;

    private Integer shYinkamincunru;

    private Integer shPutongxinyongxiane;

    private Float shPutongpeitaobilu;

    private Integer shPutongmincunru;

    private String shAddress;

    private String shFuwuzhuangtai;

    private String shEmail;

    private Integer shStatus;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Long getSpid() {
        return spid;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }

    public Long getShid() {
        return shid;
    }

    public void setShid(Long shid) {
        this.shid = shid;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public BigDecimal getSpShangpingprice() {
        return spShangpingprice;
    }

    public void setSpShangpingprice(BigDecimal spShangpingprice) {
        this.spShangpingprice = spShangpingprice;
    }

    public String getSpDizhifuwubiaoqian() {
        return spDizhifuwubiaoqian;
    }

    public void setSpDizhifuwubiaoqian(String spDizhifuwubiaoqian) {
        this.spDizhifuwubiaoqian = spDizhifuwubiaoqian == null ? null : spDizhifuwubiaoqian.trim();
    }

    public String getSpPricebiaoqian() {
        return spPricebiaoqian;
    }

    public void setSpPricebiaoqian(String spPricebiaoqian) {
        this.spPricebiaoqian = spPricebiaoqian == null ? null : spPricebiaoqian.trim();
    }

    public String getSpKexuanbiaoqian() {
        return spKexuanbiaoqian;
    }

    public void setSpKexuanbiaoqian(String spKexuanbiaoqian) {
        this.spKexuanbiaoqian = spKexuanbiaoqian == null ? null : spKexuanbiaoqian.trim();
    }

    public Integer getSpShuliang() {
        return spShuliang;
    }

    public void setSpShuliang(Integer spShuliang) {
        this.spShuliang = spShuliang;
    }

    public String getSpJianjie() {
        return spJianjie;
    }

    public void setSpJianjie(String spJianjie) {
        this.spJianjie = spJianjie == null ? null : spJianjie.trim();
    }

    public String getSpUrl() {
        return spUrl;
    }

    public void setSpUrl(String spUrl) {
        this.spUrl = spUrl == null ? null : spUrl.trim();
    }

    public String getSpShuomingshu() {
        return spShuomingshu;
    }

    public void setSpShuomingshu(String spShuomingshu) {
        this.spShuomingshu = spShuomingshu == null ? null : spShuomingshu.trim();
    }

    public String getSpTupian() {
        return spTupian;
    }

    public void setSpTupian(String spTupian) {
        this.spTupian = spTupian == null ? null : spTupian.trim();
    }

    public String getSpEweima() {
        return spEweima;
    }

    public void setSpEweima(String spEweima) {
        this.spEweima = spEweima == null ? null : spEweima.trim();
    }

    public String getSpYiweima() {
        return spYiweima;
    }

    public void setSpYiweima(String spYiweima) {
        this.spYiweima = spYiweima == null ? null : spYiweima.trim();
    }

    public Short getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(Short spStatus) {
        this.spStatus = spStatus;
    }

    public String getSpNote() {
        return spNote;
    }

    public void setSpNote(String spNote) {
        this.spNote = spNote == null ? null : spNote.trim();
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName == null ? null : shName.trim();
    }

    public String getShFullname() {
        return shFullname;
    }

    public void setShFullname(String shFullname) {
        this.shFullname = shFullname == null ? null : shFullname.trim();
    }

    public String getShFapiaoname() {
        return shFapiaoname;
    }

    public void setShFapiaoname(String shFapiaoname) {
        this.shFapiaoname = shFapiaoname == null ? null : shFapiaoname.trim();
    }

    public String getShTel() {
        return shTel;
    }

    public void setShTel(String shTel) {
        this.shTel = shTel == null ? null : shTel.trim();
    }

    public String getShMengzhuname() {
        return shMengzhuname;
    }

    public void setShMengzhuname(String shMengzhuname) {
        this.shMengzhuname = shMengzhuname == null ? null : shMengzhuname.trim();
    }

    public String getShFendianhao() {
        return shFendianhao;
    }

    public void setShFendianhao(String shFendianhao) {
        this.shFendianhao = shFendianhao == null ? null : shFendianhao.trim();
    }

    public String getShGpsdizhi() {
        return shGpsdizhi;
    }

    public void setShGpsdizhi(String shGpsdizhi) {
        this.shGpsdizhi = shGpsdizhi == null ? null : shGpsdizhi.trim();
    }

    public Integer getShZuanshixinyongxiane() {
        return shZuanshixinyongxiane;
    }

    public void setShZuanshixinyongxiane(Integer shZuanshixinyongxiane) {
        this.shZuanshixinyongxiane = shZuanshixinyongxiane;
    }

    public Float getShZuanshipeitaobilu() {
        return shZuanshipeitaobilu;
    }

    public void setShZuanshipeitaobilu(Float shZuanshipeitaobilu) {
        this.shZuanshipeitaobilu = shZuanshipeitaobilu;
    }

    public Integer getShZuanshimincunru() {
        return shZuanshimincunru;
    }

    public void setShZuanshimincunru(Integer shZuanshimincunru) {
        this.shZuanshimincunru = shZuanshimincunru;
    }

    public Integer getShJinkaxinyongxiane() {
        return shJinkaxinyongxiane;
    }

    public void setShJinkaxinyongxiane(Integer shJinkaxinyongxiane) {
        this.shJinkaxinyongxiane = shJinkaxinyongxiane;
    }

    public Float getShJinkapeitaobilu() {
        return shJinkapeitaobilu;
    }

    public void setShJinkapeitaobilu(Float shJinkapeitaobilu) {
        this.shJinkapeitaobilu = shJinkapeitaobilu;
    }

    public Integer getShJnkamincunrui() {
        return shJnkamincunrui;
    }

    public void setShJnkamincunrui(Integer shJnkamincunrui) {
        this.shJnkamincunrui = shJnkamincunrui;
    }

    public Integer getShYinkaxinyongxiane() {
        return shYinkaxinyongxiane;
    }

    public void setShYinkaxinyongxiane(Integer shYinkaxinyongxiane) {
        this.shYinkaxinyongxiane = shYinkaxinyongxiane;
    }

    public Float getShYinkapeitaobilu() {
        return shYinkapeitaobilu;
    }

    public void setShYinkapeitaobilu(Float shYinkapeitaobilu) {
        this.shYinkapeitaobilu = shYinkapeitaobilu;
    }

    public Integer getShYinkamincunru() {
        return shYinkamincunru;
    }

    public void setShYinkamincunru(Integer shYinkamincunru) {
        this.shYinkamincunru = shYinkamincunru;
    }

    public Integer getShPutongxinyongxiane() {
        return shPutongxinyongxiane;
    }

    public void setShPutongxinyongxiane(Integer shPutongxinyongxiane) {
        this.shPutongxinyongxiane = shPutongxinyongxiane;
    }

    public Float getShPutongpeitaobilu() {
        return shPutongpeitaobilu;
    }

    public void setShPutongpeitaobilu(Float shPutongpeitaobilu) {
        this.shPutongpeitaobilu = shPutongpeitaobilu;
    }

    public Integer getShPutongmincunru() {
        return shPutongmincunru;
    }

    public void setShPutongmincunru(Integer shPutongmincunru) {
        this.shPutongmincunru = shPutongmincunru;
    }

    public String getShAddress() {
        return shAddress;
    }

    public void setShAddress(String shAddress) {
        this.shAddress = shAddress == null ? null : shAddress.trim();
    }

    public String getShFuwuzhuangtai() {
        return shFuwuzhuangtai;
    }

    public void setShFuwuzhuangtai(String shFuwuzhuangtai) {
        this.shFuwuzhuangtai = shFuwuzhuangtai == null ? null : shFuwuzhuangtai.trim();
    }

    public String getShEmail() {
        return shEmail;
    }

    public void setShEmail(String shEmail) {
        this.shEmail = shEmail == null ? null : shEmail.trim();
    }

    public Integer getShStatus() {
        return shStatus;
    }

    public void setShStatus(Integer shStatus) {
        this.shStatus = shStatus;
    }
}