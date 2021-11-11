package com.mrpizzahut.app.pay.settle;


public class settleDto {
	 String mchtId;//상점아이디
     String outStatCd;          //결과코드
     String outRsltCd;          //거절코드
     String outRsltMsg;         //결과메세지
     String method;             //결제수단
     String mchtTrdNo;          //상점주문번호
     String mchtCustId;         //상점고객아이디
     String trdNo;                           //세틀뱅크 거래번호
     String trdAmt;                         //거래금액
     String mchtParam;          //상점 예약필드
     String authDt;                         //승인일시
     String authNo;                         //승인번호
     String reqIssueDt;     	       	//채번요청일시
     String intMon;                         //할부개월수
     String fnNm;                             //카드사명
     String fnCd;                             //카드사코드
     String pointTrdNo;                 //포인트거래번호
     String pointTrdAmt;               //포인트거래금액
     String cardTrdAmt;                 //신용카드결제금액
     String vtlAcntNo;          //가상계좌번호
     String expireDt;                     //입금기한
     String cphoneNo;                     //휴대폰번호
     String billKey;      
     
     //결제완료시 가맹점 포인트 여부
     int point;
     ///환불요청시 써야하는 변수
     int cnclOrd;
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getOutStatCd() {
		return outStatCd;
	}
	public void setOutStatCd(String outStatCd) {
		this.outStatCd = outStatCd;
	}
	public String getOutRsltCd() {
		return outRsltCd;
	}
	public void setOutRsltCd(String outRsltCd) {
		this.outRsltCd = outRsltCd;
	}
	public String getOutRsltMsg() {
		return outRsltMsg;
	}
	public void setOutRsltMsg(String outRsltMsg) {
		this.outRsltMsg = outRsltMsg;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMchtTrdNo() {
		return mchtTrdNo;
	}
	public void setMchtTrdNo(String mchtTrdNo) {
		this.mchtTrdNo = mchtTrdNo;
	}
	public String getMchtCustId() {
		return mchtCustId;
	}
	public void setMchtCustId(String mchtCustId) {
		this.mchtCustId = mchtCustId;
	}
	public String getTrdNo() {
		return trdNo;
	}
	public void setTrdNo(String trdNo) {
		this.trdNo = trdNo;
	}
	public String getTrdAmt() {
		return trdAmt;
	}
	public void setTrdAmt(String trdAmt) {
		this.trdAmt = trdAmt;
	}
	public String getMchtParam() {
		return mchtParam;
	}
	public void setMchtParam(String mchtParam) {
		this.mchtParam = mchtParam;
	}
	public String getAuthDt() {
		return authDt;
	}
	public void setAuthDt(String authDt) {
		this.authDt = authDt;
	}
	public String getAuthNo() {
		return authNo;
	}
	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}
	public String getReqIssueDt() {
		return reqIssueDt;
	}
	public void setReqIssueDt(String reqIssueDt) {
		this.reqIssueDt = reqIssueDt;
	}
	public String getIntMon() {
		return intMon;
	}
	public void setIntMon(String intMon) {
		this.intMon = intMon;
	}
	public String getFnNm() {
		return fnNm;
	}
	public void setFnNm(String fnNm) {
		this.fnNm = fnNm;
	}
	public String getFnCd() {
		return fnCd;
	}
	public void setFnCd(String fnCd) {
		this.fnCd = fnCd;
	}
	public String getPointTrdNo() {
		return pointTrdNo;
	}
	public void setPointTrdNo(String pointTrdNo) {
		this.pointTrdNo = pointTrdNo;
	}
	public String getPointTrdAmt() {
		return pointTrdAmt;
	}
	public void setPointTrdAmt(String pointTrdAmt) {
		this.pointTrdAmt = pointTrdAmt;
	}
	public String getCardTrdAmt() {
		return cardTrdAmt;
	}
	public void setCardTrdAmt(String cardTrdAmt) {
		this.cardTrdAmt = cardTrdAmt;
	}
	public String getVtlAcntNo() {
		return vtlAcntNo;
	}
	public void setVtlAcntNo(String vtlAcntNo) {
		this.vtlAcntNo = vtlAcntNo;
	}
	public String getExpireDt() {
		return expireDt;
	}
	public void setExpireDt(String expireDt) {
		this.expireDt = expireDt;
	}
	public String getCphoneNo() {
		return cphoneNo;
	}
	public void setCphoneNo(String cphoneNo) {
		this.cphoneNo = cphoneNo;
	}
	public String getBillKey() {
		return billKey;
	}
	public void setBillKey(String billKey) {
		this.billKey = billKey;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getCnclOrd() {
		return cnclOrd;
	}
	public void setCnclOrd(int cnclOrd) {
		this.cnclOrd = cnclOrd;
	}

}
