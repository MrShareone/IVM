package com.ivm.entity;import java.math.BigDecimal;/** *  * <br> * <b>功能：</b>THuiyuanEntity<br> */public class THuiyuan extends BaseEntity {			private Long hYID;//	private Long sHID;//	private Long kHID;//	private Integer hyJibie;//   1|普通会员//            2|银卡会员//            3|金卡会员//            4|钻石会员	private java.sql.Timestamp hyRuhuiTime;//   	private BigDecimal hySHBYue;//   	private BigDecimal hyLSBYue;//   	private String hyNote;//	public Long getHYID() {	    return this.hYID;	}	public void setHYID(Long hYID) {	    this.hYID=hYID;	}	public Long getSHID() {	    return this.sHID;	}	public void setSHID(Long sHID) {	    this.sHID=sHID;	}	public Long getKHID() {	    return this.kHID;	}	public void setKHID(Long kHID) {	    this.kHID=kHID;	}	public Integer getHyJibie() {	    return this.hyJibie;	}	public void setHyJibie(Integer hyJibie) {	    this.hyJibie=hyJibie;	}	public java.sql.Timestamp getHyRuhuiTime() {	    return this.hyRuhuiTime;	}	public void setHyRuhuiTime(java.sql.Timestamp hyRuhuiTime) {	    this.hyRuhuiTime=hyRuhuiTime;	}	public BigDecimal getHySHBYue() {	    return this.hySHBYue;	}	public void setHySHBYue(BigDecimal hySHBYue) {	    this.hySHBYue=hySHBYue;	}	public BigDecimal getHyLSBYue() {	    return this.hyLSBYue;	}	public void setHyLSBYue(BigDecimal hyLSBYue) {	    this.hyLSBYue=hyLSBYue;	}	public String getHyNote() {		return hyNote;	}	public void setHyNote(String hyNote) {		this.hyNote = hyNote;	}	}