package org.kie.quickstart;

//For demo purpose
public class MyEventConsumerApp {

    public static void main (String [] args) {
        businessLogic();
    }

    public static void businessLogic(){
        ConsumerController consumerController = new ConsumerController();
        consumerController.consumeEvents(3, "group-1", -1, 10);
    }
}
