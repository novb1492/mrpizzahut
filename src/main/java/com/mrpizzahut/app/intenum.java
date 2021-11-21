package com.mrpizzahut.app;

public enum intenum {
	  doneFlag(1),
	  cancleFlag(1),
	  defaultFlag(0);



	    private int num;

	    intenum(int num){
	        this.num=num;
	    
	    }
	    public int getInt() {
	        return num;
	    }    
}
