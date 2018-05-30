package com.ivm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class VhuiyuanKH {
    private Long shid;

    private Long khid;

    private Integer hyJibie;

    private Date hyRuhuitime;

    private BigDecimal hyShbyue;

    private BigDecimal hyLsbyue;

    private String hyNote;

    private String khWeixinname;

    private Short khWeixinsex;

    private String khTel;

    private Short khStatus;

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

    public String getKhWeixinname() {
        return khWeixinname;
    }

    public void setKhWeixinname(String khWeixinname) {
        this.khWeixinname = khWeixinname == null ? null : khWeixinname.trim();
    }

    public Short getKhWeixinsex() {
        return khWeixinsex;
    }

    public void setKhWeixinsex(Short khWeixinsex) {
        this.khWeixinsex = khWeixinsex;
    }

    public String getKhTel() {
        return khTel;
    }

    public void setKhTel(String khTel) {
        this.khTel = khTel == null ? null : khTel.trim();
    }

    public Short getKhStatus() {
        return khStatus;
    }

    public void setKhStatus(Short khStatus) {
        this.khStatus = khStatus;
    }
}