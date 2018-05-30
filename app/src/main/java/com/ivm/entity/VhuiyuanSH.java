package com.ivm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VhuiyuanSH implements Serializable{
    private Long shid;

    private Long khid;

    private Integer hyJibie;

    private Date hyRuhuitime;

    private BigDecimal hyShbyue;

    private BigDecimal hyLsbyue;

    private String hyNote;

    private String shName;

    private String shFullname;

    private String shFapiaoname;

    private String shTel;

    private String shMengzhuname;

    private String shFendianhao;

    private String shGpsdizhi;

    private Integer shWeituoshoufeibiaozhi;

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

    public Long getShid() {
        return shid;
    }

    public void setShid(Long shid) {
        this.shid = shid;
    }

    public Long getKhid() {
        return khid;
    }

    public void setKhid(Long khid) {
        this.khid = khid;
    }

    public Integer getHyJibie() {
        return hyJibie;
    }

    public void setHyJibie(Integer hyJibie) {
        this.hyJibie = hyJibie;
    }

    public Date getHyRuhuitime() {
        return hyRuhuitime;
    }

    public void setHyRuhuitime(Date hyRuhuitime) {
        this.hyRuhuitime = hyRuhuitime;
    }

    public BigDecimal getHyShbyue() {
        return hyShbyue;
    }

    public void setHyShbyue(BigDecimal hyShbyue) {
        this.hyShbyue = hyShbyue;
    }

    public BigDecimal getHyLsbyue() {
        return hyLsbyue;
    }

    public void setHyLsbyue(BigDecimal hyLsbyue) {
        this.hyLsbyue = hyLsbyue;
    }

    public String getHyNote() {
        return hyNote;
    }

    public void setHyNote(String hyNote) {
        this.hyNote = hyNote == null ? null : hyNote.trim();
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

    public Integer getShWeituoshoufeibiaozhi() {
        return shWeituoshoufeibiaozhi;
    }

    public void setShWeituoshoufeibiaozhi(Integer shWeituoshoufeibiaozhi) {
        this.shWeituoshoufeibiaozhi = shWeituoshoufeibiaozhi;
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