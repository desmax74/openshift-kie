package org.kie.quickstart;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.PartitionInfo;

//For demo purpose
public class MyEventConsumerApp {

    public static void main (String [] args) {
        businessLogic(3);
    }

    public static void businessLogic(Integer consumerNumber){
        ConsumerController consumerController = new ConsumerController();
        consumerController.consumeEvents(consumerNumber, "group-1", -1, 10);
    }

    public static Map<String, List<PartitionInfo>> getTopics(){
        ConsumerController consumerController = new ConsumerController();
        return consumerController.getTopics();
    }
}
