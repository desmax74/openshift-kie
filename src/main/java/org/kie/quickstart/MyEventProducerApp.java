package org.kie.quickstart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kie.quickstart.pubsub.model.MyEvent;

//For demo
public class MyEventProducerApp {

    public static void main (String [] args) {
        ProducerController producerController = new ProducerController();

        List<MyEvent> events = new ArrayList<>();
        for(int i=0; i < 100; i++) {
            events.add(new MyEvent("ID-" + UUID.randomUUID().toString(), "Name-" + UUID.randomUUID().toString()));
        }
        producerController.create(events);


    }

}
