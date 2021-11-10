package com.mrpizzahut.app.pay;

import java.sql.Timestamp;

public class couponDto {
	
    public int getCoid() {
		return coid;
	}

	public void setCoid(int coid) {
		this.coid = coid;
	}

	public String getCoUsedEmail() {
		return coUsedEmail;
	}

	public void setCoUsedEmail(String coUsedEmail) {
		this.coUsedEmail = coUsedEmail;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(int usedFlag) {
		this.usedFlag = usedFlag;
	}

	public Timestamp getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Timestamp usedDate) {
		this.usedDate = usedDate;
	}

	public String getCoMchtTrdNo() {
		return coMchtTrdNo;
	}

	public void setCoMchtTrdNo(String coMchtTrdNo) {
		this.coMchtTrdNo = coMchtTrdNo;
	}

	public String getCoKind() {
		return coKind;
	}

	public void setCoKind(String coKind) {
		this.coKind = coKind;
	}

	public int getCoNum() {
		return coNum;
	}

	public void setCoNum(int coNum) {
		this.coNum = coNum;
	}

	public Timestamp getCocreated() {
		return cocreated;
	}

	public void setCocreated(Timestamp cocreated) {
		this.cocreated = cocreated;
	}

	public Timestamp getCoExpired() {
		return coExpired;
	}

	public void setCoExpired(Timestamp coExpired) {
		this.coExpired = coExpired;
	}

	private int coid;

    private String coUsedEmail;

    private String couponName;

    private int usedFlag;

    private Timestamp usedDate;

    private String coMchtTrdNo;

    private String coKind;

    private int coNum;

    private Timestamp cocreated;

    private Timestamp coExpired;
}
