package com.mrpizzahut.app;

public enum stringenums {
	    kakaoPay("kpay"),
	    vbank("vbank"),
	    card("card"),
	    cardMchtId("nxca_jt_il"),
	    vbanMchtId("nx_mid_il"),
	    sucPayNum("0021"),
	    ex("exe"),
	    nu("null");


	    private String messege;

	    stringenums(String messege){
	        this.messege=messege;
	    
	    }
	    public String getString() {
	        return messege;
	    }
}
