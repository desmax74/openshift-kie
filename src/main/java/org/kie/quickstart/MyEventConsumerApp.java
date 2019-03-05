package org.kie.quickstart;

public class MyEventConsumerApp {

    public static void main (String [] args) {
        ConsumerController consumerController = new ConsumerController();
        consumerController.consumeEvents(3, "group-1", -1, 10);
    }
}
