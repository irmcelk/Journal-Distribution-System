Problem Domain: You are writing software for a distributor company that sells journals to subscribers. 
Individuals pay for their subscription by credit card and corporations pay by bank cheques. A subscriber 
can pay in partial amounts; therefore the payment that has been received so far is kept in a PaymentInfo 
object. A subscriber can subscribe to multiple copies of the same journal. A subscription continues for one 
year but it can begin in any month. For example, a subscription may begin in March 2023 and this means 
that it will end in February 2024 (inclusive). The ISSN is a unique identifier of a journal. The frequency of 
a journal denotes how many issues are published within a year. Each journal has an issue price but 
subscribers can be granted a discount ratio. 

Additional Information: 
addSubscription method: A subscription for a non-existent subscriber or journal must not be created. 
If a subscription object for the given subscriber and journal exists, its copies field will be increased by 
one in this method. 

listAllSendingOrders method: Lists which journals will be sent to which subscribers for a given month 
and year. We must not send journals that have not been paid for. You can use the result of the 
canSend method for this purpose. 

canSend method: Determines whether a particular issue of its journal can be sent to its subscriber. If 
enough payment has been received so far, the method returns true. The acceptPayment method 
does not interact with a subscriber; it just increases the received payment so far. 

saveState and loadState methods: These methods accept a file name and executes their namesake 
tasks on entire objects. They should wait for the report method to complete its tasks if it is running. 

All classes given in the UML class diagram are implemented.
GUI functionality has been added to the project.
Unit tests have also been added to the project. There are at least two jUnit test cases for each class shown as having member fields in the main UML class diagram.
