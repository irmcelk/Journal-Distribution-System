package proje;

import java.io.Serializable;
import java.util.ArrayList;

public class Journal  implements Serializable  {
 private static final long serialVersionUID = 1L;
private String name;
  private String issn;
  private int frequency; //bir yılda kaç kez yayınlanacağı
  private double issuePrice;
  private ArrayList<Subscription> subscriptions;

  public Journal(String name, String issn, int frequency, double issuePrice) {
	super();
	this.name = name;
	this.issn = issn;
	this.frequency = frequency;
	this.issuePrice = issuePrice;
	subscriptions=new ArrayList<>();
}



public String getName() {
	return name;
}


public String getIssn() {
	return issn;
}


public int getFrequency() {
	return frequency;
}


public double getIssuePrice() {
	return issuePrice;
}

public void addSubscription(Subscription s) {
	if (subscriptions.contains(s)) {
        System.out.println(s.getSubscriber().getName() + " adlı üye zaten kayıtlı");
    } else {
        subscriptions.add(s);
        System.out.println(s.getSubscriber().getName() + " adlı üye eklendi");
    }
}


public ArrayList<Subscription> getSubscriptions() {
	return subscriptions;
}
@Override
public String toString() {
    return "Journal:"+ issn ;
}
}
