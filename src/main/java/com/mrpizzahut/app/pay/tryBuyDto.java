package com.mrpizzahut.app.pay;


public class tryBuyDto {
	
	private String mobile1;
	private String  mobile2;
	private String  mobile3;
	private String  name;
	private String coupon;
	
	public String getcoupon() {
		return coupon;
	}
	public void setcoupon(String coupon) {
		this.coupon = coupon;
	}

	private int  kind;
	
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	
	////서버에서 사용할 변수
	private String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return coupon+","+kind;
	}

}
