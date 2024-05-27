package proje;


import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main  {
	private static Corporation corporation;
	private static Individual individual;
	private static Journal journal;
	private static List<Subscription> subscriptions = new ArrayList<>();
	private static List<Corporation> corporations = new ArrayList<>();
	private static List<Individual> individuals = new ArrayList<>();
	private static List<Journal> journals = new ArrayList<>();
	    
	    
    public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> createAndShowGUI());
    /*	Distributor distributor = new Distributor();

        // Add journals
        Journal journal1 = new Journal("Journal 1", "ISSN123", 12, 9.99);
        Journal journal2 = new Journal("Journal 2", "ISSN124", 6, 144.99);

        distributor.addJournal(journal1);
        distributor.addJournal(journal2);

        // Add subscribers
        Subscriber subscriber1 = new Individual("John Doe", "Address 1", "1234567890123456", 12, 2025, 123);
        Subscriber subscriber2 = new Corporation("Company XYZ", "Bank of Business", 1, "Account123", 15, 5, 2023,12);

        distributor.addSubscriber(subscriber1);
        distributor.addSubscriber(subscriber2);

        // Add subscriptions
        Subscription subscription1 = new Subscription( new DateInfo(1, 2024),new PaymentInfo(0.1,50.0),1, journal1,subscriber1);

        Subscription subscription2 = new Subscription(new DateInfo(1, 2024),new PaymentInfo(0.1,45.0),2,journal2,subscriber2);

        distributor.addSubscription("ISSN123", subscriber1, subscription1);
        distributor.addSubscription("ISSN124", subscriber2, subscription2);
        distributor.addSubscription("ISSN123", subscriber1, subscription2);

        // Accept payments
        subscription1.acceptPayment(50.0);
        subscription2.acceptPayment(100.0);

        // Generate reports (assuming report method is already running in a separate thread)
        distributor.listAllSendingOrders(5, 2024);
        distributor.listIncomplementPayment();
        distributor.listSubscriptionByIssn("ISSN123");
       ;

        // Save and load state
        distributor.saveState("state.dat");

        // Simulate program exit and later restart
        System.out.println("Simulating program exit and restart...");

        // Load state
        Distributor loadedDistributor = new Distributor();
        loadedDistributor.loadState("state.dat");

        // Continue generating reports in the loaded distributor
        loadedDistributor.listAllSendingOrders(2, 2024);*/
    }

      
    

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("A Journal Distribution Information System");
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 480);
        ImageIcon image=new ImageIcon("ytü.png");
		frame.setIconImage(image.getImage());
		JPanel mainPanel = new JPanel(new BorderLayout());
		JList<Subscriber> list=new JList<>();
		DefaultListModel<Subscriber> model=new DefaultListModel<>();
		JList<Journal> list2=new JList<>();
		DefaultListModel<Journal> model2=new DefaultListModel<>();
		JList<Subscription> list3=new JList<>();
		DefaultListModel<Subscription> model3=new DefaultListModel<>();
		
        Distributor distributor = new Distributor();
        list.setModel(model);
        list2.setModel(model2);
        list3.setModel(model3);
        JLabel welcomeLabel = createLabel("Welcome to the Journal Distribution Information System");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
      
        JButton addJournal = new JButton("Add Journal");
        addJournal.setFont(new Font("Times New Roman", Font.BOLD, 12));
        addJournal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String journalName = JOptionPane.showInputDialog(frame, "Enter journal name:");
                 String journalIssn = JOptionPane.showInputDialog(frame, "Enter journal ISSN:");
                 int frequency =Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Frequency:"));
                 double issuePrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Issue Price:"));
                 Journal j = new Journal(journalName, journalIssn, frequency, issuePrice);
                 if( distributor.addJournal(j)) {
                	 journals.add(j);
                     model2.addElement(j);
                 }
                
             }
        });
        JButton searchJournal = new JButton("Search Journal");
        searchJournal.setFont(new Font("Times New Roman", Font.BOLD, 12));
        searchJournal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String journalIssn = JOptionPane.showInputDialog(frame, "Enter journal ISSN:");
					distributor.searchJournal(journalIssn);
            }       
        });
        

        JButton listJournals = new JButton("List Journals");
        listJournals.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listJournals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (journals.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No journals to list.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    StringBuilder journalList = new StringBuilder("List of Journals:\n");
                    for (Journal journal : journals) {
                        journalList.append("Journal issn:"+journal.getIssn()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, journalList.toString(), "List of Journals", JOptionPane.INFORMATION_MESSAGE);
                }
                 
             }
        });
        
        
        JButton addSubscriber= new JButton("Add Subscriber");
        addSubscriber.setFont(new Font("Times New Roman", Font.BOLD, 12));
        addSubscriber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String[] options = {"Corporation", "Individual"};
                 int result = JOptionPane.showOptionDialog(null, "Select subscriber type:", "Subscriber Type",
                         JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                 if (result == 0) {

                     System.out.println("Corporation selected");
                	 String corporationName = JOptionPane.showInputDialog(null, "Enter corporation name:");
                     String corporationAddress = JOptionPane.showInputDialog(null, "Enter corporation address:");
                     int bankCode = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter bank code:"));
                     String bankName = JOptionPane.showInputDialog(null, "Enter bank name:");
                     int issueDay = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter issue day:"));
                     int issueMonth = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter issue month:"));
                     int issueYear = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter issue year:"));
                     int accountNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter account number:"));
                     Corporation corporation = new Corporation(corporationName, corporationAddress, bankCode, bankName,
                             issueDay, issueMonth, issueYear, accountNumber);
                     if(distributor.addSubscriber(corporation)) {
                         corporations.add(corporation);
                         model.addElement(corporation);
                     }
                     
                 } else if (result == 1) {
                     System.out.println("Individual selected");
                	 String individualName = JOptionPane.showInputDialog(null, "Enter individual name:");
                     String individualAddress = JOptionPane.showInputDialog(null, "Enter individual address:");
                     String creditCardNumber = JOptionPane.showInputDialog(null, "Enter credit card number:");
                     int expireMonth = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter expire month:"));
                     int expireYear = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter expire year:"));
                     int ccv = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter CCV:"));

                     Individual individual = new Individual(individualName, individualAddress, creditCardNumber,
                             expireMonth, expireYear, ccv);
                     if(distributor.addSubscriber(individual)) {
                         individuals.add(individual); 
                         model.addElement(individual);
                     }
                     
                 }
             }
        });
     
        JButton searchSubscriber = new JButton("Search Subscriber");
        searchSubscriber.setFont(new Font("Times New Roman", Font.BOLD, 15));
        searchSubscriber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String subscriberName = JOptionPane.showInputDialog(frame, "Enter subscriber name:");
					distributor.searchSubscriber(subscriberName);
            }       
        });
        

		list.addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        Subscriber selectedSubscriber = list.getSelectedValue();
		        if (selectedSubscriber != null) {
		            if (selectedSubscriber instanceof Corporation) {
		                System.out.println("Selected Subscriber: Corporation - " + selectedSubscriber.getName());
		                corporation = (Corporation) selectedSubscriber;  // Seçilen aboneyi güncelle
		            } else if (selectedSubscriber instanceof Individual) {
		                System.out.println("Selected Subscriber: Individual - " + selectedSubscriber.getName());
		                individual = (Individual) selectedSubscriber;  // Seçilen aboneyi güncelle
		            }
		        }
		    }
		});
		
		list2.addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		       Journal selectedJournal = list2.getSelectedValue();
		        if (selectedJournal != null) {
		        	System.out.println("Selected Journal:"+selectedJournal.getIssn());
		          
		        }
		    }
		});
		
		list3.addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		       Subscription selectedSubscription = list3.getSelectedValue();
		        if (selectedSubscription != null) {
		        	System.out.println("Selected Subscription's Owner:"+selectedSubscription.getSubscriber().getName());
		          
		        }
		    }
		});
		
        JButton createSubscription = new JButton("Create Subscription");
        createSubscription.setFont(new Font("Times New Roman", Font.BOLD, 12));
        createSubscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int startMonth=Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Start Month:"));
            	int startYear=Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Start Year:"));
            	DateInfo dateInfo=new DateInfo(startMonth,startYear);
            	//double discountRatio=Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Discount Ratio:"));
            	double receivedPayment=Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Received Payment:"));
                PaymentInfo paymentInfo=new PaymentInfo(0.1,receivedPayment);
                int copies=Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Count of Copies:"));
                JOptionPane.showMessageDialog(frame, new JScrollPane(list2), "Journal List", JOptionPane.PLAIN_MESSAGE);
                Journal selectedJournal=list2.getSelectedValue();
                if(selectedJournal!=null) {
                	 JOptionPane.showMessageDialog(frame, new JScrollPane(list), "Subscriber List", JOptionPane.PLAIN_MESSAGE);
                	 Subscriber selectedSubscriber = list.getSelectedValue();
                	 if(selectedSubscriber!=null) {
                		 if(selectedSubscriber instanceof Corporation) {
                			 Subscription subscription=new Subscription(dateInfo,paymentInfo,copies,Main.journal,selectedSubscriber);
                             System.out.println("Corporation subscription has been generated.");
                             JOptionPane.showMessageDialog(null, "Corporation subscription has been generated", "Information", JOptionPane.INFORMATION_MESSAGE);
                             model3.addElement(subscription);
                		 }
                		 else if(selectedSubscriber instanceof Individual) {
                			 Subscription subscription=new Subscription(dateInfo,paymentInfo,copies,Main.journal,selectedSubscriber);
                             System.out.println("Individual subscription has been generated.");
                             JOptionPane.showMessageDialog(null, "Individual subscription has been generated", "Information", JOptionPane.INFORMATION_MESSAGE);
                             model3.addElement(subscription);
                		 }
                	 }
                
               
                	else {
                		JOptionPane.showMessageDialog(null, "No registered members found!", "Warning", JOptionPane.WARNING_MESSAGE);
                	}
              
                }
         
            else {
        		JOptionPane.showMessageDialog(null, "No registered journals found!", "Warning", JOptionPane.WARNING_MESSAGE);
        	}
            }
        });
        
        
        
        
        JButton addSubscriptions = new JButton("Add Subscriptions");
        addSubscriptions.setFont(new Font("Times New Roman", Font.BOLD, 12));
        addSubscriptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 JOptionPane.showMessageDialog(frame, new JScrollPane(list2), "Journals List", JOptionPane.PLAIN_MESSAGE);
                 Journal selectedJournal=list2.getSelectedValue();
                 if(selectedJournal!=null) {
                 	 JOptionPane.showMessageDialog(frame, new JScrollPane(list), "Subscribers List", JOptionPane.PLAIN_MESSAGE);
                 	 Subscriber selectedSubscriber = list.getSelectedValue();
                         if(selectedSubscriber!=null) {
                        	 JOptionPane.showMessageDialog(frame, new JScrollPane(list3), "Subscriptions List", JOptionPane.PLAIN_MESSAGE);
                        	 Subscription selectedSubscription = list3.getSelectedValue();
                             if(selectedSubscription!=null) {
                            	 distributor.addSubscription(selectedJournal.getIssn(), selectedSubscriber, selectedSubscription);
                                 JOptionPane.showMessageDialog(null, "Subscription has been added", "Information", JOptionPane.INFORMATION_MESSAGE);
                             }
                             else {
                          		JOptionPane.showMessageDialog(null, "No registered subscriptions found!", "Warning", JOptionPane.WARNING_MESSAGE);
                          	}
                         }
                         else {
                     		JOptionPane.showMessageDialog(null, "No registered members found!", "Warning", JOptionPane.WARNING_MESSAGE);
                     	}
                 }   
                 else {
              		JOptionPane.showMessageDialog(null, "No registered journals found!", "Warning", JOptionPane.WARNING_MESSAGE);
              	}
           
            }       
        });

        JButton saveState = new JButton("Save State");
        saveState.setFont(new Font("Times New Roman", Font.BOLD, 12));
        saveState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(frame, "Enter file name;");
            distributor.saveState(fileName);
            JOptionPane.showMessageDialog(null, "The file has been successfully saved", "Information", JOptionPane.INFORMATION_MESSAGE);
           
            }       
        });
        
        JButton loadState = new JButton("Load State");
        loadState.setFont(new Font("Times New Roman", Font.BOLD, 12));
        loadState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(frame, "Enter file name;");
            distributor.loadState(fileName);

            JOptionPane.showMessageDialog(null, "The file has been successfully loaded", "Information", JOptionPane.INFORMATION_MESSAGE);
           
            }       
        });
       

        JButton listSendingOrders= new JButton("List Sending Orders");
        listSendingOrders.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listSendingOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.listSendingOrders("1", 2024, 1);
            }
        });
        
        JButton listAllSendingOrders= new JButton("List All Sending Orders");
        listAllSendingOrders.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listAllSendingOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.listAllSendingOrders(2024, 1);
            }
        });
        
        JButton listSubscriptionsByIssn =new JButton("List Subscriptions By Issn");
        listSubscriptionsByIssn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listSubscriptionsByIssn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String journalIssn= JOptionPane.showInputDialog(frame, "Enter Journal ISSN:");
                distributor.listSubscriptionByIssn(journalIssn);
            }
        });
        
        JButton listSubscriptionsByName= new JButton("List Subscriptions By Name");
        listSubscriptionsByName.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listSubscriptionsByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = JOptionPane.showInputDialog(frame, "Enter Name:");
                distributor.listSubscriptionByName(name);
            }
        });
        
        JButton listIncomplementPayment= new JButton("List Incomplement Payment");
        listIncomplementPayment.setFont(new Font("Times New Roman", Font.BOLD, 12));
        listIncomplementPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distributor.listIncomplementPayment();
            }
        });
        JButton acceptPayments= new JButton("Accept Payment");
        acceptPayments.setFont(new Font("Times New Roman", Font.BOLD, 12));
        acceptPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(frame, new JScrollPane(list3), "Subscriptions List", JOptionPane.PLAIN_MESSAGE);
           	 Subscription selectedSubscription = list3.getSelectedValue();
                if(selectedSubscription!=null) {
                	double amount = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Payment Amount:"));
                	selectedSubscription.acceptPayment(amount);
                    JOptionPane.showMessageDialog(null, "Subscription's payment accepted", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
             		JOptionPane.showMessageDialog(null, "No registered subscriptions found!", "Warning", JOptionPane.WARNING_MESSAGE);
             	}
            }
        });
        JButton report = new JButton("Report");
        report.setFont(new Font("Times New Roman", Font.BOLD, 12));
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 ExecutorService executorService = Executors.newSingleThreadExecutor();
                 executorService.execute(() -> distributor.report());
                 executorService.shutdown();
            }       
        });
 
        buttonPanel.add(addJournal);
        buttonPanel.add(searchJournal);
        buttonPanel.add(listJournals);
        buttonPanel.add(addSubscriber);
        buttonPanel.add(searchSubscriber);
        buttonPanel.add(acceptPayments);
        buttonPanel.add(createSubscription);
        buttonPanel.add(addSubscriptions);
        buttonPanel.add(saveState);
        buttonPanel.add(loadState);
        buttonPanel.add(listAllSendingOrders);
        buttonPanel.add(listSendingOrders);
        buttonPanel.add(listSubscriptionsByIssn);
        buttonPanel.add(listSubscriptionsByName);
        buttonPanel.add(listIncomplementPayment);
        buttonPanel.add(report);
        
        mainPanel.add(welcomePanel, BorderLayout.PAGE_START);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setVisible(true);
    }
    

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        return label;
    }

    
}
