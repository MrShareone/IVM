package com.ivm.entity;import android.graphics.Bitmap;import java.io.Serializable;import java.math.BigDecimal;import java.sql.Timestamp;import java.text.DateFormat;import java.text.SimpleDateFormat;import java.util.Date;/** * * <br> * <b>功能：</b>TDingdanEntity<br> */public class TDingdan extends BaseEntity{	public Bitmap bitmap;	public java.lang.Long dDID; 	public java.lang.Long sPID; 	public java.lang.Long kHID; 	public java.lang.Long sHID; 	public java.lang.String ddState;   // 1|XX1未支付状态	/*2|X10已支付状态    3|X11信用支付状态    4|1XX备成状态    5|5XX已提货状态    6|1XXX订单撤销状态*/	public java.lang.String ddImei; 	public java.sql.Timestamp ddDingdanTime; 	public BigDecimal ddvalue; 	public java.lang.String ddKexuanBiaoqianZhi; 	public java.lang.String ddWeizhiFuwuBiaoqian; 	public java.lang.String ddZhifuhao; 	public java.util.Date ddZhifuTime; 	public BigDecimal ddFanxianPrice; 	public java.util.Date ddBeichengTime; 	public java.util.Date ddTihuoTime; 	public java.lang.String ddTihuoDidian;    //1|储物柜自取  2|柜台领取	public java.lang.String ddTihuoma; 	public java.util.Date ddKaipiaoTime; 	public java.lang.String ddFapiao; 	public java.lang.String shName;    //商户冗余信息	public java.lang.String shMengzhuName; 	public java.lang.String shFendianhao; 	public java.lang.String shTel; 	public java.lang.String shAddress; 	public java.lang.String khWeixinName; 	public java.lang.String khTel; 	public java.lang.Integer khHuiyuanJibie;    //1|普通会员     2|银卡会员      3|金卡会员    4|钻石会员	public java.lang.String khImei; 	public java.lang.String spName; 	public java.lang.String spPriceBiaoqian; 	public java.lang.String spKexuanBiaoqian; 	public java.lang.String spWeizhiBiaoqian; 	public java.lang.String spEweima; 	public java.lang.String ddShuomingshu; 	public java.lang.String ddQitaWenjian; 	public java.lang.String ddNote; 	public java.lang.Integer ddSPNum;	public java.lang.String ddJiageBiaoqianZhi;	public java.lang.String ddKehuLiuyan;	public BigDecimal   spShangpingPrice;	public java.lang.String spTupian;	public java.lang.String spJianjie;	public java.lang.String spURL;	public Bitmap getBitmap() {		return bitmap;	}	public void setBitmap(Bitmap bitmap) {		this.bitmap = bitmap;	}	public Long getdDID() {		return dDID;	}	public void setdDID(Long dDID) {		this.dDID = dDID;	}	public Long getsPID() {		return sPID;	}	public void setsPID(Long sPID) {		this.sPID = sPID;	}	public Long getkHID() {		return kHID;	}	public void setkHID(Long kHID) {		this.kHID = kHID;	}	public Long getsHID() {		return sHID;	}	public void setsHID(Long sHID) {		this.sHID = sHID;	}	public String getDdState() {		return ddState;	}	public void setDdState(String ddState) {		this.ddState = ddState;	}	public String getDdImei() {		return ddImei;	}	public void setDdImei(String ddImei) {		this.ddImei = ddImei;	}	public Timestamp getDdDingdanTime() {		return ddDingdanTime;	}	public void setDdDingdanTime(Timestamp ddDingdanTime) {		this.ddDingdanTime = ddDingdanTime;	}	public BigDecimal getDdvalue() {		return ddvalue;	}	public void setDdvalue(BigDecimal ddvalue) {		this.ddvalue = ddvalue;	}	public String getDdKexuanBiaoqianZhi() {		return ddKexuanBiaoqianZhi;	}	public void setDdKexuanBiaoqianZhi(String ddKexuanBiaoqianZhi) {		this.ddKexuanBiaoqianZhi = ddKexuanBiaoqianZhi;	}	public String getDdWeizhiFuwuBiaoqian() {		return ddWeizhiFuwuBiaoqian;	}	public void setDdWeizhiFuwuBiaoqian(String ddWeizhiFuwuBiaoqian) {		this.ddWeizhiFuwuBiaoqian = ddWeizhiFuwuBiaoqian;	}	public String getDdZhifuhao() {		return ddZhifuhao;	}	public void setDdZhifuhao(String ddZhifuhao) {		this.ddZhifuhao = ddZhifuhao;	}	public Date getDdZhifuTime() {		return ddZhifuTime;	}	public void setDdZhifuTime(Date ddZhifuTime) {		this.ddZhifuTime = ddZhifuTime;	}	public BigDecimal getDdFanxianPrice() {		return ddFanxianPrice;	}	public void setDdFanxianPrice(BigDecimal ddFanxianPrice) {		this.ddFanxianPrice = ddFanxianPrice;	}	public Date getDdBeichengTime() {		return ddBeichengTime;	}	public void setDdBeichengTime(Date ddBeichengTime) {		this.ddBeichengTime = ddBeichengTime;	}	public Date getDdTihuoTime() {		return ddTihuoTime;	}	public void setDdTihuoTime(Date ddTihuoTime) {		this.ddTihuoTime = ddTihuoTime;	}	public String getDdTihuoDidian() {		return ddTihuoDidian;	}	public void setDdTihuoDidian(String ddTihuoDidian) {		this.ddTihuoDidian = ddTihuoDidian;	}	public String getDdTihuoma() {		return ddTihuoma;	}	public void setDdTihuoma(String ddTihuoma) {		this.ddTihuoma = ddTihuoma;	}	public Date getDdKaipiaoTime() {		return ddKaipiaoTime;	}	public void setDdKaipiaoTime(Date ddKaipiaoTime) {		this.ddKaipiaoTime = ddKaipiaoTime;	}	public String getDdFapiao() {		return ddFapiao;	}	public void setDdFapiao(String ddFapiao) {		this.ddFapiao = ddFapiao;	}	public String getShName() {		return shName;	}	public void setShName(String shName) {		this.shName = shName;	}	public String getShMengzhuName() {		return shMengzhuName;	}	public void setShMengzhuName(String shMengzhuName) {		this.shMengzhuName = shMengzhuName;	}	public String getShFendianhao() {		return shFendianhao;	}	public void setShFendianhao(String shFendianhao) {		this.shFendianhao = shFendianhao;	}	public String getShTel() {		return shTel;	}	public void setShTel(String shTel) {		this.shTel = shTel;	}	public String getShAddress() {		return shAddress;	}	public void setShAddress(String shAddress) {		this.shAddress = shAddress;	}	public String getKhWeixinName() {		return khWeixinName;	}	public void setKhWeixinName(String khWeixinName) {		this.khWeixinName = khWeixinName;	}	public String getKhTel() {		return khTel;	}	public void setKhTel(String khTel) {		this.khTel = khTel;	}	public Integer getKhHuiyuanJibie() {		return khHuiyuanJibie;	}	public void setKhHuiyuanJibie(Integer khHuiyuanJibie) {		this.khHuiyuanJibie = khHuiyuanJibie;	}	public String getKhImei() {		return khImei;	}	public void setKhImei(String khImei) {		this.khImei = khImei;	}	public String getSpName() {		return spName;	}	public void setSpName(String spName) {		this.spName = spName;	}	public String getSpPriceBiaoqian() {		return spPriceBiaoqian;	}	public void setSpPriceBiaoqian(String spPriceBiaoqian) {		this.spPriceBiaoqian = spPriceBiaoqian;	}	public String getSpKexuanBiaoqian() {		return spKexuanBiaoqian;	}	public void setSpKexuanBiaoqian(String spKexuanBiaoqian) {		this.spKexuanBiaoqian = spKexuanBiaoqian;	}	public String getSpWeizhiBiaoqian() {		return spWeizhiBiaoqian;	}	public void setSpWeizhiBiaoqian(String spWeizhiBiaoqian) {		this.spWeizhiBiaoqian = spWeizhiBiaoqian;	}	public String getSpEweima() {		return spEweima;	}	public void setSpEweima(String spEweima) {		this.spEweima = spEweima;	}	public String getDdShuomingshu() {		return ddShuomingshu;	}	public void setDdShuomingshu(String ddShuomingshu) {		this.ddShuomingshu = ddShuomingshu;	}	public String getDdQitaWenjian() {		return ddQitaWenjian;	}	public void setDdQitaWenjian(String ddQitaWenjian) {		this.ddQitaWenjian = ddQitaWenjian;	}	public String getDdNote() {		return ddNote;	}	public void setDdNote(String ddNote) {		this.ddNote = ddNote;	}	public Integer getDdSPNum() {		return ddSPNum;	}	public void setDdSPNum(Integer ddSPNum) {		this.ddSPNum = ddSPNum;	}	public String getDdJiageBiaoqianZhi() {		return ddJiageBiaoqianZhi;	}	public void setDdJiageBiaoqianZhi(String ddJiageBiaoqianZhi) {		this.ddJiageBiaoqianZhi = ddJiageBiaoqianZhi;	}	public String getDdKehuLiuyan() {		return ddKehuLiuyan;	}	public void setDdKehuLiuyan(String ddKehuLiuyan) {		this.ddKehuLiuyan = ddKehuLiuyan;	}	public BigDecimal getSpShangpingPrice() {		return spShangpingPrice;	}	public void setSpShangpingPrice(BigDecimal spShangpingPrice) {		this.spShangpingPrice = spShangpingPrice;	}	public String getSpTupian() {		return spTupian;	}	public void setSpTupian(String spTupian) {		this.spTupian = spTupian;	}	public String getSpJianjie() {		return spJianjie;	}	public void setSpJianjie(String spJianjie) {		this.spJianjie = spJianjie;	}	public String getSpURL() {		return spURL;	}	public void setSpURL(String spURL) {		this.spURL = spURL;	}}