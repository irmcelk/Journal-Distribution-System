package proje;

import java.io.Serializable;

public class DateInfo  implements Serializable  {
	private static final long serialVersionUID = 1L;
   private int startMonth;
   private int endMonth;
   private int startYear;
public DateInfo(int startMonth, int startYear) {
	super();
	this.startMonth = startMonth;
	this.startYear = startYear;
}

public int getStartMonth() {
	return startMonth;
}
public void setStartMonth(int startMonth) {
	this.startMonth = startMonth;
}
public int getEndMonth() {
	int temp=(startMonth+12)%12-1;
	if(temp<=0) {
		temp=temp+12;
	}
	endMonth=temp;
	return endMonth;
}

public int getStartYear() {
	return startYear;
}
public void setStartYear(int startYear) {
	this.startYear = startYear;
}

}
