package proje;

import java.io.Serializable;

public class Individual extends Subscriber  implements Serializable  {
	private static final long serialVersionUID = 1L;
	 private String creditCardNr;
	 private int expireMonth;
	 private int expireYear;
	 private int CCV;
	 //dateler kart son kullanma tarihi
	public Individual(String name,String address,String creditCardNr, int expireMonth, int expireYear, int CCV) {
		super(name, address);
		this.creditCardNr = creditCardNr;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
		this.CCV = CCV;
	}


	public String getCreditCardNr() {
		return creditCardNr;
	}



	public int getExpireMonth() {
		return expireMonth;
	}



	public int getExpireYear() {
		return expireYear;
	}



	public int getCCV() {
		return CCV;
	}



	@Override
	public String getBillingInformation() {
		return "Individual [creditCardNr=" + creditCardNr + ", expireMonth=" + expireMonth + ", expireYear="
				+ expireYear + ", CCV=" + CCV + "]";
	}
	@Override
    public String toString() {
        return "Subscriber:"+getName();
    }
}
