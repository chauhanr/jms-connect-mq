# Instructions to run the JMS Connect MQ Project 

## Prerequisites 

*  The Project has been tested with IBM MQ Series 9.1 and the following things were set up on the MQ Series 
	* A channel by the name `JAVA.CLIENT.CHANNEL` if that is not the case then change the channel in application.properties file 
	* There are two queues that are already set up 	`myinput-queue` and `myoutput.queue`. These names can be changed if the queues are named differently. 

* To run the app you can do the following
	* Go to the home folder of this application and type `mvn spring-boot:run`. Ensure that you have maven installed as well as have internet access. 
	* Once the project starts we need send a message to the application to see it work. 


## Testing the project for correctness

Send a POST request to the URL `http://localhost:8085/message` with the following headers: 
* Content-Type: "text/xml"
* body as shown below which is an XML 

```
<employee>
	<name>Ritesh</name>
	<age>30</age>
	<address>address1</address>
</employee>
```  

When you send the message to the application you will have to follow the message in the logs as it flows in the following sequeunce: 
1. published on myinput.queue 
2. Consumed by the ReceiveProcessor when then passes it to the ReceiveEnricher(transformer) this will unmarshall the message too. 
3. Once the message is processed by the transformer the ReceiveEnricher will return it back to the apache camel which will then publish to the myoutput-queue 
4. Finally the message is consumed by the End Processor to complete the flow.