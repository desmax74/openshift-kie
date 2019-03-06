package org.kie.quickstart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kie.quickstart.pubsub.model.MyEvent;

//For demo purpose
public class MyEventProducerApp {

    public static void main (String [] args) {
        businessLogic(100);
    }

    public static void businessLogic(Integer eventNumber){
        ProducerController producerController = new ProducerController();
        List<MyEvent> events = new ArrayList<>();
        for(int i=0; i < eventNumber; i++) {
            events.add(new MyEvent("ID-" + UUID.randomUUID().toString(), "Name-" + UUID.randomUUID().toString()));
        }
        producerController.create(events);
    }

}
