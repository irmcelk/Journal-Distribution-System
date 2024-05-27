package proje;

import java.io.Serializable;

public class Subscription  implements Serializable
{
private static final long serialVersionUID = 1L;
  private final DateInfo dates;
  private PaymentInfo payment;
  private int copies;
  private final Journal journal;
  private final Subscriber subscriber;
public Subscription(DateInfo dates, PaymentInfo payment, int copies, Journal journal, Subscriber subscriber) {
	super();
	this.dates = dates;
	this.payment = payment;
	this.copies = copies;
	this.journal = journal;
	this.subscriber = subscriber;
}
public void setCopies(int copies) {
	this.copies = copies;
}
public DateInfo getDates() {
	return dates;
}
public PaymentInfo getPayment() {
	return payment;
}
public int getCopies() {
	return copies;
}
public Journal getJournal() {
	return journal;
}
public Subscriber getSubscriber() {
	return subscriber;
}
public void acceptPayment(double amount) {
	
   payment.increasePayment(amount);
   System.out.println("Ödeme yapıldı.Güncel yapılan ödeme:"+payment.getReceivedPayment());
}
public boolean isSubscriptionActive(int issueMonth) {
    // Get the start and end months from the DateInfo
    int subscriptionStartMonth = dates.getStartMonth();
    int subscriptionEndMonth = dates.getEndMonth();
    int subscriptionStartYear = dates.getStartYear();
    if (subscriptionStartMonth <subscriptionEndMonth) {
        // Subscription within the same year
        return subscriptionStartMonth <= issueMonth && subscriptionEndMonth >= issueMonth;
    } else {
        // Subscription spans two years
        return (subscriptionStartMonth <= issueMonth && 12 >= issueMonth) ||
               (1 <= issueMonth && subscriptionEndMonth >= issueMonth);
    }
}

public boolean canSend(int issueMonth) {
    double requiredPayment = journal.getIssuePrice() * copies;
     if(!isSubscriptionActive(issueMonth)) {
    	 System.out.println(subscriber.getName() + " üyesi için aktif üyelik yok\n" +
    	            "Dergi gönderilmeyecektir.");
    	 return false;
     }
     else if (payment.getReceivedPayment() >= requiredPayment) {
        System.out.println(requiredPayment+" miktarında ödeme yapıldı. " + subscriber.getName() + " üyesine " + copies + " adet " +
                journal.getName() + " dergisi gönderilecektir.");
   	 System.out.println("Güncel alınan ödeme="+payment.getReceivedPayment());
   	 payment.setReceivedPayment(payment.getReceivedPayment() -requiredPayment);
   	System.out.println("Güncel kalan bakiye="+payment.getReceivedPayment());
        return true;
    }
     else {
    	 System.out.println("Güncel alınan ödeme="+payment.getReceivedPayment() +"\nGereken ödeme="+requiredPayment);
    System.out.println(subscriber.getName() + " üyesi için yeterli ödeme yapılmamıştır\n" +
            "Dergi gönderilmeyecektir.");
    return false;
}
}
@Override
public String toString() {
	return "Subscription []";
}


}
