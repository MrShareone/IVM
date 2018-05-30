package com.ivm.entity;

import java.math.BigDecimal;

/**
 *
 * <br>
 * <b>功能：</b>TDanjuEntity<br>
 */
//@JsonIgnoreProperties(ignoreUnknown = true)，
public class TDanju extends BaseEntity {


	private java.lang.Long dJID;//
	private java.lang.Integer djZhangwuLeixing;//   1|客户购买爱微币
	/*  2|客户转冲爱微币
      3|客户在商户窗口充值
      4|客户购买商品
      5|商户发放临时币
      6|商户发放永久币
      7|客户支付订单
      8|客户撤销订单
      9|爱微币转账
      10|公司和商户结算
      11|临时币过期
      12|客商账务调整
      13|商司账务调整
      14|商户 IVB解冻*/
	private java.lang.Long sHID;//
	private BigDecimal shRMBStart;//
	private BigDecimal shRMBChange;//
	private BigDecimal shRMBEnd;//
	private BigDecimal shIVBStart;//
	private BigDecimal shIVBChange;//
	private BigDecimal shIVBEnd;//
	private BigDecimal shYJBStart;//
	private BigDecimal shYJBEnd;//
	private BigDecimal shYJBChange;//
	private BigDecimal shLSBStart;//
	private BigDecimal shLSBEnd;//
	private BigDecimal shLSBChange;//
	private BigDecimal shSXF;//
	private BigDecimal shFandianShouru;//
	private Long kHID;//
	private java.lang.Integer djDuishou;//   0| 无关
	//            1|公司
//            2| 商户
	private BigDecimal khIVBStart;//
	private BigDecimal khIVBEnd;//
	private BigDecimal khIVBChange;//
	private BigDecimal khLSBStart;//
	private BigDecimal khLSBEnd;//
	private BigDecimal khLSBChange;//
	private java.lang.Long iVMID;//
	private BigDecimal iVMMoneyStart;//
	private BigDecimal iVMMoneyEnd;//
	private BigDecimal iVMMoneyChange;//
	private BigDecimal iVMIVBStart;//
	private BigDecimal iVMIVBEnd;//
	private BigDecimal iVMIVBChange;//
	private BigDecimal iVMSHBStart;//
	private BigDecimal iVMSHBChange;//
	private BigDecimal iVMSHBEnd;//
	private BigDecimal iVMCZFShouru;//
	private BigDecimal iVMCZFZhichu;//
	private BigDecimal iVMDGFShouru;//
	private BigDecimal iVMJYFShouru;//
	private java.lang.Integer djZhifuQudao;//   0| 非现金支付
//            1| 支付宝支付
//            2| 微信支付
//            3| 银联支付
//            4| QQ支付
//            5| 电信翼支付

	private java.lang.String djSanfangDanjuHao;//
	private java.lang.Integer djChongzheng;//   1|冲正
	//            0|未冲正
	private java.lang.Integer djDuizhang;//   0| 未对账
//            1| 已对账

	//            在对账的时候，将对账标志设置为1，并做一笔解冻操作，则将商户的IVB转换为RMB，就可以提现了。
	private java.util.Date djDuizhangTime;//
	private java.sql.Timestamp djJIluTime;//
	private java.lang.String djNote;//

	private BigDecimal khSHBStart;//
	private BigDecimal khSHBChange;//
	private BigDecimal khSHBEnd;//

	private java.lang.String shName;
	private java.lang.String khTel;
	private java.lang.String iVMGSName;


	private BigDecimal khBonus;

	public BigDecimal getKhBonus() {
		return khBonus;
	}

	public void setKhBonus(BigDecimal khBonus) {
		this.khBonus = khBonus;
	}

	public java.lang.Long getdJID() {
		return dJID;
	}
	public void setdJID(java.lang.Long dJID) {
		this.dJID = dJID;
	}
	public java.lang.Integer getDjZhangwuLeixing() {
		return djZhangwuLeixing;
	}
	public void setDjZhangwuLeixing(java.lang.Integer djZhangwuLeixing) {
		this.djZhangwuLeixing = djZhangwuLeixing;
	}
	public java.lang.Long getsHID() {
		return sHID;
	}
	public void setsHID(java.lang.Long sHID) {
		this.sHID = sHID;
	}
	public BigDecimal getShRMBStart() {
		return shRMBStart;
	}
	public void setShRMBStart(BigDecimal shRMBStart) {
		this.shRMBStart = shRMBStart;
	}
	public BigDecimal getShRMBChange() {
		return shRMBChange;
	}
	public void setShRMBChange(BigDecimal shRMBChange) {
		this.shRMBChange = shRMBChange;
	}
	public BigDecimal getShRMBEnd() {
		return shRMBEnd;
	}
	public void setShRMBEnd(BigDecimal shRMBEnd) {
		this.shRMBEnd = shRMBEnd;
	}
	public BigDecimal getShIVBStart() {
		return shIVBStart;
	}
	public void setShIVBStart(BigDecimal shIVBStart) {
		this.shIVBStart = shIVBStart;
	}
	public BigDecimal getShIVBChange() {
		return shIVBChange;
	}
	public void setShIVBChange(BigDecimal shIVBChange) {
		this.shIVBChange = shIVBChange;
	}
	public BigDecimal getShIVBEnd() {
		return shIVBEnd;
	}
	public void setShIVBEnd(BigDecimal shIVBEnd) {
		this.shIVBEnd = shIVBEnd;
	}
	public BigDecimal getShYJBStart() {
		return shYJBStart;
	}
	public void setShYJBStart(BigDecimal shYJBStart) {
		this.shYJBStart = shYJBStart;
	}
	public BigDecimal getShYJBEnd() {
		return shYJBEnd;
	}
	public void setShYJBEnd(BigDecimal shYJBEnd) {
		this.shYJBEnd = shYJBEnd;
	}
	public BigDecimal getShYJBChange() {
		return shYJBChange;
	}
	public void setShYJBChange(BigDecimal shYJBChange) {
		this.shYJBChange = shYJBChange;
	}
	public BigDecimal getShLSBStart() {
		return shLSBStart;
	}
	public void setShLSBStart(BigDecimal shLSBStart) {
		this.shLSBStart = shLSBStart;
	}
	public BigDecimal getShLSBEnd() {
		return shLSBEnd;
	}
	public void setShLSBEnd(BigDecimal shLSBEnd) {
		this.shLSBEnd = shLSBEnd;
	}
	public BigDecimal getShLSBChange() {
		return shLSBChange;
	}
	public void setShLSBChange(BigDecimal shLSBChange) {
		this.shLSBChange = shLSBChange;
	}
	public BigDecimal getShSXF() {
		return shSXF;
	}
	public void setShSXF(BigDecimal shSXF) {
		this.shSXF = shSXF;
	}
	public BigDecimal getShFandianShouru() {
		return shFandianShouru;
	}
	public void setShFandianShouru(BigDecimal shFandianShouru) {
		this.shFandianShouru = shFandianShouru;
	}
	public Long getkHID() {
		return kHID;
	}
	public void setkHID(Long kHID) {
		this.kHID = kHID;
	}
	public java.lang.Integer getDjDuishou() {
		return djDuishou;
	}
	public void setDjDuishou(java.lang.Integer djDuishou) {
		this.djDuishou = djDuishou;
	}
	public BigDecimal getKhIVBStart() {
		return khIVBStart;
	}
	public void setKhIVBStart(BigDecimal khIVBStart) {
		this.khIVBStart = khIVBStart;
	}
	public BigDecimal getKhIVBEnd() {
		return khIVBEnd;
	}
	public void setKhIVBEnd(BigDecimal khIVBEnd) {
		this.khIVBEnd = khIVBEnd;
	}
	public BigDecimal getKhIVBChange() {
		return khIVBChange;
	}
	public void setKhIVBChange(BigDecimal khIVBChange) {
		this.khIVBChange = khIVBChange;
	}
	public BigDecimal getKhLSBStart() {
		return khLSBStart;
	}
	public void setKhLSBStart(BigDecimal khLSBStart) {
		this.khLSBStart = khLSBStart;
	}
	public BigDecimal getKhLSBEnd() {
		return khLSBEnd;
	}
	public void setKhLSBEnd(BigDecimal khLSBEnd) {
		this.khLSBEnd = khLSBEnd;
	}
	public BigDecimal getKhLSBChange() {
		return khLSBChange;
	}
	public void setKhLSBChange(BigDecimal khLSBChange) {
		this.khLSBChange = khLSBChange;
	}
	public java.lang.Long getiVMID() {
		return iVMID;
	}
	public void setiVMID(java.lang.Long iVMID) {
		this.iVMID = iVMID;
	}
	public BigDecimal getiVMMoneyStart() {
		return iVMMoneyStart;
	}
	public void setiVMMoneyStart(BigDecimal iVMMoneyStart) {
		this.iVMMoneyStart = iVMMoneyStart;
	}
	public BigDecimal getiVMMoneyEnd() {
		return iVMMoneyEnd;
	}
	public void setiVMMoneyEnd(BigDecimal iVMMoneyEnd) {
		this.iVMMoneyEnd = iVMMoneyEnd;
	}
	public BigDecimal getiVMMoneyChange() {
		return iVMMoneyChange;
	}
	public void setiVMMoneyChange(BigDecimal iVMMoneyChange) {
		this.iVMMoneyChange = iVMMoneyChange;
	}
	public BigDecimal getiVMIVBStart() {
		return iVMIVBStart;
	}
	public void setiVMIVBStart(BigDecimal iVMIVBStart) {
		this.iVMIVBStart = iVMIVBStart;
	}
	public BigDecimal getiVMIVBEnd() {
		return iVMIVBEnd;
	}
	public void setiVMIVBEnd(BigDecimal iVMIVBEnd) {
		this.iVMIVBEnd = iVMIVBEnd;
	}
	public BigDecimal getiVMIVBChange() {
		return iVMIVBChange;
	}
	public void setiVMIVBChange(BigDecimal iVMIVBChange) {
		this.iVMIVBChange = iVMIVBChange;
	}
	public BigDecimal getiVMSHBStart() {
		return iVMSHBStart;
	}
	public void setiVMSHBStart(BigDecimal iVMSHBStart) {
		this.iVMSHBStart = iVMSHBStart;
	}
	public BigDecimal getiVMSHBChange() {
		return iVMSHBChange;
	}
	public void setiVMSHBChange(BigDecimal iVMSHBChange) {
		this.iVMSHBChange = iVMSHBChange;
	}
	public BigDecimal getiVMSHBEnd() {
		return iVMSHBEnd;
	}
	public void setiVMSHBEnd(BigDecimal iVMSHBEnd) {
		this.iVMSHBEnd = iVMSHBEnd;
	}
	public BigDecimal getiVMCZFShouru() {
		return iVMCZFShouru;
	}
	public void setiVMCZFShouru(BigDecimal iVMCZFShouru) {
		this.iVMCZFShouru = iVMCZFShouru;
	}
	public BigDecimal getiVMCZFZhichu() {
		return iVMCZFZhichu;
	}
	public void setiVMCZFZhichu(BigDecimal iVMCZFZhichu) {
		this.iVMCZFZhichu = iVMCZFZhichu;
	}
	public BigDecimal getiVMDGFShouru() {
		return iVMDGFShouru;
	}
	public void setiVMDGFShouru(BigDecimal iVMDGFShouru) {
		this.iVMDGFShouru = iVMDGFShouru;
	}
	public BigDecimal getiVMJYFShouru() {
		return iVMJYFShouru;
	}
	public void setiVMJYFShouru(BigDecimal iVMJYFShouru) {
		this.iVMJYFShouru = iVMJYFShouru;
	}
	public java.lang.Integer getDjZhifuQudao() {
		return djZhifuQudao;
	}
	public void setDjZhifuQudao(java.lang.Integer djZhifuQudao) {
		this.djZhifuQudao = djZhifuQudao;
	}
	public java.lang.String getDjSanfangDanjuHao() {
		return djSanfangDanjuHao;
	}
	public void setDjSanfangDanjuHao(java.lang.String djSanfangDanjuHao) {
		this.djSanfangDanjuHao = djSanfangDanjuHao;
	}
	public java.lang.Integer getDjChongzheng() {
		return djChongzheng;
	}
	public void setDjChongzheng(java.lang.Integer djChongzheng) {
		this.djChongzheng = djChongzheng;
	}
	public java.lang.Integer getDjDuizhang() {
		return djDuizhang;
	}
	public void setDjDuizhang(java.lang.Integer djDuizhang) {
		this.djDuizhang = djDuizhang;
	}
	public java.util.Date getDjDuizhangTime() {
		return djDuizhangTime;
	}
	public void setDjDuizhangTime(java.util.Date djDuizhangTime) {
		this.djDuizhangTime = djDuizhangTime;
	}
	public java.sql.Timestamp getDjJIluTime() {
		return djJIluTime;
	}
	public void setDjJIluTime(java.sql.Timestamp djJIluTime) {
		this.djJIluTime = djJIluTime;
	}
	public java.lang.String getDjNote() {
		return djNote;
	}
	public void setDjNote(java.lang.String djNote) {
		this.djNote = djNote;
	}
	public BigDecimal getKhSHBStart() {
		return khSHBStart;
	}
	public void setKhSHBStart(BigDecimal khSHBStart) {
		this.khSHBStart = khSHBStart;
	}
	public BigDecimal getKhSHBChange() {
		return khSHBChange;
	}
	public void setKhSHBChange(BigDecimal khSHBChange) {
		this.khSHBChange = khSHBChange;
	}
	public BigDecimal getKhSHBEnd() {
		return khSHBEnd;
	}
	public void setKhSHBEnd(BigDecimal khSHBEnd) {
		this.khSHBEnd = khSHBEnd;
	}
	public java.lang.String getShName() {
		return shName;
	}
	public void setShName(java.lang.String shName) {
		this.shName = shName;
	}
	public java.lang.String getKhTel() {
		return khTel;
	}
	public void setKhTel(java.lang.String khTel) {
		this.khTel = khTel;
	}
	public java.lang.String getiVMGSName() {
		return iVMGSName;
	}
	public void setiVMGSName(java.lang.String iVMGSName) {
		this.iVMGSName = iVMGSName;
	}



}

