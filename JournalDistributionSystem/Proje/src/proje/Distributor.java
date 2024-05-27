package proje;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Distributor  implements Serializable  {
	private static final long serialVersionUID = 1L;
	 private Hashtable<String, Journal> journals = new Hashtable<>();
	 private Vector<Subscriber> subscribers = new Vector<>();
	 private Hashtable<String, Vector<Subscription>> subscriberSubscriptions = new Hashtable<>();
	 private Hashtable<String, Vector<Subscription>> issnSubscriptions = new Hashtable<>();
    //issn ve subscriber üstünden subscriptiona ulaşmak için

	 public boolean addJournal(Journal j) {
		 String key = j.getIssn();
	        if (journals.containsKey(key)) {
	            System.out.println("Bu numaralı (" + key + ") bir journal zaten kayıtlı.");
	            return false;
	        }

	        journals.put(key, j);
	        System.out.println("Journal eklendi: " + j.getIssn());
		 return true;
	 }


	 public Journal searchJournal(String issn) {
	        if (journals.containsKey(issn)) {
	            System.out.println("Bu (" + issn + ") numara bir journal ile ilişkilendirilmiş.");
	            return journals.get(issn);
	        }
	      else
	    System.out.println("Journal bulunamadı: " +issn );
		 return null;

	 }


	 public boolean addSubscriber(Subscriber s) {
		    for (Subscriber subscriber : subscribers) {
		        if (subscriber.getName().equals(s.getName())) {
		            System.out.println("Bu isimde bir abone zaten var: " + s.getName());
		            return false; // Aynı isimde abone bulundu
		        }
		    }

		    subscribers.add(s);
		    System.out.println("Abone eklendi: " + s.getName());
		    return true;
		}


	 public Subscriber searchSubscriber(String name) {
		 for (Subscriber subscriber : subscribers) {
	            if (subscriber.getName().equals(name)) {
	                System.out.println(name + " adlı kişinin üyeliği bulunmaktadır.");
	                return subscriber;
	            }
	        }

	        System.out.println("Üye bulunamadı: " + name);
	        return null;
	 }


	 public boolean addSubscription(String issn, Subscriber s,Subscription sub) {
		 Journal journal = searchJournal(issn);
	        if (journal != null && subscribers.contains(s)) {

	            Vector<Subscription> subscriptionsByName = subscriberSubscriptions.get(s.getName());
	            if (subscriptionsByName == null) {
	                subscriptionsByName = new Vector<>();
	                subscriberSubscriptions.put(s.getName(), subscriptionsByName);
	            }
	            subscriptionsByName.add(sub);
	            Vector<Subscription> subscriptionsByIssn = issnSubscriptions.get(issn);
	            if (subscriptionsByIssn == null) {
	                subscriptionsByIssn = new Vector<>();
	                issnSubscriptions.put(issn, subscriptionsByIssn);
	            }
	            subscriptionsByIssn.add(sub);
	            System.out.println("Subscription oluşturuldu.");
	            return true;
	        } else {
	            System.out.println("Subscription oluşturma başarısız");
	            return false;
	        }
	    }

	 public Hashtable<String, Journal> getJournals() {
		return journals;
	}


	public Vector<Subscriber> getSubscribers() {
		return subscribers;
	}



	public void listAllSendingOrders(int year, int month) {
        System.out.println("Gönderilecek Tüm Siparişler:");
        for (Subscriber subscriber : subscribers) {
            System.out.println("Abone: " + subscriber.getName());
            Vector<Subscription> subscriptions = subscriberSubscriptions.get(subscriber.getName());
            if (subscriptions != null) {
                for (Subscription subscription : subscriptions) {
                    // Aboneliğin dergisi ve gönderim siparişi
                    Journal journal = subscription.getJournal();
                        if (subscription.canSend(month)) {
                            // Eğer gönderilebilirse, bilgileri yazdır
                            System.out.println(" - " + journal.getName() + " gönderilecek. " +
                                    "Kopya Sayısı: " + subscription.getCopies());
                        }
                }
            }
        }
    }
     public void listSendingOrders(String issn,int year,int month) {
    	 Journal journal = searchJournal(issn);

         if (journal != null) {
             System.out.println("Gönderilecek Siparişler (" + journal.getName() + " - " + issn + "):");

             for (Subscriber subscriber : subscribers) {
                 Vector<Subscription> subscriptions = subscriberSubscriptions.get(subscriber.getName());
                 if (subscriptions != null) { // null kontrolü ekledik
                     for (Subscription subscription : subscriptions) {
                         if (subscription != null && subscription.getJournal() != null && subscription.getJournal().getIssn().equals(issn) && subscription.canSend(month)) {
                             // Abonelik gönderilebilirse, bilgileri yazdır
                             System.out.println("Abone: " + subscriber.getName() +
                                     ", Kopya Sayısı: " + subscription.getCopies());
                         }
                     }
                 }
             }
         } else {
             System.out.println("Dergi bulunamadı: " + issn);
         }
     }
     public void listIncomplementPayment() {
    	 System.out.println("Eksik Ödemeler:");

    	    for (Subscriber subscriber : subscribers) {
    	        Vector<Subscription> subscriptions = subscriberSubscriptions.get(subscriber.getName());
    	        if (subscriptions != null) {
    	            for (Subscription subscription : subscriptions) {
    	                if (!subscription.canSend(subscription.getDates().getEndMonth())) {
    	                	double totalPayment=subscription.getJournal().getIssuePrice()*subscription.getJournal().getFrequency()*subscription.getCopies();
    	                    double remainingPayment =totalPayment- subscription.getPayment().getReceivedPayment();
    	                    if(remainingPayment>0) {
    	                    System.out.println("Abone: " + subscriber.getName() +
    	                            ", Abonelik: " + subscription.getJournal().getName() +
    	                            ", Eksik Ödeme: " + remainingPayment + " TL");
    	                }
    	            }
    	        }
    	    }
     }
    }
     public void listSubscriptionByName(String subscriberName) {
    	 System.out.println("List Subscription By Name");
    	 if (subscriberSubscriptions.containsKey(subscriberName)) {
    	        Vector<Subscription> subscriptions = subscriberSubscriptions.get(subscriberName);
             
    	        System.out.println("Subscriptions for " + subscriberName + ":");

    	        for (Subscription subscription : subscriptions) {
    	            System.out.println("    Journal: " + subscription.getJournal().getName());

    	        }
    	    } else {
    	        System.out.println("Subscriber not found: " + subscriberName);
    	    }

	 }
     public void listSubscriptionByIssn(String issn) {
    	 System.out.println("List Subscription By ISSN");
    	 if (issnSubscriptions.containsKey(issn)) {
 	        Vector<Subscription> subscriptions = issnSubscriptions.get(issn);
 	        System.out.println("Subscriptions for " + issn + ":");

 	        for (Subscription subscription : subscriptions) {
 	            System.out.println("  Subscriber: " + subscription.getSubscriber().getName());

 	        }
 	    } else {
 	        System.out.println("Subscriber not found: " + issn);
 	    }

	 }

	public void saveState(String fileName) {
         try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
             outputStream.writeObject(this);
             outputStream.writeObject(journals);
             outputStream.writeObject(subscribers);
             System.out.println("Durum kaydedildi.");
         } catch (IOException e) {
             e.printStackTrace();
             System.err.println("Durum kaydedilemedi.");
         }
     }

     
     public void loadState(String fileName) {
         try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
        	 Object obj = inputStream.readObject();
             if (obj instanceof Distributor)//typecasting
            	{
                 Distributor loadedDistributor = (Distributor) obj;
                 this.journals = loadedDistributor.journals;
                 this.subscribers = loadedDistributor.subscribers;
                 System.out.println(journals);
                 System.out.println(subscribers);
                 System.out.println("Durum yüklendi.");
             }
         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
             System.err.println("Durum yüklenemedi.");
         }
     }
     public void report() {
         ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

         // Schedule the report to run periodically
         executorService.scheduleAtFixedRate(() -> {
             System.out.println("Generating report...");

             // Include logic for generating the report (subscriptions expiring after a given date)
             int currentMonth = 1;  // Örnek: şu anki ay
             int currentYear = 2024;  // Örnek: şu anki yıl
             int expirationMonth = currentMonth + 3;  // Örnek: 3 ay sonrası
             int expirationYear = currentYear;

             // Güncellenen metodu çağır
             listAllSendingOrders(currentMonth, currentYear);
             listAllSendingOrders(expirationMonth, expirationYear);

             // Include logic for other aspects of the report

             System.out.println("Report generated.");
         }, 0, 1, TimeUnit.DAYS);  // Her gün çalıştır, ihtiyaca göre ayarlayın
     }

 
}

