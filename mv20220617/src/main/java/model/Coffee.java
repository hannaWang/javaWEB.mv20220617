package model;

public class Coffee {
	//field
	String COF_NAME;
	int SUP_ID;
	double PRICE;
	int SALES;
	int TOTAL;

	
	//constructor
	public Coffee(){}
	public Coffee(String COF_NAME, int SUP_ID, double PRICE, int SALES, int TOTAL){
		this.COF_NAME = COF_NAME;
		this.SUP_ID = SUP_ID;
		this.TOTAL = TOTAL;
		this.SALES = SALES;
		this.PRICE = PRICE;
	}
	
	
	//getter setter
	public String getCOF_NAME() {
		return COF_NAME;
	}
	public void setCOF_NAME(String cOF_NAME) {
		COF_NAME = cOF_NAME;
	}
	public int getSUP_ID() {
		return SUP_ID;
	}
	public void setSUP_ID(int sUP_ID) {
		SUP_ID = sUP_ID;
	}
	public int getTOTAL() {
		return TOTAL;
	}
	public void setTOTAL(int tOTAL) {
		TOTAL = tOTAL;
	}
	public int getSALES() {
		return SALES;
	}
	public void setSALES(int sALES) {
		SALES = sALES;
	}
	public double getPRICE() {
		return PRICE;
	}
	public void setPRICE(double pRICE) {
		PRICE = pRICE;
	}

	
	
}
