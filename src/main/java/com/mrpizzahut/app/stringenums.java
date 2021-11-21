package com.mrpizzahut.app;

public enum stringenums {
	  	change("change"),
	    defalutErrorMessage("알 수없는 에러발생"),
	    insert("insert"),
	    update("update"),
	    delete("delete"),
	    first("first"),
	    noFirst("noFirst"),
	    reset("reset"),
	    tooMany("tooMany"),
	    role_user("role_user"),
	    flag("flag"),
	    message("message"),
	    data("data"),
	    statusPaid("paid"),
	    statusReady("ready"),
	    reservation("reservation"),
	    tooSmall("small"),
	    tooBig("big"),
	    collect("collect"),
	    find("find"),
	    confrim("confrim"),
	    food("food"),
	    product("product"),
	    kakaoPay("kpay"),
	    vbank("vbank"),
	    card("card"),
	    cardMchtId("nxca_jt_il"),
	    vbanMchtId("nx_mid_il"),
	    sucPayNum("0021");


	    private String messege;

	    stringenums(String messege){
	        this.messege=messege;
	    
	    }
	    public String getString() {
	        return messege;
	    }
}
