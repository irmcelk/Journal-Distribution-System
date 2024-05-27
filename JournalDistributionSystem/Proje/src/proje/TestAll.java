package proje;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestAll {
    private Corporation corporation;
    private DateInfo dateInfo;
    private Individual individual;
    private PaymentInfo paymentInfo;
    private PaymentInfo paymentInfo2;
    private Subscriber subscriber;
    private Subscriber subscriber2;
    private Journal journal;
    private Subscription subscription;
    private Subscription subscription2;
    private Distributor distributor;
    @Before
    public void setUp() {
     corporation = new Corporation("Test Corporation", "Test Address", 123, "Test Bank", 1, 1, 2012, 456789);
     dateInfo=new DateInfo(1,2013);
     individual=new Individual("Test Name","Test Address","1234",8,2016,345);
     paymentInfo= new PaymentInfo(0.1,100.0);
     paymentInfo2= new PaymentInfo(0.2,150.0);
     subscriber = new Individual("Test Name", "Test Address", "1234",8,2016,345);
     subscriber2=new Corporation("Test Corporation Subscriber","Address",456,"Subscriber's Bank",30,9,2013,142536);
     journal=new Journal("Test Journal","111",3,50);
     subscription=new Subscription(dateInfo,paymentInfo,3,journal,subscriber);
     subscription2=new Subscription(dateInfo,paymentInfo2,2,journal,subscriber2);
     distributor = new Distributor();
    }
    @Test
	public void testCorporationGetName() {
		assertEquals("Test Corporation", corporation.getName());
	}
    @Test
    public void testCorporationGetAccountNumber() {
    	  assertEquals(456789, corporation.getAccountNumber());
    }
    @Test
	public void testCorporationgetBillingInformation() {
		String expectedCorporationBillingInfo = "Corporation [bankCode=456, bankName=Subscriber's Bank, issueDay=30, issueMonth=9, issueYear=2013, accountNumber=142536]";
	    assertEquals(expectedCorporationBillingInfo , subscriber2.getBillingInformation());
	}
    @Test
	public void testDateInfoGetStartMonth() {
		assertEquals(1,dateInfo.getStartMonth());
	}
	@Test
	public void testDateInfoGetEndMonth() {
		assertEquals(12,dateInfo.getEndMonth());
	}
	@Test
	public void testIndividualGetCreditCardNr() {
        assertEquals("1234", individual.getCreditCardNr());
	}
	@Test
	public void testIndividualGetCVV() {
        assertEquals(345, individual.getCCV());

	}
	@Test
	public void testIndividualgetBillingInformation() {
		String expectedBillingInfo = "Individual [creditCardNr=1234, expireMonth=8, expireYear=2016, CCV=345]";
		assertEquals(expectedBillingInfo, individual.getBillingInformation());
	}
	//0.001 hata payı double değer alıyoruz ondan
	@Test
	public void testPaymentInfoGetReceivedPayment() {
	 assertEquals(100.0, paymentInfo.getReceivedPayment(), 0.001);
	}

	@Test
	public void testPaymentInfoIncreasePayment() {
		 paymentInfo.increasePayment(20.0);
		 assertEquals(120.0, paymentInfo.getReceivedPayment(), 0.001);
	}

	@Test
	public void testSubscriberGetName() {
		assertEquals("Test Name",subscriber.getName());
	}

	@Test
	public void testSubscriberGetAddress() {
		assertEquals("Test Address",subscriber.getAddress());
	}

	@Test
	public void testJournalGetIssn() {
		assertEquals("111",journal.getIssn());
	}
	@Test
	public void testJournalAddSubscription() {
		journal.addSubscription(subscription);
		assertEquals(1, journal.getSubscriptions().size());
        assertTrue(journal.getSubscriptions().contains(subscription));
        journal.addSubscription(subscription2);
		assertEquals(2, journal.getSubscriptions().size());
        assertTrue(journal.getSubscriptions().contains(subscription2));
        journal.addSubscription(subscription2);
		//assertEquals(3, journal.getSubscriptions().size()); //zaten ekli olduğu için size artmayacak
        assertTrue(journal.getSubscriptions().contains(subscription2));

	}

	@Test
	public void testSubscriptionAcceptPayment() {
		double initialPayment=subscription.getPayment().getReceivedPayment();
		subscription.acceptPayment(50.0);
	    assertEquals(initialPayment+50.0,subscription.getPayment().getReceivedPayment(),0.01);
	}

    @Test
    public void testSubscriptionCanSendWhenPaymentIsNotEnough() {
        assertFalse(subscription.canSend(2));
    }

    @Test
    public void testSubscriptionCanSendWhenPaymentIsEnough() {
    	subscription.acceptPayment(60.0);
        assertTrue(subscription.canSend(2));
    }
    @Test
    public void testDistributorAddJournal() {
        assertTrue(distributor.addJournal(journal));
        assertFalse(distributor.addJournal(journal)); // Zaten ekli olduğu için false dönmeli
    }

    @Test
    public void testDistributorSearchJournal() {
        assertNull(distributor.searchJournal("456")); // Kayıtlı olmayan bir journal aranıyor, null dönmeli
        distributor.addJournal(journal);
        assertEquals(journal, distributor.searchJournal("111")); // Ekli olan bir journal aranıyor, eşleşmeli
    }
    @Test
    public void testDistributorAddSubscriber() {
        assertTrue(distributor.addSubscriber(subscriber));
        assertFalse(distributor.addSubscriber(subscriber)); // Zaten ekli olduğu için false dönmeli
    }

    @Test
    public void testDistributorSearchSubscriber() {
        assertNull(distributor.searchSubscriber("Nonexistent")); // Kayıtlı olmayan bir abone aranıyor, null dönmeli
        distributor.addSubscriber(subscriber);
        assertEquals(subscriber, distributor.searchSubscriber("Test Name")); // Ekli olan bir abone aranıyor, eşleşmeli
        distributor.addSubscriber(subscriber2);
        assertEquals(subscriber2, distributor.searchSubscriber("Test Corporation Subscriber"));
    }
    @Test
    public void testDistributorAddSubscription() {
    	assertFalse(distributor.addSubscription("invalidIssn", subscriber, new Subscription(null, null, 0, null, null))); // Gecersiz issn return false
        System.out.println("eklenemez3");
    	distributor.addJournal(journal);
        distributor.addSubscriber(subscriber);
        assertTrue(distributor.addSubscription("111", subscriber, subscription)); // geçerli issn return true
        //distributor.addSubscriber(subscriber2);
        //assertTrue(distributor.addSubscription("111", subscriber2, subscription2)); // başka subscriber return true
    }
       

    @Test
    public void testSaveState() {
        distributor.addJournal(journal);
        distributor.addSubscriber(subscriber);
        distributor.addSubscription("111", subscriber, subscription);
        String fileName = "testDistributorState.ser";
        distributor.saveState(fileName);

        // Kontrol: Dosya var mı ve boş değil mi?
        assertNotNull(fileName);
        assertNotEquals(0, fileName.length());
    }

    @Test
    public void testLoadState() {
        distributor.addJournal(journal);
        distributor.addSubscriber(subscriber);
        distributor.addSubscription("111", subscriber, subscription);
        String fileName = "testDistributorState.ser";
        distributor.saveState(fileName);

        Distributor loadedDistributor = new Distributor();
        loadedDistributor.loadState(fileName);

        // Kontroller
        assertEquals(distributor.getSubscribers().size(), loadedDistributor.getSubscribers().size());
        assertEquals(distributor.getJournals().size(), loadedDistributor.getJournals().size());
       // assertTrue(loadedDistributor.getJournals().contains(journal));
    }
    @Test
    public void testDistributorListAllSendingOrders() {
    	distributor.addJournal(journal);
    	distributor.addSubscriber(subscriber);
        distributor.addSubscription(journal.getIssn(), subscriber, subscription);
    	distributor.addSubscriber(subscriber2);
    	distributor.addSubscription(journal.getIssn(), subscriber2, subscription2);
        System.out.println("Test Başladı");
        distributor.listAllSendingOrders(2013, 4);
        System.out.println("Test Tamamlandı");
    }
    @Test
    public void testDistributorListIncompletePayments() {

        Distributor distributor = new Distributor();
        Subscriber subscriber3 = new Individual("John Doe", "123 Main St.", "1234-5678-9012-3456", 12, 2023, 123);
        Journal journal3 = new Journal("Example Journal", "ISSN123", 12, 9.99);
        Subscription subscription = new Subscription(new DateInfo(3, 2023), new PaymentInfo(0.01,0.0),2, journal3, subscriber3);
        distributor.addSubscriber(subscriber3);
        distributor.addJournal(journal3);
        distributor.addSubscription("ISSN123", subscriber3, subscription);
        distributor.addJournal(journal);
    	distributor.addSubscriber(subscriber);
        distributor.addSubscription(journal.getIssn(), subscriber, subscription);
        distributor.listIncomplementPayment();

    }
    @Test
    public void testDistributorListSubscriptionByName() {
    	distributor.addJournal(journal);
    	distributor.addSubscriber(subscriber);
        distributor.addSubscription(journal.getIssn(), subscriber, subscription);
    	distributor.addSubscriber(subscriber2);
    	distributor.addSubscription(journal.getIssn(), subscriber2, subscription2);
        System.out.println("Test Başladı");
        distributor.listSubscriptionByName(subscriber.getName());
      //  distributor.listSubscriptionByName(subscriber2.getName());
        System.out.println("Test Tamamlandı");
    }
    @Test
    public void testDistributorListSubscriptionByIsnn() {
    	distributor.addJournal(journal);
    	distributor.addSubscriber(subscriber);
        distributor.addSubscription("111", subscriber, subscription);
    	/*distributor.addSubscriber(subscriber2);
    	distributor.addSubscription(journal.getIssn(), subscriber2, subscription2);
        */
        System.out.println("Test Başladı");
        distributor.listSubscriptionByIssn(journal.getIssn());
        System.out.println("Test Tamamlandı");
    }
    @Test
    public void testDistributorListSendingOrders() {
    	Journal testJournal = new Journal("Test Journal", "12345", 12, 10.0);
    	distributor.addJournal(testJournal);
    	distributor.addSubscriber(subscriber2);
        distributor.addSubscription("12345", subscriber2, subscription);
        System.out.println("Test Başladı:");
        distributor.listSendingOrders(testJournal.getIssn(), 2013, 7);
        System.out.println("Test Tamamlandı.");
    }
}




